

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import database.AddressBookQueries;
import entries.Address;
import entries.AddressBuilder;
import entries.Contact;
import entries.ContactBuilder;
import entries.Person;
import entries.PersonBuilder;
import entries.Phone;
import entries.PhoneBuilder;
import types.ActionType;

public class AddressBookApp {
    private static final Map<String, ActionType> mainMenuKeyMap = Map.of("0", ActionType.EXIT, "1", ActionType.SHOW_CONTACTS, "2", ActionType.ADD_CONTACT);
    private static final Map<String, ActionType> showContactsKeyMap = Map.of("1", ActionType.MAIN_MENU, "2", ActionType.ADD_CONTACT, "3", ActionType.SHOW_CONTACT, "4", ActionType.EDIT_CONTACT, "5", ActionType.REMOVE_CONTACT);
    private static final Map<String, ActionType> showContactKeyMap = Map.of("1", ActionType.MAIN_MENU, "2", ActionType.SHOW_CONTACTS, "3", ActionType.EDIT_CONTACT, "4", ActionType.REMOVE_CONTACT);
    
    private static AddressBookQueries queries;

    public static void main(String[] args) throws Exception {
        try(Scanner userInputReader = new Scanner(System.in)) {
            queries = new AddressBookQueries();
            mainMenu(userInputReader);
        }
    }

    private static void mainMenu(Scanner userInputReader) {
        System.out.println("Du kannst folgende Aktionen durchführen: ");
        for(Map.Entry<String, ActionType> entry : mainMenuKeyMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getLabel());
        }
        switch(userInputReader.nextLine()) {
            case "0":
                System.exit(0);
                break;
            case "1":
                showContacts(userInputReader);
                break;
            case "2":
                addContact(userInputReader);
                break;
            case null:
            default:
                System.out.println("Die Eingabe ist ungültig.");
                mainMenu(userInputReader);
        }
    }

    private static void showContacts(Scanner userInputReader) {
        System.out.println("Nr.\tVorname\t\tNachname\t\tGeburtstag");
        List<Contact> contacts = queries.getAllPeople();
        for(Person person : contacts.stream().map(Contact::getPerson).collect(Collectors.toList())) {
            System.out.println(person.getId() + "\t" + person.getFirstname() + "\t\t" + person.getLastname() + "\t\t" + person.getBirthday());
        }
        System.out.println("--------------------");
        System.out.println("Du kannst folgende Aktionen durchführen: ");
        for(Map.Entry<String, ActionType> entry : showContactsKeyMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getLabel());
        }
        switch(userInputReader.nextLine()) {
            case "1":
                mainMenu(userInputReader);
                break;
            case "2":
                addContact(userInputReader);
                break;
            case "3":
                showContact(userInputReader);
                break;
            case "4":
                editContact(userInputReader);
                break;
            case "5":
                removeContact(userInputReader);
                break;
            case null:
            default:
                System.out.println("Die Eingabe ist ungültig.");
                showContacts(userInputReader);
        }
    }

    private static void addContact(Scanner userInputReader) {
        ContactBuilder contactBuilder = new ContactBuilder();

        PersonBuilder personBuilder = new PersonBuilder();
        System.out.println("--------------------");
        System.out.println("Wie ist der Vorname des Kontakts?");
        personBuilder.firstname(userInputReader.nextLine());
        System.out.println("Wie ist der Nachname des Kontakts?");
        personBuilder.lastname(userInputReader.nextLine());
        System.out.println("Wann ist der Geburtstag des Kontakts?");
        personBuilder.birthday(userInputReader.nextLine());

        contactBuilder.person(personBuilder.build());

        System.out.println("----------");
        System.out.println("Möchtest du eine Adresse hinterlegen? (J/N)");
        boolean fillAddress = userInputReader.nextLine().equals("J");
        while(fillAddress) {
            AddressBuilder addressBuilder = new AddressBuilder();
            System.out.println("Wie heißt die Straße?");
            addressBuilder.street(userInputReader.nextLine());
            System.out.println("Wie lautet die Hausnummer?");
            addressBuilder.houseNumber(userInputReader.nextLine());
            System.out.println("Wie lautet die PLZ?");
            addressBuilder.postalCode(userInputReader.nextLine());
            System.out.println("Wie heißt die Stadt?");
            addressBuilder.city(userInputReader.nextLine());
            System.out.println("Ist die Adresse privat oder geschäftlich? (P/G)");
            addressBuilder.isWork(userInputReader.nextLine().equals("G"));
            contactBuilder.address(addressBuilder.build());

            System.out.println("Möchtest du eine weitere Adresse hinterlegen? (J/N)");
            fillAddress = userInputReader.nextLine().equals("J");
        }

        System.out.println("----------");
        System.out.println("Möchtest du eine Telefonnummer hinterlegen? (J/N)");
        boolean fillPhone = userInputReader.nextLine().equals("J");
        while(fillPhone) {
            PhoneBuilder phoneBuilder = new PhoneBuilder();
            System.out.println("Wie lautet die Festnetznummer?");
            phoneBuilder.landlineNumber(userInputReader.nextLine());
            System.out.println("Wie lautet die mobile Nummer?");
            phoneBuilder.mobileNumber(userInputReader.nextLine());
            System.out.println("Ist die Nummer privat oder geschäftlich? (P/G)");
            phoneBuilder.isWork(userInputReader.nextLine().equals("G"));
            contactBuilder.phoneNumber(phoneBuilder.build());

            System.out.println("Möchtest du eine weitere Telefonnummer hinterlegen? (J/N)");
            fillPhone = userInputReader.nextLine().equals("J");
        }

        queries.addContact(contactBuilder.build());
        showContacts(userInputReader);
    }

    private static void showContact(Scanner userInputReader) {
        System.out.println("Welchen Kontakt möchtest du dir ansehen? Gib die Nummer an: ");
        Long id = Long.valueOf(userInputReader.nextLine());
        if(id != null) {
            showContact(userInputReader, id);
        } else {
            showContact(userInputReader);
        }
    }

    private static void showContact(Scanner userInputReader, long id) {
        //TODO: refactor
        Contact contact = queries.getContact(id);
        if(contact != null) {
            System.out.println("--------------------");
            System.out.println("Person:");
            System.out.println("Nr.\tVorname\t\tNachname\t\tGeburtstag");
            if(contact.getPerson() != null) {
                System.out.println(contact.getPerson().getId() + "\t" + contact.getPerson().getFirstname() + "\t\t" + contact.getPerson().getLastname() + "\t\t" + contact.getPerson().getBirthday());
            }
            System.out.println("----------");
            System.out.println("Adressen:");
            System.out.println("Nr.\tStraße\t\tHausnummer\t\tPLZ\t\tOrt\t\tArt der Anschrift");
            for(Address address : contact.getAddresses()) {
                System.out.println(address.getId() + "\t" + address.getStreet() + "\t\t" + address.getHouseNumber() + "\t\t" + address.getPostalCode() + "\t\t" + address.getCity() + "\t\t" + (address.isWork() ? "geschäftlich" : "privat"));
            }
            System.out.println("----------");
            System.out.println("Telefon:");
            System.out.println("Nr.\tFestnetznr.\t\tHandynr.\t\tArt der Nummer");
            for(Phone phoneNumber : contact.getPhoneNumbers()) {
                System.out.println(phoneNumber.getId() + "\t" + phoneNumber.getLandlineNumber() + "\t\t" + phoneNumber.getMobileNumber() + "\t\t" + (phoneNumber.isWork() ? "geschäftlich" : "privat"));
            }
        }

        System.out.println("--------------------");
        System.out.println("Du kannst folgende Aktionen durchführen: ");
        for(Map.Entry<String, ActionType> entry : showContactKeyMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getLabel());
        }
        switch(userInputReader.nextLine()) {
            case "1":
                mainMenu(userInputReader);
                break;
            case "2":
                showContacts(userInputReader);
                break;
            case "3":
                editContact(userInputReader, id);
                break;
            case "4":
                removeContact(userInputReader, id);
                break;
            case null:
            default:
                System.out.println("Die Eingabe ist ungültig.");
                showContact(userInputReader);
        }
    }

    private static void editContact(Scanner userInputReader) {
        System.out.println("--------------------");
        System.out.println("Welchen Kontakt möchtest du bearbeiten? Gib die Nummer an: ");
        Long id = Long.valueOf(userInputReader.nextLine());
        if(id != null) {
            editContact(userInputReader, id);
        }
    }

    private static void editContact(Scanner userInputReader, Long personId) {
        //TODO: refactor into method
        Contact contact = queries.getContact(personId);
        if(contact != null) {
            System.out.println("--------------------");
            System.out.println("Person:");
            System.out.println("Nr.\tVorname\t\tNachname\t\tGeburtstag");
            if(contact.getPerson() != null) {
                System.out.println(contact.getPerson().getId() + "\t" + contact.getPerson().getFirstname() + "\t\t" + contact.getPerson().getLastname() + "\t\t" + contact.getPerson().getBirthday());
            }
            System.out.println("----------");
            System.out.println("Adressen:");
            System.out.println("Nr.\tStraße\t\tHausnummer\t\tPLZ\t\tOrt\t\tArt der Anschrift");
            for(Address address : contact.getAddresses()) {
                System.out.println(address.getId() + "\t" + address.getStreet() + "\t\t" + address.getHouseNumber() + "\t\t" + address.getPostalCode() + "\t\t" + address.getCity() + "\t\t" + (address.isWork() ? "geschäftlich" : "privat"));
            }
            System.out.println("----------");
            System.out.println("Telefon:");
            System.out.println("Nr.\tFestnetznr.\t\tHandynr.\t\tArt der Nummer");
            for(Phone phoneNumber : contact.getPhoneNumbers()) {
                System.out.println(phoneNumber.getId() + "\t" + phoneNumber.getLandlineNumber() + "\t\t" + phoneNumber.getMobileNumber() + "\t\t" + (phoneNumber.isWork() ? "geschäftlich" : "privat"));
            }
        }

        System.out.println("----------");
        System.out.println("Du kannst folgende Aktionen durchführen: ");
        System.out.println("1 - Bearbeitung beenden");
        System.out.println("2 - Angaben zur Person bearbeiten");
        System.out.println("3 - Angaben zur Adresse bearbeiten");
        System.out.println("4 - Adresse hinzufügen");
        System.out.println("5 - Adresse löschen");
        System.out.println("6 - Angaben zur Telefonnummer bearbeiten");
        System.out.println("7 - Telefonnummer hinzufügen");
        System.out.println("8 - Telefonnummer löschen");

        switch(userInputReader.nextLine()) {
            case "1":
                showContact(userInputReader, personId);
                break;
            case "2":
                PersonBuilder personBuilder = new PersonBuilder()
                    .id(personId);
                System.out.println("--------------------");
                System.out.println("Wie ist der Vorname des Kontakts?");
                personBuilder.firstname(userInputReader.nextLine());
                System.out.println("Wie ist der Nachname des Kontakts?");
                personBuilder.lastname(userInputReader.nextLine());
                System.out.println("Wann ist der Geburtstag des Kontakts?");
                personBuilder.birthday(userInputReader.nextLine());

                queries.updatePerson(personBuilder.build());

                editContact(userInputReader, personId);
                break;
            case "3":
                System.out.println("Welche Adresse möchtest du bearbeiten? Gib die Nummer an: ");
                Long editAddressId = Long.valueOf(userInputReader.nextLine());
                if(editAddressId != null) {
                    AddressBuilder addressBuilder = new AddressBuilder()
                        .id(editAddressId);
                    System.out.println("Wie heißt die Straße?");
                    addressBuilder.street(userInputReader.nextLine());
                    System.out.println("Wie lautet die Hausnummer?");
                    addressBuilder.houseNumber(userInputReader.nextLine());
                    System.out.println("Wie lautet die PLZ?");
                    addressBuilder.postalCode(userInputReader.nextLine());
                    System.out.println("Wie heißt die Stadt?");
                    addressBuilder.city(userInputReader.nextLine());
                    System.out.println("Ist die Adresse privat oder geschäftlich? (P/G)");
                    addressBuilder.isWork(userInputReader.nextLine().equals("G"));
                    
                    queries.updateAddress(addressBuilder.build(), personId);
                }
                
                editContact(userInputReader, personId);
                break;
            case "4":
                boolean fillAddress = true;
                while(fillAddress) {
                    AddressBuilder addressBuilder = new AddressBuilder();
                    System.out.println("Wie heißt die Straße?");
                    addressBuilder.street(userInputReader.nextLine());
                    System.out.println("Wie lautet die Hausnummer?");
                    addressBuilder.houseNumber(userInputReader.nextLine());
                    System.out.println("Wie lautet die PLZ?");
                    addressBuilder.postalCode(userInputReader.nextLine());
                    System.out.println("Wie heißt die Stadt?");
                    addressBuilder.city(userInputReader.nextLine());
                    System.out.println("Ist die Adresse privat oder geschäftlich? (P/G)");
                    addressBuilder.isWork(userInputReader.nextLine().equals("G"));

                    queries.addAddress(addressBuilder.build(), personId);
                    
                    System.out.println("Möchtest du eine weitere Adresse hinterlegen? (J/N)");
                    fillAddress = userInputReader.nextLine().equals("J");
                }

                editContact(userInputReader, personId);
                break;
            case "5":
                System.out.println("Welche Adresse möchtest du löschen? Gib die Nummer an: ");
                Long deleteAddressId = Long.valueOf(userInputReader.nextLine());
                if(deleteAddressId != null) {
                    queries.deleteAddress(deleteAddressId, personId);
                }
                
                editContact(userInputReader, personId);
                break;
            case "6":
                System.out.println("Welche Telefonnummer möchtest du bearbeiten? Gib die Nummer an: ");
                Long editPhoneId = Long.valueOf(userInputReader.nextLine());
                if(editPhoneId != null) {
                    PhoneBuilder phoneBuilder = new PhoneBuilder()
                        .id(editPhoneId);
                    System.out.println("Wie lautet die Festnetznummer?");
                    phoneBuilder.landlineNumber(userInputReader.nextLine());
                    System.out.println("Wie lautet die mobile Nummer?");
                    phoneBuilder.mobileNumber(userInputReader.nextLine());
                    System.out.println("Ist die Nummer privat oder geschäftlich? (P/G)");
                    phoneBuilder.isWork(userInputReader.nextLine().equals("G"));

                    queries.updatePhone(phoneBuilder.build(), personId);
                }
                
                editContact(userInputReader, personId);
                break;
            case "7":
                boolean fillPhone = true;
                while(fillPhone) {
                    PhoneBuilder phoneBuilder = new PhoneBuilder();
                    System.out.println("Wie lautet die Festnetznummer?");
                    phoneBuilder.landlineNumber(userInputReader.nextLine());
                    System.out.println("Wie lautet die mobile Nummer?");
                    phoneBuilder.mobileNumber(userInputReader.nextLine());
                    System.out.println("Ist die Nummer privat oder geschäftlich? (P/G)");
                    phoneBuilder.isWork(userInputReader.nextLine().equals("G"));
                    
                    queries.addPhone(phoneBuilder.build(), personId);

                    System.out.println("Möchtest du eine weitere Telefonnummer hinterlegen? (J/N)");
                    fillPhone = userInputReader.nextLine().equals("J");
                }
                
                editContact(userInputReader, personId);
                break;
            case "8":
                System.out.println("Welche Telefonnummer möchtest du löschen? Gib die Nummer an: ");
                Long deletePhoneId = Long.valueOf(userInputReader.nextLine());
                if(deletePhoneId != null) {
                    queries.deletePhone(deletePhoneId, personId);
                }
                
                editContact(userInputReader, personId);
                break;
            case null:
            default:
                System.out.println("Die Eingabe ist ungültig.");
                editContact(userInputReader, personId);
        }
    }

    private static void removeContact(Scanner userInputReader) {
        System.out.println("--------------------");
        System.out.println("Welchen Kontakt möchtest du löschen? Gib die Nummer an: ");
        Long id = Long.valueOf(userInputReader.nextLine());
        if(id != null) {
            queries.deleteContact(id);
        }
        showContacts(userInputReader);
    }

    private static void removeContact(Scanner userInputReader, long id) {
        queries.deleteContact(id);
        showContacts(userInputReader);
    }
}
