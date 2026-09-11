package com.example.inspire_jpa.featuers.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inspire_jpa.featuers.comments.domain.entity.CommentEntity;

@Repository 
public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

}
