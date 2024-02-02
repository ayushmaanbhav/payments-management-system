package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import com.ayushmaanbhav.commons.contstants.ReviewType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class ReviewRequest {
    @Min(value = 1, message = ErrorMessage.RATING_VALUE_TOO_SMALL_ERROR_MESSAGE)
    @Max(value = 5, message = ErrorMessage.RATING_VALUE_TOO_LARGE_ERROR_MESSAGE)
    private int rating;
    @Size(max = 255, message = ErrorMessage.REVIEW_COMMENT_TOO_LARGE_MESSAGE)
    private String formPartyReviewComment;
    private ReviewType reviewType;
}
