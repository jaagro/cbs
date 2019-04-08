package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BreedingStandardExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    private Integer limit;

    private Integer offset;

    public BreedingStandardExample() {
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

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria implements Serializable {
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

        public Criteria andStandardNameIsNull() {
            addCriterion("standard_name is null");
            return (Criteria) this;
        }

        public Criteria andStandardNameIsNotNull() {
            addCriterion("standard_name is not null");
            return (Criteria) this;
        }

        public Criteria andStandardNameEqualTo(String value) {
            addCriterion("standard_name =", value, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameNotEqualTo(String value) {
            addCriterion("standard_name <>", value, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameGreaterThan(String value) {
            addCriterion("standard_name >", value, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameGreaterThanOrEqualTo(String value) {
            addCriterion("standard_name >=", value, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameLessThan(String value) {
            addCriterion("standard_name <", value, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameLessThanOrEqualTo(String value) {
            addCriterion("standard_name <=", value, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameLike(String value) {
            addCriterion("standard_name like", value, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameNotLike(String value) {
            addCriterion("standard_name not like", value, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameIn(List<String> values) {
            addCriterion("standard_name in", values, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameNotIn(List<String> values) {
            addCriterion("standard_name not in", values, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameBetween(String value1, String value2) {
            addCriterion("standard_name between", value1, value2, "standardName");
            return (Criteria) this;
        }

        public Criteria andStandardNameNotBetween(String value1, String value2) {
            addCriterion("standard_name not between", value1, value2, "standardName");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeIsNull() {
            addCriterion("breeding_type is null");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeIsNotNull() {
            addCriterion("breeding_type is not null");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeEqualTo(Integer value) {
            addCriterion("breeding_type =", value, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeNotEqualTo(Integer value) {
            addCriterion("breeding_type <>", value, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeGreaterThan(Integer value) {
            addCriterion("breeding_type >", value, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("breeding_type >=", value, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeLessThan(Integer value) {
            addCriterion("breeding_type <", value, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeLessThanOrEqualTo(Integer value) {
            addCriterion("breeding_type <=", value, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeIn(List<Integer> values) {
            addCriterion("breeding_type in", values, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeNotIn(List<Integer> values) {
            addCriterion("breeding_type not in", values, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeBetween(Integer value1, Integer value2) {
            addCriterion("breeding_type between", value1, value2, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("breeding_type not between", value1, value2, "breedingType");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysIsNull() {
            addCriterion("breeding_days is null");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysIsNotNull() {
            addCriterion("breeding_days is not null");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysEqualTo(Integer value) {
            addCriterion("breeding_days =", value, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysNotEqualTo(Integer value) {
            addCriterion("breeding_days <>", value, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysGreaterThan(Integer value) {
            addCriterion("breeding_days >", value, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("breeding_days >=", value, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysLessThan(Integer value) {
            addCriterion("breeding_days <", value, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysLessThanOrEqualTo(Integer value) {
            addCriterion("breeding_days <=", value, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysIn(List<Integer> values) {
            addCriterion("breeding_days in", values, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysNotIn(List<Integer> values) {
            addCriterion("breeding_days not in", values, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysBetween(Integer value1, Integer value2) {
            addCriterion("breeding_days between", value1, value2, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andBreedingDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("breeding_days not between", value1, value2, "breedingDays");
            return (Criteria) this;
        }

        public Criteria andStandardStatusIsNull() {
            addCriterion("standard_status is null");
            return (Criteria) this;
        }

        public Criteria andStandardStatusIsNotNull() {
            addCriterion("standard_status is not null");
            return (Criteria) this;
        }

        public Criteria andStandardStatusEqualTo(Integer value) {
            addCriterion("standard_status =", value, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andStandardStatusNotEqualTo(Integer value) {
            addCriterion("standard_status <>", value, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andStandardStatusGreaterThan(Integer value) {
            addCriterion("standard_status >", value, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andStandardStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("standard_status >=", value, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andStandardStatusLessThan(Integer value) {
            addCriterion("standard_status <", value, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andStandardStatusLessThanOrEqualTo(Integer value) {
            addCriterion("standard_status <=", value, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andStandardStatusIn(List<Integer> values) {
            addCriterion("standard_status in", values, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andStandardStatusNotIn(List<Integer> values) {
            addCriterion("standard_status not in", values, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andStandardStatusBetween(Integer value1, Integer value2) {
            addCriterion("standard_status between", value1, value2, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andStandardStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("standard_status not between", value1, value2, "standardStatus");
            return (Criteria) this;
        }

        public Criteria andEnableIsNull() {
            addCriterion("enable is null");
            return (Criteria) this;
        }

        public Criteria andEnableIsNotNull() {
            addCriterion("enable is not null");
            return (Criteria) this;
        }

        public Criteria andEnableEqualTo(Boolean value) {
            addCriterion("enable =", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableNotEqualTo(Boolean value) {
            addCriterion("enable <>", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableGreaterThan(Boolean value) {
            addCriterion("enable >", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enable >=", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableLessThan(Boolean value) {
            addCriterion("enable <", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableLessThanOrEqualTo(Boolean value) {
            addCriterion("enable <=", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableIn(List<Boolean> values) {
            addCriterion("enable in", values, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableNotIn(List<Boolean> values) {
            addCriterion("enable not in", values, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableBetween(Boolean value1, Boolean value2) {
            addCriterion("enable between", value1, value2, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enable not between", value1, value2, "enable");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdIsNull() {
            addCriterion("modify_user_id is null");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdIsNotNull() {
            addCriterion("modify_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdEqualTo(Integer value) {
            addCriterion("modify_user_id =", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdNotEqualTo(Integer value) {
            addCriterion("modify_user_id <>", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdGreaterThan(Integer value) {
            addCriterion("modify_user_id >", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("modify_user_id >=", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdLessThan(Integer value) {
            addCriterion("modify_user_id <", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("modify_user_id <=", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdIn(List<Integer> values) {
            addCriterion("modify_user_id in", values, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdNotIn(List<Integer> values) {
            addCriterion("modify_user_id not in", values, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdBetween(Integer value1, Integer value2) {
            addCriterion("modify_user_id between", value1, value2, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("modify_user_id not between", value1, value2, "modifyUserId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
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