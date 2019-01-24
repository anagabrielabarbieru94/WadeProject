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
        hotelNameOriginal = hotelName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")
        hotelName = hotelNameOriginal.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "")

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
        hotelNameOriginal = hotelName.replace(u"’", "").replace(u"'", "")
        hotelName = hotelNameOriginal.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "") \
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "") \
            .replace(":", "").replace("-", "")

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
        restaurantOriginalName = restaurantName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")
        restaurantName = restaurantOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "")

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
        queryString += "geo:long " + restaurantLong + ";"
        queryString += "tA:isContainedBy tA:"+city.replace(" ", "_") + ".\n"
        queryString += "tA:" +city.replace(" ", "_")+ " tA:isNearBy tA:" + restaurantName +".}\n}"

        print(queryString)
        sparql = SPARQLWrapper(sparqlEndpoint)
        sparql.method = 'POST'
        sparql.setQuery(queryString)
        sparql.query()

    for restaurant in proximityRestaurants:
        restaurantName = restaurant.address
        restaurantOriginalName = restaurantName.replace(u"’", "").replace(u"'", "").replace(u"‘", "").replace(u"`", "")
        restaurantName = restaurantOriginalName.replace(" ", "_").replace(",", "").replace("&", "").replace("*", "")\
            .replace("!", "").replace("/", "").replace("?", "").replace("(", "").replace(")", "").replace(".", "")\
            .replace(":", "").replace("-", "")

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
        queryString += "geo:long " + restaurantLong + ";"
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
    print(nearbyMuseums)

    addresses = [r.address for r in nearbyMuseums]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru', featureCode='MUS')
    proximityMuseums = [r for r in g if r.address not in addresses]
    print(proximityMuseums)

def getGeoNamesMonuments(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nMonuments")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['MNMT', 'PYR', 'CSTL', 'PAL'])
    nearbyMonuments = [r for r in g]
    print(nearbyMonuments)

    addresses = [r.address for r in nearbyMonuments]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['MNMT', 'PYR', 'CSTL', 'PAL'])
    proximityMonuments = [r for r in g if r.address not in addresses]
    print(proximityMonuments)

def getGeoNamesTheaters(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    state = ""
    for r in g:
        state = r.state
        break

    print("\nTheaters")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru', featureCode=['THTR','OPRA'])
    nearbyTheaters = [r for r in g]
    print(nearbyTheaters)

    addresses = [r.address for r in nearbyTheaters]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru', featureCode=['THTR','OPRA'])
    proximityTheaters = [r for r in g if r.address not in addresses]
    print(proximityTheaters)


def getGeoNamesChurches(city, countryCode):
    state = ""
    g = geocoder.geonames(city, country=[countryCode], key='gabibarbieru')
    for r in g:
        state = r.state
        break

    print("\nChurches")
    g = geocoder.geonames(city, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['CH', 'MSTY', 'MSQE', 'TMPL'])
    nearbyChurches = [r for r in g]
    print(nearbyChurches)

    addresses = [r.address for r in nearbyChurches]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['CH', 'MSTY', 'MSQE', 'TMPL'])
    proximityChurches = [r for r in g if r.address not in addresses]
    print(proximityChurches)

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
    print(nearbyLakes)

    addresses = [r.address for r in nearbyLakes]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['LK', 'LKC', 'LKN', 'LKS', 'LKI', 'LKSC', 'LKX', 'RGNL'])
    proximityLakes = [r for r in g if r.address not in addresses]
    print(proximityLakes)

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
    print(nearbyForests)

    addresses = [r.address for r in nearbyForests]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['FRST', 'FRSTF', 'RESF'])
    proximityForests = [r for r in g if r.address not in addresses]
    print(proximityForests)

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
    print(nearbyReservations)

    addresses = [r.address for r in nearbyReservations]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['RESN', 'RESV', 'RESW', 'RES', 'PRK'])
    proximityReservations = [r for r in g if r.address not in addresses]
    print(proximityReservations)

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
    print(nearbyEntertainment)

    addresses = [r.address for r in nearbyEntertainment]

    g = geocoder.geonames(state, maxRows=1000, country=[countryCode], key='gabibarbieru',
                          featureCode=['AMUS', 'RESV', 'MALL', 'RSRT', 'POOL'])
    proximityEntertainment = [r for r in g if r.address not in addresses]
    print(proximityEntertainment)


# getGeoNamesHotels("Dubai", '')
# getGeoNamesRestaurants("Iași", 'RO')
#
# getGeoNamesMuseums("Paris", 'FR')
# getGeoNamesTheaters("Milan", 'IT')
# getGeoNamesMonuments("Piatra Neamt", 'RO')
# getGeoNamesChurches("Istanbul", '')
#
# getGeoNamesMountains('Suceava', 'RO')
# getGeoNamesBeaches("Athens", 'GR')
# getGeoNamesLakes("Zurich", "CH")
# getGeoNamesForests("Paris", 'FR')
# getGeoNamesReservations("yellowstone", "US")
# getGeoNamesEntertainment("Paris", "FR")