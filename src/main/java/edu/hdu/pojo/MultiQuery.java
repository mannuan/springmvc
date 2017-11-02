package edu.hdu.pojo;

public class MultiQuery {

    /**
     * query : {"multi_match":{"query":"xxxxxxx","type":"best_fields","fields":["title","abstract"]}}
     */

    private QueryBean_old query;

    public QueryBean_old getQuery() {
        return query;
    }

    public void setQuery(QueryBean_old query) {
        this.query = query;
    }
}
