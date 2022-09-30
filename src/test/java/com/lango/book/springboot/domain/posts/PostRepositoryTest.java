package com.lango.book.springboot.domain.posts;

import javafx.geometry.Pos;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void clean() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "test title";
        String content = "test content is ...";

        postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .author("xmun777@naver.com")
                        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2022, 9, 30, 0, 0, 0);
        postsRepository.save(Posts.builder()
                        .title("title !!!")
                        .content("content is ...")
                        .author("xmun777@naver.com")
                .build());

        //when
        List<Posts> all_list = postsRepository.findAll();

        //then
        Posts posts = all_list.get(0);

        System.out.println("createdDate:" + posts.getCreatedDate() + " modiftedDate:" + posts.getModifyedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifyedDate()).isAfter(now);

    }
}
