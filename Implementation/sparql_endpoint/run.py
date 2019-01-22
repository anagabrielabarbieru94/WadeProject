from SPARQLWrapper import SPARQLWrapper

queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
queryString += "{ 	tA:Germania rdf:type tA:Country ; \n"
queryString += "tA:name \'Germania\'; \n"
queryString += "tA:description \'Insorita\' .\n}\n}"

print(queryString)
sparql = SPARQLWrapper("http://192.168.1.52:7200/repositories/6666666/statements")
sparql.method = 'POST'
sparql.setQuery(queryString)
sparql.query()
