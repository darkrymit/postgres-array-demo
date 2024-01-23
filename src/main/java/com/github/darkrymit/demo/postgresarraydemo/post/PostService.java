package com.github.darkrymit.demo.postgresarraydemo.post;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getPosts(@Nullable List<String> tags) {
        Specification<Post> spec = Specification.where(null);
        if (tags != null && !tags.isEmpty()){
            spec = spec.and(hasTags(tags));
        }
        return postRepository.findAll(spec);
    }

    private Specification<Post> hasTags(List<String> tags) {
        return (root, query, cb) -> {
            // we need to convert the tags list to a postgres array string since it's not properly supported by JPA
            String arrayAsString = "{" + String.join(",", tags) + "}";
            return cb.isTrue(cb.function("@>", Boolean.class, root.get("tags"), cb.literal(arrayAsString)));
        };
    }
}
