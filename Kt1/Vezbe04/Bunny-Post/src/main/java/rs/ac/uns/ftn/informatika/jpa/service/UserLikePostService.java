package rs.ac.uns.ftn.informatika.jpa.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.UserLikePost;
import rs.ac.uns.ftn.informatika.jpa.repository.UserLikePostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserLikePostService {

    @Autowired
    private UserLikePostRepository userLikePostRepository;

    /**
     * Save a like (User-Like-Post relationship).
     *
     * @param userLikePost The UserLikePost object to save.
     * @return Saved UserLikePost object.
     */
    @Transactional
    public UserLikePost saveUserLikePost(UserLikePost userLikePost) {
        return userLikePostRepository.save(userLikePost);
    }

    /**
     * Get the top users with the most likes in the last 7 days.
     *
     * @return A list of user IDs and like counts.
     */
    public List<Integer> getTopUsersWithMostLikes() {
        LocalDateTime lastWeek = LocalDateTime.now().minusDays(7);
        return userLikePostRepository.findTopUsersWithMostLikes(lastWeek);
    }
   
    public Integer countLikesForBunnyPost(Integer bunnyPostId) {
    	return userLikePostRepository.countLikesForBunnyPost(bunnyPostId);
    };
    
    public Integer countUserLikes(Integer userId) {
    	return userLikePostRepository.countUserLikes(userId);
    };
    
    public List<UserLikePost> getLikesForUser(Integer userId) {
    	return userLikePostRepository.findByUserID(userId);
    };
    
    public List<Integer> get5TopLikedBunnyPostsInLastWeek() {
        // Calculate last week (7 days ago)
    	LocalDateTime lastWeek = LocalDateTime.now().minusDays(7);

        // Call the UserLikePostService to get the top liked bunny posts from the last week
        List<Integer> topLikedBunnyPosts = userLikePostRepository.findTopLikedBunnyPostsInLastWeek(lastWeek);
        
        if (topLikedBunnyPosts.size() > 5) {
            return topLikedBunnyPosts.subList(0, 5); // Get first 5 elements
        }

        // Return the result
        return topLikedBunnyPosts;
    }
    
    public List<Integer> get10TopLikedBunnyPosts() {
        List<Integer> topLikedBunnyPosts = userLikePostRepository.findTopLikedBunnyPosts();
        	
        if (topLikedBunnyPosts.size() > 10) {
            return topLikedBunnyPosts.subList(0, 10); // Get first 5 elements
        }
        
        // Return the result
        return topLikedBunnyPosts;
    }
    
    public Integer numOfLikesSince (User user, LocalDateTime time) {
    	List<UserLikePost> likePost=getLikesForUser(user.getId());
    	Integer res=0;
    	for(UserLikePost like: likePost) {
    		if(like.getDateTime().isAfter(time)){
    			res++;
    		}
    	}
    	return res;
    }

}
