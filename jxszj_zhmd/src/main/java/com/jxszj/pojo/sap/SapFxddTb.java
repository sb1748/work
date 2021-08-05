package com.jxszj.pojo.sap;

public class SapFxddTb {
    private String xspz;

    private String cjrq;

    private String ydrq;

    public String getXspz() {
        return xspz;
    }

    public void setXspz(String xspz) {
        this.xspz = xspz == null ? null : xspz.trim();
    }

    public String getCjrq() {
        return cjrq;
    }

    public void setCjrq(String cjrq) {
        this.cjrq = cjrq == null ? null : cjrq.trim();
    }

    public String getYdrq() {
        return ydrq;
    }

    public void setYdrq(String ydrq) {
        this.ydrq = ydrq == null ? null : ydrq.trim();
    }
}