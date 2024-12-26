package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "user_like_post",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"})  // Ensure unique combination of user_id and post_id
)
public class UserLikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremented ID
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userID;

    @Column(name = "post_id", nullable = false)
    private Integer postID;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    // Default constructor
    public UserLikePost() {}

    // Constructor to initialize the like with a user and post
    public UserLikePost(Integer user, Integer post, LocalDateTime dateTime) {
        this.userID = user;
        this.postID = post;
        this.dateTime = dateTime;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer user) {
        this.userID = user;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer post) {
        this.postID = post;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Override toString() to represent the like relationship clearly
    @Override
    public String toString() {
        return "UserLikePost{" +
                "id=" + id +
                ", user=" + userID.toString() +
                ", post=" + postID.toString() +
                ", dateTime=" + dateTime +
                '}';
    }
}
