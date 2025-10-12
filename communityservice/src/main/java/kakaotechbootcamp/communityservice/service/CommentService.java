package kakaotechbootcamp.communityservice.service;

import kakaotechbootcamp.communityservice.entity.Comment;
import kakaotechbootcamp.communityservice.entity.Post;
import kakaotechbootcamp.communityservice.entity.User;
import kakaotechbootcamp.communityservice.exception.BadRequestException;
import kakaotechbootcamp.communityservice.repository.CommentRepository;
import kakaotechbootcamp.communityservice.repository.PostRepository;
import kakaotechbootcamp.communityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment create (Long postId, Long authorId, String content) {
        if (content == null || content.length() == 0) {
            throw new BadRequestException("댓글 내용을 입력하세요");
        }
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException("없는 게시물"));
        User author = userRepository.findById(authorId).orElseThrow(() -> new BadRequestException("사용자 없음"));

        Comment comment = new Comment(post, author, content);
        return commentRepository.save(comment);
    }
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("게시글을 찾을 수 없습니다"));
    }
    @Transactional
    public Comment update(Long id, String content) {
        if (content == null || content.isBlank())
            throw new BadRequestException("내용을 입력해주세요");

        Comment comment = findById(id);
        comment.setContent(content);
        comment.setEdited(true); // 수정 플래그
        return comment;
    }
    @Transactional
    public void delete(Long id) {
        if (!commentRepository.existsById(id))
            throw new BadRequestException("게시글을 찾을 수 없습니다");
        commentRepository.deleteById(id);
    }
}
