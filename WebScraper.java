import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class NewsArticle {
    String headline;
    String publicationDate;
    String author;

    public NewsArticle(String headline, String publicationDate, String author) {
        this.headline = headline;
        this.publicationDate = publicationDate;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Headline: " + headline + "\nDate: " + publicationDate + "\nAuthor: " + author + "\n";
    }
}

public class WebScraper {

    public static void main(String[] args) {
        try {
            String url = "https://www.bbc.com";
            Document doc = Jsoup.connect(url).get();

            // Title
            System.out.println("Page Title: " + doc.title());

            // Headings
            System.out.println("\nHeadings:");
            for (int i = 1; i <= 6; i++) {
                Elements headings = doc.select("h" + i);
                for (Element heading : headings) {
                    System.out.println("h" + i + ": " + heading.text());
                }
            }

            // Links
            System.out.println("\nLinks:");
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                System.out.println(link.attr("abs:href") + " - " + link.text());
            }

            // Scrape headlines, authors, and publication dates
            List<NewsArticle> articles = new ArrayList<>();
            Elements articleElements = doc.select("article");

            for (Element article : articleElements) {
                String headline = article.select("h1, h2, h3").text();
                String date = article.select("time").attr("datetime");
                String author = article.select("[class*=author], [rel=author]").text();

                if (!headline.isEmpty()) {
                    articles.add(new NewsArticle(headline, date, author));
                }
            }

            System.out.println("\nNews Articles:");
            for (NewsArticle article : articles) {
                System.out.println(article);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}