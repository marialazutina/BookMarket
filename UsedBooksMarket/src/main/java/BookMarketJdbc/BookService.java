package BookMarketJdbc;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

import static BookMarketJdbc.Books.books;

public class BookService {
    /* Application allows to:
     - create new book; done
     - show all books; done;
     - delete book using ID; done
     - sort books by title; done
     - sort books by author; done
     - find book by title; done
     - find book by keyword (in title); done
     - buy book;
     --- for status make option 1.2.3.---
    */
    public static void addNewBook() throws SQLException {

        try {
            Connection connection1 = UsedBookManager.getConnection();

            Scanner input = new Scanner(System.in);
            System.out.println("Enter book's title:");
            String title = input.nextLine();
            System.out.println("Author:");
            String author = input.nextLine();
//            System.out.println("Status:");
//            String status1 = input.nextLine();
            System.out.println("Category (choose from the list): \n" +
                    "1. Classics   2. Detective     3. Science \n" +
                    "4. Art        5. Novel         6. Adventure \n" +
                    "7. Fantasy    8. Fairy tales   9. Other");
            int category = input.nextInt();
            System.out.println("Price:");
            double price = input.nextDouble();

//        Book newBookInfo = new Book(title, author, category, price);
//        books.add(newBookInfo);
//        System.out.println(newBookInfo);

            String addNewBook = "INSERT INTO usedbookmarket.book (title, author, price, category_id) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection1.prepareStatement(addNewBook);
            preparedStatement.setString(1,title);
            preparedStatement.setString(2,author);
//            preparedStatement.setString(3,status1);
            preparedStatement.setDouble(3,price);
            preparedStatement.setInt(4,category);


            preparedStatement.executeUpdate();
            System.out.println("Book has been successfully added to your listing.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UsedBookManager.menuAfterLogin();

    }
    public static List<Books> getAllBooks() throws SQLException{
        Connection connection1 = UsedBookManager.getConnection();
        //1. programm do not check if list is empty!!!
        //2. format list of books (display as in table
//        if(books.isEmpty()){
//            System.out.println("List is empty!");
//        }

        System.out.printf("| %-6s | %-35s | %-20s| %-10s| %-8s ","Book ID", "Title", "Author", "Status", "Price");
        String showAllBooks = "SELECT * FROM usedbookmarket.Book";

        try{
            Statement statement = connection1.createStatement();
            ResultSet resultSet = statement.executeQuery(showAllBooks);

            showResultSet(resultSet);

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\n1 - Book details  2 - Back to menu");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();


        if(option == 1){
            System.out.print("See book's details. Enter book id:");
            showBookInfo();
        }else if(option == 2){
            UsedBookManager.menuAfterLogin();
        }else{
            System.out.println("Invalid option." + scanner);
        }


/*
NEED OPTIONS BUY BOOK AND GO BACK TO MENU AFTER LOGIN
MAYBE OPTION TO OPEN BOOKS INFO (TO SEE FULL INFORMATION)
 */
//        UsedBookManager.menuBeforeLogin();
        return books;
    }

    public static void showBookInfo() throws SQLException{
        Scanner scanner = new Scanner(System.in);

        int bookId = scanner.nextInt();
        String showThisBook = "SELECT * FROM usedbookmarket.book WHERE id=" + bookId;

        try{
            Connection connection = UsedBookManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(showThisBook);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("SELECTED BOOK DETAILS \nID: " + resultSet.getInt("id") +
                        "\nTitle: " + resultSet.getString("title") +
                        "\nAuthor: " + resultSet.getString("author") +
                        "\nCategory: " + resultSet.getInt("category_id") +
                        "\nPrice: " + resultSet.getDouble("price") +
                        "\nStatus: " + resultSet.getString("status"));

                System.out.println("1. Buy book" +
                        "2. Back to menu");
                Scanner scanner3 = new Scanner(System.in);
                int action = scanner3.nextInt();

                if(action == 1){
                    buyBook(resultSet.getInt(1));
                }else if(action == 2){
                    UsedBookManager.menuAfterLogin();
                }else {
                    System.out.println("Invalid option" + scanner3);
                }
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    private static void showResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.printf("\n| %-8d", resultSet.getInt(1) );
            System.out.printf("| %-35s ", resultSet.getString(2));
            System.out.printf("| %-20s",  resultSet.getString(3));
            System.out.printf("| %-10s", resultSet.getString(4));
            System.out.printf("| %.2f", resultSet.getDouble(5));
        }
    }

    public static void deleteBook() throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Which book you wish to delete? Enter book id: ");
        int id = input.nextInt();
        Connection connection = UsedBookManager.getConnection();

        try{
            Statement statement = connection.createStatement();
            String deleteThisBook = "DELETE FROM usedbookmarket.Book WHERE id=" + id;
            statement.executeUpdate(deleteThisBook);

            System.out.println("Book with id: " + id + " was deleted from listing.");

        }catch (SQLException e){
            e.printStackTrace();
        }
//        String title = input.nextLine();
//
//        Iterator<Book> iterator = books.iterator();
//        while(iterator.hasNext()){
//            Book book = iterator.next();
//            if (title.equals(book.getTitle())){
//                iterator.remove();
//                System.out.println("Book " + "'" + title + "'" + " has been deleted.");
//                UserBookManager.showOptions();
//                return;
//            }
//        }
//        System.out.println("No book with this title.");
        UsedBookManager.menuBeforeLogin();
    }
    public static void sortBooksByTitle() throws SQLException{
//        Collections.sort(books, Comparator.comparing(Book::getTitle));
//        System.out.println(books);
        System.out.println("SORTED BOOK LIST");
        Connection connection = UsedBookManager.getConnection();
        System.out.printf("| %-6s | %-35s | %-20s| %-10s| %-8s ","Book ID", "Title", "Author", "Status", "Price");

        try {
            Statement statement = connection.createStatement();
            String sortBooksByTitle = "SELECT * FROM usedbookmarket.Book ORDER BY Title ASC";

            ResultSet resultSet = statement.executeQuery(sortBooksByTitle);
            showResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UsedBookManager.menuBeforeLogin();

    }
    public static void sortBooksByAuthor() throws SQLException{
        System.out.println("SORTED BOOK LIST");
        Connection connection = UsedBookManager.getConnection();
        System.out.printf("| %-6s | %-35s | %-20s| %-10s| %-8s ","Book ID", "Title", "Author", "Status", "Price");

        try {
            Statement statement = connection.createStatement();
            String sortBooksByAuthor = "SELECT * FROM usedbookmarket.Book ORDER BY AUTHOR ASC";

            ResultSet resultSet = statement.executeQuery(sortBooksByAuthor);
            showResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void findBookByTitle() throws SQLException{
        Scanner scanner = new Scanner(System.in);
        String bookTitle = scanner.nextLine();

        Connection connection = UsedBookManager.getConnection();
        String findBookByTitle = "SELECT * FROM usedbookmarket.Book WHERE Title=" + "'" + bookTitle + "'";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findBookByTitle);

            while(resultSet.next()){
                System.out.print(resultSet.getInt(1)+ ". ");
                System.out.print(" " + resultSet.getString(2));
                System.out.print(" " + resultSet.getString(3));
                System.out.print(" " + resultSet.getString(4));
                System.out.println(" " + resultSet.getDouble(5));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        UsedBookManager.menuBeforeLogin();

//        Book foundBook = null;
//        for (Book book : books) {
//            if(book.getTitle().equals(bookTitle)) {
//                foundBook = book;
//                break;
//            }else{
//                System.out.println("Nothing matches your search.");
//                break;
//            }
//        }
//        System.out.println(foundBook);
    }

    public static void findBookByKeyword() throws SQLException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Find book by keyword:");
        String keyWord = scanner.nextLine();

        Connection connection = UsedBookManager.getConnection();
        String findBookByKeyword = "SELECT * FROM usedbookmarket.Book WHERE Title LIKE " +"'%" + keyWord + "%'";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findBookByKeyword);

            System.out.printf("| %-6s | %-35s | %-20s| %-10s| %-8s ","Book ID", "Title", "Author", "Status", "Price");

            while(resultSet.next()){
                System.out.printf("\n| %-8d", resultSet.getInt(1) );
                System.out.printf("| %-35s ", resultSet.getString(2));
                System.out.printf("| %-20s",  resultSet.getString(3));
                System.out.printf("| %-10s", resultSet.getString(4));
                System.out.printf("| %.2f", resultSet.getDouble(5));

                System.out.println("1. Buy book" +
                        "2. Back to menu");
                Scanner scanner2 = new Scanner(System.in);
                int action = scanner2.nextInt();

                if(action == 1){
                    buyBook(resultSet.getInt(1));
                }else if(action == 2){
                    UsedBookManager.menuAfterLogin();
                }else {
                    System.out.println("Invalid option" + scanner2);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
//        Book found = null;
//
//        for(Book book: books){
//            if(book.getTitle().contains(keyWord)){
//                found = book;
//                System.out.println(found);
//                UserBookManager.showOptions();
//                break;
//            }else{
//                System.out.println("Cant find anything by mentioned keyword.");
//                UserBookManager.showOptions();
//                break;
//            }
//        }
    }

    public static void changeStatus(int bookId) throws SQLException{
        Connection connection = UsedBookManager.getConnection();
        String changeStatusToSold = "UPDATE usedbookmarket.Book SET status='SOLD' WHERE id= ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(changeStatusToSold);
            preparedStatement.setInt(1,bookId);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void buyBook() throws SQLException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which book you would like to buy. Enter book ID here:");
        int bookId = scanner.nextInt();

        String getBookById = "SELECT * FROM usedbookmarket.Book WHERE id=" + bookId;

        try{
            Connection connection = UsedBookManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getBookById);

            while(resultSet.next()) {
                if (!resultSet.getString("status").equals("available".toUpperCase())) {
                    System.out.println("Book is not available for purchase.");
                } else {
                    changeStatus(bookId);
                    System.out.println("Congrats! Books is yours.");
                }
            }

//check if book id exists,
// check if book with this id is available (if not ->message)
//if status is avaiable -> buy book -> change status to sold/reserved
        }catch (Exception e){
            e.printStackTrace();
        }

//        for(Book book: books){
//            if(book.getId().equals(bookId)){
//                while(book.getStatus().equals(Status.AVAILABLE)){
//                    book.setStatus(Status.SOLD);
//                    System.out.println("You successfully bought this book:");
//                    System.out.println(book);
//                    UserBookManager.showOptions();
//                    break;
//                }
//            }else{
//                System.out.println("Sorry, this book is unavailable");
//                UserBookManager.showOptions();
//                break;
//            }
//        }
    }

    public static void buyBook(int bookId) throws SQLException {
        String getBookById = "SELECT * FROM usedbookmarket.Book WHERE id=" + bookId;

        try {
            Connection connection = UsedBookManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getBookById);

            while (resultSet.next()) {
                if (!resultSet.getString("status").equals("available".toUpperCase())) {
                    System.out.println("Book is not available for purchase.");
                    getAllBooks();
                } else {
                    changeStatus(bookId);
                    System.out.println("Congrats! Books is yours.");
                    getAllBooks();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
