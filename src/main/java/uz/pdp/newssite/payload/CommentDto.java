package uz.pdp.newssite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotNull(message = "Post id must not be empty!")
    private Long postId;

    @NotNull(message = "Comment must not be empty!")
    private String comment;
}
