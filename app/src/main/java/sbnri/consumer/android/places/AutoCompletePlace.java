package sbnri.consumer.android.places;

public class AutoCompletePlace {
    private final String address;
    private final String id;
    private Double latitude;
    private Double longitude;

    public AutoCompletePlace(String address, String id) {
        this.address = address;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return address;
    }
}
