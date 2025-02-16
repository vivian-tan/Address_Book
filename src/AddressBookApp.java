

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import database.AddressBookQueries;
import entries.Contact;
import entries.Person;
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
        System.out.println("Nr.\tVorname\tNachname\tGeburtstag");
        List<Contact> contacts = queries.getAllPeople();
        for(Person person : contacts.stream().map(Contact::getPerson).collect(Collectors.toList())) {
            System.out.println(person.getId() + "\t" + person.getFirstname() + "\t" + person.getLastname() + "\t" + person.getBirthday());
        }
        System.out.println("----------");
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
        
    }

    private static void showContact(Scanner userInputReader) {
        System.out.println("----------");
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
                editContact(userInputReader);
                break;
            case "4":
                removeContact(userInputReader);
                break;
            case null:
            default:
                System.out.println("Die Eingabe ist ungültig.");
                showContact(userInputReader);
        }
    }

    private static void editContact(Scanner userInputReader) {

    }

    private static void removeContact(Scanner userInputReader) {
         
    }
}
