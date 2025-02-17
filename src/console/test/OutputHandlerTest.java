package console.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import console.OutputHandler;
import entries.Address;
import entries.AddressBuilder;
import entries.Contact;
import entries.ContactBuilder;
import entries.Person;
import entries.PersonBuilder;
import entries.Phone;
import entries.PhoneBuilder;
import types.MainMenuOptions;

public class OutputHandlerTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void testPrintPossibleActions() {
        OutputHandler.printPossibleActions(MainMenuOptions.values());
        String output = outContent.toString();
        for(MainMenuOptions option : MainMenuOptions.values()) {
            assertTrue(output.contains(option.getKeyCode() + " - " + option.getLabel()));
        }
    }

    @Test
    public void testPrintPersonList() {
        List<Person> people = getPeople();
        OutputHandler.printPersonList(people);
        String output = outContent.toString();
        people.forEach(person -> checkPerson(person, output));
    }

    @Test
    public void testPrintContact() {
        Contact contact = getContact();
        OutputHandler.printContactDetails(contact);
        String output = outContent.toString();
        checkPerson(contact.getPerson(), output);
        contact.getAddresses().forEach(address -> checkAddress(address, output));
        contact.getPhoneNumbers().forEach(phone -> checkPhone(phone, output));
    }

    private void checkPerson(Person person, String output) {
        assertTrue(output.contains(String.valueOf(person.getId())));
        assertTrue(output.contains(person.getFirstname()));
        assertTrue(output.contains(person.getLastname()));
        assertTrue(output.contains(person.getBirthday()));
    }

    private void checkAddress(Address address, String output) {
        assertTrue(output.contains(String.valueOf(address.getId())));
        assertTrue(output.contains(address.getStreet()));
        assertTrue(output.contains(address.getHouseNumber()));
        assertTrue(output.contains(address.getPostalCode()));
        assertTrue(output.contains(address.getCity()));
        assertTrue(output.contains(address.isWork() ? "geschäftlich" : "privat"));
    }

    private void checkPhone(Phone phone, String output) {
        assertTrue(output.contains(String.valueOf(phone.getId())));
        if(phone.getLandlineNumber() != null) {
            assertTrue(output.contains(phone.getLandlineNumber()));
        }
        if(phone.getMobileNumber() != null) {
            assertTrue(output.contains(phone.getMobileNumber()));
        }
        assertTrue(output.contains(phone.isWork() ? "geschäftlich" : "privat"));
    }

    private List<Person> getPeople() {
        Person person1 = new PersonBuilder()
            .id(1)
            .firstname("Max")
            .lastname("Mustermann")
            .birthday("01.01.1990")
            .build();
        Person person2 = new PersonBuilder()
            .id(2)
            .firstname("Jane")
            .lastname("Doe")
            .birthday("31.12.2020")
            .build();
        return List.of(person1, person2);
    }

    private List<Address> getAddresses() {
        Address address1 = new AddressBuilder()
            .id(1)
            .street("Musterstraße")
            .houseNumber("123")
            .postalCode("12345")
            .city("Musterstadt")
            .isWork(false)
            .build();
        Address address2 = new AddressBuilder()
            .id(2)
            .street("Straßenmuster")
            .houseNumber("321")
            .postalCode("54321")
            .city("Stadtmuster")
            .isWork(true)
            .build();
        return List.of(address1, address2);
    }

    private List<Phone> getPhones() {
        Phone phone1 = new PhoneBuilder()
            .id(1)
            .landlineNumber("123456789")
            .isWork(true)
            .build();
        Phone phone2 = new PhoneBuilder()
            .id(2)
            .mobileNumber("987654321")
            .isWork(false)
            .build();
        return List.of(phone1, phone2);
    }

    private Contact getContact() {
        return new ContactBuilder()
            .person(getPeople().get(0))
            .addresses(getAddresses())
            .phoneNumbers(getPhones())
            .build();
    }
}
