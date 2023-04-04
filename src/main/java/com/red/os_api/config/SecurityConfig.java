package com.red.os_api.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.red.os_api.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  private final AuthenticationProvider provider;
  private final LogoutHandler logoutHandler;

//  private final RSAKeyConfig rsaKeys;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors().and()
            .csrf(csrf -> csrf.disable())
            //.disable()
            .requiresChannel(channel ->
                    channel.anyRequest().requiresSecure())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/store/api/auth/**", "/store/api/search/**", "/management/category")
                    .permitAll()
                    .requestMatchers("/store/api/admin/category/**"
                            , "/store/api/admin/customer/**"
                            , "/store/api/admin/paymentMethod/**"
                            , "/store/api/admin/deliveryMethod/**"
                            , "/store/api/admin/orderProduct/**"
                            , "/store/api/admin/product/**"
                            , "/store/api/admin/**"
                            , "/store/api/admin/review/**").hasAuthority("ADMIN")
                    .requestMatchers("/store/api/master/**").hasAuthority("MASTER")
                    .requestMatchers("/store/api/account/**"
                            , "/store/api/account/cart/**"
                            , "/store/api/account/order/**"
                            , "/store/api/account/review/**").hasAuthority("CUSTOMER")
                    .anyRequest()
                    .authenticated())
            //  .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            //  .and()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            //   .and()
            .authenticationProvider(provider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout
                    .logoutUrl("/store/api/auth/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
    return httpSecurity.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource(){
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
    corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT", "OPTIONS"));
    corsConfiguration.setAllowedHeaders(List.of("Authorization","Content-Type"));
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
    return urlBasedCorsConfigurationSource;
  }

//  @Bean
//  JwtDecoder jwtDecoder(){
//    return NimbusJwtDecoder.withPublicKey(rsaKeys.rsaPublicKey()).build();
//  }
//
//  @Bean
//  JwtEncoder jwtEncoder(){
//    JWK jwk = new RSAKey.Builder(rsaKeys.rsaPublicKey()).privateKey(rsaKeys.rsaPrivateKey()).build();
//    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
//    return new NimbusJwtEncoder(jwkSource);
//  }


}
