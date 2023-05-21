package com.erolcloud.erolcloud.repository;

import com.erolcloud.erolcloud.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(Role.Authority authority);

    Boolean existsByAuthority(Role.Authority authority);
}
