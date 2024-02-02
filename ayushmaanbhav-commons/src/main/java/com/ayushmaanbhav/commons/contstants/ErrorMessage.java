package com.ayushmaanbhav.commons.contstants;

public class ErrorMessage {

    public static final String PINCODE_EMPTY_ERROR_MESSAGE = "Pincode cannot be empty";
    public static final String PINCODE_SIZE_ERROR_MESSAGE = "Pincode must be of size 6";

    public static final String STORE_NAME_EMPTY_ERROR_MESSAGE = "Store name cannot be empty";
    public static final String STORE_NAME_TOO_SMALL_ERROR_MESSAGE = "Store name too small";

    public static final String ADDRESS_EMPTY_ERROR_MESSAGE = "Address cannot be empty";
    public static final String ADDRESS_TOO_SMALL_ERROR_MESSAGE = "Address too small";
    public static final String ADDRESS_TOO_LARGE_ERROR_MESSAGE = "Address too large";

    public static final String ORDER_AMOUNT_NEGATIVE_ERROR_MESSAGE = "Order amount cannot be less than 0";
    public static final String DELIVERY_FEE_NEGATIVE_ERROR_MESSAGE = "Delivery fee cannot be less than 0";
    public static final String SELLING_PRICE_NEGATIVE_ERROR_MESSAGE = "Selling price cannot be less than 0";
    public static final String MRP_NEGATIVE_ERROR_MESSAGE = "MRP cannot be less than 0";
    public static final String DISTANCE_NEGATIVE_ERROR_MESSAGE = "Distance cannot be less than 0";
    public static final String SP_MORE_THAN_MRP_ERROR_MESSAGE = "Selling Price cannot be more than MRP";


    public static final String ACCEPTED_QUANTITY_NEGATIVE_ERROR_MESSAGE = "Accepted quantity cannot be less than 0";
    public static final String FULFILLED_QUANTITY_NEGATIVE_ERROR_MESSAGE = "Fulfilled quantity cannot be less than 0";

    public static final String ORDER_QUANTITY_ZERO_ERROR_MESSAGE = "Order quantity cannot be less than 1";

    public static final String LOCALITY_ID_INVALID_ERROR_MESSAGE = "Locality Id is not valid";
    public static final String LOCALITY_NAME_EMPTY_ERROR_MESSAGE = "Locality name cannot be empty";
    public static final String LOCALITY_NAME_TOO_SMALL_ERROR_MESSAGE = "Locality name too small";

    public static final String PRODUCT_ID_INVALID_ERROR_MESSAGE = "Product Id is not valid";
    public static final String PRODUCT_NAME_EMPTY_ERROR_MESSAGE = "Product name cannot be empty";
    public static final String PRODUCT_NAME_TOO_SMALL_ERROR_MESSAGE = "Product name too small";

    public static final String PARTY_ID_INVALID_ERROR_MESSAGE = "Party Id is not valid";
    public static final String ORDER_ID_INVALID_ERROR_MESSAGE = "Order Id is not valid";
    public static final String DELIVERY_ADDRESS_INVALID_ERROR_MESSAGE = "Delivery address is not valid";

    public static final String CATEGORY_ID_INVALID_ERROR_MESSAGE = "Category Id is not valid";
    public static final String CATEGORY_NAME_EMPTY_ERROR_MESSAGE = "Category name cannot be empty";
    public static final String CATEGORY_NAME_TOO_SMALL_ERROR_MESSAGE = "Category name too small";

    public static final String IMAGE_ID_INVALID_ERROR_MESSAGE = "Image Id is not valid";

    public static final String MOBILE_EMPTY_ERROR_MESSAGE = "Mobile number cannot be empty";
    public static final String MOBILE_INVALID_ERROR_MESSAGE = "Invalid mobile number";

    public static final String CUSTOMER_NAME_EMPTY_ERROR_MESSAGE = "Customer name cannot be empty";
    public static final String CUSTOMER_NAME_TOO_SHORT_ERROR_MESSAGE = "Customer name is too short";
    public static final String GRIEVANCE_MESSAGE_EMPTY_ERROR_MESSAGE = "Grievance message cannot be empty";
    public static final String GRIEVANCE_MESSAGE_TOO_SMALL_ERROR_MESSAGE = "Grievance message is too small";
    public static final String GRIEVANCE_MESSAGE_TOO_LARGE_ERROR_MESSAGE = "Grievance message is too large";
    public static final String RATING_VALUE_TOO_SMALL_ERROR_MESSAGE = "Rating cannot be less that 1";
    public static final String RATING_VALUE_TOO_LARGE_ERROR_MESSAGE = "Rating cannot be more that 5";
    public static final String REVIEW_COMMENT_TOO_LARGE_MESSAGE = "Review Comment too large";

    public static final String TIME_SLOTS_LIST_EMPTY_ERROR_MESSAGE = "Number of time slots cannot be less than 1";
    public static final String DAY_SCHEDULES_LIST_EMPTY_ERROR_MESSAGE = "Number of day schedules cannot be less than 1";
    public static final String END_TIME_NOT_AFTER_START_TIME_ERROR_MESSAGE = "End time cannot be before or same as start time";
}
