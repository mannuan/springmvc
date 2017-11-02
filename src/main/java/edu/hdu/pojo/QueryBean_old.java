package edu.hdu.pojo;

public class QueryBean_old {
    /**
     * multi_match : {"query":"xxxxxxx","type":"best_fields","fields":["title","abstract"]}
     */

    private MultiMatchBean multi_match;

    public MultiMatchBean getMulti_match() {
        return multi_match;
    }

    public void setMulti_match(MultiMatchBean multi_match) {
        this.multi_match = multi_match;
    }
}
