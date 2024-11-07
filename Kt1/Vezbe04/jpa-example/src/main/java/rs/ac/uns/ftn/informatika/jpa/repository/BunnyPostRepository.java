package rs.ac.uns.ftn.informatika.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.Course;

public interface BunnyPostRepository extends JpaRepository<BunnyPost, Integer> {
	List<BunnyPost> findByUserId(Integer userId);
	
	/*
	@Query("SELECT c FROM Comment c WHERE c.bunnyPost.id = ?1")
	List<Comment> findCommentsByBunnyPostId(Integer bunnyPostId);
	/*
    // Query to get all comments with a specific bunnyPostId and userId
    @Query("SELECT c FROM Comment c WHERE c.bunnyPostId = ?1 AND c.userId = ?2")
    List<Comment> findByBunnyPostIdAndUserId(Integer bunnyPostId, Integer userId);
    */
	
	@Query("select u from BunnyPost u join fetch u.comments c where u.id =?1")
	public Course findOneWithComments(Integer bunnyPostID);
}
