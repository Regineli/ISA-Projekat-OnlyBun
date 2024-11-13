package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.jpa.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.jpa.repository.LocationRepository;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	public Location findOne(Integer locationId) {
		return locationRepository.findById(locationId).orElseGet(null);
	}

	public List<Location > findAll() {
		return locationRepository.findAll();
	}
	
	public Location save(Location location) {
		System.out.print("loc servis");
		System.out.print(location.getId());
		return locationRepository.save(location);
	}

	public void remove(Integer id) {
		locationRepository.deleteById(id);
	}

}
