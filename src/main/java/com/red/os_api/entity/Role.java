package com.red.os_api.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;


public enum Role {

  CUSTOMER,
  ADMIN,
  MASTER;

  public Set<Role> getRoles(){
    Set<Role> roles = new HashSet<>();
    roles.add(CUSTOMER);
    roles.add(ADMIN);
    roles.add(MASTER);
    return roles;
  }

}
