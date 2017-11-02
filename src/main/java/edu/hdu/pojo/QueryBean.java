package edu.hdu.pojo;

public class QueryBean {
    /**
     * query_string : {"fields":["content","name"],"query":"this AND that","analyzer":"standard"}
     */

    private QueryStringBean query_string;

    public QueryStringBean getQuery_string() {
        return query_string;
    }

    public void setQuery_string(QueryStringBean query_string) {
        this.query_string = query_string;
    }
}