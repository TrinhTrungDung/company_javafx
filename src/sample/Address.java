package sample;

public class Address {
    private String streetName;
    private String streetNumber;
    private String state;
    private String country;

    public Address(String streetNumber, String streetName, String state, String country) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.state = state;
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return  streetNumber + ", " + streetName + ", " + state + ", " + country;
    }
}
