package com.red.os_api.config;

import com.red.os_api.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  private final AuthenticationProvider provider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers("/store/api/auth/**", "/store/api/search/**")
          .permitAll()
            .requestMatchers("/store/api/admin/category/**"
            ,"/store/api/admin/customer/**"
            ,"/store/api/admin/paymentMethod/**"
            ,"/store/api/admin/deliveryMethod/**"
            ,"/store/api/admin/orderProduct/**"
            ,"/store/api/admin/product/**"
            ,"/store/api/admin/**"
            ,"/store/api/admin/review/**").hasRole(Role.ADMIN.name())
            .requestMatchers("/store/api/master/**").hasRole(Role.MASTER.name())
            .requestMatchers("/store/api/account/**"
            ,"/store/api/account/cart/**"
            ,"/store/api/account/order/**"
            ,"/store/api/account/review/**").hasRole(Role.CUSTOMER.name())
        .anyRequest()
          .authenticated()
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(provider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout()
        .logoutUrl("/store/api/auth/logout")
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    ;

    return httpSecurity.build();
  }
}
