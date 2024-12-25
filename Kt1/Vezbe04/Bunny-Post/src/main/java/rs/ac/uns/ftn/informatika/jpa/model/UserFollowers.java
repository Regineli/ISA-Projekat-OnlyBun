package rs.ac.uns.ftn.informatika.jpa.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_followers")
public class UserFollowers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Unique ID for this relationship entry

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="follower")
    @JsonBackReference("followerReference")
    private User from;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="following")
    @JsonBackReference("followingReference")
    private User to;

    public UserFollowers() {};

    public UserFollowers(User from, User to) {
        this.from = from;
        this.to = to;
    }
    
    public User getFrom() {
    	return from;
    }
    
    public User getTo() {
    	return to;
    }

    // Override toString() to represent the relationship clearly
    @Override
    public String toString() {
        return "UserFollowers{" +
                "follower=" + from.getUsername() +
                ", following=" + to.getUsername() +
                '}';
    }
}
