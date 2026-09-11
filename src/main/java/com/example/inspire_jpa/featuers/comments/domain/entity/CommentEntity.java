package com.example.inspire_jpa.featuers.comments.domain.entity;

import com.example.inspire_jpa.featuers.blogs.entity.BlogEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity 
@Table(name = "SPRING_JPA_COMMENT_TBL")
@Builder 
@Getter 
@ToString 
@NoArgsConstructor 
@AllArgsConstructor 
public class CommentEntity {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer commentId;

    private String comment;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blogId")
    private BlogEntity blog; 

    public void updateComment(String comment){
        this.comment = comment;
    }
}
