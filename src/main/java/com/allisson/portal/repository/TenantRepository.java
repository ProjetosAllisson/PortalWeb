package com.allisson.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allisson.portal.model.Tenant;


public interface TenantRepository extends JpaRepository<Tenant, String>{

}
