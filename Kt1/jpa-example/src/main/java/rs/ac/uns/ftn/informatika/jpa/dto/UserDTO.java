package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.User;

public class UserDTO {
	private Integer id;
	private String email;
	private String firstName;
	private String lastName;

	public UserDTO() {

	}

	public UserDTO(User User) {
		this(User.getId(), User.getEmail(), User.getFirstName(), User.getLastName());
	}

	public UserDTO(Integer id, String email, String firstName, String lastName) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
