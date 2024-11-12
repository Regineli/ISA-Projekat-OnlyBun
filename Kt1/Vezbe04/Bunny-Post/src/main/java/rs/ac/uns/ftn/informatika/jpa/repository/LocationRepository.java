package rs.ac.uns.ftn.informatika.jpa.repository;

import rs.ac.uns.ftn.informatika.jpa.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<Location, Integer>{
	
	public Location findOneById(Integer id);
	public Page<Location> findAll(Pageable pageable);
}
