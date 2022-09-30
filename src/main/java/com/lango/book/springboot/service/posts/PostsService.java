package com.lango.book.springboot.service.posts;

import com.lango.book.springboot.domain.posts.Posts;
import com.lango.book.springboot.domain.posts.PostsRepository;
import com.lango.book.springboot.web.dto.PostsResponseDto;
import com.lango.book.springboot.web.dto.PostsSaveRequestDto;
import com.lango.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

//    @Transactional
    public PostsResponseDto findByid(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다. id=" + id));
        return new PostsResponseDto(entity);
    }
}
