package com.red.os_api.repository;

import com.red.os_api.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails,Integer> {
}
