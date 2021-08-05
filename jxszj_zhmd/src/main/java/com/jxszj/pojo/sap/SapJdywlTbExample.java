package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapJdywlTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapJdywlTbExample() {
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

        public Criteria andCplxIsNull() {
            addCriterion("cplx is null");
            return (Criteria) this;
        }

        public Criteria andCplxIsNotNull() {
            addCriterion("cplx is not null");
            return (Criteria) this;
        }

        public Criteria andCplxEqualTo(String value) {
            addCriterion("cplx =", value, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxNotEqualTo(String value) {
            addCriterion("cplx <>", value, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxGreaterThan(String value) {
            addCriterion("cplx >", value, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxGreaterThanOrEqualTo(String value) {
            addCriterion("cplx >=", value, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxLessThan(String value) {
            addCriterion("cplx <", value, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxLessThanOrEqualTo(String value) {
            addCriterion("cplx <=", value, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxLike(String value) {
            addCriterion("cplx like", value, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxNotLike(String value) {
            addCriterion("cplx not like", value, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxIn(List<String> values) {
            addCriterion("cplx in", values, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxNotIn(List<String> values) {
            addCriterion("cplx not in", values, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxBetween(String value1, String value2) {
            addCriterion("cplx between", value1, value2, "cplx");
            return (Criteria) this;
        }

        public Criteria andCplxNotBetween(String value1, String value2) {
            addCriterion("cplx not between", value1, value2, "cplx");
            return (Criteria) this;
        }

        public Criteria andSapIsNull() {
            addCriterion("sap is null");
            return (Criteria) this;
        }

        public Criteria andSapIsNotNull() {
            addCriterion("sap is not null");
            return (Criteria) this;
        }

        public Criteria andSapEqualTo(String value) {
            addCriterion("sap =", value, "sap");
            return (Criteria) this;
        }

        public Criteria andSapNotEqualTo(String value) {
            addCriterion("sap <>", value, "sap");
            return (Criteria) this;
        }

        public Criteria andSapGreaterThan(String value) {
            addCriterion("sap >", value, "sap");
            return (Criteria) this;
        }

        public Criteria andSapGreaterThanOrEqualTo(String value) {
            addCriterion("sap >=", value, "sap");
            return (Criteria) this;
        }

        public Criteria andSapLessThan(String value) {
            addCriterion("sap <", value, "sap");
            return (Criteria) this;
        }

        public Criteria andSapLessThanOrEqualTo(String value) {
            addCriterion("sap <=", value, "sap");
            return (Criteria) this;
        }

        public Criteria andSapLike(String value) {
            addCriterion("sap like", value, "sap");
            return (Criteria) this;
        }

        public Criteria andSapNotLike(String value) {
            addCriterion("sap not like", value, "sap");
            return (Criteria) this;
        }

        public Criteria andSapIn(List<String> values) {
            addCriterion("sap in", values, "sap");
            return (Criteria) this;
        }

        public Criteria andSapNotIn(List<String> values) {
            addCriterion("sap not in", values, "sap");
            return (Criteria) this;
        }

        public Criteria andSapBetween(String value1, String value2) {
            addCriterion("sap between", value1, value2, "sap");
            return (Criteria) this;
        }

        public Criteria andSapNotBetween(String value1, String value2) {
            addCriterion("sap not between", value1, value2, "sap");
            return (Criteria) this;
        }

        public Criteria andPpmcIsNull() {
            addCriterion("ppmc is null");
            return (Criteria) this;
        }

        public Criteria andPpmcIsNotNull() {
            addCriterion("ppmc is not null");
            return (Criteria) this;
        }

        public Criteria andPpmcEqualTo(String value) {
            addCriterion("ppmc =", value, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcNotEqualTo(String value) {
            addCriterion("ppmc <>", value, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcGreaterThan(String value) {
            addCriterion("ppmc >", value, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcGreaterThanOrEqualTo(String value) {
            addCriterion("ppmc >=", value, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcLessThan(String value) {
            addCriterion("ppmc <", value, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcLessThanOrEqualTo(String value) {
            addCriterion("ppmc <=", value, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcLike(String value) {
            addCriterion("ppmc like", value, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcNotLike(String value) {
            addCriterion("ppmc not like", value, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcIn(List<String> values) {
            addCriterion("ppmc in", values, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcNotIn(List<String> values) {
            addCriterion("ppmc not in", values, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcBetween(String value1, String value2) {
            addCriterion("ppmc between", value1, value2, "ppmc");
            return (Criteria) this;
        }

        public Criteria andPpmcNotBetween(String value1, String value2) {
            addCriterion("ppmc not between", value1, value2, "ppmc");
            return (Criteria) this;
        }

        public Criteria andWlzbmIsNull() {
            addCriterion("wlzbm is null");
            return (Criteria) this;
        }

        public Criteria andWlzbmIsNotNull() {
            addCriterion("wlzbm is not null");
            return (Criteria) this;
        }

        public Criteria andWlzbmEqualTo(String value) {
            addCriterion("wlzbm =", value, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmNotEqualTo(String value) {
            addCriterion("wlzbm <>", value, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmGreaterThan(String value) {
            addCriterion("wlzbm >", value, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmGreaterThanOrEqualTo(String value) {
            addCriterion("wlzbm >=", value, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmLessThan(String value) {
            addCriterion("wlzbm <", value, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmLessThanOrEqualTo(String value) {
            addCriterion("wlzbm <=", value, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmLike(String value) {
            addCriterion("wlzbm like", value, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmNotLike(String value) {
            addCriterion("wlzbm not like", value, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmIn(List<String> values) {
            addCriterion("wlzbm in", values, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmNotIn(List<String> values) {
            addCriterion("wlzbm not in", values, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmBetween(String value1, String value2) {
            addCriterion("wlzbm between", value1, value2, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzbmNotBetween(String value1, String value2) {
            addCriterion("wlzbm not between", value1, value2, "wlzbm");
            return (Criteria) this;
        }

        public Criteria andWlzmcIsNull() {
            addCriterion("wlzmc is null");
            return (Criteria) this;
        }

        public Criteria andWlzmcIsNotNull() {
            addCriterion("wlzmc is not null");
            return (Criteria) this;
        }

        public Criteria andWlzmcEqualTo(String value) {
            addCriterion("wlzmc =", value, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcNotEqualTo(String value) {
            addCriterion("wlzmc <>", value, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcGreaterThan(String value) {
            addCriterion("wlzmc >", value, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcGreaterThanOrEqualTo(String value) {
            addCriterion("wlzmc >=", value, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcLessThan(String value) {
            addCriterion("wlzmc <", value, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcLessThanOrEqualTo(String value) {
            addCriterion("wlzmc <=", value, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcLike(String value) {
            addCriterion("wlzmc like", value, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcNotLike(String value) {
            addCriterion("wlzmc not like", value, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcIn(List<String> values) {
            addCriterion("wlzmc in", values, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcNotIn(List<String> values) {
            addCriterion("wlzmc not in", values, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcBetween(String value1, String value2) {
            addCriterion("wlzmc between", value1, value2, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andWlzmcNotBetween(String value1, String value2) {
            addCriterion("wlzmc not between", value1, value2, "wlzmc");
            return (Criteria) this;
        }

        public Criteria andFlbsIsNull() {
            addCriterion("flbs is null");
            return (Criteria) this;
        }

        public Criteria andFlbsIsNotNull() {
            addCriterion("flbs is not null");
            return (Criteria) this;
        }

        public Criteria andFlbsEqualTo(String value) {
            addCriterion("flbs =", value, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsNotEqualTo(String value) {
            addCriterion("flbs <>", value, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsGreaterThan(String value) {
            addCriterion("flbs >", value, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsGreaterThanOrEqualTo(String value) {
            addCriterion("flbs >=", value, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsLessThan(String value) {
            addCriterion("flbs <", value, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsLessThanOrEqualTo(String value) {
            addCriterion("flbs <=", value, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsLike(String value) {
            addCriterion("flbs like", value, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsNotLike(String value) {
            addCriterion("flbs not like", value, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsIn(List<String> values) {
            addCriterion("flbs in", values, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsNotIn(List<String> values) {
            addCriterion("flbs not in", values, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsBetween(String value1, String value2) {
            addCriterion("flbs between", value1, value2, "flbs");
            return (Criteria) this;
        }

        public Criteria andFlbsNotBetween(String value1, String value2) {
            addCriterion("flbs not between", value1, value2, "flbs");
            return (Criteria) this;
        }

        public Criteria andCpbmIsNull() {
            addCriterion("cpbm is null");
            return (Criteria) this;
        }

        public Criteria andCpbmIsNotNull() {
            addCriterion("cpbm is not null");
            return (Criteria) this;
        }

        public Criteria andCpbmEqualTo(String value) {
            addCriterion("cpbm =", value, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmNotEqualTo(String value) {
            addCriterion("cpbm <>", value, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmGreaterThan(String value) {
            addCriterion("cpbm >", value, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmGreaterThanOrEqualTo(String value) {
            addCriterion("cpbm >=", value, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmLessThan(String value) {
            addCriterion("cpbm <", value, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmLessThanOrEqualTo(String value) {
            addCriterion("cpbm <=", value, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmLike(String value) {
            addCriterion("cpbm like", value, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmNotLike(String value) {
            addCriterion("cpbm not like", value, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmIn(List<String> values) {
            addCriterion("cpbm in", values, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmNotIn(List<String> values) {
            addCriterion("cpbm not in", values, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmBetween(String value1, String value2) {
            addCriterion("cpbm between", value1, value2, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpbmNotBetween(String value1, String value2) {
            addCriterion("cpbm not between", value1, value2, "cpbm");
            return (Criteria) this;
        }

        public Criteria andCpmcIsNull() {
            addCriterion("cpmc is null");
            return (Criteria) this;
        }

        public Criteria andCpmcIsNotNull() {
            addCriterion("cpmc is not null");
            return (Criteria) this;
        }

        public Criteria andCpmcEqualTo(String value) {
            addCriterion("cpmc =", value, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcNotEqualTo(String value) {
            addCriterion("cpmc <>", value, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcGreaterThan(String value) {
            addCriterion("cpmc >", value, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcGreaterThanOrEqualTo(String value) {
            addCriterion("cpmc >=", value, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcLessThan(String value) {
            addCriterion("cpmc <", value, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcLessThanOrEqualTo(String value) {
            addCriterion("cpmc <=", value, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcLike(String value) {
            addCriterion("cpmc like", value, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcNotLike(String value) {
            addCriterion("cpmc not like", value, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcIn(List<String> values) {
            addCriterion("cpmc in", values, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcNotIn(List<String> values) {
            addCriterion("cpmc not in", values, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcBetween(String value1, String value2) {
            addCriterion("cpmc between", value1, value2, "cpmc");
            return (Criteria) this;
        }

        public Criteria andCpmcNotBetween(String value1, String value2) {
            addCriterion("cpmc not between", value1, value2, "cpmc");
            return (Criteria) this;
        }

        public Criteria andGgccIsNull() {
            addCriterion("ggcc is null");
            return (Criteria) this;
        }

        public Criteria andGgccIsNotNull() {
            addCriterion("ggcc is not null");
            return (Criteria) this;
        }

        public Criteria andGgccEqualTo(String value) {
            addCriterion("ggcc =", value, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccNotEqualTo(String value) {
            addCriterion("ggcc <>", value, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccGreaterThan(String value) {
            addCriterion("ggcc >", value, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccGreaterThanOrEqualTo(String value) {
            addCriterion("ggcc >=", value, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccLessThan(String value) {
            addCriterion("ggcc <", value, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccLessThanOrEqualTo(String value) {
            addCriterion("ggcc <=", value, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccLike(String value) {
            addCriterion("ggcc like", value, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccNotLike(String value) {
            addCriterion("ggcc not like", value, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccIn(List<String> values) {
            addCriterion("ggcc in", values, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccNotIn(List<String> values) {
            addCriterion("ggcc not in", values, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccBetween(String value1, String value2) {
            addCriterion("ggcc between", value1, value2, "ggcc");
            return (Criteria) this;
        }

        public Criteria andGgccNotBetween(String value1, String value2) {
            addCriterion("ggcc not between", value1, value2, "ggcc");
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