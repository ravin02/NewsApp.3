package at.ac.fhcampuswien.exceptions;

public class NewsApiExceptions extends Exception{

    //Custom Message
    public NewsApiExceptions(String message) {
        super(message);
    }

    //Standard message
    public NewsApiExceptions() {
        System.out.println("Something went wrong!");
    }
}
