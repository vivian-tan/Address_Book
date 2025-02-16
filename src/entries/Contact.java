package entries;

import java.util.List;

public class Contact {
    private Person person;
    private List<Address> addresses;
    private List<Phone> phoneNumbers;

    public Contact() {
        
    }

    public Contact(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
