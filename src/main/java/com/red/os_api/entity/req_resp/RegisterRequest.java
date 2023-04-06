package com.red.os_api.entity.req_resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
  private Integer auth_id;

  private String first_name;
  private String last_name;
  private String email;
  private String password;

  private String role;
}
