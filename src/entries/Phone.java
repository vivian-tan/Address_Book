package entries;

public class Phone {
    private long id;
    private String landlineNumber;
    private String mobileNumber;
    private boolean isWork;

    public Phone(PhoneBuilder builder) {
        this.id = builder.id;
        this.landlineNumber = builder.landlineNumber;
        this.mobileNumber = builder.mobileNumber;
        this.isWork = builder.isWork;
    }

    public long getId(){
        return id;
    }
    public String getLandlineNumber() {
        return landlineNumber;
    }

    public void setLandlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean isWork) {
        this.isWork = isWork;
    }
}
