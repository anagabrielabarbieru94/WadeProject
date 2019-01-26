package wade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wade.model.Country;
import wade.repository.ItineraryService;

@RestController
public class ItineraryController {
	@Autowired
	protected ItineraryService itineraryService;
	
	@RequestMapping("/showCountries")
	public String findAllCountries()
	{
		List<Country> countries = itineraryService.getAllCountries();
		
		//return countries.get(0).getName();
		return "Done";
	}
}
