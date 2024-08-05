package com.example.bloomgift.controllers.Comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Comment;
import com.example.bloomgift.request.CommentCreateRequest;
import com.example.bloomgift.service.CommentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/user-comment")
public class CommentControllerCustomer {
    @Autowired
    private CommentService commentService;


    @DeleteMapping("/user/{userId}/comment/{commentId}")
    public ResponseEntity<Void> deleteCommentByUser(@PathVariable Integer userId, @PathVariable Integer commentId) {
        try {
            commentService.deleteCommentByUser(commentId, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
