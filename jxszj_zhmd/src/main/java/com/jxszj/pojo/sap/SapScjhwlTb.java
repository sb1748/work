package com.jxszj.pojo.sap;

public class SapScjhwlTb {
    private String gzrq;

    private String wlzbm;

    private String wlzmc;

    private Integer sl;

    public String getGzrq() {
        return gzrq;
    }

    public void setGzrq(String gzrq) {
        this.gzrq = gzrq == null ? null : gzrq.trim();
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
}