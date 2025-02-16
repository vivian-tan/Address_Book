package entries;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private Person person;
    private List<Address> addresses;
    private List<Phone> phoneNumbers;

    public Contact(ContactBuilder builder) {
        this.person = builder.person;
        this.addresses = builder.addresses;
        this.phoneNumbers = builder.phoneNumbers;
    }

    public Person getPerson() {
        return person;
    }
    public List<Address> getAddresses() {
        return addresses == null ? new ArrayList<>() : addresses;
    }
    public List<Phone> getPhoneNumbers() {
        return phoneNumbers == null ? new ArrayList<>() : phoneNumbers;
    }
}
