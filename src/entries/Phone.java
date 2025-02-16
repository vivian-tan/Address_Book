package entries;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id) && Objects.equals(landlineNumber, phone.landlineNumber) && Objects.equals(mobileNumber, phone.mobileNumber) && Objects.equals(isWork, phone.isWork);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, landlineNumber, mobileNumber, isWork);
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
