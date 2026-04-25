package com.dailycodework.demoshops.repository;

import com.dailycodework.demoshops.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Role findByName(String role);
}
