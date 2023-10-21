package UsedBooksMarket;

import java.util.*;

public class UserBookManager {
    private static List<Book> books = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    public static void main(String[] args){
        displayGreetingText();

        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();

        do{
            switch (option){
                case "1":
                    System.out.println("Find book by title");
                    findBookByTitle();
                    break;
                case "2":
                    addNewBook();
                    break;
                case "3":
                    getAllBooks();
                    break;
                case "4":
                    deleteBook();
                    break;
                case "5":
                    sortBooksByTitle();
                    break;
                case "6":
                    createUser();
                    break;
                case "7":
                    getUsers();
                    break;
                case "8":
                    deleteUserById();
                    break;
                case "10":

                default:
                    System.out.println("Invalid option");
            }
            option = scanner.nextLine();
        }while(!option.equals("10"));

    }

    public static void displayGreetingText(){
        System.out.println("Welcome to used book market!");
        System.out.println("============================");
        System.out.println("On this platform you can add and sell your old(or not so old books) AND buy used books of your interest.");
        showOptions();
    }
    public static void showOptions(){
        System.out.println("1 ==> Find book by title"+"     "+
                "2 ==> Add new book"+"     "+
                "3 ==> All books"+
                "\n4 ==> Delete book (type title)"+"     "+
                "5 ==> Sort books by title"+
                "\n6 ==> Add user"+"     "+
                "7 ==> All users"+"     "+
                "8 ==> Delete user");
    }
    public static void addNewBook(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter book's title:");
        String title = input.nextLine();
        System.out.println("Author:");
        String author = input.nextLine();
        System.out.println("Category:");
        Category category = Category.valueOf(input.next().toUpperCase());
        System.out.println("Price:");
        double price = input.nextDouble();

        Book newBookInfo = new Book(title, author, category, price);
        books.add(newBookInfo);
        System.out.println(newBookInfo);
        showOptions();

    }
    public static List<Book> getAllBooks() {
        if(books.isEmpty()){
            System.out.println("List is empty!");
        }
        System.out.println(books);
        showOptions();
        return books;
    }
    public static void deleteBook(){
        Scanner input = new Scanner(System.in);
        System.out.println("Which book you want to delete:");
        String title = input.nextLine();

        Iterator<Book> iterator = books.iterator();
        while(iterator.hasNext()){
            Book book = iterator.next();
            if (title.equals(book.getTitle())){
                iterator.remove();
                System.out.println("Book " + "'" + title + "'" + " has been deleted.");
                showOptions();
                return;
            }
        }
       System.out.println("No book with this title.");
       showOptions();
    }
    public static void sortBooksByTitle(){
        Collections.sort(books, Comparator.comparing(Book::getTitle));
    }
    public static void sortBooksByAuthor(){
        Comparator<Book> compareByAuthor = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getAuthor().compareTo(o2.getAuthor());
            }
        };
    }
    public static void findBookByTitle() {
        Scanner scanner = new Scanner(System.in);
        String bookTitle = scanner.nextLine();

        Book foundBook = null;
        for (Book book : books) {
            if(book.getTitle().equals(bookTitle)) {
                foundBook = book;
                break;
            }else{
                System.out.println("Nothing matches your search.");
                break;
            }
        }
        System.out.println(foundBook);
    }

    public static void createUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("CREATE USER");
        System.out.println("\nFull name:");
        String fullName = scanner.nextLine();
        System.out.println("Email:");
        String email = scanner.nextLine();
        System.out.println("Telephone:");
        String phone = scanner.next();

        User user = new User(fullName,email);
        users.add(user);
        System.out.println("User has been created.");

        showOptions();
    }
    public static List<User> getUsers() {
        if(users.isEmpty()){
            System.out.println("List is empty.");
        }else {
            System.out.println(users);
        }
        showOptions();
        return users;

    }

    public static void deleteUserById(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user ID:");
        Long userId = scanner.nextLong();

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if(userId.equals(user.getId())){
                iterator.remove();
                System.out.println("User with ID: "+ userId+ " has been deleted.");
                showOptions();
                return;
            }
        }
        System.out.println("Invalid ID.");
        showOptions();
    }
    //        book1.setCategory(Category.OTHER);
//        book2.setCategory(Category.NOVEL);
//        book3.setCategory(Category.FANTASY);
//        book4.setCategory(Category.FAIRY_TALES);
//        book5.setCategory(Category.HORROR);
//        book1.setStatus(Status.AVAILABLE);
//==============SORT BOOKS BY CATEGORY==============================
//        Comparator<Book> compareByCategory = new Comparator<Book>() {
//            @Override
//            public int compare(Book o1, Book o2) {
//                return o1.getCategory().getDescription().compareTo(o2.getCategory().getDescription());
//            }
//        };
//        Collections.sort(books, compareByCategory);
//        System.out.println(books);


}

// search book by keyword;

