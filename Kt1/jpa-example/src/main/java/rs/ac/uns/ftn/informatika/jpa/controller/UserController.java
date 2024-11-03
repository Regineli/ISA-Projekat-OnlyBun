package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.jpa.dto.CourseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ExamDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Exam;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

	@Autowired
	private UserService UserService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<UserDTO>> getAllUsers() {

		List<User> Users = UserService.findAll();

		// convert Users to DTOs
		List<UserDTO> UsersDTO = new ArrayList<>();
		for (User s : Users) {
			UsersDTO.add(new UserDTO(s));
		}

		return new ResponseEntity<>(UsersDTO, HttpStatus.OK);
	}

	// GET /api/Users?page=0&size=5&sort=firstName,DESC
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsersPage(Pageable page) {

		// page object holds data about pagination and sorting
		// the object is created based on the url parameters "page", "size" and "sort"
		Page<User> Users = UserService.findAll(page);

		// convert Users to DTOs
		List<UserDTO> UsersDTO = new ArrayList<>();
		for (User s : Users) {
			UsersDTO.add(new UserDTO(s));
		}

		return new ResponseEntity<>(UsersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {

		User User = UserService.findOne(id);

		// studen must exist
		if (User == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new UserDTO(User), HttpStatus.OK);
	}
	
	@PostMapping(value = "/create")
    public ResponseEntity<UserDTO> saveUser(
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName) {
        
        // Create a new User object
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // Save the new user
        user = UserService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }
	

	@PostMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO UserDTO) {

		User User = new User();
		User.setEmail(UserDTO.getEmail());
		User.setFirstName(UserDTO.getFirstName());
		User.setLastName(UserDTO.getLastName());

		User = UserService.save(User);
		return new ResponseEntity<>(new UserDTO(User), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO UserDTO) {

		// a User must exist
		User User = UserService.findOne(UserDTO.getId());

		if (User == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User.setEmail(UserDTO.getEmail());
		User.setFirstName(UserDTO.getFirstName());
		User.setLastName(UserDTO.getLastName());

		User = UserService.save(User);
		return new ResponseEntity<>(new UserDTO(User), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {

		User User = UserService.findOne(id);

		if (User != null) {
			UserService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/findEmail")
	public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {

		User User = UserService.findByEmail(email);
		if (User == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new UserDTO(User), HttpStatus.OK);
	}

	@GetMapping(value = "/findLastName")
	public ResponseEntity<List<UserDTO>> getUsersByLastName(@RequestParam String lastName) {

		List<User> Users = UserService.findByLastName(lastName);

		// convert Users to DTOs
		List<UserDTO> UsersDTO = new ArrayList<>();
		for (User s : Users) {
			UsersDTO.add(new UserDTO(s));
		}
		return new ResponseEntity<>(UsersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/prezime")
	public ResponseEntity<List<UserDTO>> pronadjiUserePoPrezimenu(@RequestParam String lastName) {

		List<User> Users = UserService.pronadjiPoPrezimenu(lastName);

		// convert Users to DTOs
		List<UserDTO> UsersDTO = new ArrayList<>();
		for (User s : Users) {
			UsersDTO.add(new UserDTO(s));
		}
		return new ResponseEntity<>(UsersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/findFirstLast")
	public ResponseEntity<List<UserDTO>> getUsersByFirstNameAndLastName(@RequestParam String firstName,
			@RequestParam String lastName) {

		List<User> Users = UserService.findByFirstNameAndLastName(firstName, lastName);

		// convert Users to DTOs
		List<UserDTO> UsersDTO = new ArrayList<>();
		for (User s : Users) {
			UsersDTO.add(new UserDTO(s));
		}
		return new ResponseEntity<>(UsersDTO, HttpStatus.OK);
	}
}
