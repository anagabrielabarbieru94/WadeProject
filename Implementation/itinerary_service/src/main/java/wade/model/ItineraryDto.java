package wade.model;

import java.util.ArrayList;
import java.util.List;

public class ItineraryDto {
	
	private String itineraryName;
	
	private List<Museum> museums;
	
	private List<OtherCulturalAttraction> otherCulturalAttractions;
	
	private List<Restaurant> restaurants;
	
	private List<Mountain> mountains;
	
	private List<Lake> lakes;
	
	private List<OtherNaturalAttraction> otherNaturalAttractions;
	
	private List<EntertainmentObjective> entertainmentObjectives;

	public ItineraryDto() {
		museums = new ArrayList<>();
		otherCulturalAttractions = new ArrayList<>();
		restaurants = new ArrayList<>();
		mountains = new ArrayList<>();
		lakes = new ArrayList<>();
		otherNaturalAttractions = new ArrayList<>();
		entertainmentObjectives = new ArrayList<>();
	}

	public List<Museum> getMuseums() {
		return museums;
	}

	public void setMuseums(List<Museum> museums) {
		this.museums = museums;
	}

	public String getItineraryName() {
		return itineraryName;
	}

	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}

	public List<OtherCulturalAttraction> getOtherCulturalAttractions() {
		return otherCulturalAttractions;
	}

	public void setOtherCulturalAttractions(List<OtherCulturalAttraction> otherCulturalAttractions) {
		this.otherCulturalAttractions = otherCulturalAttractions;
	}

	public List<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public List<Mountain> getMountains() {
		return mountains;
	}

	public void setMountains(List<Mountain> mountains) {
		this.mountains = mountains;
	}

	public List<Lake> getLakes() {
		return lakes;
	}

	public void setLakes(List<Lake> lakes) {
		this.lakes = lakes;
	}

	public List<OtherNaturalAttraction> getOtherNaturalAttractions() {
		return otherNaturalAttractions;
	}

	public void setOtherNaturalAttractions(List<OtherNaturalAttraction> otherNaturalAttractions) {
		this.otherNaturalAttractions = otherNaturalAttractions;
	}

	public List<EntertainmentObjective> getEntertainmentObjectives() {
		return entertainmentObjectives;
	}

	public void setEntertainmentObjectives(List<EntertainmentObjective> entertainmentObjectives) {
		this.entertainmentObjectives = entertainmentObjectives;
	}
}
