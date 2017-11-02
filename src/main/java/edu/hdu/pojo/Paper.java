package edu.hdu.pojo;

import com.google.gson.annotations.SerializedName;


/*
* 接收mysql返回论文的结构
* */
public class Paper {
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public String getAuthor() {
        return author;
    }

    public String getUnit() {
        return unit;
    }

    public String getMagezine() {
        return magezine;
    }

    public String getFile_url() {
        return file_url;
    }

    public String getTime() {
        return time;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getUrl() {
        return url;
    }

    public String getSpider_time() {
        return spider_time;
    }

    public int getPaper_flag() {
        return paper_flag;
    }

    public int getIndexed() {
        return indexed;
    }

    public String getFile_path() {
        return file_path;
    }

    private int id;
    private String title;
    @SerializedName("abstract")
    private String abstractX;
    private String author;
    private String unit;
    private String magezine;
    private String file_url;
    private String time;
    private String keyword;
    private String url;
    private String spider_time;
    private int paper_flag;
    private int indexed;
    private String file_path;

    public Paper(int id, String title, String abstractX, String author, String unit, String magezine, String file_url, String time, String keyword, String url, String spider_time, int paper_flag, int indexed, String file_path) {
        this.id = id;
        this.title = title;
        this.abstractX = abstractX;
        this.author = author;
        this.unit = unit;
        this.magezine = magezine;
        this.file_url = file_url;
        this.time = time;
        this.keyword = keyword;
        this.url = url;
        this.spider_time = spider_time;
        this.paper_flag = paper_flag;
        this.indexed = indexed;
        this.file_path = file_path;
    }
}
