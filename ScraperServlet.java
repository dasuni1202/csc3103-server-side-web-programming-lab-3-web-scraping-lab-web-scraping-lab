import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;

@WebServlet("/ScrapeServlet")
public class ScrapeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = request.getParameter("url");
        String[] selectedOptions = request.getParameterValues("options");
        List<String> options = selectedOptions != null ? Arrays.asList(selectedOptions) : new ArrayList<>();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Scraped Results</h2>");

        try {
            String scrapedData = WebScraper.scrape(url, options);
            out.println(scrapedData);
        } catch (Exception e) {
            out.println("<p>Error scraping the page: " + e.getMessage() + "</p>");
        }

        out.println("<br><a href='index.html'>Back</a>");
        out.println("</body></html>");
    }
}