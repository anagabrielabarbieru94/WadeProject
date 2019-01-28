package wade.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Service;

import wade.model.Accomodation;
import wade.model.Activity;
import wade.model.Country;
import wade.model.EntertainmentObjective;
import wade.model.Lake;
import wade.model.Locality;
import wade.model.Mountain;
import wade.model.Museum;
import wade.model.OtherCulturalAttraction;
import wade.model.OtherNaturalAttraction;
import wade.model.Restaurant;
import wade.model.Seaside;
import wade.model.Theater;

@Service
public class ItineraryService {
	private Model model;
	
	public ItineraryService()
	{	
		String path = "src//main//resources//toWas.ttl";
		model = FileManager.get().loadModel(path);
	}
	
	public List<Country> getAllCountries()
	{
		List<Country> availableCountries = new ArrayList<Country>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString += "select ?countryName ?description ?code ?capitalName ?lat ?long where { \n";
		queryString += "?country rdf:type tA:Country; \n tA:name ?countryName;\n";
		queryString += "tA:description ?description; \n";
		queryString += "tA:hasCode ?code; \n";
		queryString += "tA:hasCapital ?capital. \n";
		queryString += "?capital tA:name ?capitalName; \n";
		queryString += "<http://www.opengis.net/ont/geosparql#lat> ?lat; \n";
		queryString += "<http://www.opengis.net/ont/geosparql#long> ?long. \n }";
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		
		    ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		    System.out.println("intra    ");
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal l = soln.getLiteral("countryName") ;
		      Literal d = soln.getLiteral("description") ; 
		      Literal code = soln.getLiteral("code");
		      Literal capital = soln.getLiteral("capitalName");
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      
		      Country currentCountry = new Country();
		      currentCountry.setName(l.toString());
		      currentCountry.setDescription(d.toString());
		      currentCountry.setCountryCode(code.toString());
		      Locality capitalLocality = new Locality();
		      capitalLocality.setName(capital.toString());
		      capitalLocality.setLatitude(lat.getDouble());
		      capitalLocality.setLongitude(lat.getDouble());
		      currentCountry.setCapital(capitalLocality);
		      
		      availableCountries.add(currentCountry);
		      System.out.println("Printez " + l.toString());
		    }
		 
		return availableCountries;
	}
	
	public Country getCountryByName(String countryName) {
		Country country = new Country();
			
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?country ?countryName ?description ?code ?capitalName ?lat ?long where { \n";
		queryString += "?country rdf:type tA:Country; \n tA:name ?countryName;\n";
		queryString += "tA:description ?description; \n";
		queryString += "tA:hasCode ?code; \n";
		queryString += "tA:hasCapital ?capital. \n";
		queryString += "?capital tA:name ?capitalName; \n";
		queryString += "<http://www.opengis.net/ont/geosparql#lat> ?lat; \n";
		queryString += "<http://www.opengis.net/ont/geosparql#long> ?long. \n";
		queryString += "FILTER regex(?countryName, \""+ countryName + "\", \"i\"). }\n";
		System.out.println(queryString);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		
		    ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		    System.out.println("intra    ");
		      QuerySolution soln = results.nextSolution() ;
		      
		      Resource c = soln.getResource("country");
		      Literal l = soln.getLiteral("countryName") ; 
		      Literal d = soln.getLiteral("description") ; 
		      Literal code = soln.getLiteral("code");
		      Literal capital = soln.getLiteral("capitalName");
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      
		      country.setName(l.toString());
		      country.setDescription(d.toString());
		      country.setCountryCode(code.toString());
		      
		      Locality capitalLocality = new Locality();
		      capitalLocality.setName(capital.toString());
		      capitalLocality.setLatitude(lat.getDouble());
		      capitalLocality.setLongitude(lat.getDouble());
		      country.setCapital(capitalLocality);
		      System.out.println("Printez " + c.toString());
		    }
		    
		return country;
	}
	
	public List<Locality> getCitiesByCountry(String countryName){
		List<Locality> countryCities = new ArrayList<>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString += "select ?cityName ?description ?lat ?long where { \n";
		queryString += "?city rdf:type tA:Locality; \n tA:name ?cityName;\n";
		queryString += " tA:isIncludedBy ?country; \n";
		queryString += "tA:description ?description; \n";
		queryString += "<http://www.opengis.net/ont/geosparql#lat> ?lat; \n";
		queryString += "<http://www.opengis.net/ont/geosparql#long> ?long. \n ";
		queryString += "?country tA:name ?countryName. \n";
		queryString += "FILTER regex(?countryName, \""+ countryName + "\", \"i\"). }\n";
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		    System.out.println("intra    ");
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal l = soln.getLiteral("cityName") ;
		      Literal d = soln.getLiteral("description") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      
		      Country country = new Country();
		      country.setName(countryName);
		      Locality locality = new Locality();
		      locality.setName(l.toString());
		      locality.setLatitude(lat.getDouble());
		      locality.setLongitude(lat.getDouble());
		      locality.setIsIncludedBy(country);
		      
		      countryCities.add(locality);
		      System.out.println("Printez " + l.toString());
		    }
		    
		return countryCities;
	}
	
	public List<Accomodation> getAccomodationNearByLocality(String localityName)
	{
		List<Accomodation> accomodationList = new ArrayList<Accomodation>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?accomodationName ?latid ?longit where { \n";
		queryString += "?accomodation rdf:type tA:Accomodation; \n";
		queryString += "tA:name ?accomodationName; \n";
		queryString += "geo:lat ?latid; \n";
		queryString += "geo:long ?longit; \n";
		queryString += "tA:isContainedBy tA:"+ localityName + ".}";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("accomodationName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Accomodation accomodation= new Accomodation();
	      accomodation.setName(name.toString());
	      accomodation.setLatitude(latitude.getDouble());
	      accomodation.setLongitude(longitude.getDouble());
	      accomodation.setIsContainedBy(localityName);
	      accomodationList.add(accomodation);
	    }
		return accomodationList;
	}
	
	public List<Accomodation> getAccomodationsInProximity(String localityName){
		List<Accomodation> cityAccomodations = new ArrayList<>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?hotelName ?lat ?long ?cityName where { \n";
		queryString += "?hotel rdf:type tA:Accomodation; \n tA:name ?hotelName; \n";
		queryString += " tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\" , \"i\"). }\n";
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal h = soln.getLiteral("hotelName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      Accomodation accomodation = new Accomodation();
		      accomodation.setName(h.getString());
		      accomodation.setLatitude(lat.getDouble());
		      accomodation.setLongitude(lng.getDouble());
		      accomodation.setInProximityOf(c.toString());
		      
		      cityAccomodations.add(accomodation);
		      System.out.println("Printez " + h.toString());
		    }
		    
		return cityAccomodations;
	}
	
	public List<Accomodation> getAllAccomodationsAroundCity(String localityName){
		List<Accomodation> cityAccomodations = new ArrayList<>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString+="select * { \n { \n";
		
		//get proximity hotels
		queryString += "select ?hotelName ?lat ?long ?cityName where { \n";
		queryString += "?hotel rdf:type tA:Accomodation; \n tA:name ?hotelName; \n";
		queryString += " tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\" , \"i\"). }\n";
		
		queryString += "}\n UNION \n {\n";
		
		//get nearby hotels
		queryString += "select ?hotelName ?lat ?long ?cityName where { \n";
		queryString += "?hotel rdf:type tA:Accomodation; \n tA:name ?hotelName; \n";
		queryString += " tA:isContainedBy ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\" , \"i\"). }\n";
		queryString += "}\n}\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal h = soln.getLiteral("hotelName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      Accomodation accomodation = new Accomodation();
		      accomodation.setName(h.getString());
		      accomodation.setLatitude(lat.getDouble());
		      accomodation.setLongitude(lng.getDouble());
		      accomodation.setIsContainedBy(c.toString());
		      
		      cityAccomodations.add(accomodation);
		      System.out.println("Printez " + h.toString());
		    }
		    
		return cityAccomodations;
	}
	
	public List<Restaurant> getRestaurantsNearByLocality(String localityName)
	{
		List<Restaurant> restaurantList = new ArrayList<Restaurant>();
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?restaurantName ?latid ?longit where { \n";
		queryString += "?restaurant rdf:type tA:Restaurant; \n tA:name ?restaurantName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += "	tA:isContainedBy tA:"+localityName+". }\n";
		
		System.out.println(queryString);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("restaurantName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Restaurant restaurant = new Restaurant();
	      restaurant.setName(name.toString());
	      restaurant.setLatitude(latitude.getDouble());
	      restaurant.setLongitude(longitude.getDouble());
	      restaurant.setIsContainedBy(localityName);
	      restaurantList.add(restaurant);
	    }
		return restaurantList;
	}
	
	public List<Restaurant> getRestaurantsInProximity(String localityName){
		List<Restaurant> cityRestaurants = new ArrayList<>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?restaurantName ?lat ?long ?cityName where { \n";
		queryString += "?hotel rdf:type tA:Restaurant; \n tA:name ?restaurantName; \n";
		queryString += " tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal h = soln.getLiteral("restaurantName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      Restaurant restaurant = new Restaurant();
		      restaurant.setName(h.getString());
		      restaurant.setLatitude(lat.getDouble());
		      restaurant.setLongitude(lng.getDouble());
		      restaurant.setInProximityOf(c.toString());
		      
		      cityRestaurants.add(restaurant);
		      System.out.println("Printez " + h.toString());
		    }
		    
		return cityRestaurants;
	}
	
	public List<Restaurant> getAllRestaurantsAroundLocality(String localityName){
		List<Restaurant> cityRestaurants = new ArrayList<>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString+="select * { \n { \n";
		
		//get proximity hotels
		queryString += "select ?restaurantName ?lat ?long ?cityName where { \n";
		queryString += "?hotel rdf:type tA:Restaurant; \n tA:name ?restaurantName; \n";
		queryString += " tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		
		queryString += "}\n UNION \n {\n";
		
		//get nearby hotels
		queryString += "select ?restaurantName ?lat ?long ?cityName where { \n";
		queryString += "?hotel rdf:type tA:Restaurant; \n tA:name ?restaurantName; \n";
		queryString += " tA:isContainedBy ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		queryString += "}\n}\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal r = soln.getLiteral("restaurantName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      Restaurant restaurant = new Restaurant();
		      restaurant.setName(r.getString());
		      restaurant.setLatitude(lat.getDouble());
		      restaurant.setLongitude(lng.getDouble());
		      restaurant.setIsContainedBy(c.toString());
		      
		      cityRestaurants.add(restaurant);
		      System.out.println("Printez " + r.toString());
		    }
		    
		return cityRestaurants;
	}
	
	public List<Museum> getMuseumsNearByLocality(String localityName)
	{
		List<Museum> museumList = new ArrayList<Museum>();
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?museumName ?latid ?longit where { \n";
		queryString += "?museum rdf:type tA:Museum; \n tA:name ?museumName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += " tA:isContainedBy ?city.\n";
		queryString += " ?city tA:name ?cityName. \n";
		queryString += " FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		
		System.out.println(queryString);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("museumName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      
	      Museum museum = new Museum();
	      museum.setName(name.toString());
	      museum.setLatitude(latitude.getDouble());
	      museum.setLongitude(longitude.getDouble());
	      museum.setNearByLocality(localityName);
	      museumList.add(museum);
	    }
		return museumList;
	}
	
	public List<Museum> getMuseumsInProximity(String localityName)
	{
		List<Museum> museumList = new ArrayList<Museum>();
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?museumName ?latid ?longit ?cityName where { \n";
		queryString += "?museum rdf:type tA:Museum; \n tA:name ?museumName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += " tA:inProximityOf ?city.\n";
		queryString += " ?city tA:name ?cityName. \n";
		queryString += " FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		
		System.out.println(queryString);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("museumName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Literal c = soln.getLiteral("cityName") ;
	      
	      Museum museum = new Museum();
	      museum.setName(name.toString());
	      museum.setLatitude(latitude.getDouble());
	      museum.setLongitude(longitude.getDouble());
	      museum.setLocalityProximity(c.toString());
	      museumList.add(museum);
	    }
		return museumList;
	}
	
	public List<Museum> getAllMuseumsAroundLocality(String localityName){
		List<Museum> cityMuseums = new ArrayList<Museum>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString+="select * { \n { \n";
		
		//get proximity hotels
		queryString += "select ?museumName ?lat ?long ?cityName where { \n";
		queryString += "?museum rdf:type tA:Museum; \n tA:name ?museumName; \n";
		queryString += " tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		
		queryString += "}\n UNION \n {\n";
		
		//get nearby hotels
		queryString += "select ?museumName ?lat ?long ?cityName where { \n";
		queryString += "?museum rdf:type tA:Museum; \n tA:name ?museumName; \n";
		queryString += " tA:isContainedBy ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		queryString += "}\n}\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal r = soln.getLiteral("museumName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      Museum museum = new Museum();
		      museum.setName(r.getString());
		      museum.setLatitude(lat.getDouble());
		      museum.setLongitude(lng.getDouble());
		      museum.setLocalityProximity(c.toString());
		      
		      cityMuseums.add(museum);
		      System.out.println("Printez " + r.toString());
		    }
		    
		return cityMuseums;
	}
	
	public List<Theater> getTheatersNearByLocality(String localityName)
	{
		List<Theater> theaterList = new ArrayList<Theater>();
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?theaterName ?latid ?longit ?cityName where { \n";
		queryString += "?theater rdf:type tA:Theatre; \n tA:name ?theaterName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += " tA:isContainedBy ?city.\n";
		queryString += " ?city tA:name ?cityName. \n";
		queryString += " FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		
		System.out.println(queryString);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("theaterName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Literal city = soln.getLiteral("cityName");
	      
	      Theater theater = new Theater();
	      theater.setName(name.toString());
	      theater.setLatitude(latitude.getDouble());
	      theater.setLongitude(longitude.getDouble());
	      theater.setNearByLocality(city.toString());
	      theaterList.add(theater);
	    }
		return theaterList;
	}
	
	public List<Theater> getTheatersInProximity(String localityName)
	{
		List<Theater> theaterList = new ArrayList<Theater>();
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?theaterName ?latid ?longit ?cityName where { \n";
		queryString += "?theater rdf:type tA:Theatre; \n tA:name ?theaterName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += " tA:inProximityOf ?city.\n";
		queryString += " ?city tA:name ?cityName. \n";
		queryString += " FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		
		System.out.println(queryString);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("theaterName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Literal city = soln.getLiteral("cityName");
	      
	      Theater theater = new Theater();
	      theater.setName(name.toString());
	      theater.setLatitude(latitude.getDouble());
	      theater.setLongitude(longitude.getDouble());
	      theater.setLocalityProximity(city.toString());
	      theaterList.add(theater);
	    }
		return theaterList;
	}
	
	public List<Theater> getAllTheatersAroundLocality(String localityName){
		List<Theater> cityTheaters = new ArrayList<Theater>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString+="select * { \n { \n";
		
		//get proximity hotels
		queryString += "select ?theaterName ?lat ?long ?cityName where { \n";
		queryString += "?theater rdf:type tA:Theatre; \n tA:name ?theaterName; \n";
		queryString += " tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		
		queryString += "}\n UNION \n {\n";
		
		//get nearby hotels
		queryString += "select ?theaterName ?lat ?long ?cityName where { \n";
		queryString += "?theater rdf:type tA:Theatre; \n tA:name ?theaterName; \n";
		queryString += " tA:isContainedBy ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		queryString += "}\n}\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal r = soln.getLiteral("theaterName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      Theater theater = new Theater();
		      theater.setName(r.getString());
		      theater.setLatitude(lat.getDouble());
		      theater.setLongitude(lng.getDouble());
		      theater.setLocalityProximity(c.toString());
		      
		      cityTheaters.add(theater);
		      System.out.println("Printez " + r.toString());
		    }
		    
		return cityTheaters;
	}
	
	public List<OtherCulturalAttraction> getOtherCulturalAttractionsNearByLocality(String localityName)
	{
		List<OtherCulturalAttraction> attractionsList = new ArrayList<OtherCulturalAttraction>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?attractionName ?lat ?long ?cityName where { \n";
		queryString += "?attraction rdf:type tA:OtherCulturalAttraction; \n";
		queryString += "tA:name ?attractionName; \n";
		queryString += " tA:isContainedBy ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\" , \"i\"). }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("attractionName") ;
	      Literal longitude = soln.getLiteral("long");
	      Literal latitude = soln.getLiteral("lat");
	      Literal city = soln.getLiteral("cityName");
	      
	      OtherCulturalAttraction attraction = new OtherCulturalAttraction();
	      attraction.setName(name.toString());
	      attraction.setLatitude(latitude.getDouble());
	      attraction.setLongitude(longitude.getDouble());
	      attraction.setNearByLocality(city.toString());
	      attractionsList.add(attraction);
	    }
		return attractionsList;
	}
	
	public List<OtherCulturalAttraction> getOtherCulturalAttractionsInProximity(String localityName)
	{
		List<OtherCulturalAttraction> attractionsList = new ArrayList<OtherCulturalAttraction>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?attractionName ?lat ?long ?cityName where { \n";
		queryString += "?attraction rdf:type tA:OtherCulturalAttraction; \n";
		queryString += "tA:name ?attractionName; \n";
		queryString += "tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\" , \"i\"). }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("attractionName") ;
	      Literal latitude = soln.getLiteral("lat");
	      Literal longitude = soln.getLiteral("long");
	      Literal city = soln.getLiteral("cityName");
	      
	      OtherCulturalAttraction attraction = new OtherCulturalAttraction();
	      attraction.setName(name.toString());
	      attraction.setLatitude(latitude.getDouble());
	      attraction.setLongitude(longitude.getDouble());
	      attraction.setLocalityProximity(city.toString());
	      attractionsList.add(attraction);
	    }
		return attractionsList;
	}
	
	public List<OtherCulturalAttraction> getAllOthersCulturalAtrractionsAroundLocality(String localityName){
		List<OtherCulturalAttraction> attractionsList = new ArrayList<OtherCulturalAttraction>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString+="select * { \n { \n";
		
		//get proximity hotels
		queryString += "select ?attractionName ?lat ?long ?cityName where { \n";
		queryString += "?attraction rdf:type tA:OtherCulturalAttraction; \n tA:name ?attractionName; \n";
		queryString += " tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		
		queryString += "}\n UNION \n {\n";
		
		//get nearby hotels
		queryString += "select ?attractionName ?lat ?long ?cityName where { \n";
		queryString += "?attraction rdf:type tA:OtherCulturalAttraction; \n tA:name ?attractionName; \n";
		queryString += " tA:isContainedBy ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		queryString += "}\n}\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal name = soln.getLiteral("attractionName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      OtherCulturalAttraction attraction = new OtherCulturalAttraction();
		      attraction.setName(name.getString());
		      attraction.setLatitude(lat.getDouble());
		      attraction.setLongitude(lng.getDouble());
		      attraction.setLocalityProximity(c.toString());
		      
		      attractionsList.add(attraction);
		      System.out.println("Printez " + name.toString());
		    }
		    
		return attractionsList;
	}
	
	public List<Seaside> getSeasideNearByLocality(String localityName)
	{	
		List<Seaside> seasideList = new ArrayList<Seaside>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n";
		queryString += "select ?seasideName ?latid ?longit where { \n";
		queryString += "?seaside rdf:type tA:Seaside; \n tA:name ?seasideName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += "	tA:isContainedBy tA:"+localityName+". }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(queryString);
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("seasideName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Seaside seaside = new Seaside();
	      seaside.setName(name.toString());
	      seaside.setLatitude(latitude.getDouble());
	      seaside.setLongitude(longitude.getDouble());
	      seaside.setNearByLocality(localityName);
	      seasideList.add(seaside);
	    }
		return seasideList;
	}
	
	public List<Seaside> getSeasideInProximity(String localityName)
	{	
		List<Seaside> seasideList = new ArrayList<Seaside>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?seasideName ?latid ?longit where { \n";
		queryString += "?seaside rdf:type tA:Seaside; \n tA:name ?seasideName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += "	tA:inProximityOf tA:"+localityName+". }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("seasideName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Seaside seaside = new Seaside();
	      seaside.setName(name.toString());
	      seaside.setLatitude(latitude.getDouble());
	      seaside.setLongitude(longitude.getDouble());
	      seaside.setLocalityProximity(localityName);
	      seasideList.add(seaside);
	    }
		return seasideList;
	}
	
	public List<Mountain> getNearByMountains(String localityName)
	{	
		List<Mountain> mountainList = new ArrayList<Mountain>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?mountainName ?latid ?longit where { \n";
		queryString += "?mountain rdf:type tA:Mountain; \n tA:name ?mountainName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += "	tA:isIncludedBy tA:"+localityName+". }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("mountainName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Mountain mountain = new Mountain();
	      mountain.setName(name.toString());
	      mountain.setLatitude(latitude.getDouble());
	      mountain.setLongitude(longitude.getDouble());
	      mountain.setNearByLocality(localityName);
	      mountainList.add(mountain);
	    }
		return mountainList;
	}
	
	public List<Mountain> getMountainInProximity(String localityName)
	{	
		List<Mountain> mountainList = new ArrayList<Mountain>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?mountainName ?latid ?longit where { \n";
		queryString += "?mountain rdf:type tA:Mountain; \n tA:name ?mountainName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += "	tA:inProximityOf tA:"+localityName+". }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("mountainName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Mountain mountain = new Mountain();
	      mountain.setName(name.toString());
	      mountain.setLatitude(latitude.getDouble());
	      mountain.setLongitude(longitude.getDouble());
	      mountain.setLocalityProximity(localityName);
	      mountainList.add(mountain);
	    }
		return mountainList;
	}
	
	public List<Lake> getLakesNearByLocality(String localityName)
	{	
		List<Lake> lakeList = new ArrayList<Lake>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?lakeName ?latid ?longit where { \n";
		queryString += "?lake rdf:type tA:Lake; \n tA:name ?lakeName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += "	tA:isIncludedBy tA:"+localityName+". }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("lakeName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Lake lake = new Lake();
	      lake.setName(name.toString());
	      lake.setLatitude(latitude.getDouble());
	      lake.setLongitude(longitude.getDouble());
	      lake.setNearByLocality(localityName);
	      lakeList.add(lake);
	    }
		return lakeList;
	}
	
	public List<Lake> getLakeInProximity(String localityName)
	{	
		List<Lake> lakeList = new ArrayList<Lake>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?lakeName ?latid ?longit where { \n";
		queryString += "?lake rdf:type tA:Lake; \n tA:name ?lakeName; \n geo:lat ?latid; \n geo:long ?longit;\n";
		queryString += "	tA:inProximityOf tA:"+localityName+". }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("lakeName") ;
	      Literal longitude = soln.getLiteral("longit");
	      Literal latitude = soln.getLiteral("latid");
	      Lake lake = new Lake();
	      lake.setName(name.toString());
	      lake.setLatitude(latitude.getDouble());
	      lake.setLongitude(longitude.getDouble());
	      lake.setLocalityProximity(localityName);
	      lakeList.add(lake);
	    }
		return lakeList;
	}
	
	public List<OtherNaturalAttraction> getOtherNaturalAttractionsNearByLocality(String localityName)
	{
		List<OtherNaturalAttraction> attractionsList = new ArrayList<OtherNaturalAttraction>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?attractionName ?lat ?long ?cityName where { \n";
		queryString += "?attraction rdf:type tA:OtherNaturalAttraction; \n";
		queryString += "tA:name ?attractionName; \n";
		queryString += " tA:isContainedBy ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\" , \"i\"). }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("attractionName") ;
	      Literal longitude = soln.getLiteral("long");
	      Literal latitude = soln.getLiteral("lat");
	      Literal city = soln.getLiteral("cityName");
	      
	      OtherNaturalAttraction attraction = new OtherNaturalAttraction();
	      attraction.setName(name.toString());
	      attraction.setLatitude(latitude.getDouble());
	      attraction.setLongitude(longitude.getDouble());
	      attraction.setNearByLocality(city.toString());
	      attractionsList.add(attraction);
	    }
		return attractionsList;
	}
	
	public List<OtherNaturalAttraction> getOtherNaturalAttractionsInProximity(String localityName)
	{
		List<OtherNaturalAttraction> attractionsList = new ArrayList<OtherNaturalAttraction>();
		
		String queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n";
		queryString += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n ";
		queryString += "PREFIX tA: <http://www.example.com/touristAsist#> \n";
		queryString += "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?attractionName ?lat ?long ?cityName where { \n";
		queryString += "?attraction rdf:type tA:OtherNaturalAttraction; \n";
		queryString += "tA:name ?attractionName; \n";
		queryString += " tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\" , \"i\"). }\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		ResultSet results = qexec.execSelect() ;
	    System.out.println(results.getResultVars().toString());
	    for ( ; results.hasNext() ; )
	    {
	      QuerySolution soln = results.nextSolution() ;	     
	      Literal name = soln.getLiteral("attractionName") ;
	      Literal longitude = soln.getLiteral("long");
	      Literal latitude = soln.getLiteral("lat");
	      Literal city = soln.getLiteral("cityName");
	      
	      OtherNaturalAttraction attraction = new OtherNaturalAttraction();
	      attraction.setName(name.toString());
	      attraction.setLatitude(latitude.getDouble());
	      attraction.setLongitude(longitude.getDouble());
	      attraction.setLocalityProximity(city.toString());
	      attractionsList.add(attraction);
	    }
		return attractionsList;
	}
	
	public List<EntertainmentObjective> getEntertainmentObjectivesNearByLocality(String localityName){
		List<EntertainmentObjective> entertainmentsList = new ArrayList<EntertainmentObjective>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?objectiveName ?lat ?long ?cityName where { \n";
		queryString += "?objective rdf:type tA:EntertainmentObjective; \n tA:name ?objectiveName; \n";
		queryString += "tA:isContainedBy ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\" , \"i\"). }\n";
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal obj = soln.getLiteral("objectiveName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      EntertainmentObjective objective = new EntertainmentObjective();
		      objective.setName(obj.getString());
		      objective.setLatitude(lat.getDouble());
		      objective.setLongitude(lng.getDouble());
		      objective.setNearByLocality(c.toString());
		      
		      entertainmentsList.add(objective);
		      System.out.println("Printez " + obj.toString());
		    }
		    
		return entertainmentsList;
	}
	
	public List<EntertainmentObjective> getEntertainmentObjectivesInProximity(String localityName){
		List<EntertainmentObjective> entertainmentsList = new ArrayList<EntertainmentObjective>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString += "select ?objectiveName ?lat ?long ?cityName where { \n";
		queryString += "?objective rdf:type tA:EntertainmentObjective; \n tA:name ?objectiveName; \n";
		queryString += "tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\" , \"i\"). }\n";
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal obj = soln.getLiteral("objectiveName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      EntertainmentObjective objective = new EntertainmentObjective();
		      objective.setName(obj.getString());
		      objective.setLatitude(lat.getDouble());
		      objective.setLongitude(lng.getDouble());
		      objective.setLocalityProximity(c.toString());
		      
		      entertainmentsList.add(objective);
		      System.out.println("Printez " + obj.toString());
		    }
		    
		return entertainmentsList;
	}
	
	public List<EntertainmentObjective> getAllEntertainmentObjectivesAroundLocality(String localityName){
		List<EntertainmentObjective> entertainmentList = new ArrayList<EntertainmentObjective>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString+="PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n";
		queryString+="select * { \n { \n";
		
		//get proximity hotels
		queryString += "select ?attractionName ?lat ?long ?cityName where { \n";
		queryString += "?attraction rdf:type tA:EntertainmentObjective; \n tA:name ?attractionName; \n";
		queryString += " tA:inProximityOf ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		
		queryString += "}\n UNION \n {\n";
		
		//get nearby hotels
		queryString += "select ?attractionName ?lat ?long ?cityName where { \n";
		queryString += "?attraction rdf:type tA:EntertainmentObjective; \n tA:name ?attractionName; \n";
		queryString += " tA:isContainedBy ?city; \n";
		queryString += "geo:lat ?lat; \n";
		queryString += "geo:long ?long. \n ";
		queryString += "?city tA:name ?cityName. \n";
		queryString += "FILTER regex(?cityName, \""+ localityName + "\", \"i\"). }\n";
		queryString += "}\n}\n";
		
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal obj = soln.getLiteral("attractionName") ; 
		      Literal lat = soln.getLiteral("lat");
		      Literal lng = soln.getLiteral("long");
		      Literal c = soln.getLiteral("cityName") ;
		      
		      EntertainmentObjective objective = new EntertainmentObjective();
		      objective.setName(obj.getString());
		      objective.setLatitude(lat.getDouble());
		      objective.setLongitude(lng.getDouble());
		      objective.setLocalityProximity(c.toString());
		      
		      entertainmentList.add(objective);
		      System.out.println("Printez " + obj.toString());
		    }
		    
		return entertainmentList;
	}
	
	public List<Activity> getAllActivities(){
		List<Activity> activityList = new ArrayList<Activity>();
		
		String queryString = "";
		queryString+="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		queryString+="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		queryString+="PREFIX tA: <http://www.example.com/touristAsist#>\n";
		queryString += "select ?activityName ?specificObjective where { \n";
		queryString += "?activity rdf:type tA:Activity; \n tA:name ?activityName; \n";
		queryString += "tA:isSpecificTo ?specificObjectiv.\n }\n";
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		//((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

		ResultSet results = qexec.execSelect() ;
		    System.out.println(results.getResultVars().toString());
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		     
		      Literal activity = soln.getLiteral("activityName") ; 
		      Literal obj = soln.getLiteral("specificObjectiv");
		      Activity currentActivity = new Activity();
		      currentActivity.setActivityName(activity.getString());
		      activityList.add(currentActivity);
		    }
		    
		return activityList;
	}
	
}
