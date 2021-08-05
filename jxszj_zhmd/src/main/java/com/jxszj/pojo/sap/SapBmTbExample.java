package com.jxszj.pojo.sap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SapBmTbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SapBmTbExample() {
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

        public Criteria andNewNameIsNull() {
            addCriterion("new_name is null");
            return (Criteria) this;
        }

        public Criteria andNewNameIsNotNull() {
            addCriterion("new_name is not null");
            return (Criteria) this;
        }

        public Criteria andNewNameEqualTo(String value) {
            addCriterion("new_name =", value, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameNotEqualTo(String value) {
            addCriterion("new_name <>", value, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameGreaterThan(String value) {
            addCriterion("new_name >", value, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameGreaterThanOrEqualTo(String value) {
            addCriterion("new_name >=", value, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameLessThan(String value) {
            addCriterion("new_name <", value, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameLessThanOrEqualTo(String value) {
            addCriterion("new_name <=", value, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameLike(String value) {
            addCriterion("new_name like", value, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameNotLike(String value) {
            addCriterion("new_name not like", value, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameIn(List<String> values) {
            addCriterion("new_name in", values, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameNotIn(List<String> values) {
            addCriterion("new_name not in", values, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameBetween(String value1, String value2) {
            addCriterion("new_name between", value1, value2, "newName");
            return (Criteria) this;
        }

        public Criteria andNewNameNotBetween(String value1, String value2) {
            addCriterion("new_name not between", value1, value2, "newName");
            return (Criteria) this;
        }

        public Criteria andNewWlbmIsNull() {
            addCriterion("new_wlbm is null");
            return (Criteria) this;
        }

        public Criteria andNewWlbmIsNotNull() {
            addCriterion("new_wlbm is not null");
            return (Criteria) this;
        }

        public Criteria andNewWlbmEqualTo(String value) {
            addCriterion("new_wlbm =", value, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmNotEqualTo(String value) {
            addCriterion("new_wlbm <>", value, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmGreaterThan(String value) {
            addCriterion("new_wlbm >", value, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmGreaterThanOrEqualTo(String value) {
            addCriterion("new_wlbm >=", value, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmLessThan(String value) {
            addCriterion("new_wlbm <", value, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmLessThanOrEqualTo(String value) {
            addCriterion("new_wlbm <=", value, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmLike(String value) {
            addCriterion("new_wlbm like", value, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmNotLike(String value) {
            addCriterion("new_wlbm not like", value, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmIn(List<String> values) {
            addCriterion("new_wlbm in", values, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmNotIn(List<String> values) {
            addCriterion("new_wlbm not in", values, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmBetween(String value1, String value2) {
            addCriterion("new_wlbm between", value1, value2, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andNewWlbmNotBetween(String value1, String value2) {
            addCriterion("new_wlbm not between", value1, value2, "newWlbm");
            return (Criteria) this;
        }

        public Criteria andOldNameIsNull() {
            addCriterion("old_name is null");
            return (Criteria) this;
        }

        public Criteria andOldNameIsNotNull() {
            addCriterion("old_name is not null");
            return (Criteria) this;
        }

        public Criteria andOldNameEqualTo(String value) {
            addCriterion("old_name =", value, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameNotEqualTo(String value) {
            addCriterion("old_name <>", value, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameGreaterThan(String value) {
            addCriterion("old_name >", value, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameGreaterThanOrEqualTo(String value) {
            addCriterion("old_name >=", value, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameLessThan(String value) {
            addCriterion("old_name <", value, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameLessThanOrEqualTo(String value) {
            addCriterion("old_name <=", value, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameLike(String value) {
            addCriterion("old_name like", value, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameNotLike(String value) {
            addCriterion("old_name not like", value, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameIn(List<String> values) {
            addCriterion("old_name in", values, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameNotIn(List<String> values) {
            addCriterion("old_name not in", values, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameBetween(String value1, String value2) {
            addCriterion("old_name between", value1, value2, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldNameNotBetween(String value1, String value2) {
            addCriterion("old_name not between", value1, value2, "oldName");
            return (Criteria) this;
        }

        public Criteria andOldWlbmIsNull() {
            addCriterion("old_wlbm is null");
            return (Criteria) this;
        }

        public Criteria andOldWlbmIsNotNull() {
            addCriterion("old_wlbm is not null");
            return (Criteria) this;
        }

        public Criteria andOldWlbmEqualTo(String value) {
            addCriterion("old_wlbm =", value, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmNotEqualTo(String value) {
            addCriterion("old_wlbm <>", value, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmGreaterThan(String value) {
            addCriterion("old_wlbm >", value, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmGreaterThanOrEqualTo(String value) {
            addCriterion("old_wlbm >=", value, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmLessThan(String value) {
            addCriterion("old_wlbm <", value, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmLessThanOrEqualTo(String value) {
            addCriterion("old_wlbm <=", value, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmLike(String value) {
            addCriterion("old_wlbm like", value, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmNotLike(String value) {
            addCriterion("old_wlbm not like", value, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmIn(List<String> values) {
            addCriterion("old_wlbm in", values, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmNotIn(List<String> values) {
            addCriterion("old_wlbm not in", values, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmBetween(String value1, String value2) {
            addCriterion("old_wlbm between", value1, value2, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andOldWlbmNotBetween(String value1, String value2) {
            addCriterion("old_wlbm not between", value1, value2, "oldWlbm");
            return (Criteria) this;
        }

        public Criteria andBomIsNull() {
            addCriterion("bom is null");
            return (Criteria) this;
        }

        public Criteria andBomIsNotNull() {
            addCriterion("bom is not null");
            return (Criteria) this;
        }

        public Criteria andBomEqualTo(String value) {
            addCriterion("bom =", value, "bom");
            return (Criteria) this;
        }

        public Criteria andBomNotEqualTo(String value) {
            addCriterion("bom <>", value, "bom");
            return (Criteria) this;
        }

        public Criteria andBomGreaterThan(String value) {
            addCriterion("bom >", value, "bom");
            return (Criteria) this;
        }

        public Criteria andBomGreaterThanOrEqualTo(String value) {
            addCriterion("bom >=", value, "bom");
            return (Criteria) this;
        }

        public Criteria andBomLessThan(String value) {
            addCriterion("bom <", value, "bom");
            return (Criteria) this;
        }

        public Criteria andBomLessThanOrEqualTo(String value) {
            addCriterion("bom <=", value, "bom");
            return (Criteria) this;
        }

        public Criteria andBomLike(String value) {
            addCriterion("bom like", value, "bom");
            return (Criteria) this;
        }

        public Criteria andBomNotLike(String value) {
            addCriterion("bom not like", value, "bom");
            return (Criteria) this;
        }

        public Criteria andBomIn(List<String> values) {
            addCriterion("bom in", values, "bom");
            return (Criteria) this;
        }

        public Criteria andBomNotIn(List<String> values) {
            addCriterion("bom not in", values, "bom");
            return (Criteria) this;
        }

        public Criteria andBomBetween(String value1, String value2) {
            addCriterion("bom between", value1, value2, "bom");
            return (Criteria) this;
        }

        public Criteria andBomNotBetween(String value1, String value2) {
            addCriterion("bom not between", value1, value2, "bom");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
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