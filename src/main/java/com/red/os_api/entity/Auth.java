package com.red.os_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth")
public class Auth implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  @Column(name = "first_name", nullable = false)
  private String first_name;
  @Column(name = "last_name",nullable = false)
  private String last_name;
  @Column(name = "email",nullable = false, unique = true)
  private String email;
  @Column(name = "password", nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "auth")
  private List<Token> tokens;

  @OneToOne(mappedBy = "auth")
  private CustomerDetails customerDetails;

  @OneToMany(mappedBy = "auth")
  private List<Cart> carts;

  @OneToMany(mappedBy = "auth")
  private List<Review> reviews;

  @OneToMany(mappedBy = "auth")
  private List<Order> orders;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }



}

