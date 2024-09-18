package com.example.bloomgift.controllers.Comment;

import com.example.bloomgift.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add-comment")
    public String addComment(HttpServletRequest request,@RequestParam Integer accountID, @RequestParam Integer productID,
            @RequestParam String commentContent,@RequestParam Integer rating) {
        try {
            
            commentService.addCommentToSheet(accountID, productID, commentContent,rating);
            return "Comment added successfully";
        } catch (Exception e) {
            return "Failed to add comment: " + e.getMessage();
        }
    }

    @GetMapping("/view-comment")
    public ResponseEntity<List<List<Object>>> viewComments() {
        try {
            List<List<Object>> comments = commentService.viewComments();
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/update-comment")
    public ResponseEntity<String> updateComment(
            @RequestParam Integer commentID,
            @RequestParam Integer accountID,
            @RequestParam Integer productID,
            @RequestParam String updatedCommentContent) {
        try {
            commentService.updateComment(commentID, accountID, productID, updatedCommentContent);
            return ResponseEntity.ok("Comment updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating comment: " + e.getMessage());
        }
    }
     @PutMapping("/update-rating")
    public ResponseEntity<String> updateRating(
            @RequestParam Integer commentID,
            @RequestParam Integer accountID,
            @RequestParam Integer productID,
            @RequestParam Float updatedRating) {
        try {
            commentService.updateRating(commentID, accountID, productID, updatedRating);
            return ResponseEntity.ok("Comment updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating comment: " + e.getMessage());
        }
    }
    
 
    @DeleteMapping("/delete-comment")
    public ResponseEntity<String> deleteComment(
            @RequestParam("accountId") Integer accountId,
            @RequestParam("commentId") Integer commentId) {
        try {
            commentService.deleteComment(accountId, commentId);
            return ResponseEntity.ok("Comment deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting comment: " + e.getMessage());
        }
    }

    @GetMapping("/view-comment-by-Product/{productID}")
    public ResponseEntity<List<List<Object>>> getCommentsByProductID(@RequestParam Integer productID) {
        try {
                List<List<Object>> comments = commentService.getCommentsByProductID(productID);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}