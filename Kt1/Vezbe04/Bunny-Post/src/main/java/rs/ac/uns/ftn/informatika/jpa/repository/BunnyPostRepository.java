package rs.ac.uns.ftn.informatika.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.Course;

public interface BunnyPostRepository extends JpaRepository<BunnyPost, Integer> {
	List<BunnyPost> findByUserId(Integer userId);
	BunnyPost findByPhoto(String photo);
	
    @Query("SELECT u FROM BunnyPost u JOIN FETCH u.comments WHERE u.id = ?1")
    BunnyPost findOneWithComments(Integer bunnyPostID);

    @Query("SELECT b FROM BunnyPost b LEFT JOIN FETCH b.likedByUsers WHERE b.id = ?1")
    BunnyPost findOneWithLikes(Integer bunnyPostID);

	/*
	@Query("SELECT c FROM Comment c WHERE c.bunnyPost.id = ?1")
	List<Comment> findCommentsByBunnyPostId(Integer bunnyPostId);
	/*
    // Query to get all comments with a specific bunnyPostId and userId
    @Query("SELECT c FROM Comment c WHERE c.bunnyPostId = ?1 AND c.userId = ?2")
    List<Comment> findByBunnyPostIdAndUserId(Integer bunnyPostId, Integer userId);
    */
    

    List<BunnyPost> findByUserUsername(String username);

	
	@Query("SELECT MAX(b.id) FROM BunnyPost b")
    Integer findMaxId();
	
}
