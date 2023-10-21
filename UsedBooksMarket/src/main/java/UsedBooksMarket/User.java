package UsedBooksMarket;

import java.util.Random;

public class User {
    Long id;
    String fullName;
    int dob;
    String phoneNumber;
    String email;
    public Long generateId(){
        Random random = new Random();
        return random.nextLong(100000);
    }
    public User(String fullName, String email){
        this.id = generateId();
        this.fullName = fullName;
        this.email = email;
    }
    public Long getId(){
        return id;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
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

    public String toString(){
        return "User id: " + id +
                "\nfull name: " + fullName +
                "\nemail: " + email + ".\n";
    }
}
