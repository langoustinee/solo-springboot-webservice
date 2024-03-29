package com.lango.book.springboot.web;

import com.lango.book.springboot.config.auth.LoginUser;
import com.lango.book.springboot.config.auth.dto.SessionUser;
import com.lango.book.springboot.service.posts.PostsService;
import com.lango.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        // @LoginUser 어노테이션을 통해 SessionUser 중복 코드 제거
        // login 성공 시 세션에서 user의 값을 가져올 수 있다.
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findByid(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
