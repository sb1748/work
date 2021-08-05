package com.jxszj.pojo.sap;

public class PpxxTb {
    private Integer id;

    private String ppmc;

    private String ppbm;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPpmc() {
        return ppmc;
    }

    public void setPpmc(String ppmc) {
        this.ppmc = ppmc == null ? null : ppmc.trim();
    }

    public String getPpbm() {
        return ppbm;
    }

    public void setPpbm(String ppbm) {
        this.ppbm = ppbm == null ? null : ppbm.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}