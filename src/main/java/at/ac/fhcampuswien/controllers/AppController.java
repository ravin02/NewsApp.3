package at.ac.fhcampuswien.controllers;

import at.ac.fhcampuswien.api.NewsApi;
import at.ac.fhcampuswien.downloader.Downloader;
import at.ac.fhcampuswien.enums.Country;
import at.ac.fhcampuswien.enums.Endpoint;
import at.ac.fhcampuswien.models.Article;
import at.ac.fhcampuswien.models.NewsResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AppController {
    private List<Article> articles;

    public AppController() {}

    public void setArticles(List<Article> articles){
        this.articles = articles;
    }

    public List<Article> getArticles(){
        return articles;
    }

    // Method is needed for exercise 4 - ignore for exercise 2 solution
    // returns number of downloaded article urls
    public int downloadURLs(Downloader downloader) {
        List<String> urls = new ArrayList<>();

        //TODO extract urls from articles with java stream

        return downloader.process(urls);
    }
    /**
     * gets the size of last fetched articles
     * @return size of article list
     */
    public int getArticleCount(){
        if(articles != null) {
            return articles.size();
        }
        return 0;
    }

    /**
     * get the top headlines from austria via newsapi
     * @return article list
     */
    public List<Article> getTopHeadlinesAustria() {
        NewsApi api = new NewsApi("corona", Country.at, Endpoint.TOP_HEADLINES);
        NewsResponse response = api.requestData();

        if(response != null){
            articles = response.getArticles();
            return response.getArticles();
        }
        return new ArrayList<>();
    }
    public String printSourceMostArticles(){
        if(articles==null){
            return "There were no articles found";
        }else{
            return articles
                    .stream()
                    .max(Comparator.comparing(article -> article.getSource().getName()))
                    .get().getSource().getName();
        }
    }

    public String printAuthorLongestName(){
        if(articles==null){
            return "There were no articles found";
        }else{
            return articles.stream()
                    .max(Comparator.comparing(article -> article.getAuthor().length()))
                    .get().getAuthor();
        }
    }

    public List<Article> getYourKeyword(String input) {

        NewsApi api = new NewsApi(input, Endpoint.EVERYTHING);
        NewsResponse response = api.requestData();

        if(response != null) {
            articles = response.getArticles();
            return response.getArticles();
        }

        return new ArrayList<>();
    }


    public int getNewYorkTimesArticleCount() {
        if (articles == null) {
            return 0;
        } else {
            return (int) articles
                    .stream()
                    .filter(article -> article
                            .getSource()
                            .getName()
                            .toLowerCase()
                            .contains("new york times"))
                    .count();
        }
    }

    public List<Article> printArticlesUnder15() {
        if (articles == null) {
            return new ArrayList<>();
        } else {
            return articles
                    .stream()
                    .filter(article -> article
                            .getTitle()
                            .length() < 15).collect(Collectors.toList());
        }

    }
    /**
     * returns all articles that do contain "bitcoin"
     * in their title from newsapi
     * @return filtered list
     */
    public List<Article> getAllNewsBitcoin() {
        NewsApi api = new NewsApi("bitcoin", Endpoint.EVERYTHING);
        NewsResponse response = api.requestData();

        if(response != null) {
            articles = response.getArticles();
            return response.getArticles();
        }
        return new ArrayList<>();
    }

    /**
     * filters a given article list based on a query
     * @param query to filter by
     * @param articles  list to filter
     * @return filtered list
     */
    private static List<Article> filterList(String query, List<Article> articles){
        List<Article> filtered = new ArrayList<>();
        for(Article i : articles){
            if(i.getTitle().toLowerCase().contains(query)){
                filtered.add(i);
            }
        }
        return filtered;
    }
}
