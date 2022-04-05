package uz.pdp.newssite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.newssite.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
