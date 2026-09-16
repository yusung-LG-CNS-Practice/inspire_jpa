package com.example.inspire_jpa.features.blogs.ai.tools;

import java.util.List;
import java.util.Map;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.example.inspire_jpa.features.blogs.domain.dto.BlogRequestDTO;
import com.example.inspire_jpa.features.blogs.domain.dto.BlogResponseDTO;
import com.example.inspire_jpa.features.blogs.service.BlogService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BlogAITool {

    private final BlogService blogService;

    // LLM이 스스로 판단해서 호출
    @Tool(description = "주어진, 카테고리와 키워드로 이미 작성된 블로그 게시글이 있는지 검색한다.")
    public List<BlogResponseDTO> searchBlogKeyword(Map<String, Object> map) {

        System.out.println("debug >>>> blog ai agent tool searchBlogKeyword");
        System.out.println("debug >>>> blog ai agent tool params : " + map.get("category"));
        System.out.println("debug >>>> blog ai agent tool params : " + map.get("keyword"));

        // return null;

        List<BlogResponseDTO> list = blogService.searchByKeyword(map);
        System.out.println("debug >>>> blog ai agent tool result size : " + list.size());
        list.stream().forEach(System.out::println);

        return list;

    }

    @Tool(description = "사용자가 작성한 블로그 글을 실제 테이블에 저장한다.")
    public BlogResponseDTO saveBlog(BlogRequestDTO request) {

        System.out.println("debug >>>> blog ai agent tool saveBlog");
        System.out.println("debug >>>> blog ai agent tool saveBlog params : " + request);

        return blogService.insert(request);
    }

}
