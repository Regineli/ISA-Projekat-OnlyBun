package rs.ac.uns.ftn.informatika.jpa.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "bunnypost")
public class BunnyPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "details", unique = false, nullable = false)
    private String details;

    @Column(name = "photo", nullable = false)
    private String photo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bunnyPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "likes_count", nullable = false)
    private int likesCount = 0;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @ManyToMany
    @JoinTable(
        name = "post_likes",
        joinColumns = @JoinColumn(name = "bunny_post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedByUsers = new HashSet<>();

    public BunnyPost() {}

    public BunnyPost(Integer id, String details, String photo) {
        this.id = id;
        this.details = details;
        this.photo = photo;
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void incrementLikes() {
        this.likesCount++;
    }

    public void decrementLikes() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void addLike(User user) {
        likedByUsers.add(user);
        incrementLikes();
    }

    public void removeLike(User user) {
        likedByUsers.remove(user);
        decrementLikes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BunnyPost s = (BunnyPost) o;
        return Objects.equals(details, s.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(details);
    }

    @Override
    public String toString() {
        return "BunnyPost [id=" + id + ", details=" + details + ", user=" + user.getFirstName() + ", photo=" + photo + "]";
    }
}