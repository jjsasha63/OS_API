package com.red.os_api.entity.req_resp;

import com.red.os_api.entity.Grade;
import io.swagger.models.auth.In;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    @Nullable
    Integer review_id;


    Integer product_id;

    Grade grade;

    String review_text;

    List<ReviewRequest> reviewRequestList;

}
