package rs.ac.uns.ftn.informatika.rabbitmq;

import java.io.Serializable;

public class Message implements Serializable{
    private String from = "Organizacija za brigu o zečevima";
    private String to = "Only Bunns";
    private String id;
    private String naziv;
    private Double latitude;
    private Double longitude;

    public Message() {}

    public Message(String id, String naziv, Double latitude, Double longitude) {
        this.id = id;
        this.naziv = naziv;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double double1) {
        this.latitude = double1;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
