package com.jxszj.pojo.sap;

public class SapXsddpdscTb {
    private String pdrq;

    private String wlz;

    private Integer xdsl;

    private Integer jhmb;

    private Integer rksl;

    public String getPdrq() {
        return pdrq;
    }

    public void setPdrq(String pdrq) {
        this.pdrq = pdrq == null ? null : pdrq.trim();
    }

    public String getWlz() {
        return wlz;
    }

    public void setWlz(String wlz) {
        this.wlz = wlz == null ? null : wlz.trim();
    }

    public Integer getXdsl() {
        return xdsl;
    }

    public void setXdsl(Integer xdsl) {
        this.xdsl = xdsl;
    }

    public Integer getJhmb() {
        return jhmb;
    }

    public void setJhmb(Integer jhmb) {
        this.jhmb = jhmb;
    }

    public Integer getRksl() {
        return rksl;
    }

    public void setRksl(Integer rksl) {
        this.rksl = rksl;
    }
}