package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j  //로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {

    @Autowired  //스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;  //댓글 서비스 객체 주입

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());  //로깅 코드 추가로 아래의 System.out.println(form.toString()); 대체
        //System.out.println(form.toString());

        //1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString()); //DTO가 엔티티로 잘 변환되는지 확인 출력

        //2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);    //article 엔티티를 저장해 saved 객체에 반환
        log.info(saved.toString());
        //System.out.println(saved.toString());   //article이 DB에 잘 저장되는지 확인 출력
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")   //데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model) { //매개변수로 id 받아 오기
        log.info("id = " + id); //id를 받았는지 확인하는 로그 찍기

        //1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id); //조회한 댓글 목록을 commentDtos 참조변수로 참조

        //2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos); //댓글 목록 모델에 등록

        //3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        //1. 모든 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        //2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        //3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        //뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        //1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        //2. 엔티티를 DB에 저장하기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        //기존 데이터 값을 갱신하기
        if (target != null) articleRepository.save(articleEntity);  //엔티티를 DB에 저장
        //3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!");
        //1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        //2. 대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target);   //삭제할 대상이 있는 경우 delete() 메서드로 대상 삭제
            rttr.addFlashAttribute("msg", "삭제됐습니다!");   //삭제 완료 메시지 남기기
        }

        //3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
