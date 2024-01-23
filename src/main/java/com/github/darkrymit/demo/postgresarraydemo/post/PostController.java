package com.github.darkrymit.demo.postgresarraydemo.post;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/posts", produces = "application/json")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getPosts(@RequestParam(required = false) @Nullable List<String> tags) {
        return postService.getPosts(tags);
    }

}
