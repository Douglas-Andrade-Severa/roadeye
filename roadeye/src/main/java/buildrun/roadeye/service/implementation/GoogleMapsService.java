package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.RouteRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService {
    @Value("${google.geocoding.api.key}")
    private String apiKey;

    public ResponseEntity<String> computeRoutes(RouteRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Goog-Api-Key", apiKey);
        headers.set("X-Goog-FieldMask", "routes.optimizedIntermediateWaypointIndex");

        HttpEntity<RouteRequest> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange("https://routes.googleapis.com/directions/v2:computeRoutes",HttpMethod.POST, entity, String.class);
    }
}
