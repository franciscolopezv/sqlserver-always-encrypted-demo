package com.alwaysencrypted.demo.repository;

import com.alwaysencrypted.demo.model.CustomerVarchar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerVarcharRepository extends JpaRepository<CustomerVarchar, Long> {
}