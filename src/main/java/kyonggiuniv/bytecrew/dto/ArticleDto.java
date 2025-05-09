package kyonggiuniv.bytecrew.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ArticleDto {
    private String title;
    private String content;
    private String URL;

    public ArticleDto(String title, String content,String URL) {
        this.title = title;
        this.content = content;
        this.URL = URL;
    }
}
