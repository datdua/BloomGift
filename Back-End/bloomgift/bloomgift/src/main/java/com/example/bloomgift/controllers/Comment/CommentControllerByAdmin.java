package com.example.bloomgift.controllers.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.service.CommentService;

@RestController
@RequestMapping("/api/comments/admin")
public class CommentControllerByAdmin {
     @Autowired
    private CommentService commentService;


    @PostMapping("/update-status")
    public ResponseEntity<String> updateCommentStatus(@RequestParam Integer commentID,
                                                      @RequestParam Boolean status) {
        try {
            commentService.updateStatus(commentID, status);
            return ResponseEntity.ok("Comment status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating comment status: " + e.getMessage());
        }
    }
}
