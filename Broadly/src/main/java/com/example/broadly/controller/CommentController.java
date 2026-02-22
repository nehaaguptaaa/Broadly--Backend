package com.example.broadly.controller;

import com.example.broadly.dto.CommentRequestDto;
import com.example.broadly.dto.CommentResponseDto;
import com.example.broadly.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    // ADD COMMENT
    @PostMapping
    public CommentResponseDto addComment(@RequestBody CommentRequestDto dto) {
        return commentService.addComment(dto);
    }

    // GET COMMENTS FOR A SHOT
    @GetMapping("/shot/{shotId}")
    public List<CommentResponseDto> getCommentsByShot(@PathVariable Long shotId) {
        return commentService.getCommentsByShot(shotId);
    }

    // DELETE COMMENT
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "Comment deleted";
    }
}

