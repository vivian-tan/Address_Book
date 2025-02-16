package entries;

public class PersonBuilder {
    long id;
    String firstname;
    String lastname;
    String birthday;

    public PersonBuilder id(long id) {
        this.id = id;
        return this;
    }

    public PersonBuilder firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public PersonBuilder lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public PersonBuilder birthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public Person build() {
        return new Person(this);
    }
}
