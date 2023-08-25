package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {
    private Long id;
    private String title;   //제목을 받을 필드
    private String content; //내용을 받을 필드

    /*  lombok에서 제공하는 @AllArgsConstructor 어노테이션 추가로 아래의 생성자 대체
    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

     */

    /* lombok에서 제공하는 @ToString 어노테이션 추가로 아래의 메서드 대체
    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
     */

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
