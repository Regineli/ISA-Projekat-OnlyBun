package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "care_messages")
public class BunnyCareOrganizationMessage {

    @Id
    @Column(name = "id")  // ID is a column in the database
    private String id;  // Unique ID of the message (e.g., UUID)

    @Column(name = "name")  // Name is a column in the database
    private String name;  // Name of the organization or user
    
    @Column(name = "message_from")  // Name is a column in the database
    private String from;  // Name of the organization or user
    
    @Column(name = "message_to")  // Name is a column in the database
    private String to;  // Name of the organization or user

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

	@Column(name = "original_message_id")  // originalMessageId is a column in the database
    private String originalMessageId;  // ID of the original message, if this is a reply

    @ManyToOne  // Many messages can relate to one location
    @JoinColumn(name = "location_id")  // Foreign key for location
    private Location location;  // Location object

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
