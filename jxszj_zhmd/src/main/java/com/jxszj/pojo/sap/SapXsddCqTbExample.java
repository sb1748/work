package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapXsddCqTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapXsddCqTbExample() {
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

        public Criteria andXspzIsNull() {
            addCriterion("xspz is null");
            return (Criteria) this;
        }

        public Criteria andXspzIsNotNull() {
            addCriterion("xspz is not null");
            return (Criteria) this;
        }

        public Criteria andXspzEqualTo(String value) {
            addCriterion("xspz =", value, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzNotEqualTo(String value) {
            addCriterion("xspz <>", value, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzGreaterThan(String value) {
            addCriterion("xspz >", value, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzGreaterThanOrEqualTo(String value) {
            addCriterion("xspz >=", value, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzLessThan(String value) {
            addCriterion("xspz <", value, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzLessThanOrEqualTo(String value) {
            addCriterion("xspz <=", value, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzLike(String value) {
            addCriterion("xspz like", value, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzNotLike(String value) {
            addCriterion("xspz not like", value, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzIn(List<String> values) {
            addCriterion("xspz in", values, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzNotIn(List<String> values) {
            addCriterion("xspz not in", values, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzBetween(String value1, String value2) {
            addCriterion("xspz between", value1, value2, "xspz");
            return (Criteria) this;
        }

        public Criteria andXspzNotBetween(String value1, String value2) {
            addCriterion("xspz not between", value1, value2, "xspz");
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

        public Criteria andXspzxmEqualTo(Integer value) {
            addCriterion("xspzxm =", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmNotEqualTo(Integer value) {
            addCriterion("xspzxm <>", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmGreaterThan(Integer value) {
            addCriterion("xspzxm >", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmGreaterThanOrEqualTo(Integer value) {
            addCriterion("xspzxm >=", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmLessThan(Integer value) {
            addCriterion("xspzxm <", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmLessThanOrEqualTo(Integer value) {
            addCriterion("xspzxm <=", value, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmIn(List<Integer> values) {
            addCriterion("xspzxm in", values, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmNotIn(List<Integer> values) {
            addCriterion("xspzxm not in", values, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmBetween(Integer value1, Integer value2) {
            addCriterion("xspzxm between", value1, value2, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andXspzxmNotBetween(Integer value1, Integer value2) {
            addCriterion("xspzxm not between", value1, value2, "xspzxm");
            return (Criteria) this;
        }

        public Criteria andWlIsNull() {
            addCriterion("wl is null");
            return (Criteria) this;
        }

        public Criteria andWlIsNotNull() {
            addCriterion("wl is not null");
            return (Criteria) this;
        }

        public Criteria andWlEqualTo(String value) {
            addCriterion("wl =", value, "wl");
            return (Criteria) this;
        }

        public Criteria andWlNotEqualTo(String value) {
            addCriterion("wl <>", value, "wl");
            return (Criteria) this;
        }

        public Criteria andWlGreaterThan(String value) {
            addCriterion("wl >", value, "wl");
            return (Criteria) this;
        }

        public Criteria andWlGreaterThanOrEqualTo(String value) {
            addCriterion("wl >=", value, "wl");
            return (Criteria) this;
        }

        public Criteria andWlLessThan(String value) {
            addCriterion("wl <", value, "wl");
            return (Criteria) this;
        }

        public Criteria andWlLessThanOrEqualTo(String value) {
            addCriterion("wl <=", value, "wl");
            return (Criteria) this;
        }

        public Criteria andWlLike(String value) {
            addCriterion("wl like", value, "wl");
            return (Criteria) this;
        }

        public Criteria andWlNotLike(String value) {
            addCriterion("wl not like", value, "wl");
            return (Criteria) this;
        }

        public Criteria andWlIn(List<String> values) {
            addCriterion("wl in", values, "wl");
            return (Criteria) this;
        }

        public Criteria andWlNotIn(List<String> values) {
            addCriterion("wl not in", values, "wl");
            return (Criteria) this;
        }

        public Criteria andWlBetween(String value1, String value2) {
            addCriterion("wl between", value1, value2, "wl");
            return (Criteria) this;
        }

        public Criteria andWlNotBetween(String value1, String value2) {
            addCriterion("wl not between", value1, value2, "wl");
            return (Criteria) this;
        }

        public Criteria andSlIsNull() {
            addCriterion("sl is null");
            return (Criteria) this;
        }

        public Criteria andSlIsNotNull() {
            addCriterion("sl is not null");
            return (Criteria) this;
        }

        public Criteria andSlEqualTo(Integer value) {
            addCriterion("sl =", value, "sl");
            return (Criteria) this;
        }

        public Criteria andSlNotEqualTo(Integer value) {
            addCriterion("sl <>", value, "sl");
            return (Criteria) this;
        }

        public Criteria andSlGreaterThan(Integer value) {
            addCriterion("sl >", value, "sl");
            return (Criteria) this;
        }

        public Criteria andSlGreaterThanOrEqualTo(Integer value) {
            addCriterion("sl >=", value, "sl");
            return (Criteria) this;
        }

        public Criteria andSlLessThan(Integer value) {
            addCriterion("sl <", value, "sl");
            return (Criteria) this;
        }

        public Criteria andSlLessThanOrEqualTo(Integer value) {
            addCriterion("sl <=", value, "sl");
            return (Criteria) this;
        }

        public Criteria andSlIn(List<Integer> values) {
            addCriterion("sl in", values, "sl");
            return (Criteria) this;
        }

        public Criteria andSlNotIn(List<Integer> values) {
            addCriterion("sl not in", values, "sl");
            return (Criteria) this;
        }

        public Criteria andSlBetween(Integer value1, Integer value2) {
            addCriterion("sl between", value1, value2, "sl");
            return (Criteria) this;
        }

        public Criteria andSlNotBetween(Integer value1, Integer value2) {
            addCriterion("sl not between", value1, value2, "sl");
            return (Criteria) this;
        }

        public Criteria andKhckIsNull() {
            addCriterion("khck is null");
            return (Criteria) this;
        }

        public Criteria andKhckIsNotNull() {
            addCriterion("khck is not null");
            return (Criteria) this;
        }

        public Criteria andKhckEqualTo(String value) {
            addCriterion("khck =", value, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckNotEqualTo(String value) {
            addCriterion("khck <>", value, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckGreaterThan(String value) {
            addCriterion("khck >", value, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckGreaterThanOrEqualTo(String value) {
            addCriterion("khck >=", value, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckLessThan(String value) {
            addCriterion("khck <", value, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckLessThanOrEqualTo(String value) {
            addCriterion("khck <=", value, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckLike(String value) {
            addCriterion("khck like", value, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckNotLike(String value) {
            addCriterion("khck not like", value, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckIn(List<String> values) {
            addCriterion("khck in", values, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckNotIn(List<String> values) {
            addCriterion("khck not in", values, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckBetween(String value1, String value2) {
            addCriterion("khck between", value1, value2, "khck");
            return (Criteria) this;
        }

        public Criteria andKhckNotBetween(String value1, String value2) {
            addCriterion("khck not between", value1, value2, "khck");
            return (Criteria) this;
        }

        public Criteria andSdfIsNull() {
            addCriterion("sdf is null");
            return (Criteria) this;
        }

        public Criteria andSdfIsNotNull() {
            addCriterion("sdf is not null");
            return (Criteria) this;
        }

        public Criteria andSdfEqualTo(String value) {
            addCriterion("sdf =", value, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfNotEqualTo(String value) {
            addCriterion("sdf <>", value, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfGreaterThan(String value) {
            addCriterion("sdf >", value, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfGreaterThanOrEqualTo(String value) {
            addCriterion("sdf >=", value, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfLessThan(String value) {
            addCriterion("sdf <", value, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfLessThanOrEqualTo(String value) {
            addCriterion("sdf <=", value, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfLike(String value) {
            addCriterion("sdf like", value, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfNotLike(String value) {
            addCriterion("sdf not like", value, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfIn(List<String> values) {
            addCriterion("sdf in", values, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfNotIn(List<String> values) {
            addCriterion("sdf not in", values, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfBetween(String value1, String value2) {
            addCriterion("sdf between", value1, value2, "sdf");
            return (Criteria) this;
        }

        public Criteria andSdfNotBetween(String value1, String value2) {
            addCriterion("sdf not between", value1, value2, "sdf");
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

        public Criteria andJjyyIsNull() {
            addCriterion("jjyy is null");
            return (Criteria) this;
        }

        public Criteria andJjyyIsNotNull() {
            addCriterion("jjyy is not null");
            return (Criteria) this;
        }

        public Criteria andJjyyEqualTo(String value) {
            addCriterion("jjyy =", value, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyNotEqualTo(String value) {
            addCriterion("jjyy <>", value, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyGreaterThan(String value) {
            addCriterion("jjyy >", value, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyGreaterThanOrEqualTo(String value) {
            addCriterion("jjyy >=", value, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyLessThan(String value) {
            addCriterion("jjyy <", value, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyLessThanOrEqualTo(String value) {
            addCriterion("jjyy <=", value, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyLike(String value) {
            addCriterion("jjyy like", value, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyNotLike(String value) {
            addCriterion("jjyy not like", value, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyIn(List<String> values) {
            addCriterion("jjyy in", values, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyNotIn(List<String> values) {
            addCriterion("jjyy not in", values, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyBetween(String value1, String value2) {
            addCriterion("jjyy between", value1, value2, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJjyyNotBetween(String value1, String value2) {
            addCriterion("jjyy not between", value1, value2, "jjyy");
            return (Criteria) this;
        }

        public Criteria andJhrqIsNull() {
            addCriterion("jhrq is null");
            return (Criteria) this;
        }

        public Criteria andJhrqIsNotNull() {
            addCriterion("jhrq is not null");
            return (Criteria) this;
        }

        public Criteria andJhrqEqualTo(String value) {
            addCriterion("jhrq =", value, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqNotEqualTo(String value) {
            addCriterion("jhrq <>", value, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqGreaterThan(String value) {
            addCriterion("jhrq >", value, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqGreaterThanOrEqualTo(String value) {
            addCriterion("jhrq >=", value, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqLessThan(String value) {
            addCriterion("jhrq <", value, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqLessThanOrEqualTo(String value) {
            addCriterion("jhrq <=", value, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqLike(String value) {
            addCriterion("jhrq like", value, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqNotLike(String value) {
            addCriterion("jhrq not like", value, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqIn(List<String> values) {
            addCriterion("jhrq in", values, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqNotIn(List<String> values) {
            addCriterion("jhrq not in", values, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqBetween(String value1, String value2) {
            addCriterion("jhrq between", value1, value2, "jhrq");
            return (Criteria) this;
        }

        public Criteria andJhrqNotBetween(String value1, String value2) {
            addCriterion("jhrq not between", value1, value2, "jhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqIsNull() {
            addCriterion("njhrq is null");
            return (Criteria) this;
        }

        public Criteria andNjhrqIsNotNull() {
            addCriterion("njhrq is not null");
            return (Criteria) this;
        }

        public Criteria andNjhrqEqualTo(String value) {
            addCriterion("njhrq =", value, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqNotEqualTo(String value) {
            addCriterion("njhrq <>", value, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqGreaterThan(String value) {
            addCriterion("njhrq >", value, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqGreaterThanOrEqualTo(String value) {
            addCriterion("njhrq >=", value, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqLessThan(String value) {
            addCriterion("njhrq <", value, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqLessThanOrEqualTo(String value) {
            addCriterion("njhrq <=", value, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqLike(String value) {
            addCriterion("njhrq like", value, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqNotLike(String value) {
            addCriterion("njhrq not like", value, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqIn(List<String> values) {
            addCriterion("njhrq in", values, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqNotIn(List<String> values) {
            addCriterion("njhrq not in", values, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqBetween(String value1, String value2) {
            addCriterion("njhrq between", value1, value2, "njhrq");
            return (Criteria) this;
        }

        public Criteria andNjhrqNotBetween(String value1, String value2) {
            addCriterion("njhrq not between", value1, value2, "njhrq");
            return (Criteria) this;
        }

        public Criteria andWlzIsNull() {
            addCriterion("wlz is null");
            return (Criteria) this;
        }

        public Criteria andWlzIsNotNull() {
            addCriterion("wlz is not null");
            return (Criteria) this;
        }

        public Criteria andWlzEqualTo(String value) {
            addCriterion("wlz =", value, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzNotEqualTo(String value) {
            addCriterion("wlz <>", value, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzGreaterThan(String value) {
            addCriterion("wlz >", value, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzGreaterThanOrEqualTo(String value) {
            addCriterion("wlz >=", value, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzLessThan(String value) {
            addCriterion("wlz <", value, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzLessThanOrEqualTo(String value) {
            addCriterion("wlz <=", value, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzLike(String value) {
            addCriterion("wlz like", value, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzNotLike(String value) {
            addCriterion("wlz not like", value, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzIn(List<String> values) {
            addCriterion("wlz in", values, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzNotIn(List<String> values) {
            addCriterion("wlz not in", values, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzBetween(String value1, String value2) {
            addCriterion("wlz between", value1, value2, "wlz");
            return (Criteria) this;
        }

        public Criteria andWlzNotBetween(String value1, String value2) {
            addCriterion("wlz not between", value1, value2, "wlz");
            return (Criteria) this;
        }

        public Criteria andSsfzIsNull() {
            addCriterion("ssfz is null");
            return (Criteria) this;
        }

        public Criteria andSsfzIsNotNull() {
            addCriterion("ssfz is not null");
            return (Criteria) this;
        }

        public Criteria andSsfzEqualTo(String value) {
            addCriterion("ssfz =", value, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzNotEqualTo(String value) {
            addCriterion("ssfz <>", value, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzGreaterThan(String value) {
            addCriterion("ssfz >", value, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzGreaterThanOrEqualTo(String value) {
            addCriterion("ssfz >=", value, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzLessThan(String value) {
            addCriterion("ssfz <", value, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzLessThanOrEqualTo(String value) {
            addCriterion("ssfz <=", value, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzLike(String value) {
            addCriterion("ssfz like", value, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzNotLike(String value) {
            addCriterion("ssfz not like", value, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzIn(List<String> values) {
            addCriterion("ssfz in", values, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzNotIn(List<String> values) {
            addCriterion("ssfz not in", values, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzBetween(String value1, String value2) {
            addCriterion("ssfz between", value1, value2, "ssfz");
            return (Criteria) this;
        }

        public Criteria andSsfzNotBetween(String value1, String value2) {
            addCriterion("ssfz not between", value1, value2, "ssfz");
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

        public Criteria andXgrqIsNull() {
            addCriterion("xgrq is null");
            return (Criteria) this;
        }

        public Criteria andXgrqIsNotNull() {
            addCriterion("xgrq is not null");
            return (Criteria) this;
        }

        public Criteria andXgrqEqualTo(String value) {
            addCriterion("xgrq =", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqNotEqualTo(String value) {
            addCriterion("xgrq <>", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqGreaterThan(String value) {
            addCriterion("xgrq >", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqGreaterThanOrEqualTo(String value) {
            addCriterion("xgrq >=", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqLessThan(String value) {
            addCriterion("xgrq <", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqLessThanOrEqualTo(String value) {
            addCriterion("xgrq <=", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqLike(String value) {
            addCriterion("xgrq like", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqNotLike(String value) {
            addCriterion("xgrq not like", value, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqIn(List<String> values) {
            addCriterion("xgrq in", values, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqNotIn(List<String> values) {
            addCriterion("xgrq not in", values, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqBetween(String value1, String value2) {
            addCriterion("xgrq between", value1, value2, "xgrq");
            return (Criteria) this;
        }

        public Criteria andXgrqNotBetween(String value1, String value2) {
            addCriterion("xgrq not between", value1, value2, "xgrq");
            return (Criteria) this;
        }

        public Criteria andTcrqIsNull() {
            addCriterion("tcrq is null");
            return (Criteria) this;
        }

        public Criteria andTcrqIsNotNull() {
            addCriterion("tcrq is not null");
            return (Criteria) this;
        }

        public Criteria andTcrqEqualTo(String value) {
            addCriterion("tcrq =", value, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqNotEqualTo(String value) {
            addCriterion("tcrq <>", value, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqGreaterThan(String value) {
            addCriterion("tcrq >", value, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqGreaterThanOrEqualTo(String value) {
            addCriterion("tcrq >=", value, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqLessThan(String value) {
            addCriterion("tcrq <", value, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqLessThanOrEqualTo(String value) {
            addCriterion("tcrq <=", value, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqLike(String value) {
            addCriterion("tcrq like", value, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqNotLike(String value) {
            addCriterion("tcrq not like", value, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqIn(List<String> values) {
            addCriterion("tcrq in", values, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqNotIn(List<String> values) {
            addCriterion("tcrq not in", values, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqBetween(String value1, String value2) {
            addCriterion("tcrq between", value1, value2, "tcrq");
            return (Criteria) this;
        }

        public Criteria andTcrqNotBetween(String value1, String value2) {
            addCriterion("tcrq not between", value1, value2, "tcrq");
            return (Criteria) this;
        }

        public Criteria andYyIsNull() {
            addCriterion("yy is null");
            return (Criteria) this;
        }

        public Criteria andYyIsNotNull() {
            addCriterion("yy is not null");
            return (Criteria) this;
        }

        public Criteria andYyEqualTo(String value) {
            addCriterion("yy =", value, "yy");
            return (Criteria) this;
        }

        public Criteria andYyNotEqualTo(String value) {
            addCriterion("yy <>", value, "yy");
            return (Criteria) this;
        }

        public Criteria andYyGreaterThan(String value) {
            addCriterion("yy >", value, "yy");
            return (Criteria) this;
        }

        public Criteria andYyGreaterThanOrEqualTo(String value) {
            addCriterion("yy >=", value, "yy");
            return (Criteria) this;
        }

        public Criteria andYyLessThan(String value) {
            addCriterion("yy <", value, "yy");
            return (Criteria) this;
        }

        public Criteria andYyLessThanOrEqualTo(String value) {
            addCriterion("yy <=", value, "yy");
            return (Criteria) this;
        }

        public Criteria andYyLike(String value) {
            addCriterion("yy like", value, "yy");
            return (Criteria) this;
        }

        public Criteria andYyNotLike(String value) {
            addCriterion("yy not like", value, "yy");
            return (Criteria) this;
        }

        public Criteria andYyIn(List<String> values) {
            addCriterion("yy in", values, "yy");
            return (Criteria) this;
        }

        public Criteria andYyNotIn(List<String> values) {
            addCriterion("yy not in", values, "yy");
            return (Criteria) this;
        }

        public Criteria andYyBetween(String value1, String value2) {
            addCriterion("yy between", value1, value2, "yy");
            return (Criteria) this;
        }

        public Criteria andYyNotBetween(String value1, String value2) {
            addCriterion("yy not between", value1, value2, "yy");
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