package wade.model;

public class Restaurant {
	private String name;
	private double latitude;
	private double longitude;
	private String isContainedBy;
	private String inProximityOf;
	
	public Restaurant() {
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getIsContainedBy() {
		return this.isContainedBy;
	}

	public void setIsContainedBy(String isContainedBy) {
		this.isContainedBy = isContainedBy;
	}

	public String getInProximityOf() {
		return this.inProximityOf;
	}

	public void setInProximityOf(String inProximityOf) {
		this.inProximityOf = inProximityOf;
	}

}
