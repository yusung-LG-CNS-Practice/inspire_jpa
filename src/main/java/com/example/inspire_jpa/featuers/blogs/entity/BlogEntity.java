package com.example.inspire_jpa.featuers.blogs.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.inspire_jpa.featuers.comments.domain.entity.CommentEntity;
import com.example.inspire_jpa.featuers.users.domain.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "SPRING_JPA_BLOG_TBL")
@Builder
@Getter
@ToString
@NoArgsConstructor 
@AllArgsConstructor 
public class BlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment

    private Integer blogId;
    private String content;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT '전체' CHECK( CATEGORY IN ('전체', '개발', '생활', '취미', '일상'))")
    private String category;

    // foreign key
    // fetch : select * from userEntity where email = ?
    // optional = false : null값을 허용하지 않음
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email")
    private UserEntity author;

    // user(1) : blog(n) - blog(1) : comment(n)
    // 댓글 Comments
    // 부모 삭제시 자식은 restric
    @OneToMany(mappedBy = "blog", orphanRemoval = false)
    // @OneToMany(mappedBy = "blog", orphanRemoval = true, cascade = Cascade.xxxxx)
    private List<CommentEntity> comments = new ArrayList<>();
}
