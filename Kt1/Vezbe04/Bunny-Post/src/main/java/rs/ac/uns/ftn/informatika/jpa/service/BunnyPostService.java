package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.BunnyPostRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

@Service
public class BunnyPostService {

    @Autowired
    private BunnyPostRepository bunnyPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional // Dodato za rešavanje LazyInitializationException greške
    public BunnyPost findOne(Integer id) {
        return bunnyPostRepository.findById(id).orElse(null);
    }

    // Pronađi sve postove koji nisu obrisani
    public List<BunnyPost> findAll() {
        return bunnyPostRepository.findAll().stream()
                .filter(post -> !post.isDeleted())
                .toList();
    }

    public BunnyPost save(BunnyPost bunnyPost) {
        return bunnyPostRepository.save(bunnyPost);
    }

    // Soft delete umesto fizičkog brisanja
    public void softDelete(Integer id) {
        BunnyPost bunnyPost = findOne(id);
        if (bunnyPost != null) {
            bunnyPost.setDeleted(true);
            bunnyPostRepository.save(bunnyPost);
        }
    }

    public List<BunnyPost> findByUserId(Integer userId) {
        return bunnyPostRepository.findByUserId(userId).stream()
                .filter(post -> !post.isDeleted())
                .toList();
    }

    public List<CommentDTO> getCommentsByBunnyPostId(Integer id) {
        BunnyPost bunnyPost = findOne(id);
        if (bunnyPost == null) return new ArrayList<>();

        Set<Comment> comments = bunnyPost.getComments();
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (Comment comment : comments) {
            commentDTOs.add(new CommentDTO(comment));
        }

        return commentDTOs;
    }

    // Dodaj lajk korisnika na post
    @Transactional // Dodato za održavanje sesije prilikom rada sa lajkovima
    public void likePost(Integer postId, Integer userId) {
        BunnyPost bunnyPost = findOneWithLikes(postId);
        Optional<User> user = userRepository.findById(userId);

        if (bunnyPost != null && user.isPresent()) {
            if (!bunnyPost.getLikedByUsers().contains(user.get())) {
                bunnyPost.addLike(user.get());
                bunnyPostRepository.save(bunnyPost);
            }
        }
    }

    // Ukloni lajk korisnika sa posta
    @Transactional // Dodato za održavanje sesije prilikom rada sa lajkovima
    public void unlikePost(Integer postId, Integer userId) {
        BunnyPost bunnyPost = findOneWithLikes(postId);
        Optional<User> user = userRepository.findById(userId);

        if (bunnyPost != null && user.isPresent()) {
            if (bunnyPost.getLikedByUsers().contains(user.get())) {
                bunnyPost.removeLike(user.get());
                bunnyPostRepository.save(bunnyPost);
            }
        }
    }

    // Pronađi post sa lajkovima kako bi se izbegla LazyInitializationException
    @Transactional
    public BunnyPost findOneWithLikes(Integer postId) {
        return bunnyPostRepository.findOneWithLikes(postId);
    }
}