package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CoopExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    private Integer limit;

    private Integer offset;

    public CoopExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andCoopNoIsNull() {
            addCriterion("coop_no is null");
            return (Criteria) this;
        }

        public Criteria andCoopNoIsNotNull() {
            addCriterion("coop_no is not null");
            return (Criteria) this;
        }

        public Criteria andCoopNoEqualTo(String value) {
            addCriterion("coop_no =", value, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoNotEqualTo(String value) {
            addCriterion("coop_no <>", value, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoGreaterThan(String value) {
            addCriterion("coop_no >", value, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoGreaterThanOrEqualTo(String value) {
            addCriterion("coop_no >=", value, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoLessThan(String value) {
            addCriterion("coop_no <", value, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoLessThanOrEqualTo(String value) {
            addCriterion("coop_no <=", value, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoLike(String value) {
            addCriterion("coop_no like", value, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoNotLike(String value) {
            addCriterion("coop_no not like", value, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoIn(List<String> values) {
            addCriterion("coop_no in", values, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoNotIn(List<String> values) {
            addCriterion("coop_no not in", values, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoBetween(String value1, String value2) {
            addCriterion("coop_no between", value1, value2, "coopNo");
            return (Criteria) this;
        }

        public Criteria andCoopNoNotBetween(String value1, String value2) {
            addCriterion("coop_no not between", value1, value2, "coopNo");
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

        public Criteria andCapacityIsNull() {
            addCriterion("capacity is null");
            return (Criteria) this;
        }

        public Criteria andCapacityIsNotNull() {
            addCriterion("capacity is not null");
            return (Criteria) this;
        }

        public Criteria andCapacityEqualTo(Integer value) {
            addCriterion("capacity =", value, "capacity");
            return (Criteria) this;
        }

        public Criteria andCapacityNotEqualTo(Integer value) {
            addCriterion("capacity <>", value, "capacity");
            return (Criteria) this;
        }

        public Criteria andCapacityGreaterThan(Integer value) {
            addCriterion("capacity >", value, "capacity");
            return (Criteria) this;
        }

        public Criteria andCapacityGreaterThanOrEqualTo(Integer value) {
            addCriterion("capacity >=", value, "capacity");
            return (Criteria) this;
        }

        public Criteria andCapacityLessThan(Integer value) {
            addCriterion("capacity <", value, "capacity");
            return (Criteria) this;
        }

        public Criteria andCapacityLessThanOrEqualTo(Integer value) {
            addCriterion("capacity <=", value, "capacity");
            return (Criteria) this;
        }

        public Criteria andCapacityIn(List<Integer> values) {
            addCriterion("capacity in", values, "capacity");
            return (Criteria) this;
        }

        public Criteria andCapacityNotIn(List<Integer> values) {
            addCriterion("capacity not in", values, "capacity");
            return (Criteria) this;
        }

        public Criteria andCapacityBetween(Integer value1, Integer value2) {
            addCriterion("capacity between", value1, value2, "capacity");
            return (Criteria) this;
        }

        public Criteria andCapacityNotBetween(Integer value1, Integer value2) {
            addCriterion("capacity not between", value1, value2, "capacity");
            return (Criteria) this;
        }

        public Criteria andBreedingValueIsNull() {
            addCriterion("breeding_value is null");
            return (Criteria) this;
        }

        public Criteria andBreedingValueIsNotNull() {
            addCriterion("breeding_value is not null");
            return (Criteria) this;
        }

        public Criteria andBreedingValueEqualTo(Integer value) {
            addCriterion("breeding_value =", value, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andBreedingValueNotEqualTo(Integer value) {
            addCriterion("breeding_value <>", value, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andBreedingValueGreaterThan(Integer value) {
            addCriterion("breeding_value >", value, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andBreedingValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("breeding_value >=", value, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andBreedingValueLessThan(Integer value) {
            addCriterion("breeding_value <", value, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andBreedingValueLessThanOrEqualTo(Integer value) {
            addCriterion("breeding_value <=", value, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andBreedingValueIn(List<Integer> values) {
            addCriterion("breeding_value in", values, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andBreedingValueNotIn(List<Integer> values) {
            addCriterion("breeding_value not in", values, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andBreedingValueBetween(Integer value1, Integer value2) {
            addCriterion("breeding_value between", value1, value2, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andBreedingValueNotBetween(Integer value1, Integer value2) {
            addCriterion("breeding_value not between", value1, value2, "breedingValue");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityIsNull() {
            addCriterion("device_quantity is null");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityIsNotNull() {
            addCriterion("device_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityEqualTo(Integer value) {
            addCriterion("device_quantity =", value, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityNotEqualTo(Integer value) {
            addCriterion("device_quantity <>", value, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityGreaterThan(Integer value) {
            addCriterion("device_quantity >", value, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("device_quantity >=", value, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityLessThan(Integer value) {
            addCriterion("device_quantity <", value, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("device_quantity <=", value, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityIn(List<Integer> values) {
            addCriterion("device_quantity in", values, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityNotIn(List<Integer> values) {
            addCriterion("device_quantity not in", values, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityBetween(Integer value1, Integer value2) {
            addCriterion("device_quantity between", value1, value2, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andDeviceQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("device_quantity not between", value1, value2, "deviceQuantity");
            return (Criteria) this;
        }

        public Criteria andCoopStatusIsNull() {
            addCriterion("coop_status is null");
            return (Criteria) this;
        }

        public Criteria andCoopStatusIsNotNull() {
            addCriterion("coop_status is not null");
            return (Criteria) this;
        }

        public Criteria andCoopStatusEqualTo(Integer value) {
            addCriterion("coop_status =", value, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andCoopStatusNotEqualTo(Integer value) {
            addCriterion("coop_status <>", value, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andCoopStatusGreaterThan(Integer value) {
            addCriterion("coop_status >", value, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andCoopStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("coop_status >=", value, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andCoopStatusLessThan(Integer value) {
            addCriterion("coop_status <", value, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andCoopStatusLessThanOrEqualTo(Integer value) {
            addCriterion("coop_status <=", value, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andCoopStatusIn(List<Integer> values) {
            addCriterion("coop_status in", values, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andCoopStatusNotIn(List<Integer> values) {
            addCriterion("coop_status not in", values, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andCoopStatusBetween(Integer value1, Integer value2) {
            addCriterion("coop_status between", value1, value2, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andCoopStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("coop_status not between", value1, value2, "coopStatus");
            return (Criteria) this;
        }

        public Criteria andLastStartDateIsNull() {
            addCriterion("last_start_date is null");
            return (Criteria) this;
        }

        public Criteria andLastStartDateIsNotNull() {
            addCriterion("last_start_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("last_start_date =", value, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("last_start_date <>", value, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("last_start_date >", value, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_start_date >=", value, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastStartDateLessThan(Date value) {
            addCriterionForJDBCDate("last_start_date <", value, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_start_date <=", value, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("last_start_date in", values, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("last_start_date not in", values, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_start_date between", value1, value2, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_start_date not between", value1, value2, "lastStartDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateIsNull() {
            addCriterion("last_end_date is null");
            return (Criteria) this;
        }

        public Criteria andLastEndDateIsNotNull() {
            addCriterion("last_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("last_end_date =", value, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("last_end_date <>", value, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("last_end_date >", value, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_end_date >=", value, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateLessThan(Date value) {
            addCriterionForJDBCDate("last_end_date <", value, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("last_end_date <=", value, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("last_end_date in", values, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("last_end_date not in", values, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_end_date between", value1, value2, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andLastEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("last_end_date not between", value1, value2, "lastEndDate");
            return (Criteria) this;
        }

        public Criteria andIotBrandIsNull() {
            addCriterion("iot_brand is null");
            return (Criteria) this;
        }

        public Criteria andIotBrandIsNotNull() {
            addCriterion("iot_brand is not null");
            return (Criteria) this;
        }

        public Criteria andIotBrandEqualTo(Integer value) {
            addCriterion("iot_brand =", value, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotBrandNotEqualTo(Integer value) {
            addCriterion("iot_brand <>", value, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotBrandGreaterThan(Integer value) {
            addCriterion("iot_brand >", value, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotBrandGreaterThanOrEqualTo(Integer value) {
            addCriterion("iot_brand >=", value, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotBrandLessThan(Integer value) {
            addCriterion("iot_brand <", value, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotBrandLessThanOrEqualTo(Integer value) {
            addCriterion("iot_brand <=", value, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotBrandIn(List<Integer> values) {
            addCriterion("iot_brand in", values, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotBrandNotIn(List<Integer> values) {
            addCriterion("iot_brand not in", values, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotBrandBetween(Integer value1, Integer value2) {
            addCriterion("iot_brand between", value1, value2, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotBrandNotBetween(Integer value1, Integer value2) {
            addCriterion("iot_brand not between", value1, value2, "iotBrand");
            return (Criteria) this;
        }

        public Criteria andIotUsernameIsNull() {
            addCriterion("iot_username is null");
            return (Criteria) this;
        }

        public Criteria andIotUsernameIsNotNull() {
            addCriterion("iot_username is not null");
            return (Criteria) this;
        }

        public Criteria andIotUsernameEqualTo(String value) {
            addCriterion("iot_username =", value, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameNotEqualTo(String value) {
            addCriterion("iot_username <>", value, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameGreaterThan(String value) {
            addCriterion("iot_username >", value, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("iot_username >=", value, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameLessThan(String value) {
            addCriterion("iot_username <", value, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameLessThanOrEqualTo(String value) {
            addCriterion("iot_username <=", value, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameLike(String value) {
            addCriterion("iot_username like", value, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameNotLike(String value) {
            addCriterion("iot_username not like", value, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameIn(List<String> values) {
            addCriterion("iot_username in", values, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameNotIn(List<String> values) {
            addCriterion("iot_username not in", values, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameBetween(String value1, String value2) {
            addCriterion("iot_username between", value1, value2, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotUsernameNotBetween(String value1, String value2) {
            addCriterion("iot_username not between", value1, value2, "iotUsername");
            return (Criteria) this;
        }

        public Criteria andIotPasswordIsNull() {
            addCriterion("iot_password is null");
            return (Criteria) this;
        }

        public Criteria andIotPasswordIsNotNull() {
            addCriterion("iot_password is not null");
            return (Criteria) this;
        }

        public Criteria andIotPasswordEqualTo(String value) {
            addCriterion("iot_password =", value, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordNotEqualTo(String value) {
            addCriterion("iot_password <>", value, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordGreaterThan(String value) {
            addCriterion("iot_password >", value, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("iot_password >=", value, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordLessThan(String value) {
            addCriterion("iot_password <", value, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordLessThanOrEqualTo(String value) {
            addCriterion("iot_password <=", value, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordLike(String value) {
            addCriterion("iot_password like", value, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordNotLike(String value) {
            addCriterion("iot_password not like", value, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordIn(List<String> values) {
            addCriterion("iot_password in", values, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordNotIn(List<String> values) {
            addCriterion("iot_password not in", values, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordBetween(String value1, String value2) {
            addCriterion("iot_password between", value1, value2, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andIotPasswordNotBetween(String value1, String value2) {
            addCriterion("iot_password not between", value1, value2, "iotPassword");
            return (Criteria) this;
        }

        public Criteria andNotesIsNull() {
            addCriterion("notes is null");
            return (Criteria) this;
        }

        public Criteria andNotesIsNotNull() {
            addCriterion("notes is not null");
            return (Criteria) this;
        }

        public Criteria andNotesEqualTo(String value) {
            addCriterion("notes =", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotEqualTo(String value) {
            addCriterion("notes <>", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThan(String value) {
            addCriterion("notes >", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThanOrEqualTo(String value) {
            addCriterion("notes >=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThan(String value) {
            addCriterion("notes <", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThanOrEqualTo(String value) {
            addCriterion("notes <=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLike(String value) {
            addCriterion("notes like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotLike(String value) {
            addCriterion("notes not like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesIn(List<String> values) {
            addCriterion("notes in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotIn(List<String> values) {
            addCriterion("notes not in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesBetween(String value1, String value2) {
            addCriterion("notes between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotBetween(String value1, String value2) {
            addCriterion("notes not between", value1, value2, "notes");
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