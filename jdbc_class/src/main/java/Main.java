import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException{

//        signUp();
//        logIn(getConnection(),"AlicePo96","lololo");
//        deleteProfile(4);
        updateProfile(3);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "admin");
    }

    public static void signUp() throws SQLException {
        Connection connection1 = getConnection();
        System.out.println("Welcome to profile manager system.");
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

        System.out.println("Address: ");
        String address = scanner.nextLine();

        System.out.println("Gender: ");
        String gender = scanner.nextLine();

        System.out.println("Age: ");
        int age = scanner.nextInt();

        System.out.println("Password: ");
        String password = scanner.next();

        String insertQuery = "INSERT INTO demo.Profile(firstname, lastname, phone, email, address, gender, age, password) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement pStatement = connection1.prepareStatement(insertQuery);
        pStatement.setString(1, firstName);
        pStatement.setString(2, lastName);
        pStatement.setString(3, phoneNum);
        pStatement.setString(4, email);
        pStatement.setString(5, address);
        pStatement.setString(6, gender);
        pStatement.setInt(7, age);
        pStatement.setString(8, password);

        ResultSet resultSet = pStatement.executeQuery();

        while (resultSet.next()){
            System.out.println("User "+ resultSet.getString(10)+" has been created!");
        }

        connection1.close();
    }

    public static boolean logIn(Connection connection, String username, String password){
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
                return true;
            }else{
                System.out.println("Something went wrong! Please check your username or password!");
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void updateProfile(int profileId){
        Scanner updateMenu = new Scanner(System.in);
        String updateProfileQuery = "UPDATE demo.Profile SET firstname = ?, lastname = ?, phone = ?, email = ?, address = ?, gender = ?, age = ?, password = ?  WHERE id ="+profileId;

        System.out.println("Update first name: ");
        String firstName = updateMenu.nextLine();

        System.out.println("Update last name: ");
        String lastName = updateMenu.nextLine();

        System.out.println("Update phone number: ");
        String phoneNum = updateMenu.nextLine();

        System.out.println("Update email: ");
        String email = updateMenu.nextLine();

        System.out.println("Update address: ");
        String address = updateMenu.nextLine();

        System.out.println("Update gender: ");
        String gender = updateMenu.nextLine();

        System.out.println("Update age: ");
        int age = updateMenu.nextInt();

        System.out.println("Update password: ");
        String password = updateMenu.next();
//        System.out.println("update profile: ======" +
//                "====1.Name ====2.Surname ====3.Phone nr." +
//                "====4.Email ====5.Address ====6.Gender" +
//                "====7.Age ====8.Password" +
//                "====Choose field for update.");
//        String field = "";
//
//        do{
//            field = updateMenu.nextLine();
//            switch (field){
//                case "1":
//                    String name = updateMenu.nextLine();
//                case "2":
//                case "3":
//                case "4":
//                case "5":
//                case "6":
//                case "7":
//                case "8":
//            }
//        }

        try{
            PreparedStatement pStatement = getConnection().prepareStatement(updateProfileQuery);
            pStatement.setString(1, firstName);
            pStatement.setString(2, lastName);
            pStatement.setString(3, phoneNum);
            pStatement.setString(4, email);
            pStatement.setString(5, address);
            pStatement.setString(6, gender);
            pStatement.setInt(7, age);
            pStatement.setString(8, password);

            pStatement.executeUpdate();
            System.out.println("Update has been made...");

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void deleteProfile(int profileId){
//        Scanner delete = new Scanner(System.in);

        try{
            Statement statement = getConnection().createStatement();

//            System.out.println("Enter profile ID you want to delete: ");
//            int id = delete.nextInt();
//
            String deleteThisProfile = "DELETE FROM demo.Profile WHERE id="+profileId;
            int deleteProfile = statement.executeUpdate(deleteThisProfile);
            System.out.println(deleteProfile + " profile deleted from system!");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}

