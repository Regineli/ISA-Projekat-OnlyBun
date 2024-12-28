package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.jpa.model.BunnyCareOrganizationMessage;

public interface BunnyCareOrganizationMessageRepository extends JpaRepository<BunnyCareOrganizationMessage, String> {

    // Custom query methods can be added here if needed
    // Example:
    // List<BunnyCareOrganizationMessage> findByName(String name);
}
