package wade.model;

public class EntertainmentObjective {
	
	protected String name;
	protected String proximityLocalityName;
	protected String nearByLocalityName;
	protected double latitude;
	protected double longitude;
	private boolean isSelected;
	
	public EntertainmentObjective() {
		
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getName()
	{
		return this.name;
	}
	
	public String getLocalityProximity()
	{
		return this.proximityLocalityName;
	}
	
	public String getNearByLocality()
	{
		return this.nearByLocalityName;
	}
	
	public double getLatitude()
	{
		return this.latitude;
	}

	public double getLongitude()
	{
		return this.latitude;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setLocalityProximity(String localityName)
	{
		this.proximityLocalityName = localityName;
	}
	
	public void setNearByLocality(String localityName)
	{
		this.nearByLocalityName = localityName;
	}
	
	public void setLatitude(double lat)
	{
		this.latitude = lat;
	}

	public void setLongitude(double longitude)
	{
		this.latitude = longitude;
	}

}
