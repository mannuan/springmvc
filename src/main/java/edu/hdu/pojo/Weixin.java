package edu.hdu.pojo;


/*
* 接收mysql返回微信的结构
* */
public class Weixin {
    private int id;
    private String title;
    private String time;
    private String public_name;
    private String main_body;
    private String spider_time;
    private int indexed;
    private String url;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getPublic_name() {
        return public_name;
    }

    public String getMain_body() {
        return main_body;
    }

    public String getSpider_time() {
        return spider_time;
    }

    public int getIndexed() {
        return indexed;
    }

    public String getUrl() {
        return url;
    }

    public Weixin(int id, String title, String time, String public_name, String main_body, String spider_time, int indexed, String url) {

        this.id = id;
        this.title = title;
        this.time = time;
        this.public_name = public_name;
        this.main_body = main_body;
        this.spider_time = spider_time;
        this.indexed = indexed;
        this.url = url;
    }
}
