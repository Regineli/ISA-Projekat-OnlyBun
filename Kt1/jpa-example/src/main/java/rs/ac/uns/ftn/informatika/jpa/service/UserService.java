package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository UserRepository;
	
	public User findOne(Integer id) {
		return UserRepository.findById(id).orElseGet(null);
	}

	public List<User> findAll() {
		return UserRepository.findAll();
	}
	
	public Page<User> findAll(Pageable page) {
		return UserRepository.findAll(page);
	}

	public User save(User User) {
		return UserRepository.save(User);
	}

	public void remove(Integer id) {
		UserRepository.deleteById(id);
	}
	
	public User findByEmail(String email) {
		return UserRepository.findOneByEmail(email);
	}
	
	public List<User> findByLastName(String lastName) {
		return UserRepository.findAllByLastName(lastName);
	}
	
	public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
		return UserRepository.findByFirstNameAndLastNameAllIgnoringCase(firstName, lastName);
	}
	
	public List<User> pronadjiPoPrezimenu(String prezime) {
		return UserRepository.pronadjiUserePoPrezimenu(prezime);
	}
	
}
