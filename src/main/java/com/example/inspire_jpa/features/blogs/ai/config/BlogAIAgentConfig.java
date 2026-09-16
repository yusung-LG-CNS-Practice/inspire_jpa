package com.example.inspire_jpa.features.blogs.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.inspire_jpa.features.blogs.ai.tools.BlogAITool;

@Configuration 
public class BlogAIAgentConfig {

    @Bean 
    public ChatClient blogChatClient(ChatClient.Builder builder,
                                     BlogAITool blogAITool,
                                     ObjectProvider<ToolCallbackProvider> objectProvider){

        System.out.println("debug >>>> blog ai agent config ChatClient");

        builder = builder.defaultTools(blogAITool);

        // 외부 mcp server와 연결
        // ToolCallbackProvider mcpTools = objectProvider.getIfAvailable();
        // System.out.println("debug >>>> mcpTools " + mcpTools);
        // if(mcpTools != null){
        //     builder = builder.defaultToolCallbacks(mcpTools.getToolCallbacks());
        // }

        return builder.build();
    }
}
