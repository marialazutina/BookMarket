package BookMarketJdbc;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserService {

    public static void signUp() throws SQLException{
        try {
            Connection connection = UsedBookManager.getConnection();
            System.out.println("Welcome to profile manager system.");
            System.out.println("---------------------------------");
            System.out.println("Please fill in the application form below.");

            System.out.println("Full name: ");
            Scanner scanner = new Scanner(System.in);
            String fullName = scanner.nextLine();

            System.out.println("Date of birth (format yyyy-mm-dd): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine());

            System.out.println("Phone number: ");
            String phoneNum = scanner.nextLine();
            if(phoneNum.length() > 12){
                System.out.println("Too long phone number. Check and insert again.");
                phoneNum = scanner.nextLine();
            }

            System.out.println("Email: ");
            String email = scanner.nextLine();

            while(!isEmailValid(email)){
                System.out.println("Invalid email. Check and insert again.");
                email = scanner.nextLine();
            }

            System.out.println("Password: ");
            String password = scanner.next();

            String insertQuery = "INSERT INTO usedbookmarket.user(fullName, dob, phone, email, password) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, fullName);
            preparedStatement.setDate(2, Date.valueOf(dob));
            preparedStatement.setString(3,phoneNum);
            preparedStatement.setString(4,email);
            preparedStatement.setString(5,password);

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if(generatedKeys.next()){
                int userId = generatedKeys.getInt(1);
                System.out.println("New user has been successfully created!");
                System.out.println(new User(userId,fullName,email, dob, phoneNum));
            }else{
                throw new SQLException("Signup failed. Unable to get user ID.");
            }


            UsedBookManager.menuBeforeLogin();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    public static boolean isEmailValid(String email){
        String userEmail = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(userEmail);
        return pattern.matcher(email).matches();
    }

    public static void login() throws SQLException{
        Connection connection = UsedBookManager.getConnection();

        System.out.println("LOG IN");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Email: ");
        String userEmail = scanner.nextLine();
        System.out.print("Password: ");
        String userPassword = scanner.nextLine();

        String findUser = "SELECT * FROM usedbookmarket.user WHERE email = ? and password = ?";
        try{
            PreparedStatement pStatement = connection.prepareStatement(findUser);
            pStatement.setString(1,userEmail);
            pStatement.setString(2,userPassword);

            ResultSet resultSet = pStatement.executeQuery();

            String originalEmail = "";
            String originalPassword = "";

            while(resultSet.next()){
                originalEmail = resultSet.getNString("email");
                originalPassword = resultSet.getNString("password");
            }

            if(originalEmail.equals(userEmail) && originalPassword.equals(userPassword)){
                System.out.println("Login successful.");
                UsedBookManager.menuAfterLogin();

            }else{
                System.out.println("User with this email or password does not exist.");
                login();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void viewProfile() throws SQLException{
        Connection connection = UsedBookManager.getConnection();
        System.out.print("Confirm your ID: ");
        Scanner scanner = new Scanner(System.in);
        int profileId = scanner.nextInt();

        String seeUserInfo = "SELECT * FROM usedbookmarket.user WHERE id = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(seeUserInfo);
            preparedStatement.setInt(1, profileId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("Name: " + resultSet.getString("fullname") +
                        "\nID: " + resultSet.getInt("id") +
                        "\nDate of birth: " + resultSet.getDate("dob") +
                        "\nPhone number: " + resultSet.getString("phone") +
                        "\nEmail: " + resultSet.getString("email") +
                        "\nPassword: " + resultSet.getString("password")
                        );
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        UsedBookManager.viewProfileMenu();

    }

    public static void deleteProfile() throws SQLException{
        Connection connection = UsedBookManager.getConnection();

    }


}
