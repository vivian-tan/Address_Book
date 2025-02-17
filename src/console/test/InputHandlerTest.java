package console.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import console.InputHandler;
import entries.Address;
import entries.Person;
import entries.Phone;

public class InputHandlerTest {
    private Scanner mockScanner;
    private InputHandler inputHandler;
    
    @BeforeEach
    public void setUp() {
        mockScanner = mock(Scanner.class);
        inputHandler = new InputHandler(mockScanner);
    }

    @Test
    public void testGetString() {
        when(mockScanner.nextLine()).thenReturn("Test");
        String input = inputHandler.getStringInput();
        assertEquals(input, "Test", "text input is same as text entered");
    }

    @Test
    public void testGetLong() {
        when(mockScanner.nextLine()).thenReturn("1");
        Long input = inputHandler.getLongInputForOption("");
        assertEquals(input, 1, "number input is same as number entered");
    }

    @Test
    public void testGetBooleanIsTrue() {
        when(mockScanner.nextLine()).thenReturn("J");
        boolean input = inputHandler.getBooleanFor("");
        assertTrue(input, "entered text converts to true");
    }
    
    @Test
    public void testGetBooleanIsFalse() {
        when(mockScanner.nextLine()).thenReturn("N");
        boolean input = inputHandler.getBooleanFor("");
        assertFalse(input, "entered text converts to false");
    }

    @Test
    public void testGetBooleanWithInvalidResultInFalse() {
        when(mockScanner.nextLine()).thenReturn("Test");
        boolean input = inputHandler.getBooleanFor("");
        assertFalse(input, "entered text converts to false");
    }

    @Test
    public void testGetPerson() {
        when(mockScanner.nextLine()).thenReturn("Max", "Mustermann", "01.01.1990");
        Person inputPerson = inputHandler.getInputForPerson();
        assertEquals(inputPerson.getFirstname(), "Max", "firstname is set correcty");
        assertEquals(inputPerson.getLastname(), "Mustermann", "lastname is set correcty");
        assertEquals(inputPerson.getBirthday(), "01.01.1990", "birthday is set correcty");
    }

    @Test
    public void testGetPersonWithId() {
        when(mockScanner.nextLine()).thenReturn("Max", "Mustermann", "01.01.1990");
        Person inputPerson = inputHandler.getInputForPerson(5);
        assertEquals(inputPerson.getId(), 5, "id is set correctly");
        assertEquals(inputPerson.getFirstname(), "Max", "firstname is set correcty");
        assertEquals(inputPerson.getLastname(), "Mustermann", "lastname is set correcty");
        assertEquals(inputPerson.getBirthday(), "01.01.1990", "birthday is set correcty");
    }

    @Test
    public void testGetAddress() {
        when(mockScanner.nextLine()).thenReturn("Musterstraße", "123", "12345", "Musterstadt", "G");
        Address inputAddress = inputHandler.getInputForAddress();
        assertEquals(inputAddress.getStreet(), "Musterstraße", "street is set correctly");
        assertEquals(inputAddress.getHouseNumber(), "123", "house number is set correctly");
        assertEquals(inputAddress.getPostalCode(), "12345", "postal code is set correctly");
        assertEquals(inputAddress.getCity(), "Musterstadt", "city is set correctly");
        assertTrue(inputAddress.isWork(), "address is work address");
    }

    @Test
    public void testGetAddressWithIdAndPrivate() {
        when(mockScanner.nextLine()).thenReturn("Musterstraße", "123", "12345", "Musterstadt", "P");
        Address inputAddress = inputHandler.getInputForAddress(5);
        assertEquals(inputAddress.getId(), 5, "id is set correctly");
        assertEquals(inputAddress.getStreet(), "Musterstraße", "street is set correctly");
        assertEquals(inputAddress.getHouseNumber(), "123", "house number is set correctly");
        assertEquals(inputAddress.getPostalCode(), "12345", "postal code is set correctly");
        assertEquals(inputAddress.getCity(), "Musterstadt", "city is set correctly");
        assertFalse(inputAddress.isWork(), "address is not work address");
    }

    @Test
    public void testGetAddressInvalidIsWork() {
        when(mockScanner.nextLine()).thenReturn("", "", "", "", "Test");
        Address inputAddress = inputHandler.getInputForAddress();
        assertFalse(inputAddress.isWork(), "address is not work address");
    }

    @Test
    public void testGetPhone() {
        when(mockScanner.nextLine()).thenReturn("0251123456", "0152123456", "G");
        Phone inputPhone = inputHandler.getInputForPhone();
        assertEquals(inputPhone.getLandlineNumber(), "0251123456", "landline number is set correctly");
        assertEquals(inputPhone.getMobileNumber(), "0152123456", "mobile number is set correctly");
        assertTrue(inputPhone.isWork(), "phone is work phone");
    }

    @Test
    public void testGetPhoneWithIdAndPrivate() {
        when(mockScanner.nextLine()).thenReturn("0251123456", "0152123456", "P");
        Phone inputPhone = inputHandler.getInputForPhone(5);
        assertEquals(inputPhone.getId(), 5, "id is set correctly");
        assertEquals(inputPhone.getLandlineNumber(), "0251123456", "landline number is set correctly");
        assertEquals(inputPhone.getMobileNumber(), "0152123456", "mobile number is set correctly");
        assertFalse(inputPhone.isWork(), "phone is not work phone");
    }
}
