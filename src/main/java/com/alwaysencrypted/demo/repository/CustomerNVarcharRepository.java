package com.alwaysencrypted.demo.repository;

import com.alwaysencrypted.demo.model.CustomerNVarchar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerNVarcharRepository extends JpaRepository<CustomerNVarchar, Long> {
}