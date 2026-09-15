package com.example.inspire_jpa.features.comments.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inspire_jpa.features.blogs.entity.BlogEntity;
import com.example.inspire_jpa.features.blogs.repository.BlogRepository;
import com.example.inspire_jpa.features.comments.domain.dto.CommentRequestDTO;
import com.example.inspire_jpa.features.comments.domain.dto.CommentResponseDTO;
import com.example.inspire_jpa.features.comments.domain.entity.CommentEntity;
import com.example.inspire_jpa.features.comments.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional 
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public CommentResponseDTO insert(CommentRequestDTO request) {
        System.out.println("debug >>>> comment service insert ");
        System.out.println("debug >>>> comment service insert params : " + request);

        BlogEntity blog = blogRepository.findById(request.getBlogId())
            .orElseThrow(() -> new RuntimeException(request.getBlogId() + "Blog Not Found!!"));

        CommentEntity entity = request.toEntity(blog);
        CommentEntity result = commentRepository.save(entity);

        return  CommentResponseDTO.fromEntity(result);
    };

    public int delete(Integer commentId){
        System.out.println("debug >>>> comment service delete ");
        System.out.println("debug >>>> comment service delete params : " + commentId);
        
        // CommentEntity entity = commentRepository.findById(commentId)
        //     .orElseThrow(() -> new RuntimeException(commentId + "Comment Not Found"));

        // commentRepository.delete(commentId);

        commentRepository.deleteById(commentId);

        return 1;
    };

    /*
    jpa update 주의사항
    - DML : transaction(commit, rollback)
    - Dirty Checking(변경감지)
    findById() - 영속성 관리로 진입
    setXXXXX() - 필드 수정(Dirty Checking) Not Commit
    현재 상태 vs 스냅샷 상태 비교하고 다름을 확인하면 update 실행 - commit
    */
    public int update(Map<String, Object> map){
        System.out.println("debug >>>> comment service update ");
        
        CommentEntity entity = commentRepository.findById((Integer)(map.get("id")))
            .orElseThrow(() -> new RuntimeException(((Integer)(map.get("id")) + "Comment Not Found")));

        entity.updateComment((String)(map.get("comment")));

        return 1;
    };
}

