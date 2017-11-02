package edu.hdu.pojo;

import java.util.List;

public class QueryStringBean {
    /**
     * fields : ["content","name"]
     * query : this AND that
     * analyzer : standard
     */

    private String query;
    private String analyzer;
    private List<String> fields;

    public QueryStringBean() {
    }

    public QueryStringBean(String query, String analyzer, List<String> fields) {
        this.query = query;
        this.analyzer = analyzer;
        this.fields = fields;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
