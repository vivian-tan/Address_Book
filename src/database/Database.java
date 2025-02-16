package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private String localhostUrl = "jdbc:mariadb://localhost:3306/";
    private String databaseUrl = "jdbc:mariadb://localhost:3306/address_book";
    private String username = "root";
    private String password = "";

    public Database() {
        setupDatabase();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(databaseUrl, username, password);
        } catch(SQLException e){
            System.out.println("Es konnte keine Verbindung mit der Datenbank hergestellt werden.");
        }
        return null;
    }

    private void setupDatabase() {
        createAddressBookDatabase();
        createAddressBookTables();
        createDummyData();
    }

    private void createAddressBookDatabase() {
        try(Connection connection = DriverManager.getConnection(localhostUrl, username, password); Statement statement = connection.createStatement();) {
                String createDatabaseStatement = "CREATE DATABASE IF NOT EXISTS address_book;";
                statement.executeUpdate(createDatabaseStatement);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void createAddressBookTables() {
        try(Connection connection = getConnection(); Statement statement = connection.createStatement();) {
                String createPersonTableStatement = "CREATE TABLE IF NOT EXISTS person (person_id INT AUTO_INCREMENT PRIMARY KEY, firstname VARCHAR(255), lastname VARCHAR(255), birthday VARCHAR(255))";
                String createAddressTableStatement = "CREATE TABLE IF NOT EXISTS address (address_id INT AUTO_INCREMENT PRIMARY KEY, person_id INT, street VARCHAR(255), house_number VARCHAR(255), postal_code VARCHAR(5), city VARCHAR(255), is_work BIT(1), FOREIGN KEY (person_id) REFERENCES person (person_id) ON DELETE CASCADE)";
                String createPhoneTableStatement = "CREATE TABLE IF NOT EXISTS phone (phone_id INT AUTO_INCREMENT PRIMARY KEY, person_id INT, landline_number VARCHAR(20), mobile_number VARCHAR(20), is_work BIT(1), FOREIGN KEY (person_id) REFERENCES person (person_id) ON DELETE CASCADE)";
                statement.executeUpdate(createPersonTableStatement);
                statement.executeUpdate(createAddressTableStatement);
                statement.executeUpdate(createPhoneTableStatement);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDummyData() {
        try(Connection connection = getConnection(); Statement statement = connection.createStatement();) {
                String createPersonTableStatement = "INSERT INTO person (firstname, lastname, birthday) VALUES ('Max', 'Mustermann', '01.01.1990'), ('John', 'Doe', '31.12.1980')";
                String createAddressTableStatement = "INSERT INTO address (person_id, street, house_number, postal_code, city, is_work) VALUES (1, 'Musterstraße', '123', '12345', 'Musterstadt', 0), (2, 'RandomStreet', '321', '54321', 'RandomCity', 1)";
                String createPhoneTableStatement = "INSERT INTO phone (person_id, landline_number, mobile_number, is_work) VALUES(1, '0251123456', '0152123456', 0), (2, '0251654321', '0152654321', 1)";
                statement.executeUpdate(createPersonTableStatement);
                statement.executeUpdate(createAddressTableStatement);
                statement.executeUpdate(createPhoneTableStatement);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}