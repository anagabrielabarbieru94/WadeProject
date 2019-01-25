#!/usr/bin/env python
# -*- coding: utf-8 -*-

from SPARQLWrapper import SPARQLWrapper, JSON
import geocoder


def populateCountries():
    with open("countries.txt") as f:
        content = f.readlines()
        content = [line.rstrip('\n') for line in content]

    with open("countryCodes.txt") as f:
        contentCodes = f.readlines()
        contentCodes = [line.rstrip('\n') for line in contentCodes]

    index = 0
    for resource in content:
        resourceCode = contentCodes[index]
        index += 1
        print(resource)
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
        queryString += "{   tA:" + countryName.replace(" ", "_") + " rdf:type tA:Country ; \n"
        queryString += "    tA:hasCode  \'"+ resourceCode +"\'; \n" 
        queryString += "tA:name \""+ countryName+"\"; \n"
        queryString += "tA:description \"" + abstract.replace("\"", "")+ "\" .\n}\n}"

        sparql = SPARQLWrapper("http://localhost:7200/repositories/towas/statements")
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

def getDescByCityDBpedia(cityName):
    print("Pentru orasul" + cityName)
    query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
    query += "prefix dbo: <http://dbpedia.org/ontology/>"
    query += "prefix dbp: <http://dbpedia.org/property/>"
    query += "prefix dbr: <http://dbpedia.org/resource/>"
    query += "select ?abstract where {dbr:"+cityName+" dbo:abstract ?abstract.\n"
    query += "FILTER (lang(?abstract) = 'en')}\n"

    dbpedia = SPARQLWrapper("http://dbpedia.org/sparql")
    dbpedia.setQuery(query)
    dbpedia.setReturnFormat(JSON)
    results = dbpedia.query().convert()

    for result in results["results"]["bindings"]:
        return result["abstract"]["value"]
pass

def populateNonCapitalCities(countryCodesPathFile):
    with open(countryCodesPathFile) as f:
        content = f.readlines()
        content = [x.strip() for x in content]

    g = geocoder.geonames(location= "", maxRows = 1000, country=content, featureCode = 'PPLA', key = 'valexandru')
    count = 0
    for row in g:

        cityName = row.address
        cityNameOriginal = cityName.replace(u"’", "").replace("'", "").replace(u"‘", "").replace(u"`", "")
        cityName = cityNameOriginal.replace(" ","_")
        cityLatitude = row.lat
        cityLongitude = row.lng
        countryName = row.country

        cityDescription = None
        cityDescription = getDescByCityDBpedia(cityName.replace(" ","_"))

        count += 1
        # print("Tara " + countryName)
        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo:<http://www.opengis.net/ont/geosparql#> \n "
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{    tA:" + cityName + " rdf:type tA:Locality; \n"
        queryString += " tA:name \'" + cityNameOriginal + "\'; \n"
        queryString += "tA:isIncludedBy tA:" + countryName.replace(" ","_") +";\n"
        if cityDescription is not None:
            cityDescription = cityDescription.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
                .replace(u"´", " ").replace("\"", " ").replace("\n", " ")
            queryString += "tA:description \""+ cityDescription + "\"; \n"
        queryString += "geo:lat " + cityLatitude + "; \n"
        queryString += "geo:long  " + cityLongitude + ". \n } \n }"
        print(queryString)
        print(count)

        sparql = SPARQLWrapper("http://localhost:7200/repositories/towas/statements")
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()       


def populateCapitalCities(countryCodesPathFile):
    with open(countryCodesPathFile) as f:
        content = f.readlines()
        content = [x.strip() for x in content]

    #for countryCode in content:
    g = geocoder.geonames(location= "", maxRows = 1000, country=content, featureCode = 'PPLC', key = 'valexandru')

    for row in g:
        #print((row.geonames_id, row.address, row.country, row.state))
        cityName = row.address
        cityLatitude = row.lat
        cityLongitude = row.lng
        countryName = row.country

        cityDescription = None
        cityDescription = getDescByCityDBpedia(cityName.replace(" ","_"))
        print("Pun "+cityName)
        print("Tara " + countryName)

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo:<http://www.opengis.net/ont/geosparql#> \n "
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{    tA:" + cityName.replace(" ","_") + " rdf:type tA:Locality; \n"
        queryString += "geo:lat " + cityLatitude + "; \n"
        queryString += "geo:long " + cityLongitude + "; \n"
        queryString += " tA:name \'" + cityName + "\'; \n"
        queryString += "tA:isIncludedBy tA:" + countryName.replace(" ","_") +";\n"
        if cityDescription is not None:
            cityDescription = cityDescription.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
                .replace(u"´", " ").replace("\"", " ").replace("\n", " ")
            queryString += "tA:description \"" + cityDescription + "\"; \n"
        queryString += "tA:isCapitalOf tA:" + countryName.replace(" ","_") +"; \n" + ".\n} \n }"

        sparql = SPARQLWrapper("http://localhost:7200/repositories/towas/statements")
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()
 

#populateCountries()
#populateCapitalCities("D:\\Facultate\\Dezv.Aplic.Web\\WadeProject\\Implementation\\sparql_endpoint\\countryCodes.txt")
populateNonCapitalCities("D:\\Facultate\\Dezv.Aplic.Web\\WadeProject\\Implementation\\sparql_endpoint\\countryCodes.txt")