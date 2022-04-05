package uz.pdp.newssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.newssite.entity.Post;
import uz.pdp.newssite.payload.ApiResponse;
import uz.pdp.newssite.payload.PostDto;
import uz.pdp.newssite.repository.PostRepository;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Page<Post> getAllPosts(Integer page){
        Pageable pageable= PageRequest.of(page,10);
        return postRepository.findAll(pageable);
    }

    public Post getPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElseGet(Post::new);
    }

    public ApiResponse addPost(PostDto postDto) {
        if (postRepository.existsByTitle(postDto.getTitle()))
            return new ApiResponse("This title already exist!", false);
        if (postRepository.existsByUrl(postDto.getUrl()))
            return new ApiResponse("This url already exist!", false);
        Post post = new Post(postDto.getTitle(), postDto.getText(), postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Post successfully created!",true);
    }

    public ApiResponse editPost(Long id, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent())
            return new ApiResponse("There isn't a post such an id!",false);
        if (postRepository.existsByTitle(postDto.getTitle()))
            return new ApiResponse("This title already exist!", false);
        if (postRepository.existsByUrl(postDto.getUrl()))
            return new ApiResponse("This url already exist!", false);
        Post post = optionalPost.get();
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        post.setUrl(postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Post successfully edited!",true);
    }

    public ApiResponse deletePost(Long id) {
        try {
            postRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!",true);
        }catch (Exception e){
            return new ApiResponse("Deleting error!",false);
        }
    }
}
