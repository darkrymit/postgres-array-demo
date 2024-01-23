package com.github.darkrymit.demo.postgresarraydemo.post;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.darkrymit.demo.postgresarraydemo.PostgresArrayDemoApplication;
import com.github.darkrymit.demo.postgresarraydemo.testspecific.AutoConfigureTestDatabaseContainer;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = PostgresArrayDemoApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabaseContainer
class PostControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldReturnPostsWhenNoTagsAreProvided() throws Exception {
    // given && when && then
    // @formatter:off

    mockMvc.perform(get("/posts"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$[0].id").isNumber())
      .andExpect(jsonPath("$[0].title").isString())
      .andExpect(jsonPath("$[0].tags").isArray())
      .andExpect(jsonPath("$[0].tags").isNotEmpty())
      .andExpect(jsonPath("$[0].tags[0]").isString());
    // @formatter:on
  }

  @Test
  void shouldReturnPostsWhenTagsAreProvided() throws Exception {
    // given

    List<String> tags = List.of("tag1", "tag2");

    // when && then
    // @formatter:off

    mockMvc.perform(get("/posts").param("tags", tags.toArray(new String[0])))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$[0].id").isNumber())
      .andExpect(jsonPath("$[0].title").isString())
      .andExpect(jsonPath("$[0].tags").isArray())
      .andExpect(jsonPath("$[0].tags").isNotEmpty())
      .andExpect(jsonPath("$[0].tags[0]").isString());
    // @formatter:on
  }

  @Test
  void shouldReturnEmptyListWhenTagsAreProvidedButNoPostsMatch() throws Exception {
    // given

    // tag332 is not present in the data added by DataAddConfig
    List<String> tags = List.of("tag332");

    // when && then
    // @formatter:off
    mockMvc.perform(get("/posts").param("tags", tags.toArray(new String[0])))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$").isEmpty());
    // @formatter:on
  }

}