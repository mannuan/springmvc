package edu.hdu.dao;

import edu.hdu.db.DbHelp;
import edu.hdu.pojo.Paper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaperDAO {
    public Paper getPaperById(int id) {
        DbHelp dbHelp = new DbHelp();
        String SQL = "select * from wanfang where id = ? ";
        List<Object> list = new ArrayList<>();
        list.add(id);
        Map<String, Object> map = dbHelp.findSimpleResult(SQL, list);
        if (map.isEmpty()) {
            return null;
        }
        Paper paper = new Paper(id, map.get("title").toString(), map.get("abstract").toString(), map.get("author").toString(), map.get("unit").toString(), map.get("magezine").toString(), map.get("file_url").toString(), map.get("time").toString(), map.get("keyword").toString(), map.get("url").toString(), map.get("spider_time").toString(), Integer.valueOf(map.get("paper_flag").toString()), Integer.valueOf(map.get("indexed").toString()), map.get("file_path").toString());
        return paper;
    }
}
