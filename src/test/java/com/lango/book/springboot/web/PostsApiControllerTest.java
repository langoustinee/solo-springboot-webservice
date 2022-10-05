package com.lango.book.springboot.web;

import com.lango.book.springboot.domain.posts.Posts;
import com.lango.book.springboot.domain.posts.PostsRepository;
import com.lango.book.springboot.web.dto.PostsSaveRequestDto;
import com.lango.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void clean() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록() throws Exception {
        //given
        String title = "test title";
        String content = "test content is ...";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("xmun777@naver.com")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all_list = postsRepository.findAll();
        assertThat(all_list.get(0).getTitle()).isEqualTo(title);
        assertThat(all_list.get(0).getContent()).isEqualTo(content);
        assertThat(all_list.get(0).getAuthor()).isEqualTo("xmun777@naver.com");
    }

    @Test
    public void Posts_수정() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                        .title("update title")
                        .content("update content is ...")
                        .author("xmun777@naver.com")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "update title2";
        String expectedContent = "update content2 is ...";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all_list = postsRepository.findAll();
        assertThat(all_list.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all_list.get(0).getContent()).isEqualTo(expectedContent);

    }

    @Test
    public void Posts_삭제() {
        // 삭제 테스트를 따로 만들어보자.
    }
}
