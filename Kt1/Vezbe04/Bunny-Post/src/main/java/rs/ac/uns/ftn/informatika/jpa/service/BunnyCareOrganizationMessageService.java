package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import rs.ac.uns.ftn.informatika.jpa.model.BunnyCareOrganizationMessage;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.repository.BunnyCareOrganizationMessageRepository;

@Service
public class BunnyCareOrganizationMessageService {

    @Autowired
    private BunnyCareOrganizationMessageRepository repository;
    
    @Autowired
    private LocationService locationService;

    // Example method to save a BunnyCareOrganizationMessage
    public BunnyCareOrganizationMessage saveMessage(BunnyCareOrganizationMessage message) {
        return repository.save(message);
    }
    
    // Example method to find a message by ID
    public BunnyCareOrganizationMessage findById(String id) {
        return repository.findById(id).orElse(null); // returns null if not found
    }

    // Example method to delete a message by ID
    public void deleteMessage(String id) {
        repository.deleteById(id);
    }
    
    public List<BunnyCareOrganizationMessage> findAll() {
        return repository.findAll(); // returns a list of all BunnyCareOrganizationMessages
    }
    
    public BunnyCareOrganizationMessage createMessageFromJson(String json) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	BunnyCareOrganizationMessage message = new BunnyCareOrganizationMessage();
        try {
            // Parse the JSON string into a JsonNode
            JsonNode rootNode = objectMapper.readTree(json);

            // Extract the individual fields as strings
            String from = rootNode.path("from").asText();
            String to = rootNode.path("to").asText();
            String id = rootNode.path("id").asText();
            String naziv = rootNode.path("naziv").asText();
            double latitude = rootNode.path("latitude").asDouble();
            double longitude = rootNode.path("longitude").asDouble();
            
            message.setFrom(from);
            message.setTo(to);
            message.setId(id);
            message.setName(naziv);
            
            Location loc = new Location();
            loc.setLatitude(latitude);
            loc.setLongitude(longitude);
            
            Location l = locationService.save(loc);
            
            message.setLocation(l);   
            return repository.save(message);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
