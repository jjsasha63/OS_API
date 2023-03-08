package com.red.os_api.repository;

import com.red.os_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository<P,C> extends JpaRepository<P,C> {

}
