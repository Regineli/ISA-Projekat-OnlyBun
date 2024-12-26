package rs.ac.uns.ftn.informatika.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.informatika.jpa.model.UserLikePost;

public interface UserLikePostRepository extends JpaRepository<UserLikePost, Integer> {

    
    @Query("SELECT ulp.userID, COUNT(ulp) AS likeCount " +
            "FROM UserLikePost ulp " +
            "WHERE ulp.dateTime >= :lastWeek " +
            "GROUP BY ulp.userID " +
            "ORDER BY likeCount DESC")
     List<Integer> findTopUsersWithMostLikes(LocalDateTime lastWeek);
     
     @Query("SELECT ulp.postID AS bunnyPostId, COUNT(ulp) AS likeCount " +
    	       "FROM UserLikePost ulp " +
    	       "GROUP BY ulp.postID " +
    	       "ORDER BY likeCount DESC")
    	List<Integer> findTopLikedBunnyPosts();
    	
	@Query("SELECT ulp.postID, COUNT(ulp) AS likeCount " +
		       "FROM UserLikePost ulp " +
		       "WHERE ulp.dateTime >= :lastWeek " +
		       "GROUP BY ulp.postID " +
		       "ORDER BY likeCount DESC")
		List<Integer> findTopLikedBunnyPostsInLastWeek(LocalDateTime lastWeek);
	
	@Query("SELECT COUNT(ulp) AS likeCount " +
		       "FROM UserLikePost ulp " +
		       "WHERE ulp.postID = :bunnyPostId")
		Integer countLikesForBunnyPost(Integer bunnyPostId);
	
	@Query("SELECT COUNT(ulp) AS likeCount " +
		       "FROM UserLikePost ulp " +
		       "WHERE ulp.userID = :userId")
		Integer countUserLikes(Integer userId);
}
