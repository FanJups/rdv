package biz.advanceitgroup.rdvserver.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.authentication.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);
	
	Boolean existsByName(String name);

}

