package uz.pdp.newssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.newssite.entity.Comment;
import uz.pdp.newssite.entity.Post;
import uz.pdp.newssite.entity.User;
import uz.pdp.newssite.payload.ApiResponse;
import uz.pdp.newssite.payload.CommentDto;
import uz.pdp.newssite.payload.EditCommentDto;
import uz.pdp.newssite.repository.CommentRepository;
import uz.pdp.newssite.repository.PostRepository;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    public Page<Comment> getAllComment(Integer page) {
        Pageable pageable= PageRequest.of(page,10);
        return commentRepository.findAll(pageable);
    }

    public Comment getPostById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElseGet(Comment::new);
    }

    public ApiResponse addComment(CommentDto commentDto) {
        Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
        if (!optionalPost.isPresent())
            return new ApiResponse("There isn't a post such an id!",false);
        Comment comment=new Comment();
        comment.setPost(optionalPost.get());
        comment.setText(commentDto.getComment());
        commentRepository.save(comment);
        return new ApiResponse("Successfully created!",true);
    }

    public ApiResponse deleteComment(Long id) {
        try{
            commentRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!",true);
        }catch (Exception e){
            return new ApiResponse("Deleting error!",false);
        }
    }

    public ApiResponse deleteMyComment(Long id) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (!optionalComment.isPresent()) return new ApiResponse("Comment not found", false);
            Comment comment = optionalComment.get();
            if (comment.getCreatedBy().getId() == user.getId()) {
                commentRepository.deleteById(id);
                return new ApiResponse("Successfully deleted!", true);
            }
            return new ApiResponse("Error in deleting!", false);

        } catch (Exception e) {
            return new ApiResponse("Error in deleting!", false);
        }
    }

    public ApiResponse editComment(Long id, EditCommentDto editCommentDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()) return new ApiResponse("Comment not found!", false);
        Comment comment = optionalComment.get();
        if (comment.getCreatedBy().getId() == user.getId()) {
            comment.setText(editCommentDto.getText());
            commentRepository.save(comment);
            return new ApiResponse("Successfully edited!", true);
        }
        return new ApiResponse("Error in editing!", false);
    }
}
