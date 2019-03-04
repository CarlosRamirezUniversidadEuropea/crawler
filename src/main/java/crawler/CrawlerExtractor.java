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
                Document document = Jsoup.connect(URL).get();
                Elements otherLinks = document.select("a[href~=https://www.codigococina.com/page/]");
                // First page does not match the regex used above.
                if (!links.contains("https://www.codigococina.com/)")) {
                    links.add("https://www.codigococina.com/");
                }

                for (Element page : otherLinks) {
                    links.add(URL);
                    //Url absoluta de ese atributo
                    getPageLinks(page.attr("abs:href"));
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //Connect to each link saved in the article and find all the articles in the page
    public void getArticles() {
        for (String item: links){
            try {
                Document document= Jsoup.connect(item).get();

                //Selecccionar elementos <a class="entry-title-link">
                Elements articleLinks = document.select("a[class = entry-title-link]");
                for (Element article : articleLinks) {
                    String title = article.text();
                    String link = article.attr("href");
                    System.out.println(title + " - " + link);
                    ArrayList<String> temporary = new ArrayList<String>();
                    temporary.add(title + " - " + link);
                    articles.add(temporary);
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
                    //Escribir en fichero las urls
                    writer.write(articlesUrls+ "\n\n");

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
        bwc.writeToFile("Receta.txt");
    }
}
