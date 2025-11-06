package kakaotechbootcamp.communityservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SessionFilter extends OncePerRequestFilter {
    private static final String[] EXCLUDED_PATHS = {
            "/users/login",
            "/users/signup",
            "/users/check-email",
            "/users/check-nickname"
    };
    // 필터 제외 경로 설정
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        return Arrays.stream(EXCLUDED_PATHS).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("uid") == null) {
            // Add CORS headers on auth failures so fetch() isn't blocked
            String origin = request.getHeader("Origin");
            if (origin != null && (origin.equals("http://localhost:3000") || origin.equals("http://localhost"))) {
                response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Vary", "Origin");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                // (Optional) reflect what the client asked for — helpful with some setups
                String reqHeaders = request.getHeader("Access-Control-Request-Headers");
                if (reqHeaders != null) response.setHeader("Access-Control-Allow-Headers", reqHeaders);
                String reqMethod = request.getHeader("Access-Control-Request-Method");
                if (reqMethod != null) response.setHeader("Access-Control-Allow-Methods", reqMethod);
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("unauthorized");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
