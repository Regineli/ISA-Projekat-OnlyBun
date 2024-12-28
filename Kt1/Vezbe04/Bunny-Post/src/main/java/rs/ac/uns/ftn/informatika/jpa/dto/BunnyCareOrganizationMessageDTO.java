package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Location;

public class BunnyCareOrganizationMessageDTO {

    private String id;  // ID of the message
    private String name;  // Name of the organization or user
    private String originalMessageId;  // ID of the original message
    private Location location;  // Location associated with the message
    private String from;  // Name of the organization or user
    private String to;  // ID of the original message

    // Constructors
    public BunnyCareOrganizationMessageDTO() {
    }

    public BunnyCareOrganizationMessageDTO(String id, String name, String originalMessageId, Location location) {
        this.id = id;
        this.name = name;
        this.originalMessageId = originalMessageId;
        this.location = location;
    }
    
    public BunnyCareOrganizationMessageDTO(String id, String name, String originalMessageId, Location location, String from, String to) {
        this.id = id;
        this.name = name;
        this.originalMessageId = originalMessageId;
        this.location = location;
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	// Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalMessageId() {
        return originalMessageId;
    }

    public void setOriginalMessageId(String originalMessageId) {
        this.originalMessageId = originalMessageId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
