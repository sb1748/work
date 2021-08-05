package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapScpgTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapScpgTbExample() {
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

        public Criteria andDdhIsNull() {
            addCriterion("ddh is null");
            return (Criteria) this;
        }

        public Criteria andDdhIsNotNull() {
            addCriterion("ddh is not null");
            return (Criteria) this;
        }

        public Criteria andDdhEqualTo(String value) {
            addCriterion("ddh =", value, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhNotEqualTo(String value) {
            addCriterion("ddh <>", value, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhGreaterThan(String value) {
            addCriterion("ddh >", value, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhGreaterThanOrEqualTo(String value) {
            addCriterion("ddh >=", value, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhLessThan(String value) {
            addCriterion("ddh <", value, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhLessThanOrEqualTo(String value) {
            addCriterion("ddh <=", value, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhLike(String value) {
            addCriterion("ddh like", value, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhNotLike(String value) {
            addCriterion("ddh not like", value, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhIn(List<String> values) {
            addCriterion("ddh in", values, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhNotIn(List<String> values) {
            addCriterion("ddh not in", values, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhBetween(String value1, String value2) {
            addCriterion("ddh between", value1, value2, "ddh");
            return (Criteria) this;
        }

        public Criteria andDdhNotBetween(String value1, String value2) {
            addCriterion("ddh not between", value1, value2, "ddh");
            return (Criteria) this;
        }

        public Criteria andLshIsNull() {
            addCriterion("lsh is null");
            return (Criteria) this;
        }

        public Criteria andLshIsNotNull() {
            addCriterion("lsh is not null");
            return (Criteria) this;
        }

        public Criteria andLshEqualTo(String value) {
            addCriterion("lsh =", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshNotEqualTo(String value) {
            addCriterion("lsh <>", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshGreaterThan(String value) {
            addCriterion("lsh >", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshGreaterThanOrEqualTo(String value) {
            addCriterion("lsh >=", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshLessThan(String value) {
            addCriterion("lsh <", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshLessThanOrEqualTo(String value) {
            addCriterion("lsh <=", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshLike(String value) {
            addCriterion("lsh like", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshNotLike(String value) {
            addCriterion("lsh not like", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshIn(List<String> values) {
            addCriterion("lsh in", values, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshNotIn(List<String> values) {
            addCriterion("lsh not in", values, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshBetween(String value1, String value2) {
            addCriterion("lsh between", value1, value2, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshNotBetween(String value1, String value2) {
            addCriterion("lsh not between", value1, value2, "lsh");
            return (Criteria) this;
        }

        public Criteria andPpIsNull() {
            addCriterion("pp is null");
            return (Criteria) this;
        }

        public Criteria andPpIsNotNull() {
            addCriterion("pp is not null");
            return (Criteria) this;
        }

        public Criteria andPpEqualTo(String value) {
            addCriterion("pp =", value, "pp");
            return (Criteria) this;
        }

        public Criteria andPpNotEqualTo(String value) {
            addCriterion("pp <>", value, "pp");
            return (Criteria) this;
        }

        public Criteria andPpGreaterThan(String value) {
            addCriterion("pp >", value, "pp");
            return (Criteria) this;
        }

        public Criteria andPpGreaterThanOrEqualTo(String value) {
            addCriterion("pp >=", value, "pp");
            return (Criteria) this;
        }

        public Criteria andPpLessThan(String value) {
            addCriterion("pp <", value, "pp");
            return (Criteria) this;
        }

        public Criteria andPpLessThanOrEqualTo(String value) {
            addCriterion("pp <=", value, "pp");
            return (Criteria) this;
        }

        public Criteria andPpLike(String value) {
            addCriterion("pp like", value, "pp");
            return (Criteria) this;
        }

        public Criteria andPpNotLike(String value) {
            addCriterion("pp not like", value, "pp");
            return (Criteria) this;
        }

        public Criteria andPpIn(List<String> values) {
            addCriterion("pp in", values, "pp");
            return (Criteria) this;
        }

        public Criteria andPpNotIn(List<String> values) {
            addCriterion("pp not in", values, "pp");
            return (Criteria) this;
        }

        public Criteria andPpBetween(String value1, String value2) {
            addCriterion("pp between", value1, value2, "pp");
            return (Criteria) this;
        }

        public Criteria andPpNotBetween(String value1, String value2) {
            addCriterion("pp not between", value1, value2, "pp");
            return (Criteria) this;
        }

        public Criteria andJhxdrqIsNull() {
            addCriterion("jhxdrq is null");
            return (Criteria) this;
        }

        public Criteria andJhxdrqIsNotNull() {
            addCriterion("jhxdrq is not null");
            return (Criteria) this;
        }

        public Criteria andJhxdrqEqualTo(String value) {
            addCriterion("jhxdrq =", value, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqNotEqualTo(String value) {
            addCriterion("jhxdrq <>", value, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqGreaterThan(String value) {
            addCriterion("jhxdrq >", value, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqGreaterThanOrEqualTo(String value) {
            addCriterion("jhxdrq >=", value, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqLessThan(String value) {
            addCriterion("jhxdrq <", value, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqLessThanOrEqualTo(String value) {
            addCriterion("jhxdrq <=", value, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqLike(String value) {
            addCriterion("jhxdrq like", value, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqNotLike(String value) {
            addCriterion("jhxdrq not like", value, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqIn(List<String> values) {
            addCriterion("jhxdrq in", values, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqNotIn(List<String> values) {
            addCriterion("jhxdrq not in", values, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqBetween(String value1, String value2) {
            addCriterion("jhxdrq between", value1, value2, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andJhxdrqNotBetween(String value1, String value2) {
            addCriterion("jhxdrq not between", value1, value2, "jhxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqIsNull() {
            addCriterion("sjxdrq is null");
            return (Criteria) this;
        }

        public Criteria andSjxdrqIsNotNull() {
            addCriterion("sjxdrq is not null");
            return (Criteria) this;
        }

        public Criteria andSjxdrqEqualTo(String value) {
            addCriterion("sjxdrq =", value, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqNotEqualTo(String value) {
            addCriterion("sjxdrq <>", value, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqGreaterThan(String value) {
            addCriterion("sjxdrq >", value, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqGreaterThanOrEqualTo(String value) {
            addCriterion("sjxdrq >=", value, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqLessThan(String value) {
            addCriterion("sjxdrq <", value, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqLessThanOrEqualTo(String value) {
            addCriterion("sjxdrq <=", value, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqLike(String value) {
            addCriterion("sjxdrq like", value, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqNotLike(String value) {
            addCriterion("sjxdrq not like", value, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqIn(List<String> values) {
            addCriterion("sjxdrq in", values, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqNotIn(List<String> values) {
            addCriterion("sjxdrq not in", values, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqBetween(String value1, String value2) {
            addCriterion("sjxdrq between", value1, value2, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andSjxdrqNotBetween(String value1, String value2) {
            addCriterion("sjxdrq not between", value1, value2, "sjxdrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqIsNull() {
            addCriterion("jhjhrq is null");
            return (Criteria) this;
        }

        public Criteria andJhjhrqIsNotNull() {
            addCriterion("jhjhrq is not null");
            return (Criteria) this;
        }

        public Criteria andJhjhrqEqualTo(String value) {
            addCriterion("jhjhrq =", value, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqNotEqualTo(String value) {
            addCriterion("jhjhrq <>", value, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqGreaterThan(String value) {
            addCriterion("jhjhrq >", value, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqGreaterThanOrEqualTo(String value) {
            addCriterion("jhjhrq >=", value, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqLessThan(String value) {
            addCriterion("jhjhrq <", value, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqLessThanOrEqualTo(String value) {
            addCriterion("jhjhrq <=", value, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqLike(String value) {
            addCriterion("jhjhrq like", value, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqNotLike(String value) {
            addCriterion("jhjhrq not like", value, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqIn(List<String> values) {
            addCriterion("jhjhrq in", values, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqNotIn(List<String> values) {
            addCriterion("jhjhrq not in", values, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqBetween(String value1, String value2) {
            addCriterion("jhjhrq between", value1, value2, "jhjhrq");
            return (Criteria) this;
        }

        public Criteria andJhjhrqNotBetween(String value1, String value2) {
            addCriterion("jhjhrq not between", value1, value2, "jhjhrq");
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

        public Criteria andXsddhIsNull() {
            addCriterion("xsddh is null");
            return (Criteria) this;
        }

        public Criteria andXsddhIsNotNull() {
            addCriterion("xsddh is not null");
            return (Criteria) this;
        }

        public Criteria andXsddhEqualTo(String value) {
            addCriterion("xsddh =", value, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhNotEqualTo(String value) {
            addCriterion("xsddh <>", value, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhGreaterThan(String value) {
            addCriterion("xsddh >", value, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhGreaterThanOrEqualTo(String value) {
            addCriterion("xsddh >=", value, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhLessThan(String value) {
            addCriterion("xsddh <", value, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhLessThanOrEqualTo(String value) {
            addCriterion("xsddh <=", value, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhLike(String value) {
            addCriterion("xsddh like", value, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhNotLike(String value) {
            addCriterion("xsddh not like", value, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhIn(List<String> values) {
            addCriterion("xsddh in", values, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhNotIn(List<String> values) {
            addCriterion("xsddh not in", values, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhBetween(String value1, String value2) {
            addCriterion("xsddh between", value1, value2, "xsddh");
            return (Criteria) this;
        }

        public Criteria andXsddhNotBetween(String value1, String value2) {
            addCriterion("xsddh not between", value1, value2, "xsddh");
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

        public Criteria andWlmcIsNull() {
            addCriterion("wlmc is null");
            return (Criteria) this;
        }

        public Criteria andWlmcIsNotNull() {
            addCriterion("wlmc is not null");
            return (Criteria) this;
        }

        public Criteria andWlmcEqualTo(String value) {
            addCriterion("wlmc =", value, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcNotEqualTo(String value) {
            addCriterion("wlmc <>", value, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcGreaterThan(String value) {
            addCriterion("wlmc >", value, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcGreaterThanOrEqualTo(String value) {
            addCriterion("wlmc >=", value, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcLessThan(String value) {
            addCriterion("wlmc <", value, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcLessThanOrEqualTo(String value) {
            addCriterion("wlmc <=", value, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcLike(String value) {
            addCriterion("wlmc like", value, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcNotLike(String value) {
            addCriterion("wlmc not like", value, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcIn(List<String> values) {
            addCriterion("wlmc in", values, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcNotIn(List<String> values) {
            addCriterion("wlmc not in", values, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcBetween(String value1, String value2) {
            addCriterion("wlmc between", value1, value2, "wlmc");
            return (Criteria) this;
        }

        public Criteria andWlmcNotBetween(String value1, String value2) {
            addCriterion("wlmc not between", value1, value2, "wlmc");
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

        public Criteria andCpggIsNull() {
            addCriterion("cpgg is null");
            return (Criteria) this;
        }

        public Criteria andCpggIsNotNull() {
            addCriterion("cpgg is not null");
            return (Criteria) this;
        }

        public Criteria andCpggEqualTo(String value) {
            addCriterion("cpgg =", value, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggNotEqualTo(String value) {
            addCriterion("cpgg <>", value, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggGreaterThan(String value) {
            addCriterion("cpgg >", value, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggGreaterThanOrEqualTo(String value) {
            addCriterion("cpgg >=", value, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggLessThan(String value) {
            addCriterion("cpgg <", value, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggLessThanOrEqualTo(String value) {
            addCriterion("cpgg <=", value, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggLike(String value) {
            addCriterion("cpgg like", value, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggNotLike(String value) {
            addCriterion("cpgg not like", value, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggIn(List<String> values) {
            addCriterion("cpgg in", values, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggNotIn(List<String> values) {
            addCriterion("cpgg not in", values, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggBetween(String value1, String value2) {
            addCriterion("cpgg between", value1, value2, "cpgg");
            return (Criteria) this;
        }

        public Criteria andCpggNotBetween(String value1, String value2) {
            addCriterion("cpgg not between", value1, value2, "cpgg");
            return (Criteria) this;
        }

        public Criteria andPsphIsNull() {
            addCriterion("psph is null");
            return (Criteria) this;
        }

        public Criteria andPsphIsNotNull() {
            addCriterion("psph is not null");
            return (Criteria) this;
        }

        public Criteria andPsphEqualTo(String value) {
            addCriterion("psph =", value, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphNotEqualTo(String value) {
            addCriterion("psph <>", value, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphGreaterThan(String value) {
            addCriterion("psph >", value, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphGreaterThanOrEqualTo(String value) {
            addCriterion("psph >=", value, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphLessThan(String value) {
            addCriterion("psph <", value, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphLessThanOrEqualTo(String value) {
            addCriterion("psph <=", value, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphLike(String value) {
            addCriterion("psph like", value, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphNotLike(String value) {
            addCriterion("psph not like", value, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphIn(List<String> values) {
            addCriterion("psph in", values, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphNotIn(List<String> values) {
            addCriterion("psph not in", values, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphBetween(String value1, String value2) {
            addCriterion("psph between", value1, value2, "psph");
            return (Criteria) this;
        }

        public Criteria andPsphNotBetween(String value1, String value2) {
            addCriterion("psph not between", value1, value2, "psph");
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

        public Criteria andSfbbjscIsNull() {
            addCriterion("sfbbjsc is null");
            return (Criteria) this;
        }

        public Criteria andSfbbjscIsNotNull() {
            addCriterion("sfbbjsc is not null");
            return (Criteria) this;
        }

        public Criteria andSfbbjscEqualTo(String value) {
            addCriterion("sfbbjsc =", value, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscNotEqualTo(String value) {
            addCriterion("sfbbjsc <>", value, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscGreaterThan(String value) {
            addCriterion("sfbbjsc >", value, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscGreaterThanOrEqualTo(String value) {
            addCriterion("sfbbjsc >=", value, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscLessThan(String value) {
            addCriterion("sfbbjsc <", value, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscLessThanOrEqualTo(String value) {
            addCriterion("sfbbjsc <=", value, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscLike(String value) {
            addCriterion("sfbbjsc like", value, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscNotLike(String value) {
            addCriterion("sfbbjsc not like", value, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscIn(List<String> values) {
            addCriterion("sfbbjsc in", values, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscNotIn(List<String> values) {
            addCriterion("sfbbjsc not in", values, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscBetween(String value1, String value2) {
            addCriterion("sfbbjsc between", value1, value2, "sfbbjsc");
            return (Criteria) this;
        }

        public Criteria andSfbbjscNotBetween(String value1, String value2) {
            addCriterion("sfbbjsc not between", value1, value2, "sfbbjsc");
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

        public Criteria andDyzsIsNull() {
            addCriterion("dyzs is null");
            return (Criteria) this;
        }

        public Criteria andDyzsIsNotNull() {
            addCriterion("dyzs is not null");
            return (Criteria) this;
        }

        public Criteria andDyzsEqualTo(Integer value) {
            addCriterion("dyzs =", value, "dyzs");
            return (Criteria) this;
        }

        public Criteria andDyzsNotEqualTo(Integer value) {
            addCriterion("dyzs <>", value, "dyzs");
            return (Criteria) this;
        }

        public Criteria andDyzsGreaterThan(Integer value) {
            addCriterion("dyzs >", value, "dyzs");
            return (Criteria) this;
        }

        public Criteria andDyzsGreaterThanOrEqualTo(Integer value) {
            addCriterion("dyzs >=", value, "dyzs");
            return (Criteria) this;
        }

        public Criteria andDyzsLessThan(Integer value) {
            addCriterion("dyzs <", value, "dyzs");
            return (Criteria) this;
        }

        public Criteria andDyzsLessThanOrEqualTo(Integer value) {
            addCriterion("dyzs <=", value, "dyzs");
            return (Criteria) this;
        }

        public Criteria andDyzsIn(List<Integer> values) {
            addCriterion("dyzs in", values, "dyzs");
            return (Criteria) this;
        }

        public Criteria andDyzsNotIn(List<Integer> values) {
            addCriterion("dyzs not in", values, "dyzs");
            return (Criteria) this;
        }

        public Criteria andDyzsBetween(Integer value1, Integer value2) {
            addCriterion("dyzs between", value1, value2, "dyzs");
            return (Criteria) this;
        }

        public Criteria andDyzsNotBetween(Integer value1, Integer value2) {
            addCriterion("dyzs not between", value1, value2, "dyzs");
            return (Criteria) this;
        }

        public Criteria andTempnameIsNull() {
            addCriterion("tempname is null");
            return (Criteria) this;
        }

        public Criteria andTempnameIsNotNull() {
            addCriterion("tempname is not null");
            return (Criteria) this;
        }

        public Criteria andTempnameEqualTo(String value) {
            addCriterion("tempname =", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameNotEqualTo(String value) {
            addCriterion("tempname <>", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameGreaterThan(String value) {
            addCriterion("tempname >", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameGreaterThanOrEqualTo(String value) {
            addCriterion("tempname >=", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameLessThan(String value) {
            addCriterion("tempname <", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameLessThanOrEqualTo(String value) {
            addCriterion("tempname <=", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameLike(String value) {
            addCriterion("tempname like", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameNotLike(String value) {
            addCriterion("tempname not like", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameIn(List<String> values) {
            addCriterion("tempname in", values, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameNotIn(List<String> values) {
            addCriterion("tempname not in", values, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameBetween(String value1, String value2) {
            addCriterion("tempname between", value1, value2, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameNotBetween(String value1, String value2) {
            addCriterion("tempname not between", value1, value2, "tempname");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNull() {
            addCriterion("barCode is null");
            return (Criteria) this;
        }

        public Criteria andBarcodeIsNotNull() {
            addCriterion("barCode is not null");
            return (Criteria) this;
        }

        public Criteria andBarcodeEqualTo(String value) {
            addCriterion("barCode =", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotEqualTo(String value) {
            addCriterion("barCode <>", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThan(String value) {
            addCriterion("barCode >", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeGreaterThanOrEqualTo(String value) {
            addCriterion("barCode >=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThan(String value) {
            addCriterion("barCode <", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLessThanOrEqualTo(String value) {
            addCriterion("barCode <=", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeLike(String value) {
            addCriterion("barCode like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotLike(String value) {
            addCriterion("barCode not like", value, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeIn(List<String> values) {
            addCriterion("barCode in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotIn(List<String> values) {
            addCriterion("barCode not in", values, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeBetween(String value1, String value2) {
            addCriterion("barCode between", value1, value2, "barcode");
            return (Criteria) this;
        }

        public Criteria andBarcodeNotBetween(String value1, String value2) {
            addCriterion("barCode not between", value1, value2, "barcode");
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