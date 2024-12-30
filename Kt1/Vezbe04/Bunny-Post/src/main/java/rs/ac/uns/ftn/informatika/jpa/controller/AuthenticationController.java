package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import rs.ac.uns.ftn.informatika.jpa.RateLimiter;
import rs.ac.uns.ftn.informatika.jpa.dto.JwtAuthenticationRequest;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.UserTokenState;
import rs.ac.uns.ftn.informatika.jpa.exception.ResourceConflictException;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.UserStatus;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;
import rs.ac.uns.ftn.informatika.jpa.utils.TokenUtils;



//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
    private RateLimiter rateLimiter;
	
	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.

	
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
		// Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
		// AuthenticationException
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		

		// Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
		// kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
	    System.out.println("Šifrovana lozinka korisnika: " + user.getPassword());
	    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		String jwt = tokenUtils.generateToken(user);
		int expiresIn = tokenUtils.getExpiredIn();

		// Vrati token kao odgovor na uspesnu autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}

	// Endpoint za registraciju novog korisnika
	@PostMapping("/signup")
	@Transactional
	public ResponseEntity<?> addUser(@RequestBody UserDTO userRequest, HttpServletRequest request) {
		
		String ipAddress = request.getRemoteAddr();
		System.out.println("ip address: " + ipAddress);
		
		if (rateLimiter.isBlocked(ipAddress)) {
            return new ResponseEntity<>("Too many attempts. Please try again later.", HttpStatus.TOO_MANY_REQUESTS);
        }

        rateLimiter.recordAttempt(ipAddress);
	    try {
	        // Provera da li korisničko ime već postoji
	        User existUser = this.userService.findByUsername(userRequest.getUsername());
	        if (existUser != null) {
	            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
	        }

	        System.out.println("Signup user: " + userRequest.toString());
	        
	        Integer newID = userService.getMaxUserId();
	        userRequest.setId(newID);
	        // Registracija korisnika
	        User user = this.userService.registerUser(userRequest);

	        return new ResponseEntity<>(user, HttpStatus.CREATED);
	    } catch (Exception e) {
	        // Loguj grešku u slučaju konkurentne situacije
	        System.err.println("Error during user registration: " + e.getMessage());
	        throw new RuntimeException("User registration failed due to concurrent access. Please try again.");
	    }
	}
	
	
	// Testing registration conflict situation
	@PostMapping("/testRegistration")
	@Transactional
	public ResponseEntity<String> testConcurrentRegistration(@RequestBody String requestBody, HttpServletRequest request) throws InterruptedException {
	    // Define two UserDTO objects directly in the method
		String ipAddress = request.getRemoteAddr();
		System.out.println("ip address: " + ipAddress);
		
		if (rateLimiter.isBlocked(ipAddress)) {
            return new ResponseEntity<>("Too many attempts. Please try again later.", HttpStatus.TOO_MANY_REQUESTS);
        }
		
        System.out.println("Received registration request with body: " + requestBody);
        Integer newID = userService.getMaxUserId();
	    UserDTO userDTO1 = new UserDTO(newID,
	            "test1@example.com", "John1", "Doe1", "johndoe", 
	            "password1231", "123 Street", UserStatus.PENDING_REGISTRATION_CONFIRMATION, null);
	    
	    System.out.println("test user id: " + newID.toString());
	    newID+= 1;
	    
	    UserDTO userDTO2 = new UserDTO(
	    		newID, "test@example.com", "Jane", "Doe", "johndoe", 
	            "password123", "123 Street", UserStatus.PENDING_REGISTRATION_CONFIRMATION, null);
	    System.out.println("test user id: " + newID.toString());
	    userDTO2.setId(newID);

	    // Create two threads to simulate concurrent registration attempts
	    Thread thread1 = new Thread(() -> {
	        try {
	            // Simulate the first user registration
	            User existUser = this.userService.findByUsername(userDTO1.getUsername());
	            if (existUser != null) {
	                System.out.println("Username already exists, registration failed for User 1.");
	            } else {
	                User user = this.userService.registerUser(userDTO1);
	                System.out.println("User 1 registered successfully: " + user);
	            }
	        } catch (Exception e) {
	            System.err.println("Error in Thread 1: " + e.getMessage());
	        }
	    });

	    Thread thread2 = new Thread(() -> {
	        try {
	            // Simulate the second user registration with the same username
	            User existUser = this.userService.findByUsername(userDTO2.getUsername());
	            if (existUser != null) {
	                System.out.println("Username already exists, registration failed for User 2.");
	            } else {
	                User user = this.userService.registerUser(userDTO2);
	                System.out.println("User 2 registered successfully: " + user);
	            }
	        } catch (Exception e) {
	            System.err.println("Error in Thread 2: " + e.getMessage());
	        }
	    });

	    // Start both threads concurrently
	    thread1.start();
	    thread2.start();

	    // Wait for both threads to finish
	    thread1.join();
	    thread2.join();

	    // Return a response message indicating that the test was executed
	    return new ResponseEntity<>("Concurrent registration test executed. Check the logs for results.", HttpStatus.OK);
	}


}