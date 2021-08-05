package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapJdrbTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapJdrbTbExample() {
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

        public Criteria andJdrqIsNull() {
            addCriterion("jdrq is null");
            return (Criteria) this;
        }

        public Criteria andJdrqIsNotNull() {
            addCriterion("jdrq is not null");
            return (Criteria) this;
        }

        public Criteria andJdrqEqualTo(String value) {
            addCriterion("jdrq =", value, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqNotEqualTo(String value) {
            addCriterion("jdrq <>", value, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqGreaterThan(String value) {
            addCriterion("jdrq >", value, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqGreaterThanOrEqualTo(String value) {
            addCriterion("jdrq >=", value, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqLessThan(String value) {
            addCriterion("jdrq <", value, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqLessThanOrEqualTo(String value) {
            addCriterion("jdrq <=", value, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqLike(String value) {
            addCriterion("jdrq like", value, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqNotLike(String value) {
            addCriterion("jdrq not like", value, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqIn(List<String> values) {
            addCriterion("jdrq in", values, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqNotIn(List<String> values) {
            addCriterion("jdrq not in", values, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqBetween(String value1, String value2) {
            addCriterion("jdrq between", value1, value2, "jdrq");
            return (Criteria) this;
        }

        public Criteria andJdrqNotBetween(String value1, String value2) {
            addCriterion("jdrq not between", value1, value2, "jdrq");
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

        public Criteria andDdbmIsNull() {
            addCriterion("ddbm is null");
            return (Criteria) this;
        }

        public Criteria andDdbmIsNotNull() {
            addCriterion("ddbm is not null");
            return (Criteria) this;
        }

        public Criteria andDdbmEqualTo(String value) {
            addCriterion("ddbm =", value, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmNotEqualTo(String value) {
            addCriterion("ddbm <>", value, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmGreaterThan(String value) {
            addCriterion("ddbm >", value, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmGreaterThanOrEqualTo(String value) {
            addCriterion("ddbm >=", value, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmLessThan(String value) {
            addCriterion("ddbm <", value, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmLessThanOrEqualTo(String value) {
            addCriterion("ddbm <=", value, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmLike(String value) {
            addCriterion("ddbm like", value, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmNotLike(String value) {
            addCriterion("ddbm not like", value, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmIn(List<String> values) {
            addCriterion("ddbm in", values, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmNotIn(List<String> values) {
            addCriterion("ddbm not in", values, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmBetween(String value1, String value2) {
            addCriterion("ddbm between", value1, value2, "ddbm");
            return (Criteria) this;
        }

        public Criteria andDdbmNotBetween(String value1, String value2) {
            addCriterion("ddbm not between", value1, value2, "ddbm");
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