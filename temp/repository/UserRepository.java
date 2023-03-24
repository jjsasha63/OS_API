package com.red.os_api.repository;

import com.red.os_api.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Auth,Integer> {

    Optional<Auth> findByUsername(String email);}
