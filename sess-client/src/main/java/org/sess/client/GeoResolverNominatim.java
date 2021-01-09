package org.sess.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.sess.client.pojo.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class GeoResolverNominatim implements GeoResolver {

    private final String nominatim;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public GeoResolverNominatim(@Value("${geo.nominatim.url}") String nominatim, RestTemplate restTemplate, ObjectMapper mapper) {
        this.nominatim = nominatim;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    @Override
    public City resolveCity(String langCode, float lat, float lon) {
        var headers = new HttpHeaders();
        if (langCode == null || langCode.isEmpty()) {
            headers.set("Accept-Language", "en");
        } else {
            headers.set("Accept-Language", langCode);
        }
        var entity = new HttpEntity(headers);
        var response = restTemplate.exchange(UriComponentsBuilder
                .fromUriString(nominatim)
                .path("/reverse")
                .queryParam("format", "jsonv2")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("zoom", 10)
                .queryParam("addressdetails", 1)
                .build().toUri(), HttpMethod.GET, entity, String.class);
        return parse(response.getBody());
    }

    private City parse(String body) {
        try {
            var place = mapper.readTree(body);
            long cityId = place.get("osm_id").asLong();
            String address = place.get("name").asText();
            return new City(cityId, address);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }
}
