from SPARQLWrapper import SPARQLWrapper, JSON

def populateCountries():
    with open("countries.txt") as f:
        content = f.readlines()
        content = [line.rstrip('\n') for line in content]
        for resource in content:
            dbpedia = SPARQLWrapper("http://dbpedia.org/sparql")
            dbpedia.setQuery("""
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX dbo: <http://dbpedia.org/ontology/>
                SELECT ?label ?abstract ?capitalName ?currencyName
                WHERE { <""" + resource + """> dbo:abstract ?abstract; \n
                    rdfs:label ?label; \n
                    dbo:capital ?capital; \n
                    dbo:currency ?currency. \n
                    OPTIONAL { ?capital rdfs:label ?capitalName . } \n
                    OPTIONAL { ?currency rdfs:label ?currencyName . } \n
                    FILTER (lang(?label) = 'en')\n
                    FILTER (lang(?abstract) = 'en')\n
                    FILTER (lang(?capitalName) = 'en') \n
                    FILTER (lang(?currencyName) = 'en')
                }
            """)

            dbpedia.setReturnFormat(JSON)
            results = dbpedia.query().convert()

            for result in results["results"]["bindings"]:
                countryName = result["label"]["value"]
                abstract = result["abstract"]["value"]
                capital = result["capitalName"]["value"]
                currency = result["currencyName"]["value"]

            queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
            queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
            queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
            queryString += "{ 	tA:" + countryName.replace(" ", "_") + " rdf:type tA:Country ; \n"
            queryString += "tA:name \""+ countryName+"\"; \n"
            queryString += "tA:description \"" + abstract.replace("\"", "")+ "\" .\n}\n}"

            print(queryString)
            sparql = SPARQLWrapper("http://192.168.0.102:7200/repositories/towas/statements")
            sparql.method = 'POST'
            sparql.setQuery(queryString)
            sparql.query()

populateCountries()