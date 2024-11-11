package rs.ac.uns.ftn.informatika.jpa.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.jpa.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	List<Role> findByName(String name);
}
