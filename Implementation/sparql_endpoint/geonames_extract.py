import geocoder
from SPARQLWrapper import SPARQLWrapper

sparqlEndpoint = "http://192.168.0.102:7200/repositories/towas/statements"


def getGeoNamesHotels(city, countryCode):
    print("\nHotels")
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    state = ""
    for r in g:
        state = r.state
        break

    g = geocoder.geonames(city, maxRows=100, country=[countryCode], key='gabibarbieru', featureCode='HTL')
    nearbyHotels = [r for r in g]

    addresses = [r.address for r in nearbyHotels]

    g = geocoder.geonames(state, maxRows=100, country=[countryCode], key='gabibarbieru', featureCode='HTL')
    proximityHotels = [r for r in g if r.address not in addresses]
    print(proximityHotels)

    for hotel in nearbyHotels:
        hotelName = hotel.address
        hotelNameOriginal = hotelName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        hotelName = hotelNameOriginal.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(hotelName)
        hotelLat = hotel.lat
        hotelLong = hotel.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + hotelName + " rdf:type tA:Accomodation ; \n"
        queryString += "tA:name \"" + hotelNameOriginal + "\"; \n"
        queryString += "geo:lat " + hotelLat + " ;\n"
        queryString += "geo:long " + hotelLong + ";"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + hotelName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for hotel in proximityHotels:
        hotelName = hotel.address
        hotelNameOriginal = hotelName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        hotelName = hotelNameOriginal.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "") \
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "") \
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(hotelName)
        hotelLat = hotel.lat
        hotelLong = hotel.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + hotelName + " rdf:type tA:Accomodation ; \n"
        queryString += "tA:name \"" + hotelNameOriginal + "\"; \n"
        queryString += "geo:lat " + hotelLat + " ;\n"
        queryString += "geo:long " + hotelLong + ";"
        queryString += "tA:inProximityOf tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" + city.replace(" ", "_") + " tA:addiacentTo tA:" + hotelName + ".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()


def getGeoNamesRestaurants(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nRestaurants")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru', featureCode='REST')
    nearbyRestaurants = [r for r in g]

    addresses = [r.address for r in nearbyRestaurants]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru', featureCode='REST')
    proximityRestaurants = [r for r in g if r.address not in addresses]

    for restaurant in nearbyRestaurants:
        restaurantName = restaurant.address
        restaurantOriginalName = restaurantName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        restaurantName = restaurantOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(restaurantName)
        restaurantLat = restaurant.lat
        restaurantLong = restaurant.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + restaurantName + " rdf:type tA:Restaurant ; \n"
        queryString += "tA:name \"" + restaurantOriginalName + "\"; \n"
        queryString += "geo:lat " + restaurantLat + " ;\n"
        queryString += "geo:long " + restaurantLong + ";\n"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + restaurantName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for restaurant in proximityRestaurants:
        restaurantName = restaurant.address
        restaurantOriginalName = restaurantName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        restaurantName = restaurantOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(restaurantName)
        restaurantLat = restaurant.lat
        restaurantLong = restaurant.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + restaurantName + " rdf:type tA:Restaurant ; \n"
        queryString += "tA:name \"" + restaurantOriginalName + "\"; \n"
        queryString += "geo:lat " + restaurantLat + " ;\n"
        queryString += "geo:long " + restaurantLong + ";\n"
        queryString += "tA:inProximityOf tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:addiacentTo tA:" + restaurantName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

def getGeoNamesMuseums(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nMuseums")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru', featureCode='MUS')
    nearbyMuseums = [r for r in g]

    addresses = [r.address for r in nearbyMuseums]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru', featureCode='MUS')
    proximityMuseums = [r for r in g if r.address not in addresses]

    for museum in nearbyMuseums:
        museumName = museum.address
        museumOriginalName = museumName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        museumName = museumOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(museumName)
        museumLat = museum.lat
        museumLong = museum.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + museumName + " rdf:type tA:Museum ; \n"
        queryString += "tA:name \"" + museumOriginalName + "\"; \n"
        queryString += "geo:lat " + museumLat + " ;\n"
        queryString += "geo:long " + museumLong + ";\n"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + museumName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for museum in proximityMuseums:
        museumName = museum.address
        museumOriginalName = museumName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        museumName = museumOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(museumName)
        museumLat = museum.lat
        museumLong = museum.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + museumName + " rdf:type tA:Museum ; \n"
        queryString += "tA:name \"" + museumOriginalName + "\"; \n"
        queryString += "geo:lat " + museumLat + " ;\n"
        queryString += "geo:long " + museumLong + ";\n"
        queryString += "tA:inProximityOf tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:addiacentTo tA:" + museumName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()


def getGeoNamesMonuments(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='valexandru')
    for r in g:
        state = r.state
        break

    print("\nMonuments")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='valexandru',
                          featureCode=['MNMT', 'PYR', 'CSTL', 'PAL'])
    nearbyMonuments = [r for r in g]
    print(nearbyMonuments)

    addresses = [r.address for r in nearbyMonuments]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='valexandru',
                          featureCode=['MNMT', 'PYR', 'CSTL', 'PAL'])
    proximityMonuments = [r for r in g if r.address not in addresses]
    print(proximityMonuments)

    for monument in nearbyMonuments:
        monumentName = monument.address
        monumentOriginalName = monumentName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        monumentName = monumentOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "")

        print(monumentName)
        monumentLat = monument.lat
        monumentLong = monument.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{   tA:" + monumentName + " rdf:type tA:OtherCulturalAttraction ; \n"
        queryString += "tA:name \"" + monumentOriginalName + "\"; \n"
        queryString += "geo:lat " + monumentLat + " ;\n"
        queryString += "geo:long " + monumentLong + ";\n"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + monumentName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for monument in proximityMonuments:
        monumentName = monument.address
        monumentOriginalName = monumentName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        monumentName = monumentOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "")

        print(monumentName)
        monumentLat = monument.lat
        monumentLong = monument.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{   tA:" + monumentName + " rdf:type tA:OtherCulturalAttraction ; \n"
        queryString += "tA:name \"" + monumentOriginalName + "\"; \n"
        queryString += "geo:lat " + monumentLat + " ;\n"
        queryString += "geo:long " + monumentLong + ";\n"
        queryString += "tA:inProximityOf tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:addiacentTo tA:" + monumentName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()


def getGeoNamesChurches(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='valexandru')
    for r in g:
        state = r.state
        break

    print("\nChurches")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='valexandru',
                          featureCode=['CH', 'MSTY', 'MSQE', 'TMPL'])
    nearbyChurches = [r for r in g]
    print(nearbyChurches)

    addresses = [r.address for r in nearbyChurches]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='valexandru',
                          featureCode=['CH', 'MSTY', 'MSQE', 'TMPL'])
    proximityChurches = [r for r in g if r.address not in addresses]

    print(proximityChurches)
    for church in nearbyChurches:
        churchName = church.address
        churchOriginalName = churchName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "") \
            .replace(u"´", " ").replace("\"", "")
        churchName = churchOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "") \
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "") \
            .replace(":", "").replace("-", "")

        print(churchName)
        churchLat = church.lat
        churchLong = church.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{   tA:" + churchName + " rdf:type tA:OtherCulturalAttraction ; \n"
        queryString += "tA:name \"" + churchOriginalName + "\"; \n"
        queryString += "geo:lat " + churchLat + " ;\n"
        queryString += "geo:long " + churchLong + ";\n"
        queryString += "tA:isContainedBy tA:" + city.replace(" ", "_") + ".\n"
        queryString += "tA:" + city.replace(" ", "_") + " tA:isNearBy tA:" + churchName + ".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for church in proximityChurches:
        churchName = church.address
        churchOriginalName = churchName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "") \
            .replace(u"´", " ").replace("\"", "")
        churchName = churchOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "") \
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "") \
            .replace(":", "").replace("-", "")

        #print(churchName)
        churchLat = church.lat
        churchLong = church.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{   tA:" + churchName + " rdf:type tA:OtherCulturalAttraction ; \n"
        queryString += "tA:name \"" + churchOriginalName + "\"; \n"
        queryString += "geo:lat " + churchLat + " ;\n"
        queryString += "geo:long " + churchLong + ";\n"
        queryString += "tA:inProximityOf tA:" + city.replace(" ", "_") + ".\n"
        queryString += "tA:" + city.replace(" ", "_") + " tA:addiacentTo tA:" + churchName + ".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()


def getGeoNamesMountains(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nMountains")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru', featureCode=['MT', 'MTS', 'PK', 'PKS'])
    nearbyMountains = [r for r in g]
    print(nearbyMountains)

    addresses = [r.address for r in nearbyMountains]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['MT', 'MTS', 'PK', 'PKS'])
    proximityMountains = [r for r in g if r.address not in addresses]
    print(proximityMountains)
    for Mountain in nearbyMountains:
        MountainName = Mountain.address
        MountainOriginalName = MountainName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "") \
            .replace(u"´", " ").replace("\"", "")
        MountainName = MountainOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "") \
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "") \
            .replace(":", "").replace("-", "")

        print(MountainName)
        MountainLat = Mountain.lat
        MountainLong = Mountain.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{   tA:" + MountainName + " rdf:type tA:OtherCulturalAttraction ; \n"
        queryString += "tA:name \"" + MountainOriginalName + "\"; \n"
        queryString += "geo:lat " + MountainLat + " ;\n"
        queryString += "geo:long " + MountainLong + ";\n"
        queryString += "tA:isContainedBy tA:" + city.replace(" ", "_") + ".\n"
        queryString += "tA:" + city.replace(" ", "_") + " tA:isNearBy tA:" + MountainName + ".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for Mountain in proximityMountains:
        MountainName = Mountain.address
        MountainOriginalName = MountainName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "") \
            .replace(u"´", " ").replace("\"", "")
        MountainName = MountainOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "") \
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "") \
            .replace(":", "").replace("-", "")

        print(MountainName)
        MountainLat = Mountain.lat
        MountainLong = Mountain.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{   tA:" + MountainName + " rdf:type tA:OtherCulturalAttraction ; \n"
        queryString += "tA:name \"" + MountainOriginalName + "\"; \n"
        queryString += "geo:lat " + MountainLat + " ;\n"
        queryString += "geo:long " + MountainLong + ";\n"
        queryString += "tA:inProximityOf tA:" + city.replace(" ", "_") + ".\n"
        queryString += "tA:" + city.replace(" ", "_") + " tA:addiacentTo tA:" + MountainName + ".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()


def getGeoNamesBeaches(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nBeaches")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['BCH', 'BCHS', 'CAPE', 'SEA'])
    nearbyBeaches = [r for r in g]
    print(nearbyBeaches)

    addresses = [r.address for r in nearbyBeaches]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['BCH', 'BCHS', 'CAPE', 'SEA'])
    proximityBeaches = [r for r in g if r.address not in addresses]
    print(proximityBeaches)

    for beach in nearbyBeaches:
        beachName = beach.address
        beachNameOriginal = beachName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        beachName = beachNameOriginal.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "")

        print(beachName)
        beachLat = beach.lat
        beachLong = beach.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{   tA:" + beachName + " rdf:type tA:Accomodation ; \n"
        queryString += "tA:name \"" + beachNameOriginal + "\"; \n"
        queryString += "geo:lat " + beachLat + " ;\n"
        queryString += "geo:long " + beachLong + ";"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + beachName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for beach in proximityBeaches:
        beachName = beach.address
        beachNameOriginal = beachName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        beachName = beachNameOriginal.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "") \
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "") \
            .replace(":", "").replace("-", "")

        print(beachName)
        beachLat = beach.lat
        beachLong = beach.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{   tA:" + beachName + " rdf:type tA:Accomodation ; \n"
        queryString += "tA:name \"" + beachNameOriginal + "\"; \n"
        queryString += "geo:lat " + beachLat + " ;\n"
        queryString += "geo:long " + beachLong + ";"
        queryString += "tA:inProximityOf tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" + city.replace(" ", "_") + " tA:addiacentTo tA:" + beachName + ".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

def getGeoNamesLakes(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nLakes")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['LK', 'LKC', 'LKN', 'LKS', 'LKI', 'LKSC', 'LKX', 'RGNL'])
    nearbyLakes = [r for r in g]

    addresses = [r.address for r in nearbyLakes]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['LK', 'LKC', 'LKN', 'LKS', 'LKI', 'LKSC', 'LKX', 'RGNL'])
    proximityLakes = [r for r in g if r.address not in addresses]

    for lake in nearbyLakes:
        lakeName = lake.address
        lakeOriginalName = lakeName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        lakeName = lakeOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(lakeName)
        lakeLat = lake.lat
        lakeLong = lake.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + lakeName + " rdf:type tA:Lake ; \n"
        queryString += "tA:name \"" + lakeOriginalName + "\"; \n"
        queryString += "geo:lat " + lakeLat + " ;\n"
        queryString += "geo:long " + lakeLong + ";\n"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + lakeName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for lake in proximityLakes:
        lakeName = lake.address
        lakeOriginalName = lakeName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        lakeName = lakeOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(lakeName)
        lakeLat = lake.lat
        lakeLong = lake.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + lakeName + " rdf:type tA:Lake ; \n"
        queryString += "tA:name \"" + lakeOriginalName + "\"; \n"
        queryString += "geo:lat " + lakeLat + " ;\n"
        queryString += "geo:long " + lakeLong + ";\n"
        queryString += "tA:inProximityOf tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:addiacentTo tA:" + lakeName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

def getGeoNamesForests(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nForests")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['FRST', 'FRSTF', 'RESF'])
    nearbyForests = [r for r in g]

    addresses = [r.address for r in nearbyForests]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['FRST', 'FRSTF', 'RESF'])
    proximityForests = [r for r in g if r.address not in addresses]

    for forest in nearbyForests:
        forestName = forest.address
        forestOriginalName = forestName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        forestName = forestOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(forestName)
        forestLat = forest.lat
        forestLng = forest.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + forestName + " rdf:type tA:OtherNaturalAttraction ; \n"
        queryString += "tA:name \"" + forestOriginalName + "\"; \n"
        queryString += "geo:lat " + forestLat + " ;\n"
        queryString += "geo:long " + forestLng + ";\n"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + forestName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for forest in proximityForests:
        forestName = forest.address
        forestOriginalName = forestName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        forestName = forestOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(forestName)
        forestLat = forest.lat
        forestLng = forest.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + forestName + " rdf:type tA:OtherNaturalAttraction ; \n"
        queryString += "tA:name \"" + forestOriginalName + "\"; \n"
        queryString += "geo:lat " + forestLat + " ;\n"
        queryString += "geo:long " + forestLng + ";\n"
        queryString += "tA:inProximityOf tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:addiacentTo tA:" + forestName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

def getGeoNamesReservations(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nNatural reservations")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['RESN', 'RESV', 'RESW', 'RES', 'PRK'])
    nearbyReservations = [r for r in g]

    addresses = [r.address for r in nearbyReservations]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['RESN', 'RESV', 'RESW', 'RES', 'PRK'])
    proximityReservations = [r for r in g if r.address not in addresses]

    for reservation in nearbyReservations:
        reservationName = reservation.address
        reservationOriginalName = reservationName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        reservationName = reservationOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(reservationName)
        reservationLat = reservation.lat
        reservationLong = reservation.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + reservationName + " rdf:type tA:OtherNaturalAttraction ; \n"
        queryString += "tA:name \"" + reservationOriginalName + "\"; \n"
        queryString += "geo:lat " + reservationLat + " ;\n"
        queryString += "geo:long " + reservationLong + ";\n"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + reservationName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for reservation in proximityReservations:
        reservationName = reservation.address
        reservationOriginalName = reservationName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        reservationName = reservationOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(reservationName)
        reservationLat = reservation.lat
        reservationLong = reservation.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + reservationName + " rdf:type tA:OtherNaturalAttraction ; \n"
        queryString += "tA:name \"" + reservationOriginalName + "\"; \n"
        queryString += "geo:lat " + reservationLat + " ;\n"
        queryString += "geo:long " + reservationLong + ";\n"
        queryString += "tA:inProximityOf tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:addiacentTo tA:" + reservationName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

def getGeoNamesEntertainment(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nEntertainment")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['AMUS', 'RESV', 'MALL', 'RSRT', 'POOL']) #temathic parks, stadiums, malls
    nearbyEntertainment = [r for r in g]

    addresses = [r.address for r in nearbyEntertainment]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['AMUS', 'RESV', 'MALL', 'RSRT', 'POOL'])
    proximityEntertainment = [r for r in g if r.address not in addresses]

    for entertainment in nearbyEntertainment:
        entertainmentName = entertainment.address
        entertainmentOriginalName = entertainmentName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        entertainmentName = entertainmentOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(entertainmentName)
        entertainmentLat = entertainment.lat
        entertainmentLong = entertainment.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + entertainmentName + " rdf:type tA:EntertainmentObjective ; \n"
        queryString += "tA:name \"" + entertainmentOriginalName + "\"; \n"
        queryString += "geo:lat " + entertainmentLat + " ;\n"
        queryString += "geo:long " + entertainmentLong + ";\n"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + entertainmentName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for entertainment in proximityEntertainment:
        entertainmentName = entertainment.address
        entertainmentOriginalName = entertainmentName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")\
            .replace(u"´", " ").replace("\"", "")
        entertainmentName = entertainmentOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "_").replace("–", "_")

        print(entertainmentName)
        entertainmentLat = entertainment.lat
        entertainmentLong = entertainment.lng

        queryString = "prefix tA: <http://www.example.com/touristAsist#> \n"
        queryString += "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n "
        queryString += "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
        queryString += "INSERT DATA { GRAPH <http://example.com/touristAsist> \n"
        queryString += "{ 	tA:" + entertainmentName + " rdf:type tA:EntertainmentObjective ; \n"
        queryString += "tA:name \"" + entertainmentOriginalName + "\"; \n"
        queryString += "geo:lat " + entertainmentLat + " ;\n"
        queryString += "geo:long " + entertainmentLong + ";\n"
        queryString += "tA:inProximityOf tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:addiacentTo tA:" + entertainmentName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()



# getGeoNamesHotels("Dubai", '')
# getGeoNamesRestaurants("Iași", 'RO')
#
# getGeoNamesMuseums("Venice", 'IT')
# getGeoNamesTheaters("Milan", 'IT')
# getGeoNamesMonuments("Sibiu", 'RO')
# getGeoNamesChurches("Iași", 'RO')
#
# getGeoNamesMountains('Suceava', 'RO')
# getGeoNamesBeaches("Crete", 'GR')
# getGeoNamesLakes("Roma", "IT")
# getGeoNamesForests("Torino", 'IT')
# getGeoNamesReservations("Roma", "IT")
# getGeoNamesEntertainment("Constanța", "RO")