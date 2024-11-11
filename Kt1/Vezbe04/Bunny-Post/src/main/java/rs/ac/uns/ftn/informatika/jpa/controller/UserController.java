package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.model.Exam;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.UserStatus;
import rs.ac.uns.ftn.informatika.jpa.service.BunnyPostService;
import rs.ac.uns.ftn.informatika.jpa.service.EmailService;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;
import rs.ac.uns.ftn.informatika.jpa.dto.BunnyPostDTO; // Make sure you have a BunnyPostDTO class
import javax.validation.Validator;

@RestController
@RequestMapping(value = "api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserService UserService;
	
	@Autowired
    private BunnyPostService bunnyPostService;
	
	@Autowired
    private EmailService emailService;
	
	private final Validator validator;

	public UserController(Validator validator) {
        //this.userService = userService;
        this.validator = validator;
    }
	

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
	public ResponseEntity<?> saveUser(
	        @RequestParam String email,
	        @RequestParam String username,
	        @RequestParam String password,
	        @RequestParam String firstName,
	        @RequestParam String lastName,
	        @RequestParam(required = false) String address) {

	    // Create a new User object
	    User user = new User();
	    user.setEmail(email);
	    user.setUsername(username);
	    user.setPassword(password);
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    user.setAddress(address);

	    // Validate user manually or via service
	    Set<ConstraintViolation<User>> violations = validator.validate(user);
	    if (!violations.isEmpty()) {
	        Map<String, String> errors = new HashMap<>();
	        for (ConstraintViolation<User> violation : violations) {
	            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
	        }
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }

	    try {
	        user = UserService.registerUser(user);
	        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
	    } catch (DataIntegrityViolationException e) {
	        Map<String, String> error = new HashMap<>();
	        error.put("error", "Email or Username already exists");
	        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	    }
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
	
	@PostMapping(value = "/login")
	public ResponseEntity<UserDTO> logIn(@RequestParam String email, @RequestParam String password) {
	    // Try to find the user by email
		
		System.out.println("Email: " + email);
		System.out.println("Password: " + password);
		
	    User user = UserService.findByEmail(email);

	    if (user == null || !user.getPassword().equals(password)) {
	        // If user is not found or password does not match, return error
	    	System.out.println("Nije nasao usera");
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Incorrect email or password
	    }
	    
	    if (user.getStatus() != UserStatus.ACTIVE) {
	        System.out.println("User status is not active: " + user.getStatus());
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Forbidden if user is not active
	    }
	    
	    System.out.println("Nasao je usera");
	    // If user is found and password matches, return the user data
	    UserDTO userDTO = new UserDTO(user);
	    return new ResponseEntity<>(userDTO, HttpStatus.OK); // Return user details with OK status
	}
	
	@GetMapping("/bunnyPosts")
	public ResponseEntity<List<BunnyPostDTO>> getBunnyPostsByUserId(@RequestParam Integer userID) {
	    // Fetch all BunnyPosts by the user ID using the service layer
	    List<BunnyPost> bunnyPosts = bunnyPostService.findByUserId(userID);
	    
	    // Convert BunnyPost entities to BunnyPostDTOs
	    List<BunnyPostDTO> bunnyPostDTOs = new ArrayList<>();
	    for (BunnyPost post : bunnyPosts) {
	        bunnyPostDTOs.add(new BunnyPostDTO(post));
	    }

	    // Return the list of DTOs wrapped in a ResponseEntity with HTTP OK status
	    return new ResponseEntity<>(bunnyPostDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/testemail")
    public String testEmail() {
        try {
            emailService.sendTestEmail();  // Call your method to send the email
            return "Test email sent successfully!";
        } catch (MailException e) {
            return "Failed to send test email: " + e.getMessage();
        }
    }
	
	@PutMapping("/activate")
    public ResponseEntity<User> activateUser(@RequestParam Integer userId) {
        try {
            User updatedUser = UserService.activateUser(userId);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    // Method to deactivate a user
    @PutMapping("/deactivate")
    public ResponseEntity<User> deactivateUser(@RequestParam Integer userId) {
        try {
            User updatedUser = UserService.deactivateUser(userId);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
