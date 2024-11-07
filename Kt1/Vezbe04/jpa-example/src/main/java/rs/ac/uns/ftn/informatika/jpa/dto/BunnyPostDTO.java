package rs.ac.uns.ftn.informatika.jpa.dto;

import java.util.List;
import java.util.stream.Collectors;

import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;

public class BunnyPostDTO {
    private Integer id;
    private String details;
    private String photo;
    private UserDTO user; // Add UserDTO field
    private List<CommentDTO> comments; // Add comments field

    public BunnyPostDTO(BunnyPost bunnyPost) {
        this(bunnyPost.getId(), bunnyPost.getDetails(), bunnyPost.getPhoto());
        if (bunnyPost.getUser() != null) {
            this.user = new UserDTO(bunnyPost.getUser());
        }
        /*
        if (bunnyPost.getComments() != null) {
            this.comments = new CommentDTO(bunnyPost.getComments());
        }*/

    }
    
    public BunnyPostDTO() {
    }

    public BunnyPostDTO(Integer id, String details, String photo) {
        this.id = id;
        this.details = details;
        this.photo = photo;
    }
    

    public Integer getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public String getPhoto() {
        return photo;
    }

    public UserDTO getUser() {
        return user; // Add getter for user
    }

	public void setComments(List<CommentDTO> commentDTOs) {
		// TODO Auto-generated method stub
		this.comments = commentDTOs;
		
	}
}