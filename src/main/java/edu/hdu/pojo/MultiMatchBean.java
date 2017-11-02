package edu.hdu.pojo;

import java.util.List;

public class MultiMatchBean {
    /**
     * query : xxxxxxx
     * type : best_fields
     * fields : ["title","abstract"]
     */

    private String query;
    private String type;
    private List<String> fields;
    private String analyzer;
    private String operator;

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public String getOperator() {
        return operator;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}