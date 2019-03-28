package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TechConsultRecordExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    private Integer limit;

    private Integer offset;

    public TechConsultRecordExample() {
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

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(Integer value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(Integer value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(Integer value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(Integer value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(Integer value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<Integer> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<Integer> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(Integer value1, Integer value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNull() {
            addCriterion("customer_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNotNull() {
            addCriterion("customer_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameEqualTo(String value) {
            addCriterion("customer_name =", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotEqualTo(String value) {
            addCriterion("customer_name <>", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThan(String value) {
            addCriterion("customer_name >", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("customer_name >=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThan(String value) {
            addCriterion("customer_name <", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("customer_name <=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLike(String value) {
            addCriterion("customer_name like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotLike(String value) {
            addCriterion("customer_name not like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIn(List<String> values) {
            addCriterion("customer_name in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotIn(List<String> values) {
            addCriterion("customer_name not in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameBetween(String value1, String value2) {
            addCriterion("customer_name between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotBetween(String value1, String value2) {
            addCriterion("customer_name not between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberIsNull() {
            addCriterion("customer_phone_number is null");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberIsNotNull() {
            addCriterion("customer_phone_number is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberEqualTo(String value) {
            addCriterion("customer_phone_number =", value, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberNotEqualTo(String value) {
            addCriterion("customer_phone_number <>", value, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberGreaterThan(String value) {
            addCriterion("customer_phone_number >", value, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberGreaterThanOrEqualTo(String value) {
            addCriterion("customer_phone_number >=", value, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberLessThan(String value) {
            addCriterion("customer_phone_number <", value, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberLessThanOrEqualTo(String value) {
            addCriterion("customer_phone_number <=", value, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberLike(String value) {
            addCriterion("customer_phone_number like", value, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberNotLike(String value) {
            addCriterion("customer_phone_number not like", value, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberIn(List<String> values) {
            addCriterion("customer_phone_number in", values, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberNotIn(List<String> values) {
            addCriterion("customer_phone_number not in", values, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberBetween(String value1, String value2) {
            addCriterion("customer_phone_number between", value1, value2, "customerPhoneNumber");
            return (Criteria) this;
        }

        public Criteria andCustomerPhoneNumberNotBetween(String value1, String value2) {
            addCriterion("customer_phone_number not between", value1, value2, "customerPhoneNumber");
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

        public Criteria andCoopNameIsNull() {
            addCriterion("coop_name is null");
            return (Criteria) this;
        }

        public Criteria andCoopNameIsNotNull() {
            addCriterion("coop_name is not null");
            return (Criteria) this;
        }

        public Criteria andCoopNameEqualTo(String value) {
            addCriterion("coop_name =", value, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameNotEqualTo(String value) {
            addCriterion("coop_name <>", value, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameGreaterThan(String value) {
            addCriterion("coop_name >", value, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameGreaterThanOrEqualTo(String value) {
            addCriterion("coop_name >=", value, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameLessThan(String value) {
            addCriterion("coop_name <", value, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameLessThanOrEqualTo(String value) {
            addCriterion("coop_name <=", value, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameLike(String value) {
            addCriterion("coop_name like", value, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameNotLike(String value) {
            addCriterion("coop_name not like", value, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameIn(List<String> values) {
            addCriterion("coop_name in", values, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameNotIn(List<String> values) {
            addCriterion("coop_name not in", values, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameBetween(String value1, String value2) {
            addCriterion("coop_name between", value1, value2, "coopName");
            return (Criteria) this;
        }

        public Criteria andCoopNameNotBetween(String value1, String value2) {
            addCriterion("coop_name not between", value1, value2, "coopName");
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

        public Criteria andPlantNameIsNull() {
            addCriterion("plant_name is null");
            return (Criteria) this;
        }

        public Criteria andPlantNameIsNotNull() {
            addCriterion("plant_name is not null");
            return (Criteria) this;
        }

        public Criteria andPlantNameEqualTo(String value) {
            addCriterion("plant_name =", value, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameNotEqualTo(String value) {
            addCriterion("plant_name <>", value, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameGreaterThan(String value) {
            addCriterion("plant_name >", value, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameGreaterThanOrEqualTo(String value) {
            addCriterion("plant_name >=", value, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameLessThan(String value) {
            addCriterion("plant_name <", value, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameLessThanOrEqualTo(String value) {
            addCriterion("plant_name <=", value, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameLike(String value) {
            addCriterion("plant_name like", value, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameNotLike(String value) {
            addCriterion("plant_name not like", value, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameIn(List<String> values) {
            addCriterion("plant_name in", values, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameNotIn(List<String> values) {
            addCriterion("plant_name not in", values, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameBetween(String value1, String value2) {
            addCriterion("plant_name between", value1, value2, "plantName");
            return (Criteria) this;
        }

        public Criteria andPlantNameNotBetween(String value1, String value2) {
            addCriterion("plant_name not between", value1, value2, "plantName");
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

        public Criteria andBatchNoIsNull() {
            addCriterion("batch_no is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("batch_no =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("batch_no <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("batch_no >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("batch_no >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("batch_no <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("batch_no <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("batch_no like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("batch_no not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("batch_no in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("batch_no not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("batch_no between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("batch_no not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelIsNull() {
            addCriterion("emergency_level is null");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelIsNotNull() {
            addCriterion("emergency_level is not null");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelEqualTo(Integer value) {
            addCriterion("emergency_level =", value, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelNotEqualTo(Integer value) {
            addCriterion("emergency_level <>", value, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelGreaterThan(Integer value) {
            addCriterion("emergency_level >", value, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("emergency_level >=", value, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelLessThan(Integer value) {
            addCriterion("emergency_level <", value, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelLessThanOrEqualTo(Integer value) {
            addCriterion("emergency_level <=", value, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelIn(List<Integer> values) {
            addCriterion("emergency_level in", values, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelNotIn(List<Integer> values) {
            addCriterion("emergency_level not in", values, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelBetween(Integer value1, Integer value2) {
            addCriterion("emergency_level between", value1, value2, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andEmergencyLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("emergency_level not between", value1, value2, "emergencyLevel");
            return (Criteria) this;
        }

        public Criteria andProblemDescIsNull() {
            addCriterion("problem_desc is null");
            return (Criteria) this;
        }

        public Criteria andProblemDescIsNotNull() {
            addCriterion("problem_desc is not null");
            return (Criteria) this;
        }

        public Criteria andProblemDescEqualTo(String value) {
            addCriterion("problem_desc =", value, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescNotEqualTo(String value) {
            addCriterion("problem_desc <>", value, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescGreaterThan(String value) {
            addCriterion("problem_desc >", value, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescGreaterThanOrEqualTo(String value) {
            addCriterion("problem_desc >=", value, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescLessThan(String value) {
            addCriterion("problem_desc <", value, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescLessThanOrEqualTo(String value) {
            addCriterion("problem_desc <=", value, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescLike(String value) {
            addCriterion("problem_desc like", value, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescNotLike(String value) {
            addCriterion("problem_desc not like", value, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescIn(List<String> values) {
            addCriterion("problem_desc in", values, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescNotIn(List<String> values) {
            addCriterion("problem_desc not in", values, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescBetween(String value1, String value2) {
            addCriterion("problem_desc between", value1, value2, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andProblemDescNotBetween(String value1, String value2) {
            addCriterion("problem_desc not between", value1, value2, "problemDesc");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusIsNull() {
            addCriterion("tech_consult_status is null");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusIsNotNull() {
            addCriterion("tech_consult_status is not null");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusEqualTo(Integer value) {
            addCriterion("tech_consult_status =", value, "techConsultStatus");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusNotEqualTo(Integer value) {
            addCriterion("tech_consult_status <>", value, "techConsultStatus");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusGreaterThan(Integer value) {
            addCriterion("tech_consult_status >", value, "techConsultStatus");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("tech_consult_status >=", value, "techConsultStatus");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusLessThan(Integer value) {
            addCriterion("tech_consult_status <", value, "techConsultStatus");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusLessThanOrEqualTo(Integer value) {
            addCriterion("tech_consult_status <=", value, "techConsultStatus");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusIn(List<Integer> values) {
            addCriterion("tech_consult_status in", values, "techConsultStatus");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusNotIn(List<Integer> values) {
            addCriterion("tech_consult_status not in", values, "techConsultStatus");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusBetween(Integer value1, Integer value2) {
            addCriterion("tech_consult_status between", value1, value2, "techConsultStatus");
            return (Criteria) this;
        }

        public Criteria andTechConsultStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("tech_consult_status not between", value1, value2, "techConsultStatus");
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