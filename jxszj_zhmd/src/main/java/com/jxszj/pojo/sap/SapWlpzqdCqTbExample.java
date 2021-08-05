package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapWlpzqdCqTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapWlpzqdCqTbExample() {
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

        public Criteria andCcddIsNull() {
            addCriterion("ccdd is null");
            return (Criteria) this;
        }

        public Criteria andCcddIsNotNull() {
            addCriterion("ccdd is not null");
            return (Criteria) this;
        }

        public Criteria andCcddEqualTo(String value) {
            addCriterion("ccdd =", value, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddNotEqualTo(String value) {
            addCriterion("ccdd <>", value, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddGreaterThan(String value) {
            addCriterion("ccdd >", value, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddGreaterThanOrEqualTo(String value) {
            addCriterion("ccdd >=", value, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddLessThan(String value) {
            addCriterion("ccdd <", value, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddLessThanOrEqualTo(String value) {
            addCriterion("ccdd <=", value, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddLike(String value) {
            addCriterion("ccdd like", value, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddNotLike(String value) {
            addCriterion("ccdd not like", value, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddIn(List<String> values) {
            addCriterion("ccdd in", values, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddNotIn(List<String> values) {
            addCriterion("ccdd not in", values, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddBetween(String value1, String value2) {
            addCriterion("ccdd between", value1, value2, "ccdd");
            return (Criteria) this;
        }

        public Criteria andCcddNotBetween(String value1, String value2) {
            addCriterion("ccdd not between", value1, value2, "ccdd");
            return (Criteria) this;
        }

        public Criteria andYdlxIsNull() {
            addCriterion("ydlx is null");
            return (Criteria) this;
        }

        public Criteria andYdlxIsNotNull() {
            addCriterion("ydlx is not null");
            return (Criteria) this;
        }

        public Criteria andYdlxEqualTo(String value) {
            addCriterion("ydlx =", value, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxNotEqualTo(String value) {
            addCriterion("ydlx <>", value, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxGreaterThan(String value) {
            addCriterion("ydlx >", value, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxGreaterThanOrEqualTo(String value) {
            addCriterion("ydlx >=", value, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxLessThan(String value) {
            addCriterion("ydlx <", value, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxLessThanOrEqualTo(String value) {
            addCriterion("ydlx <=", value, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxLike(String value) {
            addCriterion("ydlx like", value, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxNotLike(String value) {
            addCriterion("ydlx not like", value, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxIn(List<String> values) {
            addCriterion("ydlx in", values, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxNotIn(List<String> values) {
            addCriterion("ydlx not in", values, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxBetween(String value1, String value2) {
            addCriterion("ydlx between", value1, value2, "ydlx");
            return (Criteria) this;
        }

        public Criteria andYdlxNotBetween(String value1, String value2) {
            addCriterion("ydlx not between", value1, value2, "ydlx");
            return (Criteria) this;
        }

        public Criteria andTskcIsNull() {
            addCriterion("tskc is null");
            return (Criteria) this;
        }

        public Criteria andTskcIsNotNull() {
            addCriterion("tskc is not null");
            return (Criteria) this;
        }

        public Criteria andTskcEqualTo(String value) {
            addCriterion("tskc =", value, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcNotEqualTo(String value) {
            addCriterion("tskc <>", value, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcGreaterThan(String value) {
            addCriterion("tskc >", value, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcGreaterThanOrEqualTo(String value) {
            addCriterion("tskc >=", value, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcLessThan(String value) {
            addCriterion("tskc <", value, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcLessThanOrEqualTo(String value) {
            addCriterion("tskc <=", value, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcLike(String value) {
            addCriterion("tskc like", value, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcNotLike(String value) {
            addCriterion("tskc not like", value, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcIn(List<String> values) {
            addCriterion("tskc in", values, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcNotIn(List<String> values) {
            addCriterion("tskc not in", values, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcBetween(String value1, String value2) {
            addCriterion("tskc between", value1, value2, "tskc");
            return (Criteria) this;
        }

        public Criteria andTskcNotBetween(String value1, String value2) {
            addCriterion("tskc not between", value1, value2, "tskc");
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

        public Criteria andXsddxmIsNull() {
            addCriterion("xsddxm is null");
            return (Criteria) this;
        }

        public Criteria andXsddxmIsNotNull() {
            addCriterion("xsddxm is not null");
            return (Criteria) this;
        }

        public Criteria andXsddxmEqualTo(Integer value) {
            addCriterion("xsddxm =", value, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andXsddxmNotEqualTo(Integer value) {
            addCriterion("xsddxm <>", value, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andXsddxmGreaterThan(Integer value) {
            addCriterion("xsddxm >", value, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andXsddxmGreaterThanOrEqualTo(Integer value) {
            addCriterion("xsddxm >=", value, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andXsddxmLessThan(Integer value) {
            addCriterion("xsddxm <", value, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andXsddxmLessThanOrEqualTo(Integer value) {
            addCriterion("xsddxm <=", value, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andXsddxmIn(List<Integer> values) {
            addCriterion("xsddxm in", values, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andXsddxmNotIn(List<Integer> values) {
            addCriterion("xsddxm not in", values, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andXsddxmBetween(Integer value1, Integer value2) {
            addCriterion("xsddxm between", value1, value2, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andXsddxmNotBetween(Integer value1, Integer value2) {
            addCriterion("xsddxm not between", value1, value2, "xsddxm");
            return (Criteria) this;
        }

        public Criteria andLrrqIsNull() {
            addCriterion("lrrq is null");
            return (Criteria) this;
        }

        public Criteria andLrrqIsNotNull() {
            addCriterion("lrrq is not null");
            return (Criteria) this;
        }

        public Criteria andLrrqEqualTo(String value) {
            addCriterion("lrrq =", value, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqNotEqualTo(String value) {
            addCriterion("lrrq <>", value, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqGreaterThan(String value) {
            addCriterion("lrrq >", value, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqGreaterThanOrEqualTo(String value) {
            addCriterion("lrrq >=", value, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqLessThan(String value) {
            addCriterion("lrrq <", value, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqLessThanOrEqualTo(String value) {
            addCriterion("lrrq <=", value, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqLike(String value) {
            addCriterion("lrrq like", value, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqNotLike(String value) {
            addCriterion("lrrq not like", value, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqIn(List<String> values) {
            addCriterion("lrrq in", values, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqNotIn(List<String> values) {
            addCriterion("lrrq not in", values, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqBetween(String value1, String value2) {
            addCriterion("lrrq between", value1, value2, "lrrq");
            return (Criteria) this;
        }

        public Criteria andLrrqNotBetween(String value1, String value2) {
            addCriterion("lrrq not between", value1, value2, "lrrq");
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

        public Criteria andWlpzIsNull() {
            addCriterion("wlpz is null");
            return (Criteria) this;
        }

        public Criteria andWlpzIsNotNull() {
            addCriterion("wlpz is not null");
            return (Criteria) this;
        }

        public Criteria andWlpzEqualTo(String value) {
            addCriterion("wlpz =", value, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzNotEqualTo(String value) {
            addCriterion("wlpz <>", value, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzGreaterThan(String value) {
            addCriterion("wlpz >", value, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzGreaterThanOrEqualTo(String value) {
            addCriterion("wlpz >=", value, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzLessThan(String value) {
            addCriterion("wlpz <", value, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzLessThanOrEqualTo(String value) {
            addCriterion("wlpz <=", value, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzLike(String value) {
            addCriterion("wlpz like", value, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzNotLike(String value) {
            addCriterion("wlpz not like", value, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzIn(List<String> values) {
            addCriterion("wlpz in", values, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzNotIn(List<String> values) {
            addCriterion("wlpz not in", values, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzBetween(String value1, String value2) {
            addCriterion("wlpz between", value1, value2, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzNotBetween(String value1, String value2) {
            addCriterion("wlpz not between", value1, value2, "wlpz");
            return (Criteria) this;
        }

        public Criteria andWlpzxmIsNull() {
            addCriterion("wlpzxm is null");
            return (Criteria) this;
        }

        public Criteria andWlpzxmIsNotNull() {
            addCriterion("wlpzxm is not null");
            return (Criteria) this;
        }

        public Criteria andWlpzxmEqualTo(Integer value) {
            addCriterion("wlpzxm =", value, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andWlpzxmNotEqualTo(Integer value) {
            addCriterion("wlpzxm <>", value, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andWlpzxmGreaterThan(Integer value) {
            addCriterion("wlpzxm >", value, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andWlpzxmGreaterThanOrEqualTo(Integer value) {
            addCriterion("wlpzxm >=", value, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andWlpzxmLessThan(Integer value) {
            addCriterion("wlpzxm <", value, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andWlpzxmLessThanOrEqualTo(Integer value) {
            addCriterion("wlpzxm <=", value, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andWlpzxmIn(List<Integer> values) {
            addCriterion("wlpzxm in", values, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andWlpzxmNotIn(List<Integer> values) {
            addCriterion("wlpzxm not in", values, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andWlpzxmBetween(Integer value1, Integer value2) {
            addCriterion("wlpzxm between", value1, value2, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andWlpzxmNotBetween(Integer value1, Integer value2) {
            addCriterion("wlpzxm not between", value1, value2, "wlpzxm");
            return (Criteria) this;
        }

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(String value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(String value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(String value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(String value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(String value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(String value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLike(String value) {
            addCriterion("year like", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotLike(String value) {
            addCriterion("year not like", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<String> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<String> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(String value1, String value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(String value1, String value2) {
            addCriterion("year not between", value1, value2, "year");
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