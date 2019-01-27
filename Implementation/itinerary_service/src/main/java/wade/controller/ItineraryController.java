package wade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import wade.model.Country;
import wade.model.Locality;
import wade.model.Seaside;
import wade.repository.ItineraryService;

@RestController
public class ItineraryController {
	@Autowired
	protected ItineraryService itineraryService;
	
	@RequestMapping(value = "/showCountries", method = RequestMethod.GET)
	@ResponseBody
	public List<Country>  findAllCountries()
	{
		List<Country> countries = itineraryService.getAllCountries();
		
		return countries;
		//return "Done";
	}
	
	@RequestMapping(value = "/showCountries/{name}", method = RequestMethod.GET)
	@ResponseBody
	public Country getCountryByName(@PathVariable("name") String name)
	{
		Country country = itineraryService.getCountryByName(name);
		
		return country;
	}
	
	@RequestMapping(value = "/showCities", method = RequestMethod.GET, params = {"countryName"})
	@ResponseBody
	public List<Locality> getCitiesByCountry(@RequestParam(value = "countryName") String countryName)
	{
		List<Locality> cities = itineraryService.getCitiesByCountry(countryName);
		
		return cities;
	}
	
	@RequestMapping(value = "/showSeasides",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Seaside> getNaturalTouristicObjective(@RequestParam(value = "localityName") String localityName)
	{
		List<Seaside> seasideList = itineraryService.getSeasideInProximity(localityName);
	
		return seasideList;
	}
}
