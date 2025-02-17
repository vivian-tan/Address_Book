package entries;

public class Person {
    private long id;
    private String firstname;
    private String lastname;
    private String birthday;

    Person(PersonBuilder builder) {
        this.id = builder.id;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.birthday = builder.birthday;
    }
    
    public long getId() {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getBirthday() {
        return birthday;
    }
}
