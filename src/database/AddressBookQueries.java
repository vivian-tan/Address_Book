package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entries.Address;
import entries.AddressBuilder;
import entries.Contact;
import entries.ContactBuilder;
import entries.Person;
import entries.PersonBuilder;
import entries.Phone;
import entries.PhoneBuilder;

public class AddressBookQueries {
    Database database;

    public AddressBookQueries() {
        database = new Database();
    }

    public long addPerson(Person person) {
        String sql = "INSERT INTO person (firstname, lastname, birthday) VALUES (?, ?, ?)";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, person.getFirstname());
            preparedStatement.setString(2, person.getLastname());
            preparedStatement.setString(3, person.getBirthday());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void addAddress(Address address, long personId) {
        String sql = "INSERT INTO address (person_id, street, house_number, postal_code, city, is_work) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, personId);
            preparedStatement.setString(2, address.getStreet());
            preparedStatement.setString(3, address.getHouseNumber());
            preparedStatement.setString(4, address.getPostalCode());
            preparedStatement.setString(5, address.getCity());
            preparedStatement.setBoolean(6, address.isWork());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addPhone(Phone phone, long personId) {
        String sql = "INSERT INTO phone (person_id, landline_number, mobile_number, is_work) VALUES (?, ?, ?, ?)";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, personId);
            preparedStatement.setString(2, phone.getLandlineNumber());
            preparedStatement.setString(3, phone.getMobileNumber());
            preparedStatement.setBoolean(4, phone.isWork());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updatePerson(Person person) {
        String sql = "UPDATE person SET firstname = ?, lastname = ?, birthday = ? WHERE person_id = ?";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, person.getFirstname());
            preparedStatement.setString(2, person.getLastname());
            preparedStatement.setString(3, person.getBirthday());
            preparedStatement.setLong(4, person.getId());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateAddress(Address address, Long personId) {
        String sql = "UPDATE address SET street = ?, house_number = ?, postal_code = ?, city = ?, is_work = ? WHERE person_id = ? AND address_id = ?";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setString(2, address.getHouseNumber());
            preparedStatement.setString(3, address.getPostalCode());
            preparedStatement.setString(4, address.getCity());
            preparedStatement.setBoolean(5, address.isWork());
            preparedStatement.setLong(6, personId);
            preparedStatement.setLong(7, address.getId());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updatePhone(Phone phone, Long personId) {
        String sql = "UPDATE phone SET landline_number = ?, mobile_number = ?, is_work = ? WHERE person_id = ? AND phone_id = ?";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, phone.getLandlineNumber());
            preparedStatement.setString(2, phone.getMobileNumber());
            preparedStatement.setBoolean(3, phone.isWork());
            preparedStatement.setLong(4, personId);
            preparedStatement.setLong(5, phone.getId());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Contact> getAllPeople() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try(Connection connection = database.getConnection(); Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                Person person = new PersonBuilder()
                    .id(resultSet.getLong(1))
                    .firstname(resultSet.getString(2))
                    .lastname(resultSet.getString(3))
                    .birthday(resultSet.getString(4))
                    .build();
                contacts.add(new ContactBuilder().person(person).build());
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return contacts;
    }

    public Contact getContact(long id) {
        Contact contact = null;
        String sql = "SELECT * FROM person JOIN address ON person.person_id = address.person_id JOIN phone ON person.person_id = phone.person_id WHERE person.person_id = ?";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Person person = null;
            Set<Address> addresses = new HashSet<>();
            Set<Phone> phoneNumbers = new HashSet<>();
            while(resultSet.next()) {
                person = new PersonBuilder()
                    .id(resultSet.getLong(1))
                    .firstname(resultSet.getString(2))
                    .lastname(resultSet.getString(3))
                    .birthday(resultSet.getString(4))
                    .build();
                Address address = new AddressBuilder()
                    .id(resultSet.getLong(5))
                    .street(resultSet.getString(7))
                    .houseNumber(resultSet.getString(8))
                    .postalCode(resultSet.getString(9))
                    .city(resultSet.getString(10))
                    .isWork(resultSet.getBoolean(11))
                    .build();
                addresses.add(address);
                Phone phone = new PhoneBuilder()
                    .id(resultSet.getLong(12))
                    .landlineNumber(resultSet.getString(14))
                    .mobileNumber(resultSet.getString(15))
                    .isWork(resultSet.getBoolean(16))
                    .build();
                phoneNumbers.add(phone);
            }
            contact = new ContactBuilder().person(person).addresses(new ArrayList<Address>(addresses)).phoneNumbers(new ArrayList<Phone>(phoneNumbers)).build();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return contact;
    }

    public void deleteContact(long id) {
        String sql = "DELETE FROM person WHERE person_id = ?";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteAddress(long id, long person_id) {
        String sql = "DELETE FROM address WHERE address_id = ? AND person_id = ?";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, person_id);
            preparedStatement.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deletePhone(long id, long person_id) {
        String sql = "DELETE FROM phone WHERE phone_id = ? AND person_id = ?";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, person_id);
            preparedStatement.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}