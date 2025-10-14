package kakaotechbootcamp.communityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class PostSummaryResponse {
    private final Long id;
    private final String title;
    private final LocalDateTime createdTime;
    private final String authorNickname;
    private final String authorProfilePicture;

/* TODO: 좋아요 댓글 조회수 기능 추가 후 다음 추가
    private final int likes;
    private final int comments;
    private final int views;
*/

/* TODO:
    1) 매개변수에 int likes, int comments, int views 주가하기
    2) this.likes, this.comments, this.views
*/
    public PostSummaryResponse(Long id, String title, LocalDateTime createdTime, String authorNickname, String authorProfilePicture) {
            this.id = id;
            this.title = title;
            this.createdTime = createdTime;
            this.authorNickname = authorNickname;
            this.authorProfilePicture = authorProfilePicture;}
}
