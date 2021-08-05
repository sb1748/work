package com.jxszj.pojo.sap;

public class YdmbTb {
    private Integer id;

    private String jxsmc;

    private String jxsbm;

    private String dd;

    private String yyzt;

    private Double bymbhk;

    private String drsj;

    private String ppbm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJxsmc() {
        return jxsmc;
    }

    public void setJxsmc(String jxsmc) {
        this.jxsmc = jxsmc == null ? null : jxsmc.trim();
    }

    public String getJxsbm() {
        return jxsbm;
    }

    public void setJxsbm(String jxsbm) {
        this.jxsbm = jxsbm == null ? null : jxsbm.trim();
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd == null ? null : dd.trim();
    }

    public String getYyzt() {
        return yyzt;
    }

    public void setYyzt(String yyzt) {
        this.yyzt = yyzt == null ? null : yyzt.trim();
    }

    public Double getBymbhk() {
        return bymbhk;
    }

    public void setBymbhk(Double bymbhk) {
        this.bymbhk = bymbhk;
    }

    public String getDrsj() {
        return drsj;
    }

    public void setDrsj(String drsj) {
        this.drsj = drsj == null ? null : drsj.trim();
    }

    public String getPpbm() {
        return ppbm;
    }

    public void setPpbm(String ppbm) {
        this.ppbm = ppbm == null ? null : ppbm.trim();
    }
}