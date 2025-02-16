package entries;

public class Address {
    private long id;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private boolean isWork;

    public Address(AddressBuilder builder) {
        this.id = builder.id;
        this.street = builder.street;
        this.houseNumber = builder.houseNumber;
        this.postalCode = builder.postalCode;
        this.city = builder.city;
        this.isWork = builder.isWork;
    }

    public long getId() {
        return id;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean isWork) {
        this.isWork = isWork;
    }
}
