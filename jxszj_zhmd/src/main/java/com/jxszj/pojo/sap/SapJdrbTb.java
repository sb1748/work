package com.jxszj.pojo.sap;

public class SapJdrbTb {
    private String jdrq;

    private String wlzbm;

    private String wlzmc;

    private Integer sl;

    private String ddbm;

    public String getJdrq() {
        return jdrq;
    }

    public void setJdrq(String jdrq) {
        this.jdrq = jdrq == null ? null : jdrq.trim();
    }

    public String getWlzbm() {
        return wlzbm;
    }

    public void setWlzbm(String wlzbm) {
        this.wlzbm = wlzbm == null ? null : wlzbm.trim();
    }

    public String getWlzmc() {
        return wlzmc;
    }

    public void setWlzmc(String wlzmc) {
        this.wlzmc = wlzmc == null ? null : wlzmc.trim();
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getDdbm() {
        return ddbm;
    }

    public void setDdbm(String ddbm) {
        this.ddbm = ddbm == null ? null : ddbm.trim();
    }
}