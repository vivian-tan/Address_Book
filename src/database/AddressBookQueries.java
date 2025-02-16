package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entries.Contact;
import entries.Person;
import entries.PersonBuilder;

public class AddressBookQueries {
    Database database;

    public AddressBookQueries() {
        database = new Database();
    }

    public List<Contact> getAllPeople() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try(Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Person person = new PersonBuilder()
                    .id(resultSet.getLong(1))
                    .firstname(resultSet.getString(2))
                    .lastname(resultSet.getString(3))
                    .birthday(resultSet.getString(4))
                    .build();
                contacts.add(new Contact(person));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return contacts;
    }

    public Contact getContact(long id) {
        Contact contact;
        
    }
}
