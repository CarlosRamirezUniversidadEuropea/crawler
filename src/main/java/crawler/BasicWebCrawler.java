package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

public class BasicWebCrawler {

    private HashSet<String> links;
    private static final int MAX_DEPTH_LEVEL = 2;

    public BasicWebCrawler() {
        links = new HashSet<String>();
    }

    public void getAllLinksFromWebsite(String URL,  int depth) {

        if (!links.contains(URL) && (depth < MAX_DEPTH_LEVEL)) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                if (isValid(URL) && URL.startsWith("http")) {
                    links.add(URL);
                    Document document = Jsoup.connect(URL).get();
                    Elements linksOnPage = document.select("a[href]");
                    depth++;
                    for (Element page : linksOnPage) {
                        String link = page.attr("href");
                        getAllLinksFromWebsite(link, depth);
                    }
                }
            } catch (Exception e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public void writeUrlsToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            for(String link : links)
                try {
                    writer.write("- Url: " + link + "\n\n");

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static boolean isValid(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        BasicWebCrawler crawler =  new BasicWebCrawler();
        crawler.getAllLinksFromWebsite("https://www.codigococina.com/", 0);
        crawler.writeUrlsToFile("Urls");
        System.out.println("Number of links: " + crawler.links.size());
    }

}
