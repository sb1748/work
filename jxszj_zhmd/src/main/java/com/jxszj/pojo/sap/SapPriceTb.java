package com.jxszj.pojo.sap;


public class SapPriceTb{

	private String id;

    private String xszz;

    private String fxqd;

    private String tjlx;

    private String khbm;

    private String khjgz;

    private String wlbm;

    private String dw;

    private String bz;

    private String djzk;

    private String djbs;

    private String zkbs;

    private String yxksrq;

    private String yxjsrq;

    private String wyj;

    private String tbzt;

    private String cjrq;

    private String sxzt;

    private String cjr;

    private String wlms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getXszz() {
        return xszz;
    }

    public void setXszz(String xszz) {
        this.xszz = xszz == null ? null : xszz.trim();
    }

    public String getFxqd() {
        return fxqd;
    }

    public void setFxqd(String fxqd) {
        this.fxqd = fxqd == null ? null : fxqd.trim();
    }

    public String getTjlx() {
        return tjlx;
    }

    public void setTjlx(String tjlx) {
        this.tjlx = tjlx == null ? null : tjlx.trim();
    }

    public String getKhbm() {
        return khbm;
    }

    public void setKhbm(String khbm) {
        this.khbm = khbm == null ? null : khbm.trim();
    }

    public String getKhjgz() {
        return khjgz;
    }

    public void setKhjgz(String khjgz) {
        this.khjgz = khjgz == null ? null : khjgz.trim();
    }

    public String getWlbm() {
        return wlbm;
    }

    public void setWlbm(String wlbm) {
        this.wlbm = wlbm == null ? null : wlbm.trim();
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getDjzk() {
        return djzk;
    }

    public void setDjzk(String djzk) {
        this.djzk = djzk == null ? null : djzk.trim();
    }

    public String getDjbs() {
        return djbs;
    }

    public void setDjbs(String djbs) {
        this.djbs = djbs == null ? null : djbs.trim();
    }

    public String getZkbs() {
        return zkbs;
    }

    public void setZkbs(String zkbs) {
        this.zkbs = zkbs == null ? null : zkbs.trim();
    }

    public String getYxksrq() {
        return yxksrq;
    }

    public void setYxksrq(String yxksrq) {
        this.yxksrq = yxksrq == null ? null : yxksrq.trim();
    }

    public String getYxjsrq() {
        return yxjsrq;
    }

    public void setYxjsrq(String yxjsrq) {
        this.yxjsrq = yxjsrq == null ? null : yxjsrq.trim();
    }

    public String getWyj() {
        return wyj;
    }

    public void setWyj(String wyj) {
        this.wyj = wyj == null ? null : wyj.trim();
    }

    public String getTbzt() {
        return tbzt;
    }

    public void setTbzt(String tbzt) {
        this.tbzt = tbzt == null ? null : tbzt.trim();
    }

    public String getCjrq() {
        return cjrq;
    }

    public void setCjrq(String cjrq) {
        this.cjrq = cjrq == null ? null : cjrq.trim();
    }

    public String getSxzt() {
        return sxzt;
    }

    public void setSxzt(String sxzt) {
        this.sxzt = sxzt == null ? null : sxzt.trim();
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr == null ? null : cjr.trim();
    }

    public String getWlms() {
        return wlms;
    }

    public void setWlms(String wlms) {
        this.wlms = wlms == null ? null : wlms.trim();
    }

	@Override
	public String toString() {
		return "SapPriceTb [id=" + id + ", xszz=" + xszz + ", fxqd=" + fxqd + ", tjlx=" + tjlx + ", khbm=" + khbm + ", khjgz="
				+ khjgz + ", wlbm=" + wlbm + ", dw=" + dw + ", bz=" + bz + ", djzk=" + djzk + ", djbs=" + djbs + ", zkbs="
				+ zkbs + ", yxksrq=" + yxksrq + ", yxjsrq=" + yxjsrq + ", wyj=" + wyj + ", tbzt=" + tbzt + ", cjrq=" + cjrq
				+ ", sxzt=" + sxzt + ", cjr=" + cjr + ", wlms=" + wlms + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bz == null) ? 0 : bz.hashCode());
		result = prime * result + ((djzk == null) ? 0 : djzk.hashCode());
		result = prime * result + ((dw == null) ? 0 : dw.hashCode());
		result = prime * result + ((khbm == null) ? 0 : khbm.hashCode());
		result = prime * result + ((khjgz == null) ? 0 : khjgz.hashCode());
		result = prime * result + ((tjlx == null) ? 0 : tjlx.hashCode());
		result = prime * result + ((yxjsrq == null) ? 0 : yxjsrq.hashCode());
		result = prime * result + ((yxksrq == null) ? 0 : yxksrq.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SapPriceTb other = (SapPriceTb) obj;
		if (bz == null) {
			if (other.bz != null)
				return false;
		} else if (!bz.equals(other.bz))
			return false;
		if (djzk == null) {
			if (other.djzk != null)
				return false;
		} else if (!djzk.equals(other.djzk))
			return false;
		if (dw == null) {
			if (other.dw != null)
				return false;
		} else if (!dw.equals(other.dw))
			return false;
		if (khbm == null) {
			if (other.khbm != null)
				return false;
		} else if (!khbm.equals(other.khbm))
			return false;
		if (khjgz == null) {
			if (other.khjgz != null)
				return false;
		} else if (!khjgz.equals(other.khjgz))
			return false;
		if (tjlx == null) {
			if (other.tjlx != null)
				return false;
		} else if (!tjlx.equals(other.tjlx))
			return false;
		if (yxjsrq == null) {
			if (other.yxjsrq != null)
				return false;
		} else if (!yxjsrq.equals(other.yxjsrq))
			return false;
		if (yxksrq == null) {
			if (other.yxksrq != null)
				return false;
		} else if (!yxksrq.equals(other.yxksrq))
			return false;
		return true;
	}
    
}