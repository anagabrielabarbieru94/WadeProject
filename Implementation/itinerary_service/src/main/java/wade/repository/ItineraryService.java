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
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Service;

import wade.model.Country;

@Service
public class ItineraryService {
	public List<Country> getAllCountries()
	{
		List<Country> availableCountries = new ArrayList<Country>();
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
	
		pss.setBaseUri("http://localhost:7200/repositories/towas");
		pss.setNsPrefix("owl","http://www.w3.org/2002/07/owl#");
		pss.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		pss.setNsPrefix("tA", "http://www.example.com/touristAsist#");
		String queryString = "";
		queryString += "select ?countryName where { \n";
		queryString += "?country rdf:type tA:Country; \n tA:name ?countryName . }";
		Model model = FileManager.get().loadModel("E:\\Facultate\\Master An 2\\TW\\Work\\WadeProject\\Implementation\\sparql_endpoint\\toWas.ttl");
		
		pss.setCommandText(queryString);
		System.out.println(pss.toString());
		
		  try (QueryExecution qexec = QueryExecutionFactory.create(pss.toString(),model)) {
			    ResultSet results = qexec.execSelect() ;
			    System.out.println(results.toString());
//			    for ( ; results.hasNext() ; )
//			    {
//			    System.out.println("intra    ");
//			      QuerySolution soln = results.nextSolution() ;
//			      //RDFNode x = soln.get("countryName") ;       
//			     // Resource r = soln.getResource("countryName") ; 
//			      Literal l = soln.getLiteral("countryName") ; 
//			      Country currentCountry = new Country();
//			      currentCountry.setName(l.toString());
//			      availableCountries.add(currentCountry);
//			      System.out.println("Printez " + l.toString());
//			    }
		  }
		 
		return availableCountries;
	}
}
