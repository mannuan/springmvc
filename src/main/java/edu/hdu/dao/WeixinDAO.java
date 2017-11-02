package edu.hdu.dao;

import edu.hdu.db.DbHelp;
import edu.hdu.pojo.Weixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeixinDAO {
    public Weixin getWeixinById(int id) {
        DbHelp dbHelp = new DbHelp();
        String SQL = "select * from weixin_info where id = ? ";
        List<Object> list = new ArrayList<>();
        list.add(id);
        Map<String, Object> map = dbHelp.findSimpleResult(SQL, list);
        if (map.isEmpty()) {
            return null;
        }
        Weixin weixin = new Weixin(id, map.get("title").toString(), map.get("time").toString(), map.get("public_name").toString(), map.get("main_body").toString(), map.get("spider_time").toString(), Integer.valueOf(map.get("indexed").toString()), map.get("url").toString());
        return weixin;
    }
}
