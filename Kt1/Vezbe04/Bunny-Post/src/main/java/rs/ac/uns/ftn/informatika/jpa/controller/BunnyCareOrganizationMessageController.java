package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.jpa.dto.BunnyCareOrganizationMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.BunnyPostDTO;
import rs.ac.uns.ftn.informatika.jpa.model.BunnyCareOrganizationMessage;
import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.service.BunnyCareOrganizationMessageService;
import rs.ac.uns.ftn.informatika.jpa.service.BunnyPostService;
import rs.ac.uns.ftn.informatika.jpa.service.CommentService;
import rs.ac.uns.ftn.informatika.jpa.service.UserLikePostService;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;
import rs.ac.uns.ftn.informatika.jpa.utils.TokenUtils;


@RestController
@RequestMapping(value = "api/bunnyCareMessages")
@CrossOrigin(origins = "http://localhost:4200")  // Allow only Angular app
public class BunnyCareOrganizationMessageController {
	
	@Autowired
	private BunnyCareOrganizationMessageService bunnyCareService;


	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "")
    public ResponseEntity<List<BunnyCareOrganizationMessage>> getAllMessages() {
		System.out.println("Start bunny care controller");
		List<BunnyCareOrganizationMessage> response = bunnyCareService.findAll();
		System.out.println("found:  " + response.toString());
        return ResponseEntity.ok(response); // returns all BunnyCareOrganizationMessages
    }
	
}