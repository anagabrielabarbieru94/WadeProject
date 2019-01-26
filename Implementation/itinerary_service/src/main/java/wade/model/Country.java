package wade.model;

public class Country {
	
	private String name;
	private String description;
	private String countryCode;
	
	public Country()
	{
		
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getCountryCode()
	{
		return this.countryCode;
	}
}
