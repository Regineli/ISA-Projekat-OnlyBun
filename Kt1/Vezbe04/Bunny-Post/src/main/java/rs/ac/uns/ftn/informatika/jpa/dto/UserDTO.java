package rs.ac.uns.ftn.informatika.jpa.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import rs.ac.uns.ftn.informatika.jpa.model.Role;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.UserStatus;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;

public class UserDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String username; // Added field for username
    private String password; // Added field for password
    private String address;  // Added field for address
    private UserStatus status; // Added field for user status
    private List<Role> roles;
    private Integer total_likes;
    
    private List<String> followers;
    private List<String> following;
        
    // Default constructor
    public UserDTO() {

    }   

    // Constructor for mapping from User entity
    /*public UserDTO(User user) {
        this(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getAddress(), user.getStatus());
    }*/
    
    public UserDTO(User user) {
        this(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), 
             user.getUsername(), user.getPassword(), user.getAddress(), 
             user.getStatus(), user.getFollowerUsernames(), user.getFollowingUsernames(), user.getRoles());
    }

    public UserDTO(Integer id, String email, String firstName, String lastName, String username, 
            String password, String address, UserStatus status, List<String> followerUsernames, List<String> followingUsernames, List<Role> roles) {
    	 this.id = id;
		 this.email = email;
		 this.firstName = firstName;
		 this.lastName = lastName;
		 this.username = username;
		 this.password = password;
		 this.address = address;
		 this.status = status;
		 this.followers = followerUsernames;
		 this.following = followingUsernames;
		 this.roles = roles;
		 // Calculate followers and following counts
	}

    // Constructor for initializing UserDTO
    public UserDTO(Integer id, String email, String firstName, String lastName, String username, String password, String address, UserStatus status, List<Role> roles) {
    	this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.address = address;
        this.status = status; // Initialize the status
        this.roles=roles;
    }

    public List<Role> getRoles() {
		return roles;
	}

	public void setRole(Role role) {
		this.roles = roles;
	}

    
    public Integer getId() {
        return id;
    }
    
    public void setTotalLikes(Integer likes) {
		this.total_likes = likes;
	}
    
    public Integer getTotalLikes() {
		return this.total_likes;
	}


    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", roles=" + (roles != null ? roles : "[]") + // Handle null roles list
                '}';
    }
}
