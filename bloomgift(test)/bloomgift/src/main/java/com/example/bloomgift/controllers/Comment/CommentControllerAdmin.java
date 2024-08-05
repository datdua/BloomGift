package com.example.bloomgift.controllers.Comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Comment;
import com.example.bloomgift.service.CommentService;


@RestController
@RequestMapping("/api/admin-comment")
public class CommentControllerAdmin {
    @Autowired
    private CommentService commentService;

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Integer id) {
        try {
            commentService.deleteCommentById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); 
        }
    }

    @GetMapping("/get-report/false")
    public ResponseEntity<List<Comment>> getCommentsWithReportFalse() {
        List<Comment> comments = commentService.getCommentsWithReportFalse();
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Comment> updateReportStatus(@PathVariable Integer id, @RequestParam boolean newStatus) {
        try {
            Comment updatedComment = commentService.updateStatus(id, newStatus);
            return ResponseEntity.ok(updatedComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }




    
}
