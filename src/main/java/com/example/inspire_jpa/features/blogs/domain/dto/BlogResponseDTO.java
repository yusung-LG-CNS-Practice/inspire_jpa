package com.example.inspire_jpa.features.blogs.domain.dto;

import java.util.List;

import com.example.inspire_jpa.features.blogs.entity.BlogEntity;
import com.example.inspire_jpa.features.comments.domain.dto.CommentResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BlogResponseDTO {

    private String title, content, category, email;
    private Integer blogId;

    // 1- N
    private List<CommentResponseDTO> comments;

    public static BlogResponseDTO fromEntity(BlogEntity entity) {
        return BlogResponseDTO.builder()
                .blogId(entity.getBlogId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory())
                .email(entity.getAuthor().getEmail())
                .build();
    }

    // blog(1) + comments(N) 반환도 필요함! (read)
    public static BlogResponseDTO fromEntityWithComments(BlogEntity entity) {
        return BlogResponseDTO.builder()
                .blogId(entity.getBlogId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory())
                .email(entity.getAuthor().getEmail())
                .comments(entity.getComments()
                        .stream()
                        .map(CommentResponseDTO::fromEntity)
                        .toList())
                .build();
    }
}
