package BookMarketJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.spi.AbstractResourceBundleProvider;

public class UsedBookManager {
    public static void main(String[] args) throws SQLException {
        menuBeforeLogin();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/usedbookmarket", "root", "admin");
    }


    public static void menuBeforeLogin() throws SQLException{
        System.out.println("1 - Login  2 - Signup  3 - Exit");
        Scanner scanner = new Scanner(System.in);

        int option = scanner.nextInt();

        if(option == 1){
            UserService.login();

        }else if(option == 2){
            UserService.signUp();
        }
    }

    public static void menuAfterLogin() throws SQLException{
        System.out.println("1 - Add book  2 - View all books  3 - Find book by title");
        System.out.println("4 - View profile  ");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        switch(option){
            case 1:
                BookService.addNewBook();
                break;
            case 2:
                BookService.getAllBooks();
                break;
            case 3:
                BookService.findBookByKeyword();
                break;
            case 4:
                UserService.viewProfile();
                break;
            default:
                System.out.println("Invalid option");


        }
    }

    public static void viewProfileMenu() throws SQLException{
        System.out.println("1 - Update profile  2 - Delete profile  3 - Back to menu");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        if(option == 1){
//            UserService.updateProfile();
        }else if(option == 2){
            UserService.deleteProfile();
        }else if(option == 3){
            menuAfterLogin();
        }else{
            System.out.println("Invalid option");
            option = scanner.nextInt();
        }
    }
}
