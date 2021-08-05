package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapXsddpdscTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapXsddpdscTbExample() {
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

        public Criteria andPdrqIsNull() {
            addCriterion("pdrq is null");
            return (Criteria) this;
        }

        public Criteria andPdrqIsNotNull() {
            addCriterion("pdrq is not null");
            return (Criteria) this;
        }

        public Criteria andPdrqEqualTo(String value) {
            addCriterion("pdrq =", value, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqNotEqualTo(String value) {
            addCriterion("pdrq <>", value, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqGreaterThan(String value) {
            addCriterion("pdrq >", value, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqGreaterThanOrEqualTo(String value) {
            addCriterion("pdrq >=", value, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqLessThan(String value) {
            addCriterion("pdrq <", value, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqLessThanOrEqualTo(String value) {
            addCriterion("pdrq <=", value, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqLike(String value) {
            addCriterion("pdrq like", value, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqNotLike(String value) {
            addCriterion("pdrq not like", value, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqIn(List<String> values) {
            addCriterion("pdrq in", values, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqNotIn(List<String> values) {
            addCriterion("pdrq not in", values, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqBetween(String value1, String value2) {
            addCriterion("pdrq between", value1, value2, "pdrq");
            return (Criteria) this;
        }

        public Criteria andPdrqNotBetween(String value1, String value2) {
            addCriterion("pdrq not between", value1, value2, "pdrq");
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

        public Criteria andXdslIsNull() {
            addCriterion("xdsl is null");
            return (Criteria) this;
        }

        public Criteria andXdslIsNotNull() {
            addCriterion("xdsl is not null");
            return (Criteria) this;
        }

        public Criteria andXdslEqualTo(Integer value) {
            addCriterion("xdsl =", value, "xdsl");
            return (Criteria) this;
        }

        public Criteria andXdslNotEqualTo(Integer value) {
            addCriterion("xdsl <>", value, "xdsl");
            return (Criteria) this;
        }

        public Criteria andXdslGreaterThan(Integer value) {
            addCriterion("xdsl >", value, "xdsl");
            return (Criteria) this;
        }

        public Criteria andXdslGreaterThanOrEqualTo(Integer value) {
            addCriterion("xdsl >=", value, "xdsl");
            return (Criteria) this;
        }

        public Criteria andXdslLessThan(Integer value) {
            addCriterion("xdsl <", value, "xdsl");
            return (Criteria) this;
        }

        public Criteria andXdslLessThanOrEqualTo(Integer value) {
            addCriterion("xdsl <=", value, "xdsl");
            return (Criteria) this;
        }

        public Criteria andXdslIn(List<Integer> values) {
            addCriterion("xdsl in", values, "xdsl");
            return (Criteria) this;
        }

        public Criteria andXdslNotIn(List<Integer> values) {
            addCriterion("xdsl not in", values, "xdsl");
            return (Criteria) this;
        }

        public Criteria andXdslBetween(Integer value1, Integer value2) {
            addCriterion("xdsl between", value1, value2, "xdsl");
            return (Criteria) this;
        }

        public Criteria andXdslNotBetween(Integer value1, Integer value2) {
            addCriterion("xdsl not between", value1, value2, "xdsl");
            return (Criteria) this;
        }

        public Criteria andJhmbIsNull() {
            addCriterion("jhmb is null");
            return (Criteria) this;
        }

        public Criteria andJhmbIsNotNull() {
            addCriterion("jhmb is not null");
            return (Criteria) this;
        }

        public Criteria andJhmbEqualTo(Integer value) {
            addCriterion("jhmb =", value, "jhmb");
            return (Criteria) this;
        }

        public Criteria andJhmbNotEqualTo(Integer value) {
            addCriterion("jhmb <>", value, "jhmb");
            return (Criteria) this;
        }

        public Criteria andJhmbGreaterThan(Integer value) {
            addCriterion("jhmb >", value, "jhmb");
            return (Criteria) this;
        }

        public Criteria andJhmbGreaterThanOrEqualTo(Integer value) {
            addCriterion("jhmb >=", value, "jhmb");
            return (Criteria) this;
        }

        public Criteria andJhmbLessThan(Integer value) {
            addCriterion("jhmb <", value, "jhmb");
            return (Criteria) this;
        }

        public Criteria andJhmbLessThanOrEqualTo(Integer value) {
            addCriterion("jhmb <=", value, "jhmb");
            return (Criteria) this;
        }

        public Criteria andJhmbIn(List<Integer> values) {
            addCriterion("jhmb in", values, "jhmb");
            return (Criteria) this;
        }

        public Criteria andJhmbNotIn(List<Integer> values) {
            addCriterion("jhmb not in", values, "jhmb");
            return (Criteria) this;
        }

        public Criteria andJhmbBetween(Integer value1, Integer value2) {
            addCriterion("jhmb between", value1, value2, "jhmb");
            return (Criteria) this;
        }

        public Criteria andJhmbNotBetween(Integer value1, Integer value2) {
            addCriterion("jhmb not between", value1, value2, "jhmb");
            return (Criteria) this;
        }

        public Criteria andRkslIsNull() {
            addCriterion("rksl is null");
            return (Criteria) this;
        }

        public Criteria andRkslIsNotNull() {
            addCriterion("rksl is not null");
            return (Criteria) this;
        }

        public Criteria andRkslEqualTo(Integer value) {
            addCriterion("rksl =", value, "rksl");
            return (Criteria) this;
        }

        public Criteria andRkslNotEqualTo(Integer value) {
            addCriterion("rksl <>", value, "rksl");
            return (Criteria) this;
        }

        public Criteria andRkslGreaterThan(Integer value) {
            addCriterion("rksl >", value, "rksl");
            return (Criteria) this;
        }

        public Criteria andRkslGreaterThanOrEqualTo(Integer value) {
            addCriterion("rksl >=", value, "rksl");
            return (Criteria) this;
        }

        public Criteria andRkslLessThan(Integer value) {
            addCriterion("rksl <", value, "rksl");
            return (Criteria) this;
        }

        public Criteria andRkslLessThanOrEqualTo(Integer value) {
            addCriterion("rksl <=", value, "rksl");
            return (Criteria) this;
        }

        public Criteria andRkslIn(List<Integer> values) {
            addCriterion("rksl in", values, "rksl");
            return (Criteria) this;
        }

        public Criteria andRkslNotIn(List<Integer> values) {
            addCriterion("rksl not in", values, "rksl");
            return (Criteria) this;
        }

        public Criteria andRkslBetween(Integer value1, Integer value2) {
            addCriterion("rksl between", value1, value2, "rksl");
            return (Criteria) this;
        }

        public Criteria andRkslNotBetween(Integer value1, Integer value2) {
            addCriterion("rksl not between", value1, value2, "rksl");
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