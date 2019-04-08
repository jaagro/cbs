package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeviceAlarmLogExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    private Integer limit;

    private Integer offset;

    public DeviceAlarmLogExample() {
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

        public Criteria andPlanIdIsNull() {
            addCriterion("plan_id is null");
            return (Criteria) this;
        }

        public Criteria andPlanIdIsNotNull() {
            addCriterion("plan_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlanIdEqualTo(Integer value) {
            addCriterion("plan_id =", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotEqualTo(Integer value) {
            addCriterion("plan_id <>", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThan(Integer value) {
            addCriterion("plan_id >", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_id >=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThan(Integer value) {
            addCriterion("plan_id <", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThanOrEqualTo(Integer value) {
            addCriterion("plan_id <=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdIn(List<Integer> values) {
            addCriterion("plan_id in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotIn(List<Integer> values) {
            addCriterion("plan_id not in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdBetween(Integer value1, Integer value2) {
            addCriterion("plan_id between", value1, value2, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_id not between", value1, value2, "planId");
            return (Criteria) this;
        }

        public Criteria andPlantIdIsNull() {
            addCriterion("plant_id is null");
            return (Criteria) this;
        }

        public Criteria andPlantIdIsNotNull() {
            addCriterion("plant_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlantIdEqualTo(Integer value) {
            addCriterion("plant_id =", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdNotEqualTo(Integer value) {
            addCriterion("plant_id <>", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdGreaterThan(Integer value) {
            addCriterion("plant_id >", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("plant_id >=", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdLessThan(Integer value) {
            addCriterion("plant_id <", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdLessThanOrEqualTo(Integer value) {
            addCriterion("plant_id <=", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdIn(List<Integer> values) {
            addCriterion("plant_id in", values, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdNotIn(List<Integer> values) {
            addCriterion("plant_id not in", values, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdBetween(Integer value1, Integer value2) {
            addCriterion("plant_id between", value1, value2, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdNotBetween(Integer value1, Integer value2) {
            addCriterion("plant_id not between", value1, value2, "plantId");
            return (Criteria) this;
        }

        public Criteria andCoopIdIsNull() {
            addCriterion("coop_id is null");
            return (Criteria) this;
        }

        public Criteria andCoopIdIsNotNull() {
            addCriterion("coop_id is not null");
            return (Criteria) this;
        }

        public Criteria andCoopIdEqualTo(Integer value) {
            addCriterion("coop_id =", value, "coopId");
            return (Criteria) this;
        }

        public Criteria andCoopIdNotEqualTo(Integer value) {
            addCriterion("coop_id <>", value, "coopId");
            return (Criteria) this;
        }

        public Criteria andCoopIdGreaterThan(Integer value) {
            addCriterion("coop_id >", value, "coopId");
            return (Criteria) this;
        }

        public Criteria andCoopIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("coop_id >=", value, "coopId");
            return (Criteria) this;
        }

        public Criteria andCoopIdLessThan(Integer value) {
            addCriterion("coop_id <", value, "coopId");
            return (Criteria) this;
        }

        public Criteria andCoopIdLessThanOrEqualTo(Integer value) {
            addCriterion("coop_id <=", value, "coopId");
            return (Criteria) this;
        }

        public Criteria andCoopIdIn(List<Integer> values) {
            addCriterion("coop_id in", values, "coopId");
            return (Criteria) this;
        }

        public Criteria andCoopIdNotIn(List<Integer> values) {
            addCriterion("coop_id not in", values, "coopId");
            return (Criteria) this;
        }

        public Criteria andCoopIdBetween(Integer value1, Integer value2) {
            addCriterion("coop_id between", value1, value2, "coopId");
            return (Criteria) this;
        }

        public Criteria andCoopIdNotBetween(Integer value1, Integer value2) {
            addCriterion("coop_id not between", value1, value2, "coopId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIsNull() {
            addCriterion("device_id is null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIsNotNull() {
            addCriterion("device_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdEqualTo(Integer value) {
            addCriterion("device_id =", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotEqualTo(Integer value) {
            addCriterion("device_id <>", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThan(Integer value) {
            addCriterion("device_id >", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("device_id >=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThan(Integer value) {
            addCriterion("device_id <", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThanOrEqualTo(Integer value) {
            addCriterion("device_id <=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIn(List<Integer> values) {
            addCriterion("device_id in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotIn(List<Integer> values) {
            addCriterion("device_id not in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdBetween(Integer value1, Integer value2) {
            addCriterion("device_id between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("device_id not between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDayAgeIsNull() {
            addCriterion("day_age is null");
            return (Criteria) this;
        }

        public Criteria andDayAgeIsNotNull() {
            addCriterion("day_age is not null");
            return (Criteria) this;
        }

        public Criteria andDayAgeEqualTo(Integer value) {
            addCriterion("day_age =", value, "dayAge");
            return (Criteria) this;
        }

        public Criteria andDayAgeNotEqualTo(Integer value) {
            addCriterion("day_age <>", value, "dayAge");
            return (Criteria) this;
        }

        public Criteria andDayAgeGreaterThan(Integer value) {
            addCriterion("day_age >", value, "dayAge");
            return (Criteria) this;
        }

        public Criteria andDayAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("day_age >=", value, "dayAge");
            return (Criteria) this;
        }

        public Criteria andDayAgeLessThan(Integer value) {
            addCriterion("day_age <", value, "dayAge");
            return (Criteria) this;
        }

        public Criteria andDayAgeLessThanOrEqualTo(Integer value) {
            addCriterion("day_age <=", value, "dayAge");
            return (Criteria) this;
        }

        public Criteria andDayAgeIn(List<Integer> values) {
            addCriterion("day_age in", values, "dayAge");
            return (Criteria) this;
        }

        public Criteria andDayAgeNotIn(List<Integer> values) {
            addCriterion("day_age not in", values, "dayAge");
            return (Criteria) this;
        }

        public Criteria andDayAgeBetween(Integer value1, Integer value2) {
            addCriterion("day_age between", value1, value2, "dayAge");
            return (Criteria) this;
        }

        public Criteria andDayAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("day_age not between", value1, value2, "dayAge");
            return (Criteria) this;
        }

        public Criteria andCurrentValueIsNull() {
            addCriterion("current_value is null");
            return (Criteria) this;
        }

        public Criteria andCurrentValueIsNotNull() {
            addCriterion("current_value is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentValueEqualTo(BigDecimal value) {
            addCriterion("current_value =", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotEqualTo(BigDecimal value) {
            addCriterion("current_value <>", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueGreaterThan(BigDecimal value) {
            addCriterion("current_value >", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("current_value >=", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueLessThan(BigDecimal value) {
            addCriterion("current_value <", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("current_value <=", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueIn(List<BigDecimal> values) {
            addCriterion("current_value in", values, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotIn(List<BigDecimal> values) {
            addCriterion("current_value not in", values, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("current_value between", value1, value2, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("current_value not between", value1, value2, "currentValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueIsNull() {
            addCriterion("param_standard_value is null");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueIsNotNull() {
            addCriterion("param_standard_value is not null");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueEqualTo(String value) {
            addCriterion("param_standard_value =", value, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueNotEqualTo(String value) {
            addCriterion("param_standard_value <>", value, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueGreaterThan(String value) {
            addCriterion("param_standard_value >", value, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueGreaterThanOrEqualTo(String value) {
            addCriterion("param_standard_value >=", value, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueLessThan(String value) {
            addCriterion("param_standard_value <", value, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueLessThanOrEqualTo(String value) {
            addCriterion("param_standard_value <=", value, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueLike(String value) {
            addCriterion("param_standard_value like", value, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueNotLike(String value) {
            addCriterion("param_standard_value not like", value, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueIn(List<String> values) {
            addCriterion("param_standard_value in", values, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueNotIn(List<String> values) {
            addCriterion("param_standard_value not in", values, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueBetween(String value1, String value2) {
            addCriterion("param_standard_value between", value1, value2, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andParamStandardValueNotBetween(String value1, String value2) {
            addCriterion("param_standard_value not between", value1, value2, "paramStandardValue");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNull() {
            addCriterion("handle_status is null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNotNull() {
            addCriterion("handle_status is not null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusEqualTo(Integer value) {
            addCriterion("handle_status =", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotEqualTo(Integer value) {
            addCriterion("handle_status <>", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThan(Integer value) {
            addCriterion("handle_status >", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_status >=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThan(Integer value) {
            addCriterion("handle_status <", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThanOrEqualTo(Integer value) {
            addCriterion("handle_status <=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIn(List<Integer> values) {
            addCriterion("handle_status in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotIn(List<Integer> values) {
            addCriterion("handle_status not in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusBetween(Integer value1, Integer value2) {
            addCriterion("handle_status between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_status not between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNull() {
            addCriterion("handle_time is null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNotNull() {
            addCriterion("handle_time is not null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeEqualTo(Date value) {
            addCriterion("handle_time =", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotEqualTo(Date value) {
            addCriterion("handle_time <>", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThan(Date value) {
            addCriterion("handle_time >", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("handle_time >=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThan(Date value) {
            addCriterion("handle_time <", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThanOrEqualTo(Date value) {
            addCriterion("handle_time <=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIn(List<Date> values) {
            addCriterion("handle_time in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotIn(List<Date> values) {
            addCriterion("handle_time not in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeBetween(Date value1, Date value2) {
            addCriterion("handle_time between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotBetween(Date value1, Date value2) {
            addCriterion("handle_time not between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdIsNull() {
            addCriterion("handle_user_id is null");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdIsNotNull() {
            addCriterion("handle_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdEqualTo(Integer value) {
            addCriterion("handle_user_id =", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdNotEqualTo(Integer value) {
            addCriterion("handle_user_id <>", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdGreaterThan(Integer value) {
            addCriterion("handle_user_id >", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_user_id >=", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdLessThan(Integer value) {
            addCriterion("handle_user_id <", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("handle_user_id <=", value, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdIn(List<Integer> values) {
            addCriterion("handle_user_id in", values, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdNotIn(List<Integer> values) {
            addCriterion("handle_user_id not in", values, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdBetween(Integer value1, Integer value2) {
            addCriterion("handle_user_id between", value1, value2, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_user_id not between", value1, value2, "handleUserId");
            return (Criteria) this;
        }

        public Criteria andHandleDescIsNull() {
            addCriterion("handle_desc is null");
            return (Criteria) this;
        }

        public Criteria andHandleDescIsNotNull() {
            addCriterion("handle_desc is not null");
            return (Criteria) this;
        }

        public Criteria andHandleDescEqualTo(String value) {
            addCriterion("handle_desc =", value, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescNotEqualTo(String value) {
            addCriterion("handle_desc <>", value, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescGreaterThan(String value) {
            addCriterion("handle_desc >", value, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescGreaterThanOrEqualTo(String value) {
            addCriterion("handle_desc >=", value, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescLessThan(String value) {
            addCriterion("handle_desc <", value, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescLessThanOrEqualTo(String value) {
            addCriterion("handle_desc <=", value, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescLike(String value) {
            addCriterion("handle_desc like", value, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescNotLike(String value) {
            addCriterion("handle_desc not like", value, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescIn(List<String> values) {
            addCriterion("handle_desc in", values, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescNotIn(List<String> values) {
            addCriterion("handle_desc not in", values, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescBetween(String value1, String value2) {
            addCriterion("handle_desc between", value1, value2, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleDescNotBetween(String value1, String value2) {
            addCriterion("handle_desc not between", value1, value2, "handleDesc");
            return (Criteria) this;
        }

        public Criteria andHandleTypeIsNull() {
            addCriterion("handle_type is null");
            return (Criteria) this;
        }

        public Criteria andHandleTypeIsNotNull() {
            addCriterion("handle_type is not null");
            return (Criteria) this;
        }

        public Criteria andHandleTypeEqualTo(Integer value) {
            addCriterion("handle_type =", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotEqualTo(Integer value) {
            addCriterion("handle_type <>", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeGreaterThan(Integer value) {
            addCriterion("handle_type >", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_type >=", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeLessThan(Integer value) {
            addCriterion("handle_type <", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeLessThanOrEqualTo(Integer value) {
            addCriterion("handle_type <=", value, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeIn(List<Integer> values) {
            addCriterion("handle_type in", values, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotIn(List<Integer> values) {
            addCriterion("handle_type not in", values, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeBetween(Integer value1, Integer value2) {
            addCriterion("handle_type between", value1, value2, "handleType");
            return (Criteria) this;
        }

        public Criteria andHandleTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_type not between", value1, value2, "handleType");
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