package com.jxszj.pojo.sap;

import java.util.List;

public class SapWlTb {
    private Long id;

    private String text;

    private Long pid;

    private String ptext;
    
    private List<SapWlTb> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPtext() {
        return ptext;
    }

    public void setPtext(String ptext) {
        this.ptext = ptext == null ? null : ptext.trim();
    }
    
	public List<SapWlTb> getChildren() {
		return children;
	}

	public void setChildren(List<SapWlTb> children) {
		this.children = children;
	}
}