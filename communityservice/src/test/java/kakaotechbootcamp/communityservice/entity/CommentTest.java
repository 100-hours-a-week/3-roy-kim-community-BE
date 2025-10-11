package kakaotechbootcamp.communityservice.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
public class CommentTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Test
    @Rollback(false)
    void commentTest(){
        User user = new User("tester", "test@gmail.com", "1q2w3e4r!", "http://image.com/imagepath/image.jpg" );
        entityManager.persist(user);
        User replier = new User("tester2", "test2@gmail.com", "1q2w3e4r!", "http://image.com/imagepath/image2.jpg" );
        entityManager.persist(replier);

        Post post = new Post(user, "게시글", "게시글 내용입니다");
        user.addPost(post);
        entityManager.persist(post);

        Comment comment1 = new Comment(post, user, "댓글을 달아봅니다~");
        Comment comment2 = new Comment(post, user, "댓글 잘 달아지나용~");
        Comment reply = new Comment(post, replier, "zzz 뭐하세요?");

        user.addComment(comment1);
        user.addComment(comment2);
        replier.addComment(reply);
        post.addComment(comment1);
        post.addComment(comment2);
        post.addComment(reply);
        entityManager.persist(comment1);
        entityManager.persist(comment2);
        entityManager.persist(reply);
        entityManager.flush();
        entityManager.clear();


    }

}