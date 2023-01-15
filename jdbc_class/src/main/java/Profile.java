import com.sun.source.tree.ClassTree;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Profile {
    public static void main(String[] args){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "admin");
//            signUp(connection);
//            logIn(connection);
//            updateProfile(connection);
//            deleteProfile(connection);
            sendMessage(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String address;
    private String gender;
    private int age;
    private String password;
    private String username;

    public Profile(){}
    public Profile(String firstname, String lastname, String phone, String email, String address, String gender, int age, String password, String username){
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.password = password;
        this.username = getUsername(firstname,lastname,phone);
    }

    public String getFirstname(){
        return  firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public String getPhone(){
        return phone;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress() {
        return address;
    }
    public String getGender(){
        return gender;
    }
    public int getAge(){
        return age;
    }
    public String getPassword(){
        return password;
    }
    public static String getUsername(String firstname, String lastname, String phone){
        String newUsername = firstname.toLowerCase().trim()+lastname.toLowerCase().trim()+phone.substring(phone.length()-2);
        return newUsername;
    }
    public static void signUp(Connection connection){
        System.out.println("Welcome to profile management system.");
        System.out.println("---------------------------------");
        System.out.println("Please fill in the application form below.");

        System.out.println("First name: ");
        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();

        System.out.println("Last name: ");
        String lastName = scanner.nextLine();

        System.out.println("Phone number: ");
        String phoneNum = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        while(!isEmailValid(email)) {
            System.out.println("Invalid email. Try again.");
            email = scanner.nextLine();
        }

        System.out.println("Address: ");
        String address = scanner.nextLine();

        System.out.println("Gender: ");
        String gender = scanner.nextLine();

        System.out.println("Age: ");
        int age = scanner.nextInt();

        System.out.println("Password: ");
        String password = scanner.next();

        try{
            String insertQuery = "INSERT INTO demo.Profile(firstname, lastname, phone, email, address, gender, age, password) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pStatement = connection.prepareStatement(insertQuery);
            pStatement.setString(1, firstName);
            pStatement.setString(2, lastName);
            pStatement.setString(3, phoneNum);
            pStatement.setString(4, email);
            pStatement.setString(5, address);
            pStatement.setString(6, gender);
            pStatement.setInt(7, age);
            pStatement.setString(8, password);

            pStatement.executeUpdate();

            System.out.println("NEW USER CREATED=====>\nRemember your USERNAME: "+ getUsername(firstName,lastName,phoneNum)+" and PASSWORD: "+password+".");

        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("\n================================\n");
        logIn(connection);

    }
    public static void logIn(Connection connection){
        Scanner scanner = new Scanner(System.in);
        System.out.println("LOG IN FORM");
        System.out.println("====================================");

        System.out.println("Username: ");
        String username = scanner.nextLine();

        System.out.println("Password: ");
        String password = scanner.nextLine();

        String getUsernameAndPassword = "SELECT * FROM demo.Profile WHERE username = ? and password = ?";

        try{
            PreparedStatement pStatement = connection.prepareStatement(getUsernameAndPassword);
            pStatement.setString(1,username);
            pStatement.setString(2,password);

            ResultSet resultSet = pStatement.executeQuery();

            String usernameOriginal = "";
            String passwordOriginal = "";
            while(resultSet.next()) {
                usernameOriginal = resultSet.getNString("username");
                passwordOriginal = resultSet.getNString("password");
            }
            if(usernameOriginal.equals(username) && passwordOriginal.equals(password)){
                System.out.println("Log in successful!");

                System.out.println("UPDATE(1) or SEND MESSAGE(2)");
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();

                if(option == 1){
                    updateProfile(connection);
                }else if(option == 2){
//                    deleteProfile(connection);
                }else {
                    System.out.println("No such option. Choose 1 or 2 to proceed");
                    option = scanner.nextInt();
                }
            }else{
                System.out.println("Something went wrong! Please check your username or password!");
                logIn(connection);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateProfile(Connection connection) throws SQLException {
        Scanner updateProfile = new Scanner(System.in);
        System.out.println("UPDATE FORM\n ===============================\nChoose what you want to update:" +
                "\n1.First name.\n2.Last name.\n3.Phone.\n4.Email.\n5.Address.\n6.Gender.\n7.Age.\n8.Password.");

        String field = updateProfile.nextLine();

        switch (field) {
            case ("1"):
                updateName(connection);
                break;
            case ("2"):
                updateLastname(connection);
                break;
//            case ("3"):
//                updatePhone();
//                break;
//            case ("4"):
//                updateEmail();
//                break;
//            case ("5"):
//                updateAddress();
//                break;
//            case ("6"):
//                updateGender();
//                break;
//            case ("7"):
//                updateAge();
//                break;
//            case ("8"):
//                updatePassword();
//                break;
            default:
                break;
        }
    }

    public static void updateName(Connection connection) throws SQLException{

        Scanner updateName = new Scanner(System.in);
        System.out.println("Enter profile ID: ");
        int profileId = updateName.nextInt();

        System.out.println("Insert new first name: ");
        String newFirstname = updateName.next();

        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE demo.Profile SET firstname = '"+newFirstname+ "' WHERE id = "+profileId);

        String getNewUsername = "SELECT username FROM demo.Profile WHERE id="+profileId;
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(getNewUsername);

        while (resultSet.next()){
            String updatedUsername = resultSet.getString("username");
            System.out.println("Your new username is:" + updatedUsername);
        }
    }

    public static void updateLastname(Connection connection) throws SQLException{

        Scanner updateName = new Scanner(System.in);
        System.out.println("Enter profile ID: ");
        int profileId = updateName.nextInt();

        System.out.println("Insert new last name: ");
        String newLastname = updateName.next();

        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE demo.Profile SET lastname = '"+newLastname+ "' WHERE id = "+profileId);

        String getNewUsername = "SELECT username FROM demo.Profile WHERE id="+profileId;
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(getNewUsername);

        while (resultSet.next()){
            String updatedUsername = resultSet.getString("username");
            System.out.println("Your new username is:" + updatedUsername);
        }

    }
    public static void deleteProfile(Connection connection){
        Scanner scanner = new Scanner(System.in);
        System.out.println("DELETE PROFILE");
        System.out.println("====================================");

        System.out.println("Insert profile id: ");
        int profileId = scanner.nextInt();

        try{
            Statement statement = connection.createStatement();
            String deleteThisProfile = "DELETE FROM demo.Profile WHERE id="+profileId;

            statement.executeUpdate(deleteThisProfile);
            System.out.println("Profile with id "+profileId + " was deleted from system!");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public static boolean isEmailValid(String email){
        String email1 = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(email1);
        return pattern.matcher(email).matches();
    }

    public static void sendMessage(Connection connection) throws SQLException{

        logIn(connection);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM demo.Profile");
        System.out.println("LIST OF USERS\n");

        while (resultSet.next()){
            System.out.print("id: "+ resultSet.getInt("id")+" ==> ");
            System.out.println("username: "+resultSet.getString("username"));
        }

        System.out.println("Choose user you want to chat (ID)");
        Scanner scanner = new Scanner(System.in);
        int idReceiver = scanner.nextInt();

        Statement statement1 = connection.createStatement();
        ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM demo.Profile WHERE id="+idReceiver);

        while(resultSet1.next()){
            System.out.println("Chat open with user "+resultSet1.getString("username"));
        }

        System.out.println("Type message:");
        Scanner scanner1 = new Scanner(System.in);
        String message = scanner1.next();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO demo.Message")
    }


}
