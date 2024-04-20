package buildrun.roadeye.rest.dto.service.implementation;

import buildrun.roadeye.domain.entity.GeolocationResponse;
import buildrun.roadeye.rest.dto.GeolocationDto;
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

    public GeolocationDto getGeolocation(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;
        GeolocationResponse response = restTemplate.getForObject(url, GeolocationResponse.class);
        if (response != null && response.getStatus().equals("OK") && response.getResults().size() > 0) {
            GeolocationResponse.Result result = response.getResults().get(0);
            GeolocationResponse.Geometry geometry = result.getGeometry();
            GeolocationResponse.Location location = geometry.getLocation();
            return new GeolocationDto(location.getLat(), location.getLng());
        } else {
            return null;
        }
    }
}
