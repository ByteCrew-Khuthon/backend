package kyonggiuniv.bytecrew.service;

import kyonggiuniv.bytecrew.dto.ArticleDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {

    private static final String URL = "http://www.pigpeople.net/news/section.html?sec_no=102";

    public List<ArticleDto> scrapeArticles() throws IOException {
        List<ArticleDto> articles = new ArrayList<>();

        Document doc = Jsoup.connect(URL).get();
        Elements articleElements = doc.select("ul.art_list_all > li");

        for (Element article : articleElements) {
            Element titleElement = article.selectFirst("h2.cmp.c2");
            Element contentElement = article.selectFirst("p.ffd.cmp.c2");
            Element urlElement = article.selectFirst("a[href]");

            if (titleElement != null && contentElement != null) {
                String title = titleElement.text();
                String content = contentElement.text();
                String url = "http://www.pigpeople.net"+urlElement.attr("href");
                articles.add(new ArticleDto(title, content,url));
            }
        }

        return articles;
    }
}

