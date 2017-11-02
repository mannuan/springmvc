package edu.hdu.utils;

import com.google.gson.Gson;
import edu.hdu.pojo.*;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EsUtil {

    public EsUtil() {
    }

    public String doSearch(String searchSentence, String docType) {
        ExtendWord extendWord = new ExtendWord("bolt://10.1.17.25:7687", "neo4j", "lab421");
        List<String> searchWords = new ArrayList<>();
        List<String> tmpWords = new ArrayList<>();
        searchWords = splitToWords(searchSentence);
        int length = searchWords.size();
        for (int i = 0; i < length; ++i) {
            tmpWords = extendWord.query_word(searchWords.get(i));
            searchWords.addAll(tmpWords);
        }
        System.out.println(searchWords);

        StringBuilder result = new StringBuilder();

        //        设置查询字段
        List<String> paperFields = new ArrayList<>();
        paperFields.add("abstract");
        paperFields.add("title");

        List<String> weixinFidlds = new ArrayList<>();
        weixinFidlds.add("title");
        weixinFidlds.add("main_body");

        List<WeixinResult.HitsBeanX.HitsBean.SourceBean> weixinList = new ArrayList<>();
        List<PaperResult.HitsBeanX.HitsBean.SourceBean> paperList = new ArrayList<>();

//        查询 语句在字段中的结果 先 AND 再 OR  type:most_fields
//        String papweAND = multiSearchPaper(searchWords, paperFields, "best_fields", "whitespace", "and");
//        String papweOR = multiSearchPaper(searchWords, paperFields, "best_fields", "whitespace", "or");

//        String weixinAND = multiSearchWeixin(searchWords, weixinFidlds, "best_fields", "whitespace", "and");

//        String weixinOR = multiSearchWeixin(searchWords, weixinFidlds, "best_fields", "whitespace", "or");

        String weixin = queryWeixin(searchWords, weixinFidlds, "whitespace");

        String paper = queryPaper(searchWords, paperFields, "whitespace");

//        WeixinResult tmpWeixinResult = getWeixinResult(weixinAND);
        WeixinResult tmpWeixinResult = getWeixinResult(weixin);
        List<ResultToPage> wxResult = new ArrayList<>();

        for (int i = 0; weixinList.size() < 10 && i < tmpWeixinResult.getHits().getHits().size(); i++) {
            if (!weixinList.contains(tmpWeixinResult.getHits().getHits().get(i).get_source())) {
                WeixinResult.HitsBeanX.HitsBean.SourceBean tmpWeixin = tmpWeixinResult.getHits().getHits().get(i).get_source();
                ResultToPage tmpanswer = new ResultToPage(tmpWeixin.getId(), tmpWeixin.getTitle().length() > 20 ? tmpWeixin.getTitle().substring(0, 20) + "..." : tmpWeixin.getTitle(), tmpWeixin.getMain_body().length() > 150 ? tmpWeixin.getMain_body().substring(0, 150) + "..." : tmpWeixin.getMain_body());
                weixinList.add(tmpWeixin);
                wxResult.add(tmpanswer);
            }
        }
//        tmpWeixinResult = getWeixinResult(weixinOR);
//
//        for (int i = 0; weixinList.size() < 10 && i < tmpWeixinResult.getHits().getHits().size(); i++) {
//            if (!weixinList.contains(tmpWeixinResult.getHits().getHits().get(i).get_source())) {
//                WeixinResult.HitsBeanX.HitsBean.SourceBean tmpWeixin = tmpWeixinResult.getHits().getHits().get(i).get_source();
//                ResultToPage tmpanswer = new ResultToPage(tmpWeixin.getId(), tmpWeixin.getTitle().length() > 20 ? tmpWeixin.getTitle().substring(0, 20) + "..." : tmpWeixin.getTitle(), tmpWeixin.getMain_body().length() > 150 ? tmpWeixin.getMain_body().substring(0, 150) + "..." : tmpWeixin.getMain_body());
//                weixinList.add(tmpWeixin);
//                wxResult.add(tmpanswer);
//            }
//        }

//        System.out.println(weixinList);

        PaperResult tmpPaperResult = getPaperResult(paper);

        List<ResultToPage> ppResult = new ArrayList<>();

        for (int i = 0; paperList.size() < 10 && i < tmpPaperResult.getHits().getHits().size(); i++) {
            if (!paperList.contains(tmpPaperResult.getHits().getHits().get(i).get_source())) {
                PaperResult.HitsBeanX.HitsBean.SourceBean tmpPaper = tmpPaperResult.getHits().getHits().get(i).get_source();
                ResultToPage tmpanswer = new ResultToPage(tmpPaper.getId(), tmpPaper.getTitle().length() > 20 ? tmpPaper.getTitle().substring(0, 20) + "..." : tmpPaper.getTitle(), tmpPaper.getAbstractX().length() > 150 ? tmpPaper.getAbstractX().substring(0, 150) + "..." : tmpPaper.getAbstractX());
                paperList.add(tmpPaper);
                ppResult.add(tmpanswer);
            }
        }

//        tmpPaperResult = getPaperResult(papweOR);
//        for (int i = 0; paperList.size() < 10 && i < tmpPaperResult.getHits().getHits().size(); i++) {
//            if (!paperList.contains(tmpPaperResult.getHits().getHits().get(i).get_source())) {
//                PaperResult.HitsBeanX.HitsBean.SourceBean tmpPaper = tmpPaperResult.getHits().getHits().get(i).get_source();
//                ResultToPage tmpanswer = new ResultToPage(tmpPaper.getId(), tmpPaper.getTitle().length() > 20 ? tmpPaper.getTitle().substring(0, 20) + "..." : tmpPaper.getTitle(), tmpPaper.getAbstractX().length() > 150 ? tmpPaper.getAbstractX().substring(0, 150) + "..." : tmpPaper.getAbstractX());
//                paperList.add(tmpPaper);
//                ppResult.add(tmpanswer);
//            }
//        }

//        System.out.println(weixinResult);
//        System.out.println(result.toString());

//        return result.toString();


        Gson gson = new Gson();

//        String wxanswer = gson.toJson(weixinList);
        String wxanswer = gson.toJson(wxResult);

        String ppanswer = gson.toJson(ppResult);
//        String answer = "{\n" +
//                "    \"0\": " + wxanswer + ",\n" +
//                "    \"1\": " + ppanswer + "\n" +
//                "}";
//        System.out.println("{\n" +
//                "    \"0\": "+wxanswer+",\n" +
//                "    \"1\": "+ppanswer+"\n" +
//                "}");

        String answer = "{\n" +
                "\"weixin\":" + wxanswer + ",\n" +
                "\"paper\":" + ppanswer + "\n" +
                "}";

        System.out.println(answer);
        return answer;
    }

    private List<String> splitToWords(String searchSentence) {
        List<String> searchWords = new ArrayList<>();
        Result parse = ToAnalysis.parse(searchSentence);

//        设置筛选词性
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");
            add("ns");
            add("nsf");
            add("t");
            add("v");
        }};

        List<Term> terms = parse.getTerms();

        for (int i = 0; i < terms.size(); i++) {
//            System.out.print(terms.get(i).getNatureStr() + " ");
            if (expectedNature.contains(terms.get(i).getNatureStr())) {
                searchWords.add(terms.get(i).getName());
            }
        }
        return searchWords;
    }

//    private String multiSearchPaper(List<String> stringList, List<String> fields, String type, String analyzer, String operator) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String word : stringList) {
//            stringBuilder.append(" ").append(word);
//        }
//
////        设置查询 语句,类型,分词器,操作符(AND | OR)
//        MultiMatchBean multiMatchBean = new MultiMatchBean();
//        multiMatchBean.setQuery(stringBuilder.toString());
//        multiMatchBean.setFields(fields);
//        multiMatchBean.setType(type);
//        multiMatchBean.setAnalyzer(analyzer);
//        multiMatchBean.setOperator(operator);
//
//        QueryBean_old queryBeanOld = new QueryBean_old();
//        queryBeanOld.setMulti_match(multiMatchBean);
//        MultiQuery multiQuery = new MultiQuery();
//        multiQuery.setQuery(queryBeanOld);
//
////        query.setQuery();
//        Gson gson = new Gson();
//        String output = "";
//        output = gson.toJson(multiQuery, MultiQuery.class);
//        System.out.println(output);
//
////        设置elasticsearch服务器
//        JestClientFactory factory = new JestClientFactory();
//        factory.setHttpClientConfig(new HttpClientConfig
//                .Builder("http://10.1.17.25:9200").multiThreaded(true)
//                .defaultMaxTotalConnectionPerRoute(2)
//                .maxTotalConnection(10).build());
//        JestClient client = factory.getObject();
//
//        Search search = new Search.Builder(output)
//                // multiple index or types can be added.
//                .addIndex("paper")
//                .addType("WanfangPaper")
//                .build();
//
//        SearchResult result = client.execute(search);
////        System.out.println(result.getTotal());
////        System.out.println(result.getJsonString());
//
//        return result.getJsonString();
//    }

//    private String multiSearchWeixin(List<String> stringList, List<String> fields, String type, String analyzer, String operator) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String word : stringList) {
//            stringBuilder.append(" ").append(word);
//        }
//
////        设置查询 语句,类型,分词器,操作符(AND | OR)
//        MultiMatchBean multiMatchBean = new MultiMatchBean();
//        multiMatchBean.setQuery(stringBuilder.toString());
//        multiMatchBean.setFields(fields);
//        multiMatchBean.setType(type);
//        multiMatchBean.setAnalyzer(analyzer);
//        multiMatchBean.setOperator(operator);
//
//        QueryBean_old queryBeanOld = new QueryBean_old();
//        queryBeanOld.setMulti_match(multiMatchBean);
//        MultiQuery multiQuery = new MultiQuery();
//        multiQuery.setQuery(queryBeanOld);
//
////        query.setQuery();
//        Gson gson = new Gson();
//        String output = "";
//        output = gson.toJson(multiQuery, MultiQuery.class);
//        System.out.println(output);
//
////        设置elasticsearch服务器
//        JestClientFactory factory = new JestClientFactory();
//        factory.setHttpClientConfig(new HttpClientConfig
//                .Builder("http://10.1.17.25:9200").multiThreaded(true)
//                .defaultMaxTotalConnectionPerRoute(2)
//                .maxTotalConnection(10).build());
//        JestClient client = factory.getObject();
//
//        Search search = new Search.Builder(output)
//                // multiple index or types can be added.
//                .addIndex("weixin")
//                .addType("OfficialAccount")
//                .build();
//
//        SearchResult result = client.execute(search);
////        System.out.println(result.getTotal());
////        System.out.println(result.getJsonString());
//
//        return result.getJsonString();
//    }

    private WeixinResult getWeixinResult(String result) {
        Gson gson = new Gson();
        WeixinResult tmpWeixinResult = gson.fromJson(result, WeixinResult.class);
//        System.out.println(tmpWeixinResult);
        return tmpWeixinResult;
    }

    private PaperResult getPaperResult(String result) {
        Gson gson = new Gson();
        PaperResult tmpPaperResult = gson.fromJson(result, PaperResult.class);
        return tmpPaperResult;
    }

    private String queryWeixin(List<String> stringList, List<String> fields, String analyzer) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : stringList) {
            stringBuilder.append(" ").append(word);
        }

//        构造 query_string 体
        QueryStringBean queryStringBean = new QueryStringBean(stringBuilder.toString(), analyzer, fields);
        QueryBean queryBean = new QueryBean();
        queryBean.setQuery_string(queryStringBean);
        Query query = new Query();
        query.setQuery(queryBean);

        Gson gson = new Gson();
        String output = "";
        output = gson.toJson(query, Query.class);
        System.out.println(output);

//        设置elasticsearch服务器
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://10.1.17.25:9200").multiThreaded(true)
                .defaultMaxTotalConnectionPerRoute(2)
                .maxTotalConnection(10).build());
        JestClient client = factory.getObject();

        Search search = new Search.Builder(output)
                // multiple index or types can be added.
                .addIndex("weixin")
                .addType("OfficialAccount")
                .build();

        SearchResult result = null;
        try {
            result = client.execute(search);
        } catch (IOException e) {
            System.out.println("queryPaper IOException!");
            e.printStackTrace();
        }
//        System.out.println(result.getTotal());
//        System.out.println(result.getJsonString());

        return result.getJsonString();
    }

    private String queryPaper(List<String> stringList, List<String> fields, String analyzer) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : stringList) {
            stringBuilder.append(" ").append(word);
        }

//        构造 query_string 体
        QueryStringBean queryStringBean = new QueryStringBean(stringBuilder.toString(), analyzer, fields);
        QueryBean queryBean = new QueryBean();
        queryBean.setQuery_string(queryStringBean);
        Query query = new Query();
        query.setQuery(queryBean);

        Gson gson = new Gson();
        String output = "";
        output = gson.toJson(query, Query.class);
        System.out.println(output);

//        设置elasticsearch服务器
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://10.1.17.25:9200").multiThreaded(true)
                .defaultMaxTotalConnectionPerRoute(2)
                .maxTotalConnection(10).build());
        JestClient client = factory.getObject();

        Search search = new Search.Builder(output)
                // multiple index or types can be added.
                .addIndex("paper")
                .addType("WanfangPaper")
                .build();

        SearchResult result = null;
        try {
            result = client.execute(search);
        } catch (IOException e) {
            System.out.println("queryPaper IOException!");
            e.printStackTrace();
        }
//        System.out.println(result.getTotal());
//        System.out.println(result.getJsonString());

        return result.getJsonString();
    }

}