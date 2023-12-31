package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor     //Article(Long id, String title, String content) 생성자를 대체하는 어노테이션 추가
@NoArgsConstructor      //Article() 생성자를 대체하는 어노테이션 추가
@ToString               //toString() 메서드를 대체하는 어노테이션 추가
@Entity                 //엔티티 선언
@Getter                 //getter 메서드 추가
public class Article {
    @Id //엔티티의 대푯값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id 자동 생성
    private Long id;

    @Column //DB 테이블의 title열과 연결
    private String title;
    @Column //DB 테이블의 content열과 연결
    private String content;

    public void patch(Article article) {    //수정할 내용이 있는 경우에만 호출하는 메서드
        if (article.title != null) this.title = article.title;
        if (article.content != null) this.content = article.content;
    }
}
