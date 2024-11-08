package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.jpa.dto.BunnyPostDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ExamDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.StudentDTO;
import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.Exam;
import rs.ac.uns.ftn.informatika.jpa.service.BunnyPostService;
import rs.ac.uns.ftn.informatika.jpa.service.CommentService;

@RestController
@RequestMapping(value = "api/bunnyPosts")
@CrossOrigin(origins = "http://localhost:4200")  // Allow only Angular app
public class BunnyPostController {

	@Autowired
	private BunnyPostService bunnyPostService;
	
	@Autowired
	private CommentService commentService;

	@GetMapping
	public ResponseEntity<List<BunnyPostDTO>> getBunnyPosts() {

		List<BunnyPost> BunnyPosts = bunnyPostService.findAll();

		// convert BunnyPosts to DTOs
		List<BunnyPostDTO> bunnyPostsDTO = new ArrayList<>();
		for (BunnyPost s : BunnyPosts) {
			bunnyPostsDTO.add(new BunnyPostDTO(s));
		}

		return new ResponseEntity<>(bunnyPostsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BunnyPostDTO> getBunnyPost(@PathVariable Integer id) {

		BunnyPost bunnyPost = bunnyPostService.findOne(id);

		// BunnyPost must exist
		if (bunnyPost == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new BunnyPostDTO(bunnyPost), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<BunnyPostDTO> saveBunnyPost(@RequestBody BunnyPostDTO bunnyPostDTO) {

		BunnyPost BunnyPost = new BunnyPost();
		BunnyPost.setDetails(bunnyPostDTO.getDetails());

		BunnyPost = bunnyPostService.save(BunnyPost);
		return new ResponseEntity<>(new BunnyPostDTO(BunnyPost), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<BunnyPostDTO> updateBunnyPost(@RequestBody BunnyPostDTO bunnyPostDTO) {

		// a BunnyPost must exist
		BunnyPost bunnyPost = bunnyPostService.findOne(bunnyPostDTO.getId());

		if (bunnyPost == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		bunnyPost.setDetails(bunnyPostDTO.getDetails());

		bunnyPost = bunnyPostService.save(bunnyPost);
		return new ResponseEntity<>(new BunnyPostDTO(bunnyPost), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteBunnyPost(@PathVariable Integer id) {

		BunnyPost bunnyPost = bunnyPostService.findOne(id);

		if (bunnyPost != null) {
			bunnyPostService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/comments")
	public ResponseEntity<List<CommentDTO>> getCommentsByBunnyPostId(@RequestParam Integer bunnyPostId) {
		
		System.out.println("Get Comment, Bunny post id: " + bunnyPostId.toString());
		
	    List<Comment> comments = commentService.findByBunnyPostId(bunnyPostId);

	    // Check if comments exist for the specified bunny post ID
	    if (comments.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }

	    // Convert each Comment to CommentDTO
	    List<CommentDTO> commentDTOs = comments.stream()
	                                           .map(CommentDTO::new)
	                                           .collect(Collectors.toList());

	    return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
	}

}
