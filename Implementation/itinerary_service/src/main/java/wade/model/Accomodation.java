package wade.model;

public class Accomodation {
	
	private String name;
	private double latitude;
	private double longitude;
	private Locality isContainedBy;
	private Locality inProximityOf;
	
	public Accomodation() {
		
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

	public Locality getIsContainedBy() {
		return this.isContainedBy;
	}

	public void setIsContainedBy(Locality isContainedBy) {
		this.isContainedBy = isContainedBy;
	}

	public Locality getInProximityOf() {
		return this.inProximityOf;
	}

	public void setInProximityOf(Locality inProximityOf) {
		this.inProximityOf = inProximityOf;
	}
	

}
