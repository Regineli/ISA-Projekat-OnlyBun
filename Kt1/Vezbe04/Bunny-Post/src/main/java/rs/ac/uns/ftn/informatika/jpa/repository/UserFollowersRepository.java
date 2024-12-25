package rs.ac.uns.ftn.informatika.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.UserFollowers;

@Repository
public interface UserFollowersRepository extends JpaRepository<UserFollowers, Integer> {
    
    // Find a follow relationship by follower and following
    UserFollowers findByFromAndTo(User from, User to);

    // Find all followers for a particular user
    List<UserFollowers> findByTo(User to);

    // Find all followings for a particular user
    List<UserFollowers> findByFrom(User from);
}
