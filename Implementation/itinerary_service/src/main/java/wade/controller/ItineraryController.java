package wade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import wade.model.Accomodation;
import wade.model.Country;
import wade.model.Lake;
import wade.model.Locality;
import wade.model.Mountain;
import wade.model.Museum;
import wade.model.OtherCulturalAttraction;
import wade.model.Restaurant;
import wade.model.Seaside;
import wade.model.Theater;
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
	
	@RequestMapping(value = "/showRestaurants", method = RequestMethod.GET, params = {"localityName"})
	@ResponseBody
	public List<Restaurant> getRestaurantsByLocality(@RequestParam(value = "localityName") String localityName)
	{
		List<Restaurant> restaurants = itineraryService.getRestaurantsNearByLocality(localityName);
		return restaurants;
	}
	
	@RequestMapping(value = "/showRestaurantsInProximity", method = RequestMethod.GET, params = {"localityName"})
	@ResponseBody
	public List<Restaurant> getRestaurantsInProximity(@RequestParam(value = "localityName") String localityName)
	{
		List<Restaurant> restaurants = itineraryService.getRestaurantsInProximity(localityName);
		return restaurants;
	}
	
	@RequestMapping(value = "/showAllRestaurants", method = RequestMethod.GET, params = {"localityName"})
	@ResponseBody
	public List<Restaurant> getAllRestaurantsByLocality(@RequestParam(value = "localityName") String localityName)
	{
		List<Restaurant> restaurants = itineraryService.getAllRestaurantsAroundLocality(localityName);
		return restaurants;
	}
	
	@RequestMapping(value = "/showAccomodations", method = RequestMethod.GET, params = {"localityName"})
	@ResponseBody
	public List<Accomodation> getAccomodationsByLocality(@RequestParam(value = "localityName") String localityName)
	{
		List<Accomodation> accomodations = itineraryService.getAccomodationNearByLocality(localityName);
		return accomodations;
	}
	
	@RequestMapping(value = "/showAccomodationsInProximity", method = RequestMethod.GET, params = {"localityName"})
	@ResponseBody
	public List<Accomodation> getAccomodationsInProximity(@RequestParam(value = "localityName") String localityName)
	{
		List<Accomodation> accomodations = itineraryService.getAccomodationsInProximity(localityName);
		return accomodations;
	}
	
	@RequestMapping(value = "/showAllAccomodations", method = RequestMethod.GET, params = {"localityName"})
	@ResponseBody
	public List<Accomodation> getAllAccomodationsByLocality(@RequestParam(value = "localityName") String localityName)
	{
		List<Accomodation> accomodations = itineraryService.getAllAccomodationsAroundCity(localityName);
		return accomodations;
	}
	
	@RequestMapping(value = "/showMuseums",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Museum> getNearbyMuseumsByLocality(@RequestParam(value = "localityName") String localityName)
	{
		List<Museum> museumList = itineraryService.getMuseumsNearByLocality(localityName);
		return museumList;
	}
	
	@RequestMapping(value = "/showMuseumsInProximity",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Museum> getMuseumsInProximity(@RequestParam(value = "localityName") String localityName)
	{
		List<Museum> museumList = itineraryService.getMuseumsInProximity(localityName);
		return museumList;
	}
	
	@RequestMapping(value = "/showAllMuseums",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Museum> getAllMuseumsByLocality(@RequestParam(value = "localityName") String localityName)
	{
		List<Museum> museumList = itineraryService.getAllMuseumsAroundLocality(localityName);
		return museumList;
	}
	
	@RequestMapping(value = "/showTheaters",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Theater> getNearByTheaters(@RequestParam(value = "localityName") String localityName)
	{
		List<Theater> theaterList = itineraryService.getTheatersNearByLocality(localityName);
		return theaterList;
	}
	
	@RequestMapping(value = "/showTheatersInProximity",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Theater> getNearByTheatersInProximity(@RequestParam(value = "localityName") String localityName)
	{
		List<Theater> theaterList = itineraryService.getTheatersInProximity(localityName);
		return theaterList;
	}
	
	@RequestMapping(value = "/showAllTheaters",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Theater> getAllTheatersByCity(@RequestParam(value = "localityName") String localityName)
	{
		List<Theater> theaterList = itineraryService.getAllTheatersAroundLocality(localityName);
		return theaterList;
	}
	
	@RequestMapping(value = "/showOtherCulturalAttractions",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<OtherCulturalAttraction> getOtherCulturalAttractionsNearByLocality(@RequestParam(value = "localityName") String localityName)
	{
		List<OtherCulturalAttraction> theaterList = itineraryService.getOtherCulturalAttractionsNearByLocality(localityName);
		return theaterList;
	}
	
	@RequestMapping(value = "/showOtherCulturalAttractionsInProximity",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<OtherCulturalAttraction> getOtherCulturalAttractionsInProximity(@RequestParam(value = "localityName") String localityName)
	{
		List<OtherCulturalAttraction> theaterList = itineraryService.getOtherCulturalAttractionsInProximity(localityName);
		return theaterList;
	}
	
	@RequestMapping(value = "/showAllOtherCulturalAttractions",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<OtherCulturalAttraction> getAllOtherCulturalAttractions(@RequestParam(value = "localityName") String localityName)
	{
		List<OtherCulturalAttraction> theaterList = itineraryService.getAllOthersCulturalAtrractionsAroundLocality(localityName);
		return theaterList;
	}
	
	@RequestMapping(value = "/showSeasides",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Seaside> getProximitySeasides(@RequestParam(value = "localityName") String localityName)
	{
		List<Seaside> seasideList = itineraryService.getSeasideInProximity(localityName);
		return seasideList;
	}
	
	@RequestMapping(value = "/showMountains",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Mountain> getProximityMountains(@RequestParam(value = "localityName") String localityName)
	{
		List<Mountain> mountainList = itineraryService.getMountainInProximity(localityName);
		return mountainList ;
	}
	
	@RequestMapping(value = "/showLakes",method = RequestMethod.GET, params= {"localityName"})
	@ResponseBody
	public List<Lake> getProximityLakes(@RequestParam(value = "localityName") String localityName)
	{
		List<Lake> lakeList = itineraryService.getLakeInProximity(localityName);
		return lakeList ;
	}
	
}
