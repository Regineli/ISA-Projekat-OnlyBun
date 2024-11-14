package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.UserStatus;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@Service
public class UserService {
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
    private EmailService emailService;
	
	
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
	
	// Check user by email and password
    public User findByEmailAndPassword(String email, String password) {
        return UserRepository.findByEmailAndPassword(email, password);
    }

    // Check user by username and password
    public User findByUsernameAndPassword(String username, String password) {
        return UserRepository.findByUsernameAndPassword(username, password);
    }
    
    public User findByUsername(String username) {
        return UserRepository.findByUsername(username);
    }
    
    @Transactional
    public User registerUser(User user) {
        // Set status to PENDING_CONFIRMATION
    	System.out.println("Register start user: " + user);
    	
        user.setStatus(UserStatus.PENDING_REGISTRATION_CONFIRMATION);
        //user.setLastName("testlastname");
        //user.setId(null);
        
        
        User savedUser = UserRepository.save(user);

        
        System.out.println("Saved user: " + savedUser);
        
        // Send activation email
        try {
            emailService.sendActivationEmail(savedUser);
        } catch (MailException e) {
            e.printStackTrace();  // Handle exceptions properly
        }

        return savedUser;
    }
    
    public User activateUser(Integer userId) {
        User user = UserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(UserStatus.ACTIVE);
        return UserRepository.save(user);
    }

    public User deactivateUser(Integer userId) {
        User user = UserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(UserStatus.DEACTIVE);
        return UserRepository.save(user);
    }


    public static class ResourceNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
    
    

}
