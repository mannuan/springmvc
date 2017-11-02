package edu.hdu.controller;

import com.google.gson.Gson;
import edu.hdu.dao.PaperDAO;
import edu.hdu.dao.WeixinDAO;
import edu.hdu.pojo.Paper;
import edu.hdu.pojo.Weixin;
import edu.hdu.utils.EsUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchController {
    @ResponseBody
    @RequestMapping(value = "/Test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String Test(@RequestParam() Integer id){
       return "www";
    }
    @RequestMapping(value = "/searcher")
    public String searcher(){

        return "searcher";
    }

    @RequestMapping("/search")
    public String search() {
        return "index";
    }

    @RequestMapping("/dosearch")
    @ResponseBody
    public void dosearch(String searchContent, PrintWriter printWriter) throws IOException {
        EsUtil esUtil = new EsUtil();
        String result = esUtil.doSearch(searchContent, "");
        Map<String, String> tmp = new HashMap<>();
        tmp.put("result", result);
        Gson gson = new Gson();
        printWriter.write(gson.toJson(tmp));
        System.out.println(gson.toJson(tmp));
        printWriter.flush();
        printWriter.close();
    }

    @RequestMapping(value = "/detail")
    public void showPageDetail(int id, String type, PrintWriter printWriter) {
        Gson gson = new Gson();
        if (type.equals("paper")) {
            PaperDAO paperDAO = new PaperDAO();
            Paper paper = paperDAO.getPaperById(id);
            String out = gson.toJson(paper, Paper.class);
            System.out.println(out);
            printWriter.write(out);
        } else if (type.equals("weixin")) {
            WeixinDAO weixinDAO = new WeixinDAO();
            Weixin weixin = weixinDAO.getWeixinById(id);
            String out = gson.toJson(weixin, Weixin.class);
            System.out.println(out);
            printWriter.write(out);
        }
        printWriter.flush();
        printWriter.close();
    }
}
