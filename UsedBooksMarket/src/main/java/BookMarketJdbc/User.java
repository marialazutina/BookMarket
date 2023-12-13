package BookMarketJdbc;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class User {
    private int id;
    private String fullName;
    private LocalDate dob;
    private String phoneNumber;
    private String email;
    private String password;
    private boolean isActive = true;
//    public Long generateId(){
//        Random random = new Random();
//        return random.nextLong(100000);
//    }
    public User(int id, String fullName, String email, LocalDate dob, String phoneNumber){
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public int getId(){
        return id;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
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

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String toString(){
        return "User id: " + id +
                "\nfull name: " + fullName +
                "\ndate of birth: " + dob +
                "\nphone number: " + phoneNumber +
                "\nemail: " + email;
    }
}
