package uz.pdp.newssite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.newssite.payload.ApiResponse;
import uz.pdp.newssite.payload.CommentDto;
import uz.pdp.newssite.payload.EditCommentDto;
import uz.pdp.newssite.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/{page}")
    public HttpEntity<?> allComment(@PathVariable Integer page) {
        return ResponseEntity.ok(commentService.getAllComment(page));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCommentById(@PathVariable Long id){
        return ResponseEntity.ok(commentService.getPostById(id));
    }

    @PreAuthorize(value = "hasAuthority('ADD_COMMENT')")
    @PostMapping
    public HttpEntity<?> addComment(@Valid @RequestBody CommentDto commentDto) {
        ApiResponse apiResponse = commentService.addComment(commentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_COMMENT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editComment(@PathVariable Long id,@Valid @RequestBody EditCommentDto editCommentDto) {
        ApiResponse apiResponse = commentService.editComment(id,editCommentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAuthority('DELETE_COMMENT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteComment(@PathVariable Long id) {
        ApiResponse apiResponse = commentService.deleteComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_MY_COMMENT')")
    @DeleteMapping("myComment/{id}")
    public HttpEntity<?> deleteMyComment(@PathVariable Long id) {
        ApiResponse apiResponse = commentService.deleteMyComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
