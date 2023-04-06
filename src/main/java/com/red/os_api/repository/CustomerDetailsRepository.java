package com.red.os_api.repository;

import com.red.os_api.entity.Auth;
import com.red.os_api.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails,Integer> {

CustomerDetails getCustomerDetailsByAuth(Auth auth);

List<CustomerDetails> findCustomerDetailsByAuth(Auth auth);

}

