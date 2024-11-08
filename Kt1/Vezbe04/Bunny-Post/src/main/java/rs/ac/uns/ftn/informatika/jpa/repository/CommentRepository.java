package rs.ac.uns.ftn.informatika.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.Course;
import rs.ac.uns.ftn.informatika.jpa.model.User;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	Optional<Comment> findById(Integer id);
	
	/*
	@Query("SELECT c FROM Comment c WHERE c.bunnyPost.id = ?1")
	List<Comment> findByBunnyPostId(Integer bunnyPostId);

    @Query("SELECT c FROM Comment c WHERE c.bunnyPost.id = ?1 AND c.user.id = ?2")
    List<Comment> findByBunnyPostIdAndUserId(Integer bunnyPostId, Integer userId);
    // Query to get all comments with a specific bunnyPostId and userId
    @Query("SELECT c FROM Comment c WHERE c.bunnyPostId = ?1 AND c.userId = ?2")
    List<Comment> findByBunnyPostIdAndUserId(Integer bunnyPostId, Integer userId);
    */
	
	List<Comment> findByBunnyPostId(Integer bunnyPostId);
}
