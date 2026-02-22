package com.example.broadly.repository;

import com.example.broadly.entity.AppRole;
import com.example.broadly.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepositary extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(AppRole roleName);
}
