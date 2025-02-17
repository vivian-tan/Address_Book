package entries;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber) && 
            Objects.equals(postalCode, address.postalCode) && Objects.equals(city, address.city) && Objects.equals(isWork, address.isWork);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, houseNumber, postalCode, city, isWork);
    }

    public long getId() {
        return id;
    }
    public String getStreet() {
        return street;
    }
    public String getHouseNumber() {
        return houseNumber;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getCity() {
        return city;
    }
    public boolean isWork() {
        return isWork;
    }
}
