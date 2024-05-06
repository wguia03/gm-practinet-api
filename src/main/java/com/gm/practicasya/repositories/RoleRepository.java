package com.gm.practicasya.repositories;

import com.gm.practicasya.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
