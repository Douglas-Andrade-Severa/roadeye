package buildrun.roadeye.rest.controller;

import buildrun.roadeye.rest.dto.GeocodingRequest;
import buildrun.roadeye.service.implementation.GoogleGeocodingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geocode")
public class GeocodingController {
    private final GoogleGeocodingService geocodingService;

    public GeocodingController(GoogleGeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }
    @PostMapping
    public String geocodeAddress(@RequestBody GeocodingRequest geocodingRequest) {
        return geocodingService.getCoordinates(geocodingRequest.address());
    }
}
