package kakaotechbootcamp.communityservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import kakaotechbootcamp.communityservice.dto.*;
import kakaotechbootcamp.communityservice.entity.User;
import kakaotechbootcamp.communityservice.repository.UserRepository;
import kakaotechbootcamp.communityservice.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost"},
        methods = {RequestMethod.POST,
                RequestMethod.GET,
                RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true" // 쿠키 포함 요청 막힘 방지
)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public UserResponse create(@RequestBody CreateUserRequest request) {
        User saved = userService.signUp(request.getEmail(), request.getPassword(), request.getPasswordCheck(), request.getNickname(), request.getProfilePicture());
        return UserResponse.of(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        String loginResult = userService.login(loginRequest);
        // Note: 세션 로드 혹은 생성
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("에러 메시지"));
        var session = request.getSession(true);
        session.setAttribute("uid", user.getId());
        return ResponseEntity.ok(loginResult);
    }

    // 사용자 정보 세션 기반으로 조회, 현재 새션을 검증하기 위해 사용
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session == null || session.getAttribute("uid") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long uid = (Long) session.getAttribute("uid");
        String email = (String) session.getAttribute("email");
        User user = userService.findById(uid);
        return ResponseEntity.ok(UserResponse.of(user));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session!=null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping(("/{id}"))
    public UserResponse findById(@PathVariable Long id) {
        return UserResponse.of(userService.findById(id));
    }

    @PatchMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        User updatedUser = userService.update(id, request.getNickname(), request.getProfilePicture());
        return UserResponse.of(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * 이메일 중복검사
     */
    @GetMapping("/check-email")
    public boolean checkEmail(@RequestParam String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * 닉네임 중복검사
     */
    @GetMapping("/check-nickname")
    public boolean checkNickname(@RequestParam String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Data
    public static class CreateUserRequest {
        private String email;
        private String password;
        private String passwordCheck;
        private String nickname;
        private String profilePicture;
    }

    @Data
    public static class UserResponse {
        private Long id;
        private String email;
        private String nickname;
        private String profilePicture;

        public static UserResponse of(User user) {
            return new UserResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getNickname(),
                    user.getProfilePicture()
            );

        }

        public UserResponse(long id, String email, String nickname, String profilePicture) {
            this.id = id;
            this.email = email;
            this.nickname = nickname;
            this.profilePicture = profilePicture;
        }
    }

    @Data
    public static class UpdateUserRequest {
        private String nickname;
        private String profilePicture;
    }

}