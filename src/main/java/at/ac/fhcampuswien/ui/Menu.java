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
                case "h" -> downloadURLs();
                case "e" -> getNewYorkTimesArticleCount(controller);
                case "f" -> printArticlesUnder15(controller);
                default -> printInvalidInputMessage();
            }

        }catch(NewsApiExceptions e){
            System.out.println(e.getMessage());
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
        if(controller.getArticles() == null) {
            throw new NewsApiExceptions("There are no articles yet! Search for some!");
        }else
        System.out.println("Number of articles: " + controller.getArticleCount());
    }

    private void getTopHeadlinesAustria(AppController controller) throws NewsApiExceptions{

        if(controller.getTopHeadlinesAustria() == null) {
            throw new NewsApiExceptions("There are no Top-headlines in Austria");
        }

        List<Article> articleList = controller.getTopHeadlinesAustria();

        for( Article a : articleList) {
            System.out.println(a);
        }
    }

    private void getAllNewsBitcoin(AppController controller)  {

        System.out.println(controller.getAllNewsBitcoin());
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
