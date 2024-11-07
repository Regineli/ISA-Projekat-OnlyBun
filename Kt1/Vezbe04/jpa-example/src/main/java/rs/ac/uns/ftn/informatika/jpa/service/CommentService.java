package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	public Comment findOne(Integer id) {
		return commentRepository.findById(id).orElseGet(null);
	}

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}
	
	public Comment save(Comment Comment) {
		return commentRepository.save(Comment);
	}

	public void remove(Integer id) {
		commentRepository.deleteById(id);
	}
	
	/*
	public List<Comment> findByBunnyPostId(Integer bunnyPostID) {
        return commentRepository.findByBunnyPostId(bunnyPostID);
    }
	
	public List<Comment> findByBunnyPostIdAndUserId(Integer bunnyPostID, Integer userID) {
        return commentRepository.findByBunnyPostIdAndUserId(bunnyPostID, userID);
    }
    */
	
	public List<Comment> findByBunnyPostId(Integer bunnyPostId) {
	    return commentRepository.findByBunnyPostId(bunnyPostId);
	}
}
