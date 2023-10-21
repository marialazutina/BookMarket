package UsedBooksMarket;

import java.util.Random;

public class Book{
    Long id;
    String title;
    String author;
    Category category;
//    String language;
    Status status = Status.AVAILABLE;
    double price;

    String image;

    public Book() {

    }
    public Long generateId(){
        Random random = new Random();
        return random.nextLong(1000000);
    }
    public Book(String title, String author, Category category, double price) {
        this.id = generateId();
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory(){
        return category;
    }
    public void setCategory(Category category){
        this.category = category;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Status getStatus(){
        return status;
    }


    public String toString(){
        return "Book ID:" + id +
                "\ntitle: "+  title +
                "\nauthor: " + author +
                "\ncategory: " + category+
                "\nprice: "+ price + " EUR"+
                "\nstatus: "+ status + ";\n";
    }
    public int compare(Book o1, Book o2) {
        return o1.getAuthor().compareTo(o2.getAuthor());
    }

    public boolean isAvailable(){
        if(getStatus() == Status.AVAILABLE){
            return true;
        }
        return false;
    }

}
