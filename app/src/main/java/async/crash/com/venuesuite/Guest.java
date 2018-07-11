package async.crash.com.venuesuite;

import java.util.Date;

/**
 * Created by mitchthornton on 7/7/18.
 */

public class Guest {

    private String firstName, lastName, name, phoneNumber, email, phone;
    private int partySize;
    private boolean hasEntered;
    private Date birthday;
    private User addedBy;


    // Constructors

    // Required fields
    public Guest(String firstName, String lastName, User addedBy, int partySize) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addedBy = addedBy;
        this.partySize = partySize;
        this.hasEntered = false;
    }

    public Guest(String firstName, String lastName, String phoneNumber, String email, String phone, Date birthday, User addedBy, int partySize) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.addedBy = addedBy;
        this.partySize = partySize;
        this.hasEntered = false;

    }


    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " +  lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String firstName, String lastName){
        this.name = firstName + " " + lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public boolean hasEntered() {
        return hasEntered;
    }

    public void setHasEntered(boolean hasEntered) {
        this.hasEntered = hasEntered;
    }
}
