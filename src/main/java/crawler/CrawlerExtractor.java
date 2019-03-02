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
    private void getPageLinks(String URL) {
        if (!links.contains(URL)) {
            try {

                //
                Document document = Jsoup.connect(URL).get();


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
    private void getArticles(String valor)  {
        for (String item: links){


            try {

                Document document= Jsoup.connect(item).get();

                //Selecccionar elementos con h4 y dentro de estos los hijos con etiqueta a[...]

                Elements articleLinks = document.select("h2 a.entry-title-link");
                for (Element article : articleLinks) {

                    //TODO regex
                    if (article.text().toLowerCase().contains(valor)) {
                        //Remove the comment from the line below if you want to see it running on your editor,
                        //or wait for the File at the end of the execution
                        //System.out.println(article.attr("abs:href"));

                        ArrayList<String> temporary = new ArrayList<String>();
                        temporary.add(article.text()); //The title of the article

                        temporary.add(article.attr("href")); //The URL of the article
                        articles.add(temporary);
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void writeToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            for(List<String> articlesUrls : articles)

                try {
                    writer.write("Title: "+articlesUrls.get(0)+" - URL: " +articlesUrls.get(1)+"\n\n");

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    //Nuevo metodo (no necesario) hace el proceso sin necesidad de escribirlo siempre en el main
    private static void todoProceso(String valor){
        CrawlerExtractor bwc = new CrawlerExtractor();
        bwc.getPageLinks("https://www.codigococina.com/");
        bwc.getArticles(valor);
        bwc.writeToFile(valor);
    }

    public static void main(String[] args) {
       // CrawlerExtractor bwc = new CrawlerExtractor();
       // bwc.getPageLinks("https://www.codigococina.com/");
       // bwc.getArticles("carne");
        //bwc.writeToFile("Carne");

        todoProceso("pescado");
        todoProceso("bizcocho");
    }
}
