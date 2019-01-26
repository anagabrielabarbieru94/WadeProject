package wade.model;

public class Locality {
	
	private String name;
	private String description;
	private double latitude;
	private double longitude;
	private Country isIncludedBy;
	private Country capitalOf;
	
	public Locality() {
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public Country getIsIncludedBy() {
		return this.isIncludedBy;
	}
	
	public void setIsIncludedBy(Country isIncludedBy) {
		this.isIncludedBy = isIncludedBy;
	}

	public Country getCapitalOf() {
		return this.capitalOf;
	}

	public void setCapitalOf(Country capitalOf) {
		this.capitalOf = capitalOf;
	}
	
}
