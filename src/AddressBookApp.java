import java.util.Scanner;

import database.AddressBookQueries;

public class AddressBookApp {
    public static void main(String[] args) throws Exception {
        try(Scanner userInputReader = new Scanner(System.in)) {
            System.out.println("Dein Adressbuch");
            new AddressBookService(new AddressBookQueries(), userInputReader).loadMainMenu();
        }
    }
}
