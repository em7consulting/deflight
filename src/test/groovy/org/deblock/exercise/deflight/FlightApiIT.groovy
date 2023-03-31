package org.deblock.exercise.deflight

import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class FlightApiIT extends IntegrationTest {

    def "should return flight search results"() {
        given:
        def requestBody = """{
            "origin": "LHR",
            "destination": "DBX",
            "departureDate": "2023-05-01",
            "returnDate": "2023-05-01",
            "numberOfPassengers": 4
        }"""

        when:
        ResponseEntity<String> response = restTemplate
                .exchange("http://localhost:$port/flights/search", HttpMethod.POST, new HttpEntity<String>(requestBody, headers), String.class);

        then:
        response.getStatusCode() == HttpStatus.OK
        JSONAssert.assertEquals("""[
          {
            "airline": "FlyDubai",
            "supplier": "CRAZY_AIR",
            "fare": {
              "amount": 205.99,
              "currency": "USD"
            },
            "departureAirportCode": "LHR",
            "destinationAirportCode": "DBX",
            "departureDate": "2023-05-01T08:30:00Z[Etc/UTC]",
            "arrivalDate": "2023-05-01T13:30:00+04:00[Asia/Dubai]"
          },
          {
            "airline": "FlyDubai",
            "supplier": "CRAZY_AIR",
            "fare": {
              "amount": 505.00,
              "currency": "USD"
            },
            "departureAirportCode": "LHR",
            "destinationAirportCode": "DBX",
            "departureDate": "2023-05-01T08:30:00Z[Etc/UTC]",
            "arrivalDate": "2023-05-01T17:30:00+04:00[Asia/Dubai]"
          }
        ]""", response.getBody(), true
        )
    }

    def "should return flight search results based on successful searches when some integrations fail"() {
        given:
        def requestBody = """{
            "origin": "KRK",
            "destination": "DBX",
            "departureDate": "2023-05-01",
            "returnDate": "2023-05-01",
            "numberOfPassengers": 4
        }"""

        when:
        ResponseEntity<String> response = restTemplate
                .exchange("http://localhost:$port/flights/search", HttpMethod.POST, new HttpEntity<String>(requestBody, headers), String.class);

        then:
        response.getStatusCode() == HttpStatus.OK
        JSONAssert.assertEquals("""[
          {
            "airline": "FlyDubai",
            "supplier": "CRAZY_AIR",
            "fare": {
              "amount": 505.00,
              "currency": "USD"
            },
            "departureAirportCode": "KRK",
            "destinationAirportCode": "DBX",
            "departureDate": "2023-05-01T08:30:00Z[UTC]",
            "arrivalDate": "2023-05-01T17:30:00+04:00[Asia/Dubai]"
          }
        ]""", response.getBody(), true
        )
    }

    def "should return BAD_REQUEST when request incorrect"() {
        given:
        def requestBody = """{
            "origin": "TOO_LONG",
            "destination": "DBX",
            "departureDate": "2023-05-01",
            "returnDate": "2023-05-01",
            "numberOfPassengers": 5
        }"""

        when:
        ResponseEntity<String> response = restTemplate
                .exchange("http://localhost:$port/flights/search", HttpMethod.POST, new HttpEntity<String>(requestBody, headers), String.class);

        then:
        response.getStatusCode() == HttpStatus.BAD_REQUEST
        JSONAssert.assertEquals("""{"description":"origin length must be equal to 3"}""", response.getBody(), true
        )
    }


}
