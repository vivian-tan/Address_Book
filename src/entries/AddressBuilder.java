package entries;

public class AddressBuilder {
    long id;
    String street;
    String houseNumber;
    String postalCode;
    String city;
    boolean isWork;

    public AddressBuilder id(long id) {
        this.id = id;
        return this;
    }

    public AddressBuilder street(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder houseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public AddressBuilder postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public AddressBuilder city(String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder isWork(boolean isWork) {
        this.isWork = isWork;
        return this;
    }

    public Address build() {
        return new Address(this);
    }
}
