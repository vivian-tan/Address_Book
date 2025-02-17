package console;

import java.util.List;

import entries.Address;
import entries.Contact;
import entries.Person;
import entries.Phone;
import types.IOption;

public class OutputHandler {
    public static void printInputError() {
        System.out.println("Die Eingabe ist ungültig.");
    }

    public static void printPossibleActions(IOption[] options) {
        System.out.println("--------------------");
        System.out.println("Du kannst folgende Aktionen durchführen: ");
        for(IOption option : options) {
            System.out.println(option.getKeyCode() + " - " + option.getLabel());
        }
    }

    public static void printPersonList(List<Person> people) {
        System.out.println("Nr.\tVorname\t\tNachname\t\tGeburtstag");
        for(Person person : people) {
            System.out.println(person.getId() + "\t" + person.getFirstname() + "\t\t" + person.getLastname() + "\t\t" + person.getBirthday());
        }
    }

    public static void printContactDetails(Contact contact) {
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
