package com.red.os_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer id;

  @Column(name = "token",unique = true)
  public String token;

  @Column(name = "token_type")
  @Enumerated(EnumType.STRING)
  public TokenType token_type = TokenType.BEARER;

  @Column(name = "revoked",nullable = false)
  public boolean revoked;

  @Column(name = "expired", nullable = false)
  public boolean expired;

  @ManyToOne
  @JoinColumn(name = "auth")
  public Auth auth;
}
