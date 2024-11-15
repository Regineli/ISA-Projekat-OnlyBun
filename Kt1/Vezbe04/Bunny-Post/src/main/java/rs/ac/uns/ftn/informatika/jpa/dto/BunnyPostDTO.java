package rs.ac.uns.ftn.informatika.jpa.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;

public class BunnyPostDTO {
    private Integer id;
    private String email;
    private String details;
    private String photo;
    private UserDTO user; // Add UserDTO field
    private List<CommentDTO> comments; // Add comments field
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Adjust the pattern as needed
    private LocalDateTime time;
    private double longitude;
    private double latitude;

    public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public BunnyPostDTO(BunnyPost bunnyPost) {
        this(bunnyPost.getId(), bunnyPost.getDetails(), bunnyPost.getPhoto(), bunnyPost.getTime());
        if (bunnyPost.getUser() != null) {
            this.user = new UserDTO(bunnyPost.getUser());
        }
        
        if (bunnyPost.getComments() != null) {
            this.comments = new ArrayList<>();
            for (Comment comment : bunnyPost.getComments()) {
                this.comments.add(new CommentDTO(comment));
                System.out.println("bunny post comment" + comment.toString());
            }
        }

    }
    
    public BunnyPostDTO() {
    }

    public BunnyPostDTO(Integer id, String details, String photo, LocalDateTime time) {
        this.id = id;
        this.details = details;
        this.photo = photo;
        this.time = time;
    }
    

    public Integer getId() {
        return id;
    }
    
    public List<CommentDTO> getComments() {
        return comments;
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
    public LocalDateTime time() {
		return time;
	}

    
    public String getEmail() {
    	return email;
    }

	public void setComments(List<CommentDTO> commentDTOs) {
		// TODO Auto-generated method stub
		this.comments = commentDTOs;
		
	}
}
