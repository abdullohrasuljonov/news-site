package uz.pdp.newssite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.newssite.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {

    boolean existsByTitle(String title);
    boolean existsByUrl(String url);
}
