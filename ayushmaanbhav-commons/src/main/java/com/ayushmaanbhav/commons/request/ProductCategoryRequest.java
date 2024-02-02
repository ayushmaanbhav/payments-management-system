package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProductCategoryRequest {

    @NotNull(message = ErrorMessage.CATEGORY_NAME_EMPTY_ERROR_MESSAGE)
    @Size(min = 4, message = ErrorMessage.CATEGORY_NAME_TOO_SMALL_ERROR_MESSAGE)
    private String categoryName;
    @Min(value = 1, message = ErrorMessage.IMAGE_ID_INVALID_ERROR_MESSAGE)
    private int imageId;

}
