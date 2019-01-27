package wade.model;

import java.util.List;

public class Country {
	
	private String name;
	private String description;
	private String countryCode;
	private Locality hasCapital;
	private List<Locality> include;
	
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
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getCountryCode()
	{
		return this.countryCode;
	}
	
	public void setCountryCode(String code) 
	{
		this.countryCode = code;
	}
	
	public void setCapital(Locality capital) {
		this.hasCapital = capital;
	}
	
	public Locality getCapital() {
		return this.hasCapital;
	}

	public List<Locality> getInclude() {
		return this.include;
	}

	public void setInclude(List<Locality> include) {
		this.include = include;
	}
}
