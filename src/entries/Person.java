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
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
