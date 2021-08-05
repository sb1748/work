package com.jxszj.pojo.sap;

public class SapConditionCqTb {
    private Integer id;

    private String condiname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCondiname() {
        return condiname;
    }

    public void setCondiname(String condiname) {
        this.condiname = condiname == null ? null : condiname.trim();
    }
}