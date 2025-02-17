import java.util.Scanner;

import console.InputHandler;
import console.OutputHandler;
import entries.Address;
import entries.Contact;
import entries.ContactBuilder;
import entries.ContactService;
import entries.Person;
import entries.Phone;
import types.ContactListOptions;
import types.ContactOptions;
import types.EditContactOptions;
import types.MainMenuOptions;

public class AddressBookService {
    private ContactService contactService;
    private InputHandler inputHandler;

    public AddressBookService(Scanner userInputReader) {
        this.contactService = new ContactService();
        this.inputHandler = new InputHandler(userInputReader);
    }

    public void loadMainMenu() {
        OutputHandler.printPossibleActions(MainMenuOptions.values());
        switch(inputHandler.getStringInput()) {
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
            default:
                OutputHandler.printInputError();
                OutputHandler.printPossibleActions(MainMenuOptions.values());
        }
    }

    private void showContacts() {
        OutputHandler.printPersonList(contactService.getAllPeople());
        OutputHandler.printPossibleActions(ContactListOptions.values());
       
        switch(inputHandler.getStringInput()) {
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
            default:
                OutputHandler.printInputError(); 
                OutputHandler.printPossibleActions(ContactListOptions.values());
        }
    }

    private void addContact() {
        ContactBuilder contactBuilder = new ContactBuilder();
        contactBuilder.person(inputHandler.getInputForPerson());

        if(inputHandler.getBooleanFor("Möchtest du eine Adresse hinterlegen? (J/N)")) {
            contactBuilder.addresses(inputHandler.getInputForAddresses());
        }

        if(inputHandler.getBooleanFor("Möchtest du eine Telefonnummer hinterlegen? (J/N)")) {
            contactBuilder.phoneNumbers(inputHandler.getInputForPhones());
        }

        contactService.addContact(contactBuilder.build());
        showContacts();
    }

    private void showContact() {
        Long id = inputHandler.getLongInputForOption("Welchen Kontakt möchtest du dir ansehen? Gib die Nummer an: ");
        if(id != null) {
            showContact(id);
        } else {
            OutputHandler.printInputError();
            showContact();
        }
    }

    private void showContact(long id) {
        Contact contact = contactService.getContact(id);
        if(contact != null) {
            OutputHandler.printContactDetails(contact);
        }

        OutputHandler.printPossibleActions(ContactOptions.values());
        
        switch(inputHandler.getStringInput()) {
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
            default:
                OutputHandler.printInputError();
                OutputHandler.printPossibleActions(ContactOptions.values());
        }
    }

    private void editContact() {
        Long id = inputHandler.getLongInputForOption("Welchen Kontakt möchtest du bearbeiten? Gib die Nummer an: ");
        if(id != null) {
            editContact(id);
        } else {
            editContact();
        }
    }

    private void editContact(Long personId) {
        Contact contact = contactService.getContact(personId);
        if(contact != null) {
            OutputHandler.printContactDetails(contact);
        }
        OutputHandler.printPossibleActions(EditContactOptions.values());

        switch(inputHandler.getStringInput()) {
            case "1":
                showContact(personId);
                break;
            case "2":
                Person person = inputHandler.getInputForPerson(personId);
                contactService.updatePerson(person);
                editContact(personId);
                break;
            case "3":
                Long editAddressId = inputHandler.getLongInputForOption("Welche Adresse möchtest du bearbeiten? Gib die Nummer an: ");
                if(editAddressId != null) {
                    contactService.updateAddress(inputHandler.getInputForAddress(editAddressId), personId);
                }
                editContact(personId);
                break;
            case "4":
                for(Address address : inputHandler.getInputForAddresses()) {
                    contactService.addAddress(address, personId);
                }

                editContact(personId);
                break;
            case "5":
                Long deleteAddressId = inputHandler.getLongInputForOption("Welche Adresse möchtest du löschen? Gib die Nummer an: ");
                if(deleteAddressId != null) {
                    contactService.deleteAddress(deleteAddressId, personId);
                }
                
                editContact(personId);
                break;
            case "6":
                Long editPhoneId = inputHandler.getLongInputForOption("Welche Telefonnummer möchtest du bearbeiten? Gib die Nummer an: ");
                if(editPhoneId != null) {
                    contactService.updatePhone(inputHandler.getInputForPhone(editPhoneId), personId);
                }
                editContact(personId);
                break;
            case "7":
                for(Phone phone : inputHandler.getInputForPhones()) {
                    contactService.addPhone(phone, personId);
                }
                
                editContact(personId);
                break;
            case "8":
                Long deletePhoneId = inputHandler.getLongInputForOption("Welche Telefonnummer möchtest du löschen? Gib die Nummer an: ");
                if(deletePhoneId != null) {
                    contactService.deletePhone(deletePhoneId, personId);
                }
                
                editContact(personId);
                break;
            case null:
            default:
                OutputHandler.printInputError();
                editContact(personId);
        }
    }

    private void removeContact() {
        Long id = inputHandler.getLongInputForOption("Welchen Kontakt möchtest du löschen? Gib die Nummer an: ");
        if(id != null) {
            contactService.deleteContact(id);
        }
        showContacts();
    }

    private void removeContact(long id) {
        contactService.deleteContact(id);
        showContacts();
    }
}
