package kyonggiuniv.bytecrew.controller;

import kyonggiuniv.bytecrew.dto.ArticleDto;
import kyonggiuniv.bytecrew.service.ScrapingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ScrapingController {

    private final ScrapingService scrapingService;

    public ScrapingController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GetMapping
    public List<ArticleDto> getArticles() throws IOException {
        return scrapingService.scrapeArticles();
    }
}

