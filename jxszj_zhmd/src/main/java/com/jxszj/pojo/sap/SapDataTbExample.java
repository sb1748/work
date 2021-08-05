package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.List;

public class SapDataTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapDataTbExample() {
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

        public Criteria andBmzIsNull() {
            addCriterion("bmz is null");
            return (Criteria) this;
        }

        public Criteria andBmzIsNotNull() {
            addCriterion("bmz is not null");
            return (Criteria) this;
        }

        public Criteria andBmzEqualTo(String value) {
            addCriterion("bmz =", value, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzNotEqualTo(String value) {
            addCriterion("bmz <>", value, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzGreaterThan(String value) {
            addCriterion("bmz >", value, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzGreaterThanOrEqualTo(String value) {
            addCriterion("bmz >=", value, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzLessThan(String value) {
            addCriterion("bmz <", value, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzLessThanOrEqualTo(String value) {
            addCriterion("bmz <=", value, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzLike(String value) {
            addCriterion("bmz like", value, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzNotLike(String value) {
            addCriterion("bmz not like", value, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzIn(List<String> values) {
            addCriterion("bmz in", values, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzNotIn(List<String> values) {
            addCriterion("bmz not in", values, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzBetween(String value1, String value2) {
            addCriterion("bmz between", value1, value2, "bmz");
            return (Criteria) this;
        }

        public Criteria andBmzNotBetween(String value1, String value2) {
            addCriterion("bmz not between", value1, value2, "bmz");
            return (Criteria) this;
        }

        public Criteria andDataIsNull() {
            addCriterion("data is null");
            return (Criteria) this;
        }

        public Criteria andDataIsNotNull() {
            addCriterion("data is not null");
            return (Criteria) this;
        }

        public Criteria andDataEqualTo(String value) {
            addCriterion("data =", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotEqualTo(String value) {
            addCriterion("data <>", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataGreaterThan(String value) {
            addCriterion("data >", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataGreaterThanOrEqualTo(String value) {
            addCriterion("data >=", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataLessThan(String value) {
            addCriterion("data <", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataLessThanOrEqualTo(String value) {
            addCriterion("data <=", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataLike(String value) {
            addCriterion("data like", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotLike(String value) {
            addCriterion("data not like", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataIn(List<String> values) {
            addCriterion("data in", values, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotIn(List<String> values) {
            addCriterion("data not in", values, "data");
            return (Criteria) this;
        }

        public Criteria andDataBetween(String value1, String value2) {
            addCriterion("data between", value1, value2, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotBetween(String value1, String value2) {
            addCriterion("data not between", value1, value2, "data");
            return (Criteria) this;
        }

        public Criteria andGzsIsNull() {
            addCriterion("gzs is null");
            return (Criteria) this;
        }

        public Criteria andGzsIsNotNull() {
            addCriterion("gzs is not null");
            return (Criteria) this;
        }

        public Criteria andGzsEqualTo(String value) {
            addCriterion("gzs =", value, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsNotEqualTo(String value) {
            addCriterion("gzs <>", value, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsGreaterThan(String value) {
            addCriterion("gzs >", value, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsGreaterThanOrEqualTo(String value) {
            addCriterion("gzs >=", value, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsLessThan(String value) {
            addCriterion("gzs <", value, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsLessThanOrEqualTo(String value) {
            addCriterion("gzs <=", value, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsLike(String value) {
            addCriterion("gzs like", value, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsNotLike(String value) {
            addCriterion("gzs not like", value, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsIn(List<String> values) {
            addCriterion("gzs in", values, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsNotIn(List<String> values) {
            addCriterion("gzs not in", values, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsBetween(String value1, String value2) {
            addCriterion("gzs between", value1, value2, "gzs");
            return (Criteria) this;
        }

        public Criteria andGzsNotBetween(String value1, String value2) {
            addCriterion("gzs not between", value1, value2, "gzs");
            return (Criteria) this;
        }

        public Criteria andWlmclzIsNull() {
            addCriterion("wlmclz is null");
            return (Criteria) this;
        }

        public Criteria andWlmclzIsNotNull() {
            addCriterion("wlmclz is not null");
            return (Criteria) this;
        }

        public Criteria andWlmclzEqualTo(String value) {
            addCriterion("wlmclz =", value, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzNotEqualTo(String value) {
            addCriterion("wlmclz <>", value, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzGreaterThan(String value) {
            addCriterion("wlmclz >", value, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzGreaterThanOrEqualTo(String value) {
            addCriterion("wlmclz >=", value, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzLessThan(String value) {
            addCriterion("wlmclz <", value, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzLessThanOrEqualTo(String value) {
            addCriterion("wlmclz <=", value, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzLike(String value) {
            addCriterion("wlmclz like", value, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzNotLike(String value) {
            addCriterion("wlmclz not like", value, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzIn(List<String> values) {
            addCriterion("wlmclz in", values, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzNotIn(List<String> values) {
            addCriterion("wlmclz not in", values, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzBetween(String value1, String value2) {
            addCriterion("wlmclz between", value1, value2, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andWlmclzNotBetween(String value1, String value2) {
            addCriterion("wlmclz not between", value1, value2, "wlmclz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzIsNull() {
            addCriterion("ggxhlz is null");
            return (Criteria) this;
        }

        public Criteria andGgxhlzIsNotNull() {
            addCriterion("ggxhlz is not null");
            return (Criteria) this;
        }

        public Criteria andGgxhlzEqualTo(String value) {
            addCriterion("ggxhlz =", value, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzNotEqualTo(String value) {
            addCriterion("ggxhlz <>", value, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzGreaterThan(String value) {
            addCriterion("ggxhlz >", value, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzGreaterThanOrEqualTo(String value) {
            addCriterion("ggxhlz >=", value, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzLessThan(String value) {
            addCriterion("ggxhlz <", value, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzLessThanOrEqualTo(String value) {
            addCriterion("ggxhlz <=", value, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzLike(String value) {
            addCriterion("ggxhlz like", value, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzNotLike(String value) {
            addCriterion("ggxhlz not like", value, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzIn(List<String> values) {
            addCriterion("ggxhlz in", values, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzNotIn(List<String> values) {
            addCriterion("ggxhlz not in", values, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzBetween(String value1, String value2) {
            addCriterion("ggxhlz between", value1, value2, "ggxhlz");
            return (Criteria) this;
        }

        public Criteria andGgxhlzNotBetween(String value1, String value2) {
            addCriterion("ggxhlz not between", value1, value2, "ggxhlz");
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