import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import database.AddressBookQueries;
import entries.Address;
import entries.AddressBuilder;
import entries.Contact;
import entries.ContactBuilder;
import entries.ContactService;
import entries.Person;
import entries.PersonBuilder;
import entries.Phone;
import entries.PhoneBuilder;
import types.ContactListOptions;
import types.ContactOptions;
import types.EditContactOptions;
import types.IOption;
import types.MainMenuOptions;

public class AddressBookService {
    private ContactService contactService;
    private AddressBookQueries queries;
    private Scanner userInputReader;

    public AddressBookService(AddressBookQueries queries, Scanner userInputReader) {
        this.contactService = new ContactService(queries);
        this.queries = queries;
        this.userInputReader = userInputReader;
    }

    public void loadMainMenu() {
        printPossibleActions(MainMenuOptions.values());
        switch(userInputReader.nextLine()) {
            // TODO: case MainMenuOptions.EXIT.getKeyCode(): doesnt work?
            case "0":
                System.exit(0);
                break;
            case "1":
                showContacts();
                break;
            case "2":
                addContact();
                break;
            case null:
            default:
                printInputError();
                printPossibleActions(MainMenuOptions.values());
        }
    }

    private void showContacts() {
        printPersonList(queries.getAllPeople().stream().map(Contact::getPerson).collect(Collectors.toList()));
        printPossibleActions(ContactListOptions.values());
       
        switch(userInputReader.nextLine()) {
            case "1":
                loadMainMenu();
                break;
            case "2":
                addContact();
                break;
            case "3":
                showContact();
                break;
            case "4":
                editContact();
                break;
            case "5":
                removeContact();
                break;
            case null:
            default:
                printInputError(); 
                printPossibleActions(ContactListOptions.values());
        }
    }

    private void addContact() {
        ContactBuilder contactBuilder = new ContactBuilder();
        contactBuilder.person(getInputForPerson());

        System.out.println("----------");
        System.out.println("Möchtest du eine Adresse hinterlegen? (J/N)");
        if(userInputReader.nextLine().equals("J")) {
            contactBuilder.addresses(getInputForAddresses());
        }

        System.out.println("----------");
        System.out.println("Möchtest du eine Telefonnummer hinterlegen? (J/N)");
        if(userInputReader.nextLine().equals("J")) {
            contactBuilder.phoneNumbers(getInputForPhones());
        }

        queries.addContact(contactBuilder.build());
        showContacts();
    }

    private void showContact() {
        System.out.println("Welchen Kontakt möchtest du dir ansehen? Gib die Nummer an: ");
        Long id = Long.valueOf(userInputReader.nextLine());
        if(id != null) {
            showContact(id);
        } else {
            printInputError();
            showContact();
        }
    }

    private void showContact(long id) {
        Contact contact = queries.getContact(id);
        if(contact != null) {
            printContactDetails(contact);
        }

        printPossibleActions(ContactOptions.values());
        
        switch(userInputReader.nextLine()) {
            case "1":
                loadMainMenu();
                break;
            case "2":
                showContacts();
                break;
            case "3":
                editContact(id);
                break;
            case "4":
                removeContact(id);
                break;
            case null:
            default:
                printInputError();
                printPossibleActions(ContactOptions.values());
        }
    }

    private void editContact() {
        System.out.println("--------------------");
        System.out.println("Welchen Kontakt möchtest du bearbeiten? Gib die Nummer an: ");
        Long id = Long.valueOf(userInputReader.nextLine());
        if(id != null) {
            editContact(id);
        } else {
            editContact();
        }
    }

    private void editContact(Long personId) {
        Contact contact = queries.getContact(personId);
        if(contact != null) {
            printContactDetails(contact);
        }
        printPossibleActions(EditContactOptions.values());

        switch(userInputReader.nextLine()) {
            case "1":
                showContact(personId);
                break;
            case "2":
                Person person = getInputForPerson(personId);
                queries.updatePerson(person);
                editContact(personId);
                break;
            case "3":
                System.out.println("Welche Adresse möchtest du bearbeiten? Gib die Nummer an: ");
                Long editAddressId = Long.valueOf(userInputReader.nextLine());
                if(editAddressId != null) {
                    queries.updateAddress(getInputForAddress(editAddressId), personId);
                }
                editContact(personId);
                break;
            case "4":
                for(Address address : getInputForAddresses()) {
                    queries.addAddress(address, personId);
                }

                editContact(personId);
                break;
            case "5":
                System.out.println("Welche Adresse möchtest du löschen? Gib die Nummer an: ");
                Long deleteAddressId = Long.valueOf(userInputReader.nextLine());
                if(deleteAddressId != null) {
                    queries.deleteAddress(deleteAddressId, personId);
                }
                
                editContact(personId);
                break;
            case "6":
                System.out.println("Welche Telefonnummer möchtest du bearbeiten? Gib die Nummer an: ");
                Long editPhoneId = Long.valueOf(userInputReader.nextLine());
                if(editPhoneId != null) {
                    queries.updatePhone(getInputForPhone(editPhoneId), personId);
                }
                editContact(personId);
                break;
            case "7":
                for(Phone phone : getInputForPhones()) {
                    queries.addPhone(phone, personId);
                }
                
                editContact(personId);
                break;
            case "8":
                System.out.println("Welche Telefonnummer möchtest du löschen? Gib die Nummer an: ");
                Long deletePhoneId = Long.valueOf(userInputReader.nextLine());
                if(deletePhoneId != null) {
                    queries.deletePhone(deletePhoneId, personId);
                }
                
                editContact(personId);
                break;
            case null:
            default:
                printInputError();
                editContact(personId);
        }
    }

    private void removeContact() {
        System.out.println("--------------------");
        System.out.println("Welchen Kontakt möchtest du löschen? Gib die Nummer an: ");
        Long id = Long.valueOf(userInputReader.nextLine());
        if(id != null) {
            queries.deleteContact(id);
        }
        showContacts();
    }

    private void removeContact(long id) {
        queries.deleteContact(id);
        showContacts();
    }

    private void printInputError() {
        System.out.println("Die Eingabe ist ungültig.");
    }

    private void printPossibleActions(IOption[] options) {
        System.out.println("--------------------");
        System.out.println("Du kannst folgende Aktionen durchführen: ");
        for(IOption option : options) {
            System.out.println(option.getKeyCode() + " - " + option.getLabel());
        }
    }

    private void printPersonList(List<Person> people) {
        System.out.println("Nr.\tVorname\t\tNachname\t\tGeburtstag");
        for(Person person : people) {
            System.out.println(person.getId() + "\t" + person.getFirstname() + "\t\t" + person.getLastname() + "\t\t" + person.getBirthday());
        }
    }

    private Person getInputForPerson() {
        return getInputForPerson(new PersonBuilder());
    }

    private Person getInputForPerson(long id) {
        return getInputForPerson(new PersonBuilder().id(id));
    }

    private Person getInputForPerson(PersonBuilder personBuilder) {
        System.out.println("--------------------");
        System.out.println("Wie ist der Vorname des Kontakts?");
        personBuilder.firstname(userInputReader.nextLine());
        System.out.println("Wie ist der Nachname des Kontakts?");
        personBuilder.lastname(userInputReader.nextLine());
        System.out.println("Wann ist der Geburtstag des Kontakts?");
        personBuilder.birthday(userInputReader.nextLine());
        return personBuilder.build();
    }

    private List<Address> getInputForAddresses() {
        List<Address> addresses = new ArrayList<>();
        boolean fillAddress = true;
        while(fillAddress) {
            addresses.add(getInputForAddress());

            System.out.println("Möchtest du eine weitere Adresse hinterlegen? (J/N)");
            fillAddress = userInputReader.nextLine().equals("J");
        }
        return addresses;
    }

    private Address getInputForAddress() {
        return getInputForAddress(new AddressBuilder());
    }

    private Address getInputForAddress(long id) {
        return getInputForAddress(new AddressBuilder().id(id));
    }

    private Address getInputForAddress(AddressBuilder addressBuilder) {
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
        return addressBuilder.build();
    }

    private List<Phone> getInputForPhones() {
        List<Phone> phones = new ArrayList<>();
        boolean fillPhone = true;
        while(fillPhone) {
            phones.add(getInputForPhone());

            System.out.println("Möchtest du eine weitere Telefonnummer hinterlegen? (J/N)");
            fillPhone = userInputReader.nextLine().equals("J");
        }
        return phones;
    }

    private Phone getInputForPhone() {
        return getInputForPhone(new PhoneBuilder());
    }

    private Phone getInputForPhone(long id) {
        return getInputForPhone(new PhoneBuilder().id(id));
    }

    private Phone getInputForPhone(PhoneBuilder phoneBuilder) {
        System.out.println("Wie lautet die Festnetznummer?");
        phoneBuilder.landlineNumber(userInputReader.nextLine());
        System.out.println("Wie lautet die mobile Nummer?");
        phoneBuilder.mobileNumber(userInputReader.nextLine());
        System.out.println("Ist die Nummer privat oder geschäftlich? (P/G)");
        phoneBuilder.isWork(userInputReader.nextLine().equals("G"));
        return phoneBuilder.build();
    }

    private void printContactDetails(Contact contact) {
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
    }
}
