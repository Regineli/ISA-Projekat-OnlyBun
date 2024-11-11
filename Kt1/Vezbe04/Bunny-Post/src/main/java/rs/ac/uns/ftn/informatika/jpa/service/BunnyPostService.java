package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.repository.BunnyPostRepository;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;

@Service
public class BunnyPostService {
	
	@Autowired
	private BunnyPostRepository bunnyPostRepository;
	
	public BunnyPost findOne(Integer id) {
		return bunnyPostRepository.findById(id).orElseGet(null);
	}

	public List<BunnyPost> findAll() {
		return bunnyPostRepository.findAll();
	}
	
	public BunnyPost save(BunnyPost bunnyPost) {
		return bunnyPostRepository.save(bunnyPost);
	}

	public void remove(Integer id) {
		bunnyPostRepository.deleteById(id);
	}
	
	public List<BunnyPost> findByUserId(Integer userId) {
        return bunnyPostRepository.findByUserId(userId);
    }
	
	public List<CommentDTO> getCommentsByBunnyPostId(Integer id) {
	    BunnyPost bunnyPost = findOne(id); // Assuming this method fetches the BunnyPost
	    Set<Comment> comments = bunnyPost.getComments(); // Assuming a relationship exists
	    List<CommentDTO> commentDTOs = new ArrayList<>();
	    
	    for (Comment comment : comments) {
	        commentDTOs.add(new CommentDTO(comment)); // Convert Comment to CommentDTO
	    }
	    
	    return commentDTOs;
	}
	
	public Integer findNextId() {
        Integer maxId = bunnyPostRepository.findMaxId();
        return (maxId != null) ? maxId + 1 : 1;
    }
	
	
	
}
