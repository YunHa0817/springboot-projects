package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")  //localhost:8080/hi 요청시 niceToMeetYou() 메서드 호출
    public String niceToMeetYou(Model model) {

        //model 객체가 "SpringBoot" 값을 "username"에 연결해 웹 브라우저로 보냄
        model.addAttribute("username", "SpringBoot");
        return "greetings"; //greetings.mustache 파일 반환
    }

    @GetMapping("/bye") //localhost:8080/bye 요청시 seeYouNext() 메서드 호출
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "홍길동");
        return "goodbye";   //goodbye.mustache 파일 반환
    }
}
