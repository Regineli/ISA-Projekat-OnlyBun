package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.jpa.model.Role;

public interface RoleService {
	Role findById(Long id);
	List<Role> findByName(String name);
}
