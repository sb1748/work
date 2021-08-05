package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class YdmbTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public YdmbTbExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
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

        public Criteria andDdIsNull() {
            addCriterion("dd is null");
            return (Criteria) this;
        }

        public Criteria andDdIsNotNull() {
            addCriterion("dd is not null");
            return (Criteria) this;
        }

        public Criteria andDdEqualTo(String value) {
            addCriterion("dd =", value, "dd");
            return (Criteria) this;
        }

        public Criteria andDdNotEqualTo(String value) {
            addCriterion("dd <>", value, "dd");
            return (Criteria) this;
        }

        public Criteria andDdGreaterThan(String value) {
            addCriterion("dd >", value, "dd");
            return (Criteria) this;
        }

        public Criteria andDdGreaterThanOrEqualTo(String value) {
            addCriterion("dd >=", value, "dd");
            return (Criteria) this;
        }

        public Criteria andDdLessThan(String value) {
            addCriterion("dd <", value, "dd");
            return (Criteria) this;
        }

        public Criteria andDdLessThanOrEqualTo(String value) {
            addCriterion("dd <=", value, "dd");
            return (Criteria) this;
        }

        public Criteria andDdLike(String value) {
            addCriterion("dd like", value, "dd");
            return (Criteria) this;
        }

        public Criteria andDdNotLike(String value) {
            addCriterion("dd not like", value, "dd");
            return (Criteria) this;
        }

        public Criteria andDdIn(List<String> values) {
            addCriterion("dd in", values, "dd");
            return (Criteria) this;
        }

        public Criteria andDdNotIn(List<String> values) {
            addCriterion("dd not in", values, "dd");
            return (Criteria) this;
        }

        public Criteria andDdBetween(String value1, String value2) {
            addCriterion("dd between", value1, value2, "dd");
            return (Criteria) this;
        }

        public Criteria andDdNotBetween(String value1, String value2) {
            addCriterion("dd not between", value1, value2, "dd");
            return (Criteria) this;
        }

        public Criteria andYyztIsNull() {
            addCriterion("yyzt is null");
            return (Criteria) this;
        }

        public Criteria andYyztIsNotNull() {
            addCriterion("yyzt is not null");
            return (Criteria) this;
        }

        public Criteria andYyztEqualTo(String value) {
            addCriterion("yyzt =", value, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztNotEqualTo(String value) {
            addCriterion("yyzt <>", value, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztGreaterThan(String value) {
            addCriterion("yyzt >", value, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztGreaterThanOrEqualTo(String value) {
            addCriterion("yyzt >=", value, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztLessThan(String value) {
            addCriterion("yyzt <", value, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztLessThanOrEqualTo(String value) {
            addCriterion("yyzt <=", value, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztLike(String value) {
            addCriterion("yyzt like", value, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztNotLike(String value) {
            addCriterion("yyzt not like", value, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztIn(List<String> values) {
            addCriterion("yyzt in", values, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztNotIn(List<String> values) {
            addCriterion("yyzt not in", values, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztBetween(String value1, String value2) {
            addCriterion("yyzt between", value1, value2, "yyzt");
            return (Criteria) this;
        }

        public Criteria andYyztNotBetween(String value1, String value2) {
            addCriterion("yyzt not between", value1, value2, "yyzt");
            return (Criteria) this;
        }

        public Criteria andBymbhkIsNull() {
            addCriterion("bymbhk is null");
            return (Criteria) this;
        }

        public Criteria andBymbhkIsNotNull() {
            addCriterion("bymbhk is not null");
            return (Criteria) this;
        }

        public Criteria andBymbhkEqualTo(Double value) {
            addCriterion("bymbhk =", value, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andBymbhkNotEqualTo(Double value) {
            addCriterion("bymbhk <>", value, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andBymbhkGreaterThan(Double value) {
            addCriterion("bymbhk >", value, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andBymbhkGreaterThanOrEqualTo(Double value) {
            addCriterion("bymbhk >=", value, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andBymbhkLessThan(Double value) {
            addCriterion("bymbhk <", value, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andBymbhkLessThanOrEqualTo(Double value) {
            addCriterion("bymbhk <=", value, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andBymbhkIn(List<Double> values) {
            addCriterion("bymbhk in", values, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andBymbhkNotIn(List<Double> values) {
            addCriterion("bymbhk not in", values, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andBymbhkBetween(Double value1, Double value2) {
            addCriterion("bymbhk between", value1, value2, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andBymbhkNotBetween(Double value1, Double value2) {
            addCriterion("bymbhk not between", value1, value2, "bymbhk");
            return (Criteria) this;
        }

        public Criteria andDrsjIsNull() {
            addCriterion("drsj is null");
            return (Criteria) this;
        }

        public Criteria andDrsjIsNotNull() {
            addCriterion("drsj is not null");
            return (Criteria) this;
        }

        public Criteria andDrsjEqualTo(String value) {
            addCriterion("drsj =", value, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjNotEqualTo(String value) {
            addCriterion("drsj <>", value, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjGreaterThan(String value) {
            addCriterion("drsj >", value, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjGreaterThanOrEqualTo(String value) {
            addCriterion("drsj >=", value, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjLessThan(String value) {
            addCriterion("drsj <", value, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjLessThanOrEqualTo(String value) {
            addCriterion("drsj <=", value, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjLike(String value) {
            addCriterion("drsj like", value, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjNotLike(String value) {
            addCriterion("drsj not like", value, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjIn(List<String> values) {
            addCriterion("drsj in", values, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjNotIn(List<String> values) {
            addCriterion("drsj not in", values, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjBetween(String value1, String value2) {
            addCriterion("drsj between", value1, value2, "drsj");
            return (Criteria) this;
        }

        public Criteria andDrsjNotBetween(String value1, String value2) {
            addCriterion("drsj not between", value1, value2, "drsj");
            return (Criteria) this;
        }

        public Criteria andPpbmIsNull() {
            addCriterion("ppbm is null");
            return (Criteria) this;
        }

        public Criteria andPpbmIsNotNull() {
            addCriterion("ppbm is not null");
            return (Criteria) this;
        }

        public Criteria andPpbmEqualTo(String value) {
            addCriterion("ppbm =", value, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmNotEqualTo(String value) {
            addCriterion("ppbm <>", value, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmGreaterThan(String value) {
            addCriterion("ppbm >", value, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmGreaterThanOrEqualTo(String value) {
            addCriterion("ppbm >=", value, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmLessThan(String value) {
            addCriterion("ppbm <", value, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmLessThanOrEqualTo(String value) {
            addCriterion("ppbm <=", value, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmLike(String value) {
            addCriterion("ppbm like", value, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmNotLike(String value) {
            addCriterion("ppbm not like", value, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmIn(List<String> values) {
            addCriterion("ppbm in", values, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmNotIn(List<String> values) {
            addCriterion("ppbm not in", values, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmBetween(String value1, String value2) {
            addCriterion("ppbm between", value1, value2, "ppbm");
            return (Criteria) this;
        }

        public Criteria andPpbmNotBetween(String value1, String value2) {
            addCriterion("ppbm not between", value1, value2, "ppbm");
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