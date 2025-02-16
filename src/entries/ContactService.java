package entries;

import database.AddressBookQueries;

public class ContactService {
    private AddressBookQueries queries;

    public ContactService(AddressBookQueries queries) {
        this.queries = queries;
    }
}
