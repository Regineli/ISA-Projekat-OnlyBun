package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.informatika.jpa.dto.BunnyPostDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
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

    @GetMapping
    public ResponseEntity<List<BunnyPostDTO>> getBunnyPosts() {
        List<BunnyPost> bunnyPosts = bunnyPostService.findAll();
        List<BunnyPostDTO> bunnyPostsDTO = bunnyPosts.stream()
                                                      .map(BunnyPostDTO::new)
                                                      .collect(Collectors.toList());
        return new ResponseEntity<>(bunnyPostsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BunnyPostDTO> getBunnyPost(@PathVariable Integer id) {
        BunnyPost bunnyPost = bunnyPostService.findOne(id);
        if (bunnyPost == null || bunnyPost.isDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new BunnyPostDTO(bunnyPost), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<BunnyPostDTO> saveBunnyPost(@RequestBody BunnyPostDTO bunnyPostDTO) {
        BunnyPost bunnyPost = new BunnyPost();
        bunnyPost.setDetails(bunnyPostDTO.getDetails());
        bunnyPost.setPhoto(bunnyPostDTO.getPhoto());
        bunnyPost = bunnyPostService.save(bunnyPost);
        return new ResponseEntity<>(new BunnyPostDTO(bunnyPost), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<BunnyPostDTO> updateBunnyPost(@RequestBody BunnyPostDTO bunnyPostDTO) {
        BunnyPost bunnyPost = bunnyPostService.findOne(bunnyPostDTO.getId());
        if (bunnyPost == null || bunnyPost.isDeleted()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bunnyPost.setDetails(bunnyPostDTO.getDetails());
        bunnyPost.setPhoto(bunnyPostDTO.getPhoto());
        bunnyPost = bunnyPostService.save(bunnyPost);
        return new ResponseEntity<>(new BunnyPostDTO(bunnyPost), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBunnyPost(@PathVariable Integer id) {
        BunnyPost bunnyPost = bunnyPostService.findOne(id);
        if (bunnyPost != null && !bunnyPost.isDeleted()) {
            bunnyPostService.softDelete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get comments for a specific post
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByBunnyPostId(@PathVariable Integer postId) {
        List<Comment> comments = commentService.findByBunnyPostId(postId);
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CommentDTO> commentDTOs = comments.stream()
                                               .map(CommentDTO::new)
                                               .collect(Collectors.toList());
        return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
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
}