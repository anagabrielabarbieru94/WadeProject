from SPARQLWrapper import SPARQLWrapper

# We suppose that the graph is imported at the following location http://example.com/muser/
queryString = "PREFIX muser: <http://example.com/muser#>  PREFIX rdf: " \
              "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>  INSERT DATA{ GRAPH <http://example.com/muser> { " \
              "muser:HipHop rdf:type muser:MusicalGenre ; muser:relatedMusicalGenre muser:TripHop, muser:RnB .}} "

sparql = SPARQLWrapper("http://192.168.17.1:7200/repositories/test-muser/statements")
sparql.method = 'POST'
sparql.setQuery(queryString)
sparql.query()
