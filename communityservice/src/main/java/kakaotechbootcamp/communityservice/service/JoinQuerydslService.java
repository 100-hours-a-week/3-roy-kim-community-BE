package kakaotechbootcamp.communityservice.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kakaotechbootcamp.communityservice.dto.PostSummaryResponse;
import kakaotechbootcamp.communityservice.entity.Post;
import kakaotechbootcamp.communityservice.entity.QPost;
import kakaotechbootcamp.communityservice.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinQuerydslService {
    private final JPAQueryFactory queryFactory;


    public List<PostSummaryResponse> listRecentPosts() {
        QPost p = QPost.post;
        QUser u = QUser.user;
        List<Post> posts = queryFactory.selectFrom(p)
                .join(p.author, u)
                .orderBy(p.createdTime.desc(), p.id.desc())
                .limit(30)
                .fetch();
        return posts.stream()
                .map(post -> new PostSummaryResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getCreatedTime(),
                        post.getAuthor().getNickname(),
                        post.getAuthor().getProfilePicture()
                ))
                .toList();
    }
}
