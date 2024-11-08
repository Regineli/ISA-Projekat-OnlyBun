package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ExamDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.StudentDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.Exam;
import rs.ac.uns.ftn.informatika.jpa.service.CommentService;

@RestController
@RequestMapping(value = "api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping
	public ResponseEntity<List<CommentDTO>> getComments() {

		List<Comment> comments = commentService.findAll();

		// convert Comments to DTOs
		List<CommentDTO> commentsDTO = new ArrayList<>();
		for (Comment s : comments) {
			commentsDTO.add(new CommentDTO(s));
		}

		return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CommentDTO> getComment(@PathVariable Integer id) {

		Comment comment = commentService.findOne(id);

		// Comment must exist
		if (comment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<CommentDTO> saveComment(@RequestBody CommentDTO CommentDTO) {

		Comment comment = new Comment();
		comment.setDetails(CommentDTO.getDetails());

		comment = commentService.save(comment);
		return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO CommentDTO) {

		// a Comment must exist
		Comment comment = commentService.findOne(CommentDTO.getId());

		if (comment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		comment.setDetails(CommentDTO.getDetails());

		comment = commentService.save(comment);
		return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {

		Comment comment = commentService.findOne(id);

		if (comment != null) {
			commentService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
