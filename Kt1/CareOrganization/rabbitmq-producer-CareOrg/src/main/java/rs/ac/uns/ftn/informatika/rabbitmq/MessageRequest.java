package rs.ac.uns.ftn.informatika.rabbitmq;

public class MessageRequest {
    private String naziv;
    private Double latitude;
    private Double longitude;

    // Getters and Setters
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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
}
