package buildrun.roadeye.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.transform.Result;
import java.util.List;

public class GeolocationResponse {
    private String status;
    private List<Result> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        private Geometry geometry;
        @JsonProperty("address_components")
        private List<AddressComponent> addressComponents;
        public Geometry getGeometry() {
            return geometry;
        }
        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }
        public List<AddressComponent> getAddressComponents() {
            return addressComponents;
        }
        public void setAddressComponents(List<AddressComponent> addressComponents) {
            this.addressComponents = addressComponents;
        }
    }

    public static class Geometry {
        private Location location;
        public Location getLocation() {
            return location;
        }
        public void setLocation(Location location) {
            this.location = location;
        }
    }

    // Classe interna para mapear a localização
    public static class Location {
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressComponent {
        @JsonProperty("long_name")
        private String longName;
        @JsonProperty("short_name")
        private String shortName;
        private List<String> types;

        // Getters e setters
        public String getLongName() {
            return longName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }
}
