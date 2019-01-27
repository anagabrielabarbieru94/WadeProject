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

import wade.model.Country;
import wade.model.Lake;
import wade.model.Locality;
import wade.model.Mountain;
import wade.model.Seaside;

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
		
		((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		
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
		queryString += "FILTER regex(?countryName, "+ countryName + ", \"i\"). }\n";
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://localhost:7200/repositories/towas", query);
		
		((QueryEngineHTTP)qexec).addParam("timeout", "10000");
		

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
	
}
