package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapDeliveryTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapDeliveryTbExample() {
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

        public Criteria andXsddIsNull() {
            addCriterion("xsdd is null");
            return (Criteria) this;
        }

        public Criteria andXsddIsNotNull() {
            addCriterion("xsdd is not null");
            return (Criteria) this;
        }

        public Criteria andXsddEqualTo(String value) {
            addCriterion("xsdd =", value, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddNotEqualTo(String value) {
            addCriterion("xsdd <>", value, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddGreaterThan(String value) {
            addCriterion("xsdd >", value, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddGreaterThanOrEqualTo(String value) {
            addCriterion("xsdd >=", value, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddLessThan(String value) {
            addCriterion("xsdd <", value, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddLessThanOrEqualTo(String value) {
            addCriterion("xsdd <=", value, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddLike(String value) {
            addCriterion("xsdd like", value, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddNotLike(String value) {
            addCriterion("xsdd not like", value, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddIn(List<String> values) {
            addCriterion("xsdd in", values, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddNotIn(List<String> values) {
            addCriterion("xsdd not in", values, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddBetween(String value1, String value2) {
            addCriterion("xsdd between", value1, value2, "xsdd");
            return (Criteria) this;
        }

        public Criteria andXsddNotBetween(String value1, String value2) {
            addCriterion("xsdd not between", value1, value2, "xsdd");
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

        public Criteria andCpzIsNull() {
            addCriterion("cpz is null");
            return (Criteria) this;
        }

        public Criteria andCpzIsNotNull() {
            addCriterion("cpz is not null");
            return (Criteria) this;
        }

        public Criteria andCpzEqualTo(String value) {
            addCriterion("cpz =", value, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzNotEqualTo(String value) {
            addCriterion("cpz <>", value, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzGreaterThan(String value) {
            addCriterion("cpz >", value, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzGreaterThanOrEqualTo(String value) {
            addCriterion("cpz >=", value, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzLessThan(String value) {
            addCriterion("cpz <", value, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzLessThanOrEqualTo(String value) {
            addCriterion("cpz <=", value, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzLike(String value) {
            addCriterion("cpz like", value, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzNotLike(String value) {
            addCriterion("cpz not like", value, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzIn(List<String> values) {
            addCriterion("cpz in", values, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzNotIn(List<String> values) {
            addCriterion("cpz not in", values, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzBetween(String value1, String value2) {
            addCriterion("cpz between", value1, value2, "cpz");
            return (Criteria) this;
        }

        public Criteria andCpzNotBetween(String value1, String value2) {
            addCriterion("cpz not between", value1, value2, "cpz");
            return (Criteria) this;
        }

        public Criteria andDdslIsNull() {
            addCriterion("ddsl is null");
            return (Criteria) this;
        }

        public Criteria andDdslIsNotNull() {
            addCriterion("ddsl is not null");
            return (Criteria) this;
        }

        public Criteria andDdslEqualTo(String value) {
            addCriterion("ddsl =", value, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslNotEqualTo(String value) {
            addCriterion("ddsl <>", value, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslGreaterThan(String value) {
            addCriterion("ddsl >", value, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslGreaterThanOrEqualTo(String value) {
            addCriterion("ddsl >=", value, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslLessThan(String value) {
            addCriterion("ddsl <", value, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslLessThanOrEqualTo(String value) {
            addCriterion("ddsl <=", value, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslLike(String value) {
            addCriterion("ddsl like", value, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslNotLike(String value) {
            addCriterion("ddsl not like", value, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslIn(List<String> values) {
            addCriterion("ddsl in", values, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslNotIn(List<String> values) {
            addCriterion("ddsl not in", values, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslBetween(String value1, String value2) {
            addCriterion("ddsl between", value1, value2, "ddsl");
            return (Criteria) this;
        }

        public Criteria andDdslNotBetween(String value1, String value2) {
            addCriterion("ddsl not between", value1, value2, "ddsl");
            return (Criteria) this;
        }

        public Criteria andXsdwIsNull() {
            addCriterion("xsdw is null");
            return (Criteria) this;
        }

        public Criteria andXsdwIsNotNull() {
            addCriterion("xsdw is not null");
            return (Criteria) this;
        }

        public Criteria andXsdwEqualTo(String value) {
            addCriterion("xsdw =", value, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwNotEqualTo(String value) {
            addCriterion("xsdw <>", value, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwGreaterThan(String value) {
            addCriterion("xsdw >", value, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwGreaterThanOrEqualTo(String value) {
            addCriterion("xsdw >=", value, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwLessThan(String value) {
            addCriterion("xsdw <", value, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwLessThanOrEqualTo(String value) {
            addCriterion("xsdw <=", value, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwLike(String value) {
            addCriterion("xsdw like", value, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwNotLike(String value) {
            addCriterion("xsdw not like", value, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwIn(List<String> values) {
            addCriterion("xsdw in", values, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwNotIn(List<String> values) {
            addCriterion("xsdw not in", values, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwBetween(String value1, String value2) {
            addCriterion("xsdw between", value1, value2, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXsdwNotBetween(String value1, String value2) {
            addCriterion("xsdw not between", value1, value2, "xsdw");
            return (Criteria) this;
        }

        public Criteria andXspzxmIsNull() {
            addCriterion("xspzxm is null");
            return (Criteria) this;
        }

        public Criteria andXspzxmIsNotNull() {
            addCriterion("xspzxm is not null");
            return (Criteria) this;
        }

        public Criteria andXspzxmEqualTo(String value) {
            addCriterion("xspzxm =", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmNotEqualTo(String value) {
            addCriterion("xspzxm <>", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmGreaterThan(String value) {
            addCriterion("xspzxm >", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmGreaterThanOrEqualTo(String value) {
            addCriterion("xspzxm >=", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmLessThan(String value) {
            addCriterion("xspzxm <", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmLessThanOrEqualTo(String value) {
            addCriterion("xspzxm <=", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmLike(String value) {
            addCriterion("xspzxm like", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmNotLike(String value) {
            addCriterion("xspzxm not like", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmIn(List<String> values) {
            addCriterion("xspzxm in", values, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmNotIn(List<String> values) {
            addCriterion("xspzxm not in", values, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmBetween(String value1, String value2) {
            addCriterion("xspzxm between", value1, value2, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmNotBetween(String value1, String value2) {
            addCriterion("xspzxm not between", value1, value2, "xspzxm");
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

        public Criteria andJhztIsNull() {
            addCriterion("jhzt is null");
            return (Criteria) this;
        }

        public Criteria andJhztIsNotNull() {
            addCriterion("jhzt is not null");
            return (Criteria) this;
        }

        public Criteria andJhztEqualTo(String value) {
            addCriterion("jhzt =", value, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztNotEqualTo(String value) {
            addCriterion("jhzt <>", value, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztGreaterThan(String value) {
            addCriterion("jhzt >", value, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztGreaterThanOrEqualTo(String value) {
            addCriterion("jhzt >=", value, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztLessThan(String value) {
            addCriterion("jhzt <", value, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztLessThanOrEqualTo(String value) {
            addCriterion("jhzt <=", value, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztLike(String value) {
            addCriterion("jhzt like", value, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztNotLike(String value) {
            addCriterion("jhzt not like", value, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztIn(List<String> values) {
            addCriterion("jhzt in", values, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztNotIn(List<String> values) {
            addCriterion("jhzt not in", values, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztBetween(String value1, String value2) {
            addCriterion("jhzt between", value1, value2, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJhztNotBetween(String value1, String value2) {
            addCriterion("jhzt not between", value1, value2, "jhzt");
            return (Criteria) this;
        }

        public Criteria andJxsbmIsNull() {
            addCriterion("jxsbm is null");
            return (Criteria) this;
        }

        public Criteria andJxsbmIsNotNull() {
            addCriterion("jxsbm is not null");
            return (Criteria) this;
        }

        public Criteria andJxsbmEqualTo(String value) {
            addCriterion("jxsbm =", value, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmNotEqualTo(String value) {
            addCriterion("jxsbm <>", value, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmGreaterThan(String value) {
            addCriterion("jxsbm >", value, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmGreaterThanOrEqualTo(String value) {
            addCriterion("jxsbm >=", value, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmLessThan(String value) {
            addCriterion("jxsbm <", value, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmLessThanOrEqualTo(String value) {
            addCriterion("jxsbm <=", value, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmLike(String value) {
            addCriterion("jxsbm like", value, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmNotLike(String value) {
            addCriterion("jxsbm not like", value, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmIn(List<String> values) {
            addCriterion("jxsbm in", values, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmNotIn(List<String> values) {
            addCriterion("jxsbm not in", values, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmBetween(String value1, String value2) {
            addCriterion("jxsbm between", value1, value2, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsbmNotBetween(String value1, String value2) {
            addCriterion("jxsbm not between", value1, value2, "jxsbm");
            return (Criteria) this;
        }

        public Criteria andJxsmcIsNull() {
            addCriterion("jxsmc is null");
            return (Criteria) this;
        }

        public Criteria andJxsmcIsNotNull() {
            addCriterion("jxsmc is not null");
            return (Criteria) this;
        }

        public Criteria andJxsmcEqualTo(String value) {
            addCriterion("jxsmc =", value, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcNotEqualTo(String value) {
            addCriterion("jxsmc <>", value, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcGreaterThan(String value) {
            addCriterion("jxsmc >", value, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcGreaterThanOrEqualTo(String value) {
            addCriterion("jxsmc >=", value, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcLessThan(String value) {
            addCriterion("jxsmc <", value, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcLessThanOrEqualTo(String value) {
            addCriterion("jxsmc <=", value, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcLike(String value) {
            addCriterion("jxsmc like", value, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcNotLike(String value) {
            addCriterion("jxsmc not like", value, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcIn(List<String> values) {
            addCriterion("jxsmc in", values, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcNotIn(List<String> values) {
            addCriterion("jxsmc not in", values, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcBetween(String value1, String value2) {
            addCriterion("jxsmc between", value1, value2, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJxsmcNotBetween(String value1, String value2) {
            addCriterion("jxsmc not between", value1, value2, "jxsmc");
            return (Criteria) this;
        }

        public Criteria andJjztIsNull() {
            addCriterion("jjzt is null");
            return (Criteria) this;
        }

        public Criteria andJjztIsNotNull() {
            addCriterion("jjzt is not null");
            return (Criteria) this;
        }

        public Criteria andJjztEqualTo(String value) {
            addCriterion("jjzt =", value, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztNotEqualTo(String value) {
            addCriterion("jjzt <>", value, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztGreaterThan(String value) {
            addCriterion("jjzt >", value, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztGreaterThanOrEqualTo(String value) {
            addCriterion("jjzt >=", value, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztLessThan(String value) {
            addCriterion("jjzt <", value, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztLessThanOrEqualTo(String value) {
            addCriterion("jjzt <=", value, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztLike(String value) {
            addCriterion("jjzt like", value, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztNotLike(String value) {
            addCriterion("jjzt not like", value, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztIn(List<String> values) {
            addCriterion("jjzt in", values, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztNotIn(List<String> values) {
            addCriterion("jjzt not in", values, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztBetween(String value1, String value2) {
            addCriterion("jjzt between", value1, value2, "jjzt");
            return (Criteria) this;
        }

        public Criteria andJjztNotBetween(String value1, String value2) {
            addCriterion("jjzt not between", value1, value2, "jjzt");
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

        public Criteria andDhlxIsNull() {
            addCriterion("dhlx is null");
            return (Criteria) this;
        }

        public Criteria andDhlxIsNotNull() {
            addCriterion("dhlx is not null");
            return (Criteria) this;
        }

        public Criteria andDhlxEqualTo(String value) {
            addCriterion("dhlx =", value, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxNotEqualTo(String value) {
            addCriterion("dhlx <>", value, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxGreaterThan(String value) {
            addCriterion("dhlx >", value, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxGreaterThanOrEqualTo(String value) {
            addCriterion("dhlx >=", value, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxLessThan(String value) {
            addCriterion("dhlx <", value, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxLessThanOrEqualTo(String value) {
            addCriterion("dhlx <=", value, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxLike(String value) {
            addCriterion("dhlx like", value, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxNotLike(String value) {
            addCriterion("dhlx not like", value, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxIn(List<String> values) {
            addCriterion("dhlx in", values, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxNotIn(List<String> values) {
            addCriterion("dhlx not in", values, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxBetween(String value1, String value2) {
            addCriterion("dhlx between", value1, value2, "dhlx");
            return (Criteria) this;
        }

        public Criteria andDhlxNotBetween(String value1, String value2) {
            addCriterion("dhlx not between", value1, value2, "dhlx");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdIsNull() {
            addCriterion("cjptcdgd is null");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdIsNotNull() {
            addCriterion("cjptcdgd is not null");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdEqualTo(String value) {
            addCriterion("cjptcdgd =", value, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdNotEqualTo(String value) {
            addCriterion("cjptcdgd <>", value, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdGreaterThan(String value) {
            addCriterion("cjptcdgd >", value, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdGreaterThanOrEqualTo(String value) {
            addCriterion("cjptcdgd >=", value, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdLessThan(String value) {
            addCriterion("cjptcdgd <", value, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdLessThanOrEqualTo(String value) {
            addCriterion("cjptcdgd <=", value, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdLike(String value) {
            addCriterion("cjptcdgd like", value, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdNotLike(String value) {
            addCriterion("cjptcdgd not like", value, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdIn(List<String> values) {
            addCriterion("cjptcdgd in", values, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdNotIn(List<String> values) {
            addCriterion("cjptcdgd not in", values, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdBetween(String value1, String value2) {
            addCriterion("cjptcdgd between", value1, value2, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcdgdNotBetween(String value1, String value2) {
            addCriterion("cjptcdgd not between", value1, value2, "cjptcdgd");
            return (Criteria) this;
        }

        public Criteria andCjptcjIsNull() {
            addCriterion("cjptcj is null");
            return (Criteria) this;
        }

        public Criteria andCjptcjIsNotNull() {
            addCriterion("cjptcj is not null");
            return (Criteria) this;
        }

        public Criteria andCjptcjEqualTo(String value) {
            addCriterion("cjptcj =", value, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjNotEqualTo(String value) {
            addCriterion("cjptcj <>", value, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjGreaterThan(String value) {
            addCriterion("cjptcj >", value, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjGreaterThanOrEqualTo(String value) {
            addCriterion("cjptcj >=", value, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjLessThan(String value) {
            addCriterion("cjptcj <", value, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjLessThanOrEqualTo(String value) {
            addCriterion("cjptcj <=", value, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjLike(String value) {
            addCriterion("cjptcj like", value, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjNotLike(String value) {
            addCriterion("cjptcj not like", value, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjIn(List<String> values) {
            addCriterion("cjptcj in", values, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjNotIn(List<String> values) {
            addCriterion("cjptcj not in", values, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjBetween(String value1, String value2) {
            addCriterion("cjptcj between", value1, value2, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andCjptcjNotBetween(String value1, String value2) {
            addCriterion("cjptcj not between", value1, value2, "cjptcj");
            return (Criteria) this;
        }

        public Criteria andQtIsNull() {
            addCriterion("qt is null");
            return (Criteria) this;
        }

        public Criteria andQtIsNotNull() {
            addCriterion("qt is not null");
            return (Criteria) this;
        }

        public Criteria andQtEqualTo(String value) {
            addCriterion("qt =", value, "qt");
            return (Criteria) this;
        }

        public Criteria andQtNotEqualTo(String value) {
            addCriterion("qt <>", value, "qt");
            return (Criteria) this;
        }

        public Criteria andQtGreaterThan(String value) {
            addCriterion("qt >", value, "qt");
            return (Criteria) this;
        }

        public Criteria andQtGreaterThanOrEqualTo(String value) {
            addCriterion("qt >=", value, "qt");
            return (Criteria) this;
        }

        public Criteria andQtLessThan(String value) {
            addCriterion("qt <", value, "qt");
            return (Criteria) this;
        }

        public Criteria andQtLessThanOrEqualTo(String value) {
            addCriterion("qt <=", value, "qt");
            return (Criteria) this;
        }

        public Criteria andQtLike(String value) {
            addCriterion("qt like", value, "qt");
            return (Criteria) this;
        }

        public Criteria andQtNotLike(String value) {
            addCriterion("qt not like", value, "qt");
            return (Criteria) this;
        }

        public Criteria andQtIn(List<String> values) {
            addCriterion("qt in", values, "qt");
            return (Criteria) this;
        }

        public Criteria andQtNotIn(List<String> values) {
            addCriterion("qt not in", values, "qt");
            return (Criteria) this;
        }

        public Criteria andQtBetween(String value1, String value2) {
            addCriterion("qt between", value1, value2, "qt");
            return (Criteria) this;
        }

        public Criteria andQtNotBetween(String value1, String value2) {
            addCriterion("qt not between", value1, value2, "qt");
            return (Criteria) this;
        }

        public Criteria andGcgdIsNull() {
            addCriterion("gcgd is null");
            return (Criteria) this;
        }

        public Criteria andGcgdIsNotNull() {
            addCriterion("gcgd is not null");
            return (Criteria) this;
        }

        public Criteria andGcgdEqualTo(String value) {
            addCriterion("gcgd =", value, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdNotEqualTo(String value) {
            addCriterion("gcgd <>", value, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdGreaterThan(String value) {
            addCriterion("gcgd >", value, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdGreaterThanOrEqualTo(String value) {
            addCriterion("gcgd >=", value, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdLessThan(String value) {
            addCriterion("gcgd <", value, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdLessThanOrEqualTo(String value) {
            addCriterion("gcgd <=", value, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdLike(String value) {
            addCriterion("gcgd like", value, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdNotLike(String value) {
            addCriterion("gcgd not like", value, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdIn(List<String> values) {
            addCriterion("gcgd in", values, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdNotIn(List<String> values) {
            addCriterion("gcgd not in", values, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdBetween(String value1, String value2) {
            addCriterion("gcgd between", value1, value2, "gcgd");
            return (Criteria) this;
        }

        public Criteria andGcgdNotBetween(String value1, String value2) {
            addCriterion("gcgd not between", value1, value2, "gcgd");
            return (Criteria) this;
        }

        public Criteria andJhslIsNull() {
            addCriterion("jhsl is null");
            return (Criteria) this;
        }

        public Criteria andJhslIsNotNull() {
            addCriterion("jhsl is not null");
            return (Criteria) this;
        }

        public Criteria andJhslEqualTo(String value) {
            addCriterion("jhsl =", value, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslNotEqualTo(String value) {
            addCriterion("jhsl <>", value, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslGreaterThan(String value) {
            addCriterion("jhsl >", value, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslGreaterThanOrEqualTo(String value) {
            addCriterion("jhsl >=", value, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslLessThan(String value) {
            addCriterion("jhsl <", value, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslLessThanOrEqualTo(String value) {
            addCriterion("jhsl <=", value, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslLike(String value) {
            addCriterion("jhsl like", value, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslNotLike(String value) {
            addCriterion("jhsl not like", value, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslIn(List<String> values) {
            addCriterion("jhsl in", values, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslNotIn(List<String> values) {
            addCriterion("jhsl not in", values, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslBetween(String value1, String value2) {
            addCriterion("jhsl between", value1, value2, "jhsl");
            return (Criteria) this;
        }

        public Criteria andJhslNotBetween(String value1, String value2) {
            addCriterion("jhsl not between", value1, value2, "jhsl");
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