package edu.hdu.utils;

import edu.hdu.pojo.WordEntry;
import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExtendWord {
    private final Driver driver;
//    private final Word2VEC vec;
    // private static final String bin_file = "/Users/tenghaibin/py_word2vec_test_result.bin";
//    public ExtendWord(String uri, String user, String password, String word2vec_bin) {
    public ExtendWord(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
//        vec = new Word2VEC();
//        try {
//            vec.loadGoogleModel(word2vec_bin);
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
    }

    public ArrayList<String> query_word(String word){

        ArrayList<String> word2vec_result = fuzzy_search(word);
        ArrayList<String> result_list = new ArrayList<String>();
        for(String str: word2vec_result){
            result_list.addAll(neo4j_search(str));
        }
        return result_list;
    }
    private ArrayList<String> neo4j_search(String word){
//        String word = words[0];
        ArrayList<String> result_list = new ArrayList<String>();

        try(Session sess = driver.session()){
            final String order = String.format("MATCH (n)-[r]->(nxt) WHERE n.name = \"%s\" and r.type = \"part_of\" RETURN nxt.name as name", word);
            List<Record> res = sess.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction tx) {
                    return tx.run(order).list();
                }
            });

            for(Record record : res){
                result_list.add(record.get("name").toString().replaceAll("\"",""));
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return result_list;
    }
    private ArrayList<String> fuzzy_search(String word){
        //ArrayList<String> result;
//        Set<WordEntry> result_set = vec.distance(word);
        ArrayList<String> result = new ArrayList<String>();
        result.add(word);
//        for(WordEntry we: result_set){
//            result.add(we.name);
//        }
//        return result;
        return result;
    }

//    public static void main(String argv[]){
//        String bin_file_path;
//        if(argv.length < 1){
//            bin_file_path = "/Users/tenghaibin/Downloads/word2vec-master/vectors.bin";
//        }
//        else {
//            bin_file_path = argv[0];
//        }
//        ExtendWord ew = new ExtendWord("bolt://10.1.17.25:7687", "neo4j", "lab421");
//
//        ArrayList<String> als = ew.query_word("杭州市");
//        for(String str : als){
//            System.out.println(str);
//        }
//    }
}
