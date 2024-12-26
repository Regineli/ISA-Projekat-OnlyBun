package rs.ac.uns.ftn.informatika.jpa.dto;
import java.time.LocalDateTime;

public class UserLikePostDTO {

    private Integer userId;
    private Integer postId;
    private LocalDateTime dateTime;

    // Default constructor
    public UserLikePostDTO() {}

    // Constructor to initialize DTO from UserLikePost entity
    public UserLikePostDTO(Integer userId, Integer postId, LocalDateTime dateTime) {
        this.userId = userId;
        this.postId = postId;
        this.dateTime = dateTime;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Override toString() to represent the DTO clearly
    @Override
    public String toString() {
        return "UserLikePostDTO{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", dateTime=" + dateTime +
                '}';
    }
}
