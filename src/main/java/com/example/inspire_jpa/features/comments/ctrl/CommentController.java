package com.example.inspire_jpa.features.comments.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inspire_jpa.features.comments.domain.dto.CommentRequestDTO;
import com.example.inspire_jpa.features.comments.domain.dto.CommentResponseDTO;
import com.example.inspire_jpa.features.comments.service.CommentService;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody CommentRequestDTO request) {
        System.out.println("debug >>>> comment controller insert ");
        System.out.println("debug >>>> comment controller insert params : " + request);

        CommentResponseDTO response = commentService.insert(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {

        System.out.println("debug >>>> comment controller delete ");
        System.out.println("debug >>>> comment controller delete params(comment id) : " + id);

        int flag = commentService.delete(id);
        System.out.println("debug >>>> comment controller delete result flag : " + id);

        // code : 204(no content), 404(not found)
        return flag != 0
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // case 01
    // @PatchMapping("/update/{id}")
    // public ResponseEntity<?> update(@PathVariable("id") Integer id,
    // @RequestBody Map<String, Object> map){

    // }

    // case 02
    @PatchMapping("/update/{id}/{comment}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @PathVariable("comment") String comment) {

        System.out.println("debug >>>> comment controller update");
        System.out.println("debug >>>> comment controller update params (comment id) : " + id);
        System.out.println("debug >>>> comment controller update params comment : " + comment);

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("comment", comment);

        int flag = commentService.update(map);
        // code : 204(no content), 404(not found)
        return flag != 0
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

