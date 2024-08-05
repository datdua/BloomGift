package com.example.bloomgift.controllers.Comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Comment;
import com.example.bloomgift.repository.CommentRepository;
import com.example.bloomgift.request.CommentCreateRequest;
import com.example.bloomgift.service.CommentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/comments")

public class CommentController {
    // @Autowired
    // private CommentRepository commentRepository;
    
    @Autowired
    private CommentService commentService;

    @GetMapping("/get-all-comment")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }


    @GetMapping("/get-comment-by-userId/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable Integer userId) {
        List<Comment> comments = commentService.getCommentsByUserId(userId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/get-comment-by-productid/{productId}")
    public ResponseEntity<List<Comment>> getCommentsByProductId(@PathVariable Integer productId) {
        List<Comment> comments = commentService.getCommentsByProductId(productId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
}

    @PostMapping("/create-comment")
    public ResponseEntity<Comment> createComment(@RequestBody CommentCreateRequest commentCreateRequest) {
        try {
            Comment createdComment = commentService.createComment(commentCreateRequest);
            return ResponseEntity.ok(createdComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle case where user or product does not exist
        }
    }

    

  
}


