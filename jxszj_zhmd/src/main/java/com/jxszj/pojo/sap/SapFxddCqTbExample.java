package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapFxddCqTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapFxddCqTbExample() {
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

        public Criteria andYdrqIsNull() {
            addCriterion("ydrq is null");
            return (Criteria) this;
        }

        public Criteria andYdrqIsNotNull() {
            addCriterion("ydrq is not null");
            return (Criteria) this;
        }

        public Criteria andYdrqEqualTo(String value) {
            addCriterion("ydrq =", value, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqNotEqualTo(String value) {
            addCriterion("ydrq <>", value, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqGreaterThan(String value) {
            addCriterion("ydrq >", value, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqGreaterThanOrEqualTo(String value) {
            addCriterion("ydrq >=", value, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqLessThan(String value) {
            addCriterion("ydrq <", value, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqLessThanOrEqualTo(String value) {
            addCriterion("ydrq <=", value, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqLike(String value) {
            addCriterion("ydrq like", value, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqNotLike(String value) {
            addCriterion("ydrq not like", value, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqIn(List<String> values) {
            addCriterion("ydrq in", values, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqNotIn(List<String> values) {
            addCriterion("ydrq not in", values, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqBetween(String value1, String value2) {
            addCriterion("ydrq between", value1, value2, "ydrq");
            return (Criteria) this;
        }

        public Criteria andYdrqNotBetween(String value1, String value2) {
            addCriterion("ydrq not between", value1, value2, "ydrq");
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