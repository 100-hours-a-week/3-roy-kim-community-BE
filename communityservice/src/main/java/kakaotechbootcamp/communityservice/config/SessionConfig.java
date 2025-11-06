package kakaotechbootcamp.communityservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 300)                  // TTL 5분으로 설정
public class SessionConfig {
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer s = new DefaultCookieSerializer();
        s.setCookieName("SID");
        s.setUseHttpOnlyCookie(true);                                       // JS 접근 차단
        s.setSameSite("Lax");                                               // 다른 사이트에서 온 요청에 대한 쿠키 자동 전송 제한
        s.setCookiePath("/");                                               // 모든 url 경로에서 쿠키 사용 허용
        return s;
    }
}
