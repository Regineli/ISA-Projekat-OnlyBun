package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.User;

public class CommentDTO {

    private Integer id;
    private String details;
    private Integer bunnyPostId;
    private Integer userId;
    private UserDTO user;

    public CommentDTO() {
    }

    public CommentDTO(Integer id, String details, Integer bunnyPostId, Integer userId) {
        this.id = id;
        this.details = details;
        this.bunnyPostId = bunnyPostId;
        this.userId = userId;
    }

    // Constructor to map from Comment entity
    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.details = comment.getDetails();
        this.bunnyPostId = comment.getBunnyPost().getId(); // Assuming BunnyPost has an getId() method
        this.userId = comment.getUser().getId();           // Assuming User has an getId() method
        
        if (comment.getUser() != null) {
            this.user = new UserDTO(comment.getUser());
            System.out.println("comment user " + this.userId.toString());
        }
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getBunnyPostId() {
        return bunnyPostId;
    }

    public void setBunnyPostId(Integer bunnyPostId) {
        this.bunnyPostId = bunnyPostId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
