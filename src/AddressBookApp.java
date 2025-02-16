

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import types.ActionType;

public class AddressBookApp {
    private static final Map<String, ActionType> mainMenuKeyMap = Map.of("0", ActionType.EXIT, "1", ActionType.SHOW_CONTACTS, "2", ActionType.ADD_CONTACT);
    private static final Map<String, ActionType> showContactsKeyMap = Map.of("1", ActionType.MAIN_MENU, "2", ActionType.ADD_CONTACT, "3", ActionType.SHOW_CONTACT, "4", ActionType.EDIT_CONTACT, "5", ActionType.REMOVE_CONTACT);
    
    public static void main(String[] args) throws Exception {
        try(Scanner userInputReader = new Scanner(System.in)) {
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
                showContacts();
                break;
            case "2":
                addContact();
                break;
            case null:
            default:
                System.out.println("Die Eingabe ist ungültig.");
                mainMenu(userInputReader);
        }
    }

    private static void showContacts() {
        System.out.println("----------");
        System.out.println("Du kannst folgende Aktionen durchführen: ");
        for(Map.Entry<String, ActionType> entry : showContactsKeyMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getLabel());
        }
    }

    private static void addContact() {
        
    }
}
