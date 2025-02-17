package console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entries.Address;
import entries.AddressBuilder;
import entries.Person;
import entries.PersonBuilder;
import entries.Phone;
import entries.PhoneBuilder;

public class InputHandler {
    private Scanner userInputReader;
    
    public InputHandler(Scanner userInputReader) {
        this.userInputReader = userInputReader;
    }

    public String getStringInput() {
        return userInputReader.nextLine();
    }

    public Long getLongInputForOption(String optionMessage){
        System.out.println("--------------------");
        System.out.println(optionMessage);
        try {
            return Long.valueOf(getStringInput());
        } catch(NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Es wurde keine gültige Zahl eingegeben.");
        }
        return null;
    }

    public boolean getBooleanFor(String message) {
        System.out.println("----------");
        System.out.println(message);
        return getStringInput().equals("J");
    }

    public Person getInputForPerson() {
        return getInputForPerson(new PersonBuilder());
    }

    public Person getInputForPerson(long id) {
        return getInputForPerson(new PersonBuilder().id(id));
    }

    private Person getInputForPerson(PersonBuilder personBuilder) {
        System.out.println("--------------------");
        System.out.println("Wie ist der Vorname des Kontakts?");
        personBuilder.firstname(getStringInput());
        System.out.println("Wie ist der Nachname des Kontakts?");
        personBuilder.lastname(getStringInput());
        System.out.println("Wann ist der Geburtstag des Kontakts?");
        personBuilder.birthday(getStringInput());
        return personBuilder.build();
    }

    public List<Address> getInputForAddresses() {
        List<Address> addresses = new ArrayList<>();
        boolean fillAddress = true;
        while(fillAddress) {
            addresses.add(getInputForAddress());
            fillAddress = getBooleanFor("Möchtest du eine weitere Adresse hinterlegen? (J/N)");
        }
        return addresses;
    }

    public Address getInputForAddress() {
        return getInputForAddress(new AddressBuilder());
    }

    public Address getInputForAddress(long id) {
        return getInputForAddress(new AddressBuilder().id(id));
    }

    private Address getInputForAddress(AddressBuilder addressBuilder) {
        System.out.println("Wie heißt die Straße?");
        addressBuilder.street(getStringInput());
        System.out.println("Wie lautet die Hausnummer?");
        addressBuilder.houseNumber(getStringInput());
        System.out.println("Wie lautet die PLZ?");
        addressBuilder.postalCode(getStringInput());
        System.out.println("Wie heißt die Stadt?");
        addressBuilder.city(getStringInput());
        System.out.println("Ist die Adresse privat oder geschäftlich? (P/G)");
        addressBuilder.isWork(getStringInput().equals("G"));
        return addressBuilder.build();
    }

    public List<Phone> getInputForPhones() {
        List<Phone> phones = new ArrayList<>();
        boolean fillPhone = true;
        while(fillPhone) {
            phones.add(getInputForPhone());
            fillPhone = getBooleanFor("Möchtest du eine weitere Telefonnummer hinterlegen? (J/N)");
        }
        return phones;
    }

    public Phone getInputForPhone() {
        return getInputForPhone(new PhoneBuilder());
    }

    public Phone getInputForPhone(long id) {
        return getInputForPhone(new PhoneBuilder().id(id));
    }

    private Phone getInputForPhone(PhoneBuilder phoneBuilder) {
        System.out.println("Wie lautet die Festnetznummer?");
        phoneBuilder.landlineNumber(getStringInput());
        System.out.println("Wie lautet die mobile Nummer?");
        phoneBuilder.mobileNumber(getStringInput());
        System.out.println("Ist die Nummer privat oder geschäftlich? (P/G)");
        phoneBuilder.isWork(getStringInput().equals("G"));
        return phoneBuilder.build();
    }
}
