package entries;

import java.util.ArrayList;
import java.util.List;

public class ContactBuilder {
    Person person;
    List<Address> addresses;
    List<Phone> phoneNumbers;

    public ContactBuilder person(Person person) {
        this.person = person;
        return this;
    }

    public ContactBuilder addresses(List<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public ContactBuilder address(Address address) {
        if(this.addresses == null) {
            this.addresses = new ArrayList<>();
        }
        this.addresses.add(address);
        return this;
    }

    public ContactBuilder phoneNumbers(List<Phone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

    public ContactBuilder phoneNumber(Phone phoneNumber) {
        if(this.phoneNumbers == null) {
            this.phoneNumbers = new ArrayList<>();
        }
        this.phoneNumbers.add(phoneNumber);
        return this;
    }

    public Contact build() {
        return new Contact(this);
    }
}
