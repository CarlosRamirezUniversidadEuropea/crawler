package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CrawlerExtractor {

    private HashSet<String> links;
    private List<List<String>> articles;

    public CrawlerExtractor() {
        links = new HashSet<String>();
        articles = new ArrayList<List<String>>();
    }
//TEST
    //Find all URLs that start with "https://www.codigococina.com/page" and add them to the HashSet
    public void getPageLinks(String URL) {
        if (!links.contains(URL)) {
            try {
                //TODO conectarse a la URL con Jsoup
                //
                Document document = null;

                //TODO
                Elements otherLinks = document.select("TODO");

                for (Element page : otherLinks) {
                    if (links.add(URL)) {
                        //Remove the comment from the line below if you want to see it running on your editor
                        System.out.println(URL);
                    }
                    //Url absoluta de ese atributo
                    //TODO
                    getPageLinks(page.attr("TODO"));
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //Connect to each link saved in the article and find all the articles in the page
    public void getArticles() {
        for (String item: links){
            //TODO
            Document document= null;
            try {
                //TODO
                //Conectarse a url

                //Selecccionar elementos con h4 y dentro de estos los hijos con etiqueta a[...]
                //TODO
                Elements articleLinks = document.select("");
                for (Element article : articleLinks) {
                    //Only retrieve the titles of the articles that contain Java 8
                    //TODO regex
                    if (article.text().matches("Regex TODO")) {
                        //Remove the comment from the line below if you want to see it running on your editor,
                        //or wait for the File at the end of the execution
                        //System.out.println(article.attr("abs:href"));

                        ArrayList<String> temporary = new ArrayList<String>();
                        temporary.add(article.text()); //The title of the article
                        //TODO
                        temporary.add(article.attr("")); //The URL of the article
                        articles.add(temporary);
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void writeToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            for(List<String> articlesUrls : articles)
                try {
                    //TODO
                    //Escribir en fichero las urls

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        CrawlerExtractor bwc = new CrawlerExtractor();
        bwc.getPageLinks("https://www.codigococina.com/");
        bwc.getArticles();
        bwc.writeToFile("TODO your file name");
    }
}
