package BookMarketJdbc;

public enum Category {
    DETECTIVE("Detective"),
    SCIENCE("Science"),
    ART("Art"),
    NOVEL("Novel"),
    ADVENTURE("Adventure"),
    CLASSICS("Classics"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    FAIRY_TALES("Fairy tales"),
    ROMANCE("Romance"),
    OTHER("Other");

    private String description;
    Category(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
