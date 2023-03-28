package com.red.os_api.entity.req_resp;

import com.red.os_api.entity.Grade;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

    Integer review_id;

    Integer auth_id;

    Integer product_id;

    Grade grade;

    String review_text;

    LocalDateTime review_date;

}
