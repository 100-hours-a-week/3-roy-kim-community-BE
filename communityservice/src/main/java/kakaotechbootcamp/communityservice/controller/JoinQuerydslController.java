package kakaotechbootcamp.communityservice.controller;

import kakaotechbootcamp.communityservice.dto.PostSummaryResponse;
import kakaotechbootcamp.communityservice.service.JoinQuerydslService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/board")
public class JoinQuerydslController {
    private final JoinQuerydslService joinQuerydslService;

    public JoinQuerydslController(JoinQuerydslService joinQuerydslService) {
        this.joinQuerydslService = joinQuerydslService;
    }

    @GetMapping("/posts")
    public List<PostSummaryResponse> inner() {
        return joinQuerydslService.listRecentPosts();
    }
}
