package com.e.commerce.domain.e.commerce.domain.Enum;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;

public enum ENUM {
    ORDER_PLACED("CANCELLED", "ORDER_CONFIRMED", "ORDER_REJECTED"),
    CANCELLED("REFUND_INITIATED", "CLOSED"),
    ORDER_REJECTED("REFUND_INITIATED", "CLOSED"),
    ORDER_CONFIRMED("CANCELLED", "ORDER_SHIPPED"),
    ORDER_SHIPPED("DELIVERED"),
    DELIVERED("RETURN_REQUESTED", "CLOSED"),
    RETURN_REQUESTED("RETURN_REJECTED", "RETURN_APPROVED"),
    RETURN_REJECTED("CLOSED"),
    RETURN_APPROVED("PICK_UP_INITIATED"),
    PICK_UP_INITIATED("PICK_UP_COMPLETED"),
    PICK_UP_COMPLETED("REFUND_INITIATED"),
    REFUND_INITIATED("REFUND_COMPLETED"),
    REFUND_COMPLETED("CLOSED");

    private final List<String> values;

    ENUM(String... values) {
        this.values = Arrays.asList(values);
    }

    public List<String> getValues() {
        return values;
    }

}
