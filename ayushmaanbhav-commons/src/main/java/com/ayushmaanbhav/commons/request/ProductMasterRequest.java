package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.ayushmaanbhav.commons.contstants.ErrorMessage.*;

@Data
public class ProductMasterRequest {

    @Min(value = 1, message = ErrorMessage.CATEGORY_ID_INVALID_ERROR_MESSAGE)
    private int categoryId;
    private String brand;
    @NotNull(message = ErrorMessage.PRODUCT_NAME_EMPTY_ERROR_MESSAGE)
    @Size(min = 2, message = ErrorMessage.PRODUCT_NAME_TOO_SMALL_ERROR_MESSAGE)
    private String productName;
    private String variant;
    private String variantType;
    private String type;
    @Min(value = 0, message = MRP_NEGATIVE_ERROR_MESSAGE)
    private float mrp;
    @Min(value = 0, message = SELLING_PRICE_NEGATIVE_ERROR_MESSAGE)
    private float sellingPrice;
    private String subCategory;
    private String source;
    private String jMProductId;
    private int imageId;

    @AssertTrue(message = SP_MORE_THAN_MRP_ERROR_MESSAGE)
    public boolean isMrpGreaterOrEqualToSellingPrice() {
        return mrp >= sellingPrice;
    }

}
