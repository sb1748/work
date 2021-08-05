package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapPriceTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapPriceTbExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andXszzIsNull() {
            addCriterion("xszz is null");
            return (Criteria) this;
        }

        public Criteria andXszzIsNotNull() {
            addCriterion("xszz is not null");
            return (Criteria) this;
        }

        public Criteria andXszzEqualTo(String value) {
            addCriterion("xszz =", value, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzNotEqualTo(String value) {
            addCriterion("xszz <>", value, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzGreaterThan(String value) {
            addCriterion("xszz >", value, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzGreaterThanOrEqualTo(String value) {
            addCriterion("xszz >=", value, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzLessThan(String value) {
            addCriterion("xszz <", value, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzLessThanOrEqualTo(String value) {
            addCriterion("xszz <=", value, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzLike(String value) {
            addCriterion("xszz like", value, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzNotLike(String value) {
            addCriterion("xszz not like", value, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzIn(List<String> values) {
            addCriterion("xszz in", values, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzNotIn(List<String> values) {
            addCriterion("xszz not in", values, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzBetween(String value1, String value2) {
            addCriterion("xszz between", value1, value2, "xszz");
            return (Criteria) this;
        }

        public Criteria andXszzNotBetween(String value1, String value2) {
            addCriterion("xszz not between", value1, value2, "xszz");
            return (Criteria) this;
        }

        public Criteria andFxqdIsNull() {
            addCriterion("fxqd is null");
            return (Criteria) this;
        }

        public Criteria andFxqdIsNotNull() {
            addCriterion("fxqd is not null");
            return (Criteria) this;
        }

        public Criteria andFxqdEqualTo(String value) {
            addCriterion("fxqd =", value, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdNotEqualTo(String value) {
            addCriterion("fxqd <>", value, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdGreaterThan(String value) {
            addCriterion("fxqd >", value, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdGreaterThanOrEqualTo(String value) {
            addCriterion("fxqd >=", value, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdLessThan(String value) {
            addCriterion("fxqd <", value, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdLessThanOrEqualTo(String value) {
            addCriterion("fxqd <=", value, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdLike(String value) {
            addCriterion("fxqd like", value, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdNotLike(String value) {
            addCriterion("fxqd not like", value, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdIn(List<String> values) {
            addCriterion("fxqd in", values, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdNotIn(List<String> values) {
            addCriterion("fxqd not in", values, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdBetween(String value1, String value2) {
            addCriterion("fxqd between", value1, value2, "fxqd");
            return (Criteria) this;
        }

        public Criteria andFxqdNotBetween(String value1, String value2) {
            addCriterion("fxqd not between", value1, value2, "fxqd");
            return (Criteria) this;
        }

        public Criteria andTjlxIsNull() {
            addCriterion("tjlx is null");
            return (Criteria) this;
        }

        public Criteria andTjlxIsNotNull() {
            addCriterion("tjlx is not null");
            return (Criteria) this;
        }

        public Criteria andTjlxEqualTo(String value) {
            addCriterion("tjlx =", value, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxNotEqualTo(String value) {
            addCriterion("tjlx <>", value, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxGreaterThan(String value) {
            addCriterion("tjlx >", value, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxGreaterThanOrEqualTo(String value) {
            addCriterion("tjlx >=", value, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxLessThan(String value) {
            addCriterion("tjlx <", value, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxLessThanOrEqualTo(String value) {
            addCriterion("tjlx <=", value, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxLike(String value) {
            addCriterion("tjlx like", value, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxNotLike(String value) {
            addCriterion("tjlx not like", value, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxIn(List<String> values) {
            addCriterion("tjlx in", values, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxNotIn(List<String> values) {
            addCriterion("tjlx not in", values, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxBetween(String value1, String value2) {
            addCriterion("tjlx between", value1, value2, "tjlx");
            return (Criteria) this;
        }

        public Criteria andTjlxNotBetween(String value1, String value2) {
            addCriterion("tjlx not between", value1, value2, "tjlx");
            return (Criteria) this;
        }

        public Criteria andKhbmIsNull() {
            addCriterion("khbm is null");
            return (Criteria) this;
        }

        public Criteria andKhbmIsNotNull() {
            addCriterion("khbm is not null");
            return (Criteria) this;
        }

        public Criteria andKhbmEqualTo(String value) {
            addCriterion("khbm =", value, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmNotEqualTo(String value) {
            addCriterion("khbm <>", value, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmGreaterThan(String value) {
            addCriterion("khbm >", value, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmGreaterThanOrEqualTo(String value) {
            addCriterion("khbm >=", value, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmLessThan(String value) {
            addCriterion("khbm <", value, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmLessThanOrEqualTo(String value) {
            addCriterion("khbm <=", value, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmLike(String value) {
            addCriterion("khbm like", value, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmNotLike(String value) {
            addCriterion("khbm not like", value, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmIn(List<String> values) {
            addCriterion("khbm in", values, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmNotIn(List<String> values) {
            addCriterion("khbm not in", values, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmBetween(String value1, String value2) {
            addCriterion("khbm between", value1, value2, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhbmNotBetween(String value1, String value2) {
            addCriterion("khbm not between", value1, value2, "khbm");
            return (Criteria) this;
        }

        public Criteria andKhjgzIsNull() {
            addCriterion("khjgz is null");
            return (Criteria) this;
        }

        public Criteria andKhjgzIsNotNull() {
            addCriterion("khjgz is not null");
            return (Criteria) this;
        }

        public Criteria andKhjgzEqualTo(String value) {
            addCriterion("khjgz =", value, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzNotEqualTo(String value) {
            addCriterion("khjgz <>", value, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzGreaterThan(String value) {
            addCriterion("khjgz >", value, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzGreaterThanOrEqualTo(String value) {
            addCriterion("khjgz >=", value, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzLessThan(String value) {
            addCriterion("khjgz <", value, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzLessThanOrEqualTo(String value) {
            addCriterion("khjgz <=", value, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzLike(String value) {
            addCriterion("khjgz like", value, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzNotLike(String value) {
            addCriterion("khjgz not like", value, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzIn(List<String> values) {
            addCriterion("khjgz in", values, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzNotIn(List<String> values) {
            addCriterion("khjgz not in", values, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzBetween(String value1, String value2) {
            addCriterion("khjgz between", value1, value2, "khjgz");
            return (Criteria) this;
        }

        public Criteria andKhjgzNotBetween(String value1, String value2) {
            addCriterion("khjgz not between", value1, value2, "khjgz");
            return (Criteria) this;
        }

        public Criteria andWlbmIsNull() {
            addCriterion("wlbm is null");
            return (Criteria) this;
        }

        public Criteria andWlbmIsNotNull() {
            addCriterion("wlbm is not null");
            return (Criteria) this;
        }

        public Criteria andWlbmEqualTo(String value) {
            addCriterion("wlbm =", value, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmNotEqualTo(String value) {
            addCriterion("wlbm <>", value, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmGreaterThan(String value) {
            addCriterion("wlbm >", value, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmGreaterThanOrEqualTo(String value) {
            addCriterion("wlbm >=", value, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmLessThan(String value) {
            addCriterion("wlbm <", value, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmLessThanOrEqualTo(String value) {
            addCriterion("wlbm <=", value, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmLike(String value) {
            addCriterion("wlbm like", value, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmNotLike(String value) {
            addCriterion("wlbm not like", value, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmIn(List<String> values) {
            addCriterion("wlbm in", values, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmNotIn(List<String> values) {
            addCriterion("wlbm not in", values, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmBetween(String value1, String value2) {
            addCriterion("wlbm between", value1, value2, "wlbm");
            return (Criteria) this;
        }

        public Criteria andWlbmNotBetween(String value1, String value2) {
            addCriterion("wlbm not between", value1, value2, "wlbm");
            return (Criteria) this;
        }

        public Criteria andDwIsNull() {
            addCriterion("dw is null");
            return (Criteria) this;
        }

        public Criteria andDwIsNotNull() {
            addCriterion("dw is not null");
            return (Criteria) this;
        }

        public Criteria andDwEqualTo(String value) {
            addCriterion("dw =", value, "dw");
            return (Criteria) this;
        }

        public Criteria andDwNotEqualTo(String value) {
            addCriterion("dw <>", value, "dw");
            return (Criteria) this;
        }

        public Criteria andDwGreaterThan(String value) {
            addCriterion("dw >", value, "dw");
            return (Criteria) this;
        }

        public Criteria andDwGreaterThanOrEqualTo(String value) {
            addCriterion("dw >=", value, "dw");
            return (Criteria) this;
        }

        public Criteria andDwLessThan(String value) {
            addCriterion("dw <", value, "dw");
            return (Criteria) this;
        }

        public Criteria andDwLessThanOrEqualTo(String value) {
            addCriterion("dw <=", value, "dw");
            return (Criteria) this;
        }

        public Criteria andDwLike(String value) {
            addCriterion("dw like", value, "dw");
            return (Criteria) this;
        }

        public Criteria andDwNotLike(String value) {
            addCriterion("dw not like", value, "dw");
            return (Criteria) this;
        }

        public Criteria andDwIn(List<String> values) {
            addCriterion("dw in", values, "dw");
            return (Criteria) this;
        }

        public Criteria andDwNotIn(List<String> values) {
            addCriterion("dw not in", values, "dw");
            return (Criteria) this;
        }

        public Criteria andDwBetween(String value1, String value2) {
            addCriterion("dw between", value1, value2, "dw");
            return (Criteria) this;
        }

        public Criteria andDwNotBetween(String value1, String value2) {
            addCriterion("dw not between", value1, value2, "dw");
            return (Criteria) this;
        }

        public Criteria andBzIsNull() {
            addCriterion("bz is null");
            return (Criteria) this;
        }

        public Criteria andBzIsNotNull() {
            addCriterion("bz is not null");
            return (Criteria) this;
        }

        public Criteria andBzEqualTo(String value) {
            addCriterion("bz =", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotEqualTo(String value) {
            addCriterion("bz <>", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThan(String value) {
            addCriterion("bz >", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThanOrEqualTo(String value) {
            addCriterion("bz >=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThan(String value) {
            addCriterion("bz <", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThanOrEqualTo(String value) {
            addCriterion("bz <=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLike(String value) {
            addCriterion("bz like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotLike(String value) {
            addCriterion("bz not like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzIn(List<String> values) {
            addCriterion("bz in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotIn(List<String> values) {
            addCriterion("bz not in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzBetween(String value1, String value2) {
            addCriterion("bz between", value1, value2, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotBetween(String value1, String value2) {
            addCriterion("bz not between", value1, value2, "bz");
            return (Criteria) this;
        }

        public Criteria andDjzkIsNull() {
            addCriterion("djzk is null");
            return (Criteria) this;
        }

        public Criteria andDjzkIsNotNull() {
            addCriterion("djzk is not null");
            return (Criteria) this;
        }

        public Criteria andDjzkEqualTo(String value) {
            addCriterion("djzk =", value, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkNotEqualTo(String value) {
            addCriterion("djzk <>", value, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkGreaterThan(String value) {
            addCriterion("djzk >", value, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkGreaterThanOrEqualTo(String value) {
            addCriterion("djzk >=", value, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkLessThan(String value) {
            addCriterion("djzk <", value, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkLessThanOrEqualTo(String value) {
            addCriterion("djzk <=", value, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkLike(String value) {
            addCriterion("djzk like", value, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkNotLike(String value) {
            addCriterion("djzk not like", value, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkIn(List<String> values) {
            addCriterion("djzk in", values, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkNotIn(List<String> values) {
            addCriterion("djzk not in", values, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkBetween(String value1, String value2) {
            addCriterion("djzk between", value1, value2, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjzkNotBetween(String value1, String value2) {
            addCriterion("djzk not between", value1, value2, "djzk");
            return (Criteria) this;
        }

        public Criteria andDjbsIsNull() {
            addCriterion("djbs is null");
            return (Criteria) this;
        }

        public Criteria andDjbsIsNotNull() {
            addCriterion("djbs is not null");
            return (Criteria) this;
        }

        public Criteria andDjbsEqualTo(String value) {
            addCriterion("djbs =", value, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsNotEqualTo(String value) {
            addCriterion("djbs <>", value, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsGreaterThan(String value) {
            addCriterion("djbs >", value, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsGreaterThanOrEqualTo(String value) {
            addCriterion("djbs >=", value, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsLessThan(String value) {
            addCriterion("djbs <", value, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsLessThanOrEqualTo(String value) {
            addCriterion("djbs <=", value, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsLike(String value) {
            addCriterion("djbs like", value, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsNotLike(String value) {
            addCriterion("djbs not like", value, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsIn(List<String> values) {
            addCriterion("djbs in", values, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsNotIn(List<String> values) {
            addCriterion("djbs not in", values, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsBetween(String value1, String value2) {
            addCriterion("djbs between", value1, value2, "djbs");
            return (Criteria) this;
        }

        public Criteria andDjbsNotBetween(String value1, String value2) {
            addCriterion("djbs not between", value1, value2, "djbs");
            return (Criteria) this;
        }

        public Criteria andZkbsIsNull() {
            addCriterion("zkbs is null");
            return (Criteria) this;
        }

        public Criteria andZkbsIsNotNull() {
            addCriterion("zkbs is not null");
            return (Criteria) this;
        }

        public Criteria andZkbsEqualTo(String value) {
            addCriterion("zkbs =", value, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsNotEqualTo(String value) {
            addCriterion("zkbs <>", value, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsGreaterThan(String value) {
            addCriterion("zkbs >", value, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsGreaterThanOrEqualTo(String value) {
            addCriterion("zkbs >=", value, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsLessThan(String value) {
            addCriterion("zkbs <", value, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsLessThanOrEqualTo(String value) {
            addCriterion("zkbs <=", value, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsLike(String value) {
            addCriterion("zkbs like", value, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsNotLike(String value) {
            addCriterion("zkbs not like", value, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsIn(List<String> values) {
            addCriterion("zkbs in", values, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsNotIn(List<String> values) {
            addCriterion("zkbs not in", values, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsBetween(String value1, String value2) {
            addCriterion("zkbs between", value1, value2, "zkbs");
            return (Criteria) this;
        }

        public Criteria andZkbsNotBetween(String value1, String value2) {
            addCriterion("zkbs not between", value1, value2, "zkbs");
            return (Criteria) this;
        }

        public Criteria andYxksrqIsNull() {
            addCriterion("yxksrq is null");
            return (Criteria) this;
        }

        public Criteria andYxksrqIsNotNull() {
            addCriterion("yxksrq is not null");
            return (Criteria) this;
        }

        public Criteria andYxksrqEqualTo(String value) {
            addCriterion("yxksrq =", value, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqNotEqualTo(String value) {
            addCriterion("yxksrq <>", value, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqGreaterThan(String value) {
            addCriterion("yxksrq >", value, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqGreaterThanOrEqualTo(String value) {
            addCriterion("yxksrq >=", value, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqLessThan(String value) {
            addCriterion("yxksrq <", value, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqLessThanOrEqualTo(String value) {
            addCriterion("yxksrq <=", value, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqLike(String value) {
            addCriterion("yxksrq like", value, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqNotLike(String value) {
            addCriterion("yxksrq not like", value, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqIn(List<String> values) {
            addCriterion("yxksrq in", values, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqNotIn(List<String> values) {
            addCriterion("yxksrq not in", values, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqBetween(String value1, String value2) {
            addCriterion("yxksrq between", value1, value2, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxksrqNotBetween(String value1, String value2) {
            addCriterion("yxksrq not between", value1, value2, "yxksrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqIsNull() {
            addCriterion("yxjsrq is null");
            return (Criteria) this;
        }

        public Criteria andYxjsrqIsNotNull() {
            addCriterion("yxjsrq is not null");
            return (Criteria) this;
        }

        public Criteria andYxjsrqEqualTo(String value) {
            addCriterion("yxjsrq =", value, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqNotEqualTo(String value) {
            addCriterion("yxjsrq <>", value, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqGreaterThan(String value) {
            addCriterion("yxjsrq >", value, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqGreaterThanOrEqualTo(String value) {
            addCriterion("yxjsrq >=", value, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqLessThan(String value) {
            addCriterion("yxjsrq <", value, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqLessThanOrEqualTo(String value) {
            addCriterion("yxjsrq <=", value, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqLike(String value) {
            addCriterion("yxjsrq like", value, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqNotLike(String value) {
            addCriterion("yxjsrq not like", value, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqIn(List<String> values) {
            addCriterion("yxjsrq in", values, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqNotIn(List<String> values) {
            addCriterion("yxjsrq not in", values, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqBetween(String value1, String value2) {
            addCriterion("yxjsrq between", value1, value2, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andYxjsrqNotBetween(String value1, String value2) {
            addCriterion("yxjsrq not between", value1, value2, "yxjsrq");
            return (Criteria) this;
        }

        public Criteria andWyjIsNull() {
            addCriterion("wyj is null");
            return (Criteria) this;
        }

        public Criteria andWyjIsNotNull() {
            addCriterion("wyj is not null");
            return (Criteria) this;
        }

        public Criteria andWyjEqualTo(String value) {
            addCriterion("wyj =", value, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjNotEqualTo(String value) {
            addCriterion("wyj <>", value, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjGreaterThan(String value) {
            addCriterion("wyj >", value, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjGreaterThanOrEqualTo(String value) {
            addCriterion("wyj >=", value, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjLessThan(String value) {
            addCriterion("wyj <", value, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjLessThanOrEqualTo(String value) {
            addCriterion("wyj <=", value, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjLike(String value) {
            addCriterion("wyj like", value, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjNotLike(String value) {
            addCriterion("wyj not like", value, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjIn(List<String> values) {
            addCriterion("wyj in", values, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjNotIn(List<String> values) {
            addCriterion("wyj not in", values, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjBetween(String value1, String value2) {
            addCriterion("wyj between", value1, value2, "wyj");
            return (Criteria) this;
        }

        public Criteria andWyjNotBetween(String value1, String value2) {
            addCriterion("wyj not between", value1, value2, "wyj");
            return (Criteria) this;
        }

        public Criteria andTbztIsNull() {
            addCriterion("tbzt is null");
            return (Criteria) this;
        }

        public Criteria andTbztIsNotNull() {
            addCriterion("tbzt is not null");
            return (Criteria) this;
        }

        public Criteria andTbztEqualTo(String value) {
            addCriterion("tbzt =", value, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztNotEqualTo(String value) {
            addCriterion("tbzt <>", value, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztGreaterThan(String value) {
            addCriterion("tbzt >", value, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztGreaterThanOrEqualTo(String value) {
            addCriterion("tbzt >=", value, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztLessThan(String value) {
            addCriterion("tbzt <", value, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztLessThanOrEqualTo(String value) {
            addCriterion("tbzt <=", value, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztLike(String value) {
            addCriterion("tbzt like", value, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztNotLike(String value) {
            addCriterion("tbzt not like", value, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztIn(List<String> values) {
            addCriterion("tbzt in", values, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztNotIn(List<String> values) {
            addCriterion("tbzt not in", values, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztBetween(String value1, String value2) {
            addCriterion("tbzt between", value1, value2, "tbzt");
            return (Criteria) this;
        }

        public Criteria andTbztNotBetween(String value1, String value2) {
            addCriterion("tbzt not between", value1, value2, "tbzt");
            return (Criteria) this;
        }

        public Criteria andCjrqIsNull() {
            addCriterion("cjrq is null");
            return (Criteria) this;
        }

        public Criteria andCjrqIsNotNull() {
            addCriterion("cjrq is not null");
            return (Criteria) this;
        }

        public Criteria andCjrqEqualTo(String value) {
            addCriterion("cjrq =", value, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqNotEqualTo(String value) {
            addCriterion("cjrq <>", value, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqGreaterThan(String value) {
            addCriterion("cjrq >", value, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqGreaterThanOrEqualTo(String value) {
            addCriterion("cjrq >=", value, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqLessThan(String value) {
            addCriterion("cjrq <", value, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqLessThanOrEqualTo(String value) {
            addCriterion("cjrq <=", value, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqLike(String value) {
            addCriterion("cjrq like", value, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqNotLike(String value) {
            addCriterion("cjrq not like", value, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqIn(List<String> values) {
            addCriterion("cjrq in", values, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqNotIn(List<String> values) {
            addCriterion("cjrq not in", values, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqBetween(String value1, String value2) {
            addCriterion("cjrq between", value1, value2, "cjrq");
            return (Criteria) this;
        }

        public Criteria andCjrqNotBetween(String value1, String value2) {
            addCriterion("cjrq not between", value1, value2, "cjrq");
            return (Criteria) this;
        }

        public Criteria andSxztIsNull() {
            addCriterion("sxzt is null");
            return (Criteria) this;
        }

        public Criteria andSxztIsNotNull() {
            addCriterion("sxzt is not null");
            return (Criteria) this;
        }

        public Criteria andSxztEqualTo(String value) {
            addCriterion("sxzt =", value, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztNotEqualTo(String value) {
            addCriterion("sxzt <>", value, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztGreaterThan(String value) {
            addCriterion("sxzt >", value, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztGreaterThanOrEqualTo(String value) {
            addCriterion("sxzt >=", value, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztLessThan(String value) {
            addCriterion("sxzt <", value, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztLessThanOrEqualTo(String value) {
            addCriterion("sxzt <=", value, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztLike(String value) {
            addCriterion("sxzt like", value, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztNotLike(String value) {
            addCriterion("sxzt not like", value, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztIn(List<String> values) {
            addCriterion("sxzt in", values, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztNotIn(List<String> values) {
            addCriterion("sxzt not in", values, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztBetween(String value1, String value2) {
            addCriterion("sxzt between", value1, value2, "sxzt");
            return (Criteria) this;
        }

        public Criteria andSxztNotBetween(String value1, String value2) {
            addCriterion("sxzt not between", value1, value2, "sxzt");
            return (Criteria) this;
        }

        public Criteria andCjrIsNull() {
            addCriterion("cjr is null");
            return (Criteria) this;
        }

        public Criteria andCjrIsNotNull() {
            addCriterion("cjr is not null");
            return (Criteria) this;
        }

        public Criteria andCjrEqualTo(String value) {
            addCriterion("cjr =", value, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrNotEqualTo(String value) {
            addCriterion("cjr <>", value, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrGreaterThan(String value) {
            addCriterion("cjr >", value, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrGreaterThanOrEqualTo(String value) {
            addCriterion("cjr >=", value, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrLessThan(String value) {
            addCriterion("cjr <", value, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrLessThanOrEqualTo(String value) {
            addCriterion("cjr <=", value, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrLike(String value) {
            addCriterion("cjr like", value, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrNotLike(String value) {
            addCriterion("cjr not like", value, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrIn(List<String> values) {
            addCriterion("cjr in", values, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrNotIn(List<String> values) {
            addCriterion("cjr not in", values, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrBetween(String value1, String value2) {
            addCriterion("cjr between", value1, value2, "cjr");
            return (Criteria) this;
        }

        public Criteria andCjrNotBetween(String value1, String value2) {
            addCriterion("cjr not between", value1, value2, "cjr");
            return (Criteria) this;
        }

        public Criteria andWlmsIsNull() {
            addCriterion("wlms is null");
            return (Criteria) this;
        }

        public Criteria andWlmsIsNotNull() {
            addCriterion("wlms is not null");
            return (Criteria) this;
        }

        public Criteria andWlmsEqualTo(String value) {
            addCriterion("wlms =", value, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsNotEqualTo(String value) {
            addCriterion("wlms <>", value, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsGreaterThan(String value) {
            addCriterion("wlms >", value, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsGreaterThanOrEqualTo(String value) {
            addCriterion("wlms >=", value, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsLessThan(String value) {
            addCriterion("wlms <", value, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsLessThanOrEqualTo(String value) {
            addCriterion("wlms <=", value, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsLike(String value) {
            addCriterion("wlms like", value, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsNotLike(String value) {
            addCriterion("wlms not like", value, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsIn(List<String> values) {
            addCriterion("wlms in", values, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsNotIn(List<String> values) {
            addCriterion("wlms not in", values, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsBetween(String value1, String value2) {
            addCriterion("wlms between", value1, value2, "wlms");
            return (Criteria) this;
        }

        public Criteria andWlmsNotBetween(String value1, String value2) {
            addCriterion("wlms not between", value1, value2, "wlms");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}