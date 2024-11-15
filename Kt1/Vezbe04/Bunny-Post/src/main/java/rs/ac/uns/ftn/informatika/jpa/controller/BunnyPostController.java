package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.tools.DocumentationTool.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.Exam;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.service.BunnyPostService;
import rs.ac.uns.ftn.informatika.jpa.service.CommentService;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;


@RestController
@RequestMapping(value = "api/bunnyPosts")
@CrossOrigin(origins = "http://localhost:4200")  // Allow only Angular app
public class BunnyPostController {

	@Autowired
	private BunnyPostService bunnyPostService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
    private UserService userService;

	@GetMapping("/public")
	//@PreAuthorize("hasAnyAuthority('ADMIN', 'USER', '') or isAnonymous()")
	public ResponseEntity<List<BunnyPostDTO>> getBunnyPosts() {
	    List<BunnyPost> bunnyPosts = bunnyPostService.findAll();

	    // Sort BunnyPosts by 'time' in descending order (newest first)
	    bunnyPosts.sort(Comparator.comparing(BunnyPost::getTime).reversed());

	    // Convert sorted BunnyPosts to DTOs
	    List<BunnyPostDTO> bunnyPostsDTO = new ArrayList<>();
	    for (BunnyPost post : bunnyPosts) {
	    	//System.out.println("Bunny post time: " + post.getTime());
	    	BunnyPostDTO newPost = new BunnyPostDTO(post);
	    	System.out.println("new post time" + newPost.getComments());
	    	//newPost.setComments(this.getCommentsByBunnyPostId(newPost.getId()));
	        bunnyPostsDTO.add(newPost);
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
	
	
	@GetMapping("/public/comments")
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
	
	@GetMapping("/nextId")
	public ResponseEntity<Integer> getNextId() {
	    Integer nextId = bunnyPostService.findNextId();
	    return new ResponseEntity<>(nextId, HttpStatus.OK);
	}


    // Add a comment to a post
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> addCommentToPost(@PathVariable Integer postId, @RequestBody CommentDTO commentDTO) {
        BunnyPost bunnyPost = bunnyPostService.findOne(postId);
        if (bunnyPost == null || bunnyPost.isDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Find the user who is making the comment and associate it with the comment
        User user = userService.findOne(commentDTO.getUserId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Comment newComment = new Comment();
        newComment.setDetails(commentDTO.getDetails());
        newComment.setBunnyPost(bunnyPost);
        newComment.setUser(user);
        newComment = commentService.save(newComment);

        return new ResponseEntity<>(new CommentDTO(newComment), HttpStatus.CREATED);
    }

    // Like a post
    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        BunnyPost bunnyPost = bunnyPostService.findOne(postId);
        User user = userService.findOne(userId);

        if (bunnyPost == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bunnyPostService.likePost(postId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Unlike a post
    @PostMapping("/{postId}/unlike")
    public ResponseEntity<Void> unlikePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        BunnyPost bunnyPost = bunnyPostService.findOne(postId);
        User user = userService.findOne(userId);

        if (bunnyPost == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bunnyPostService.unlikePost(postId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get users who liked a post
    @GetMapping("/{postId}/likes")
    public ResponseEntity<List<UserDTO>> getLikesForPost(@PathVariable Integer postId) {
        BunnyPost bunnyPost = bunnyPostService.findOneWithLikes(postId);

        if (bunnyPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UserDTO> likedUsers = bunnyPost.getLikedByUsers().stream()
                                             .map(UserDTO::new)
                                             .collect(Collectors.toList());

        return new ResponseEntity<>(likedUsers, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/add")
    public ResponseEntity<BunnyPost> addNewPost(@RequestBody BunnyPostDTO bunnyPostDTO){  
    	System.out.print(bunnyPostDTO);
        BunnyPost newPost = bunnyPostService.addNewPost(
        		userService.findByEmail(bunnyPostDTO.getEmail()),
        		bunnyPostDTO.getDetails(),
        		bunnyPostDTO.getPhoto(),
        		bunnyPostDTO.getLongitude(),
        		bunnyPostDTO.getLatitude()
        );

        return ResponseEntity.ok(newPost); 
    }
}
