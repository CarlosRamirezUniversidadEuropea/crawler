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

    //Find all URLs that start with "https://www.codigococina.com/page" and add them to the HashSet
    public void getPageLinks(String URL) {
        if (!links.contains(URL)) {
            try {
                //TODO conectarse a la URL con Jsoup
                //
                Document document = Jsoup.connect(URL).get();

                //TODO
                Elements otherLinks = document.select("a[href^=https://www.codigococina.com/page]");

                for (Element page : otherLinks) {
                    if (links.add(URL)) {
                        //Remove the comment from the line below if you want to see it running on your editor
                        System.out.println(URL);
                    }
                    //Url absoluta de ese atributo
                    //TODO
                    getPageLinks(page.attr("href"));
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //Connect to each link saved in the article and find all the articles in the page
    public void getArticles(String keyword) throws Exception{

        for (String item: links){
            //TODO

            try {

                //Conectarse a url
                Document document = Jsoup.connect(item).get();


                //Selecccionar elementos con h2 y dentro de estos los hijos con etiqueta a[...]
                //TODO
                Elements articleLinks = document.select("h2 a");
                for (Element article : articleLinks) {
                    //Only retrieve the titles of the articles that contain Java 8
                    //TODO regex
                    String articleText = article.text().toLowerCase();
                    if (articleText.contains(keyword)) {
                        //Remove the comment from the line below if you want to see it running on your editor,
                        //or wait for the File at the end of the execution
                        //System.out.println(article.attr("abs:href"));

                        ArrayList<String> temporary = new ArrayList<String>();
                        temporary.add(article.text()); //The title of the article

                        temporary.add(article.attr("href")); //The URL of the article
                       if(articles.contains(temporary) == false) {
                           articles.add(temporary);
                       }
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

                    writer.write("Title" + articlesUrls.get(0) + "- ( Url: " + articlesUrls.get(1) + " )\n");
                    //Escribir en fichero las urls

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        CrawlerExtractor bwc = new CrawlerExtractor();
        bwc.getPageLinks("https://www.codigococina.com/");

        bwc.getArticles("carne");
        bwc.writeToFile("Recetas con carne");
        bwc.getArticles("bizcocho");
        bwc.writeToFile("Recetas con bizcocho");
    }
}
