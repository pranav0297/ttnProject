package com.e.commerce.domain.e.commerce.domain.table.order;

import java.util.Date;

public class Order {
    private long ID;
    private long CUSTOMER_USER_ID;
    private int AMOUNT_PAID;
    private Date DATE_CREATED;
    private String PAYMENT_METHOD;
    private String CUSTOMER_ADDRESS_CITY;
    private String CUSTOMER_ADDRESS_STATE;
    private String CUSTOMER_ADDRESS_COUNTRY;
    private String CUSTOMER_ADDRESS_ADDRESS_LINE;
    private String CUSTOMER_ADDRESS_ZIP_CODE;
    private String CUSTOMER_ADDRESS_LABEL;
}
