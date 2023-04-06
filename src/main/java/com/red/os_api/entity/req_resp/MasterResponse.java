package com.red.os_api.entity.req_resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterResponse {
    Integer auth_id;
    String email;
    String first_name;
    String last_name;
    String role;
}
