package com.github.darkrymit.demo.postgresarraydemo;

import com.github.darkrymit.demo.postgresarraydemo.post.Post;
import com.github.darkrymit.demo.postgresarraydemo.post.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used to add some data to the database when the application starts.
 */
@Configuration
public class DataAddConfig {

  private static final List<String> TITLE_PREFIXES = List.of("There", "is", "a", "way", "but", "do",
      "you", "know", "it", "is", "not", "easy", "to", "find", "it", "out", "and", "you", "will",
      "be", "surprised", "when", "you", "find", "it", "out");

  @Bean
  public CommandLineRunner addData(PostRepository postRepository) {
    return args -> {
      // random data genaration
      Random random = new Random();
      random.setSeed(0);

      int postCount = random.nextInt(100);

      for (int i = 0; i < postCount; i++) {
        Post post = new Post();
        int tagCount = random.nextInt(10);
        List<String> tags = new ArrayList<>();
        for (int j = 0; j < tagCount; j++) {
          tags.add("tag" + j);
        }
        post.setTags(tags);
        post.setTitle(TITLE_PREFIXES.get(random.nextInt(TITLE_PREFIXES.size())) + " " + i);
        postRepository.save(post);
      }
    };
  }

}
