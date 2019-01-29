package wade.model;

import java.util.List;

public class ItineraryDto {
	
	private String itineraryName;
	
	private List<Museum> museums;

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
	

}
