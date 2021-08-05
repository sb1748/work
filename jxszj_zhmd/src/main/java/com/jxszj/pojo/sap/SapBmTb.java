package com.jxszj.pojo.sap;


public class SapBmTb {
    private Integer id;

    private String newName;

    private String newWlbm;

    private String oldName;

    private String oldWlbm;

    private String bom;

    private String createtime;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName == null ? null : newName.trim();
    }

    public String getNewWlbm() {
        return newWlbm;
    }

    public void setNewWlbm(String newWlbm) {
        this.newWlbm = newWlbm == null ? null : newWlbm.trim();
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName == null ? null : oldName.trim();
    }

    public String getOldWlbm() {
        return oldWlbm;
    }

    public void setOldWlbm(String oldWlbm) {
        this.oldWlbm = oldWlbm == null ? null : oldWlbm.trim();
    }

    public String getBom() {
        return bom;
    }

    public void setBom(String bom) {
        this.bom = bom == null ? null : bom.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	@Override
	public String toString() {
		return "SapBmTb [id=" + id + ", newName=" + newName + ", newWlbm=" + newWlbm + ", oldName=" + oldName + ", oldWlbm="
				+ oldWlbm + ", bom=" + bom + ", createtime=" + createtime + ", status=" + status + "]";
	}
    
}