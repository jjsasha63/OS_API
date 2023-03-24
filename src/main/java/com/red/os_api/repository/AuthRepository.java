package com.red.os_api.repository;

import com.red.os_api.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {

  Optional<Auth> findByEmail(String email);

}
