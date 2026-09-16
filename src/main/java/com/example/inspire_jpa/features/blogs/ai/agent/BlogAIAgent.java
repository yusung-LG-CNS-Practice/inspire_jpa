package com.example.inspire_jpa.features.blogs.ai.agent;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.example.inspire_jpa.features.blogs.domain.dto.BlogRequestDTO;
import com.example.inspire_jpa.features.blogs.domain.dto.BlogResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogAIAgent {

    private final ChatClient blogChatClient;

    public String generate(Map<String, Object> map) {
        System.out.println("debug >>>> blog AI agent generate");

        // SEO(Search Engine Optimization)
        String systemPrompt = """
                    당신은 SEO를 고려한 블로그 작성 에이전트입니다.
                    rule
                    - searchBlogKeyword 중복여부를 확인
                    - 본문내용을 300자 이내로 작성
                """;

        String userPrompt = """
                    %s 와 %s 를 기반으로 글 작성하고 저장하지말고 반환해줘.
                """.formatted((String) (map.get("category")), (String) (map.get("keyword")));

        String result = blogChatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();

        System.out.println("debug >>>> blog agent generate result : ");
        System.out.println(result);

        return result;
    }

    public BlogResponseDTO insert(BlogRequestDTO request) {
        System.out.println("debug >>>> blog AI agent generate");

        // SEO(Search Engine Optimization)
        String systemPrompt = """
                당신은 SEO를 고려한 블로그 작성·발행 에이전트입니다.
                아래 절차를 순서대로 반드시 지키세요.

                <절차>
                1. searchBlogKeyword 로 같은 카테고리·키워드의 기존 글이 있는지 확인한다.
                2. 결과가 1건 이상이면 저장하지 말고, 기존 글 제목을 알려주며 중복임을 보고하고 종료한다.
                3. 결과가 0건일 때만 제목과 본문을 새로 작성한다.
                4. saveBlog 로 저장한다.
                5. 저장된 blogId 를 포함해 한 줄로 결과를 보고한다.
                </절차>

                <규칙>
                - 본문은 300자 이내로 작성한다.
                - 마크다운과 백틱(`)을 사용하지 않는다.
                - searchBlogKeyword 와 saveBlog 는 각각 최대 1회만 호출한다.
                - category 는 사용자가 준 값을 그대로 사용하고 임의로 바꾸지 않는다.
                - 절차 2에 해당하면 어떤 경우에도 saveBlog 를 호출하지 않는다.
                </규칙>
                """;

        String userPrompt = """
                    아래 요청사항에 따라 글 작성하고 저장해줘.
                    category: %s, title: %s, content: %s, email: %s
                """.formatted(request.getCategory(), request.getTitle(), request.getContent(), request.getEmail());

        BlogResponseDTO result = blogChatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .entity(BlogResponseDTO.class);

        System.out.println("debug >>>> blog agent generate result :");
        System.out.println(result);

        return result;
    }

}
