package at.ac.fhcampuswien.ui;

import at.ac.fhcampuswien.controllers.AppController;
import at.ac.fhcampuswien.downloader.ParallelDownloader;
import at.ac.fhcampuswien.downloader.SequentialDownloader;
import at.ac.fhcampuswien.exceptions.NewsApiExceptions;
import at.ac.fhcampuswien.models.Article;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final String INVALID_INPUT_MESSAGE = "No valid input. Try again";
    private static final String EXIT_MESSAGE = "Bye bye!";
    private AppController controller;

    public void start(){
        String input;
        controller = new AppController();

        do{
            System.out.println(getMenuText());
            input = readLine();
            handleInput(input);
        } while(!input.equals("q"));

    }

    private void handleInput(String input){
        try {
            switch (input) {

                case "a" -> getTopHeadlinesAustria(controller);
                case "b" -> getAllNewsBitcoin(controller);
                case "y" -> getArticleCount(controller);
                case "q" -> printExitMessage();
                case "m" -> getYourKeyword(controller);
                case "h" -> downloadURLs();
                case "c" -> printSourceMostArticles(controller);
                case "d" -> printAuthorLongestName(controller);
                case "e" -> getNewYorkTimesArticleCount(controller);
                case "f" -> printArticlesUnder15(controller);
                default -> printInvalidInputMessage();
            }

        }catch(NewsApiExceptions e){
            System.out.println(e.getMessage());
        }

    }

    private void getYourKeyword(AppController controller) throws NewsApiExceptions{

        System.out.println("Choose your keyword!");
        String input;
        Scanner scanner = new Scanner(System.in);
        input = scanner.next();

        try {
            System.out.println(controller.getYourKeyword(input));
        } catch (NullPointerException e){
            System.out.println("There are no articles... Search for another keyword!");
        }

    }


    // Method is needed for exercise 4 - ignore for exercise 2 solution
    private void downloadURLs(){
        int resultSequential = controller.downloadURLs(new SequentialDownloader());
        // TODO print time in ms it took to download URLs sequentially

        // TODO implement the process() function in ParallelDownloader class
        int resultParallel = controller.downloadURLs(new ParallelDownloader());

        // TODO print time in ms it took to download URLs parallel
    }

    private void getArticleCount(AppController controller) throws NewsApiExceptions {
        try{
            System.out.println("Number of articles: " + controller.getArticleCount());
        }catch (NullPointerException e){
            System.out.println("There are no articles!");
        }

    }

    private void getTopHeadlinesAustria(AppController controller) throws NewsApiExceptions{

        try {
            System.out.println(controller.getTopHeadlinesAustria());
        } catch (NullPointerException e) {
            System.out.println("There are no top headlines in Austria!");
        }
    }

    private void getAllNewsBitcoin(AppController controller)  {

        try{
            System.out.println(controller.getAllNewsBitcoin());
        }catch(NullPointerException e){
            System.out.println("There are no bitcoin news!");
        }
    }

    private void printSourceMostArticles(AppController controller){
        System.out.println(controller.printSourceMostArticles());
    }
    private void printAuthorLongestName(AppController controller){
        System.out.println(controller.printAuthorLongestName());
    }

    private void getNewYorkTimesArticleCount(AppController controller){
        System.out.println(controller.getNewYorkTimesArticleCount());
    }
    private void printArticlesUnder15(AppController controller){
        System.out.println(controller.printArticlesUnder15());
    }

    public static void printExitMessage(){
        System.out.println(EXIT_MESSAGE);
    }

    public static void printInvalidInputMessage(){
        System.out.println(INVALID_INPUT_MESSAGE);
    }
    
    private static String getMenuText(){
        return """
                *****************************
                *   Welcome to NewsApp   *
                *****************************
                Enter what you wanna do:
                a: Get top headlines austria
                b: Get all news about bitcoin
                y: Get article count
                q: Quit program
                c: Get provider with most articles
                m: Get articles via keyword
                d: Get longest author name
                e: Count articles from NY Times
                f: Get articles with short title
                g: Sort articles by content length
                h: Download URLs
                """;
    }

    private static String readLine() {
        String value;
        Scanner scanner = new Scanner(System.in);
        value = scanner.nextLine();
        return value.trim();
    }

}
