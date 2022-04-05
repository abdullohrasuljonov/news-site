package uz.pdp.newssite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @NotNull(message = "Title must not be empty!")
    private String title;

    @NotNull(message = "Text must not be empty!")
    private String text;

    @NotNull(message = "Url must not be empty!")
    private String url;
}
