package edu.hdu.pojo;

public class Query {

    /**
     * query : {
     * "query_string":{
     * "fields":["content","name"],
     * "query":"this AND that",
     * "analyzer":"standard"
     * }
     * }
     */

    private QueryBean query;

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }
}
