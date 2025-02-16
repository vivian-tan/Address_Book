package entries;

public class PhoneBuilder {
    long id;
    String landlineNumber;
    String mobileNumber;
    boolean isWork;

    public PhoneBuilder id(long id) {
        this.id = id;
        return this;
    }

    public PhoneBuilder landlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
        return this;
    }

    public PhoneBuilder mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public PhoneBuilder isWork(boolean isWork) {
        this.isWork = isWork;
        return this;
    }

    public Phone build() {
        return new Phone(this);
    }
}
