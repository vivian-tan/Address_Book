package entries;

import java.util.List;
import java.util.stream.Collectors;

import database.AddressBookQueries;

public class ContactService {
    private AddressBookQueries queries;

    public ContactService() {
        this.queries = new AddressBookQueries();
    }

    public void addContact(Contact contact) {
        long personId = queries.addPerson(contact.getPerson());
        for(Address address : contact.getAddresses()) {
            queries.addAddress(address, personId);
        }
        for(Phone phone : contact.getPhoneNumbers()) {
            queries.addPhone(phone, personId);
        } 
    }

    public void updatePerson(Person person) {
        queries.updatePerson(person);
    }

    public void addAddress(Address address, long personId) {
        queries.addAddress(address, personId);
    }

    public void updateAddress(Address address, long personId) {
        queries.updateAddress(address, personId);
    }

    public void deleteAddress(long addressId, long personId) {
        queries.deleteAddress(addressId, personId);
    }

    public void addPhone(Phone phone, long personId) {
        queries.addPhone(phone, personId);
    }

    public void updatePhone(Phone phone, long personId) {
        queries.updatePhone(phone, personId);
    }

    public void deletePhone(long phoneId, long personId) {
        queries.deletePhone(phoneId, personId);
    }

    public Contact getContact(long id) {
        return queries.getContact(id);
    }

    public List<Person> getAllPeople() {
        return queries.getAllPeople().stream().map(Contact::getPerson).collect(Collectors.toList());
    }

    public void deleteContact(long id) {
        queries.deleteContact(id);
    }
}
