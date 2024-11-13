package rs.ac.uns.ftn.informatika.jpa.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.models.PathItem;
import java.nio.file.Path;
import java.nio.file.Paths;
import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;
import rs.ac.uns.ftn.informatika.jpa.repository.BunnyPostRepository;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.service.LocationService;
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
	
	@Autowired
    private LocationService locationService;
	
	private Map<Integer, Location> locationCache = new HashMap<>();
	
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
	
	public BunnyPost findBunnyPostByImagePath(String imagePath) {
		return bunnyPostRepository.findByPhoto(imagePath);
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
	
	public BunnyPost addNewPost(User user, String details, String base64Photo, double longitude, double latitude) {
	    BunnyPost newPost = new BunnyPost(details, user); // Postavi `photo` privremeno kao prazan string
	    
	    newPost.setTime(LocalDateTime.now());
	    newPost.setId(findNextId());

	    // Set location
	    
	    Location location = new Location();
	    
	    location.setLatitude(latitude);
	    location.setLongitude(longitude);
	    newPost.setLocation(location);
	    newPost.setDeleted(false);
	    

	    try {
	        String directoryPath = "src/main/webapp/images/";
	        String fileName = "photo_" + newPost.getId() + ".jpg"; 
	        String filePath = directoryPath + fileName;

	        File directory = new File(directoryPath);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }
	        if (base64Photo.startsWith("data:image/png;base64,")) {
                base64Photo = base64Photo.replace("data:image/png;base64,", "");
            }
	        
	        base64Photo = base64Photo.replaceAll("\\s", ""); // Uklanja razmake


	        byte[] imageBytes = Base64.getDecoder().decode(base64Photo);
	        try (FileOutputStream fos = new FileOutputStream(filePath)) {
	            fos.write(imageBytes);
	        }

	        newPost.setPhoto(filePath);

	    } catch (IOException e) {
	        e.printStackTrace();	        
	    }

	    return save(newPost);
	}
	
	
	public Location getLocationFromCache(Integer locationId) {
        Location location = locationCache.get(locationId);
        if (location == null) {
            location = locationService.findOne(locationId);
            if (location != null) {
                locationCache.put(location.getId(), location);
            }
        }
        return location;
    }
	
	public BunnyPost findOneLocationCashe(Integer id) {
        BunnyPost bunnyPost = findOne(id);
        if (bunnyPost != null && bunnyPost.getLocation() != null) {
            Location location = getLocationFromCache(bunnyPost.getLocation().getId());
            bunnyPost.setLocation(location);
        }
        return bunnyPost;
    }
	
	@Scheduled(cron = "0 0 0 * * ?") 
	public void compressOldImages() {
	    LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
	    Path start = Paths.get("src/main/webapp/images/");
	    
	    try (Stream<Path> paths = Files.walk(start)) {
	        paths.filter(Files::isRegularFile)
	             .filter(path -> isOlderThanOneMonth(path, oneMonthAgo)) 
	             .filter(path -> !path.getFileName().toString().startsWith("compressed_"))
	             .forEach(this::compressImage);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
    
    private boolean isOlderThanOneMonth(Path path, LocalDate oneMonthAgo) {
        try {
            return Files.getLastModifiedTime(path).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(oneMonthAgo);
        } catch (IOException e) {
            return false;
        }
    }

    private void compressImage(Path imagePath) {
    	System.out.print("aaaaaaaaaaaaaaaaaa");
        File inputFile = imagePath.toFile();
        
        // Definišite putanju za kompresovanu sliku sa prefiksom 'compressed_'
        String compressedFileName = "compressed_" + inputFile.getName();
        File compressedFile = new File("src/main/webapp/images/" + compressedFileName);
        System.out.print("napravljen compresfile");
        try {
            // Učitajte originalnu sliku
            BufferedImage image = ImageIO.read(inputFile);
            if (image.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
                rgbImage.createGraphics().drawImage(image, 0, 0, null);
                image = rgbImage;
            }
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
            
            if (!writers.hasNext()) {
                throw new IllegalStateException("No JPEG writer available");
            }
            ImageWriter writer = writers.next();

            // Kompresujte sliku
            
            try (ImageOutputStream output = ImageIO.createImageOutputStream(compressedFile)) {
                writer.setOutput(output);

                ImageWriteParam param = writer.getDefaultWriteParam();
                if (param.canWriteCompressed()) {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.5f); // Možete promeniti kvalitet po potrebi
                }

                writer.write(null, new javax.imageio.IIOImage(image, null, null), param);

                System.out.print("kompresovanje");
            }
            writer.dispose();

            // Brišite originalnu sliku
            if (inputFile.exists()) {
                if (inputFile.delete()) {
                    System.out.println("Original image deleted: " + inputFile.getAbsolutePath());
                } else {
                    System.out.println("Failed to delete original image: " + inputFile.getAbsolutePath());
                }
            }

            // Ispisujemo gde je sačuvana kompresovana slika
            System.out.println("Compressed image saved to " + compressedFile.getAbsolutePath());

            // Ažurirajte bazu podataka - ovde treba pozvati metod koji ažurira putanju slike u bazi
            updateImagePathInDatabase(compressedFileName, imagePath.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void updateImagePathInDatabase(String newImagePath, String oldImagePath) {
        BunnyPost bunnyPost = findBunnyPostByImagePath(oldImagePath); 
        if (bunnyPost != null) {
            bunnyPost.setPhoto(newImagePath);
            bunnyPostRepository.save(bunnyPost);
        } else {
            System.out.println("no image");
        }
    }


	

    // Soft delete umesto fizičkog brisanja
    public void softDelete(Integer id) {
        BunnyPost bunnyPost = findOne(id);
        if (bunnyPost != null) {
            bunnyPost.setDeleted(true);
            bunnyPostRepository.save(bunnyPost);
        }
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
