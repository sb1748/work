package com.jxszj.pojo.sap;

public class SapDataTb {
    private Integer id;

    private String bmz;

    private String data;

    private String gzs;

    private String wlmclz;

    private String ggxhlz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBmz() {
        return bmz;
    }

    public void setBmz(String bmz) {
        this.bmz = bmz == null ? null : bmz.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public String getGzs() {
        return gzs;
    }

    public void setGzs(String gzs) {
        this.gzs = gzs == null ? null : gzs.trim();
    }

    public String getWlmclz() {
        return wlmclz;
    }

    public void setWlmclz(String wlmclz) {
        this.wlmclz = wlmclz == null ? null : wlmclz.trim();
    }

    public String getGgxhlz() {
        return ggxhlz;
    }

    public void setGgxhlz(String ggxhlz) {
        this.ggxhlz = ggxhlz == null ? null : ggxhlz.trim();
    }
}