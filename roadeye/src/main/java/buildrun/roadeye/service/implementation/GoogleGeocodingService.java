package buildrun.roadeye.service.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleGeocodingService {
    @Value("${google.geocoding.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;
    public GoogleGeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String getCoordinates(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;
        return restTemplate.getForObject(url, String.class);
    }
}
