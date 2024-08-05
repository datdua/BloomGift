package com.example.bloomgift.controllers.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Comment;
import com.example.bloomgift.service.CommentService;


@RestController
@RequestMapping("/api/staff-comment")
public class CommentControllerStaff {

     @Autowired
    private CommentService commentService;

    @PatchMapping("/update-report/{id}")
    public ResponseEntity<Comment> updateReportStatus(@PathVariable Integer id, @RequestParam boolean newStatus) {
        try {
            Comment updatedComment = commentService.updateReportStatus(id, newStatus);
            return ResponseEntity.ok(updatedComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
