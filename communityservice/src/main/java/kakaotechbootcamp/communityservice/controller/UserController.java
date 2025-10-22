package kakaotechbootcamp.communityservice.controller;

import kakaotechbootcamp.communityservice.dto.*;
import kakaotechbootcamp.communityservice.entity.User;
import kakaotechbootcamp.communityservice.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost"}, methods = {RequestMethod.POST, RequestMethod.OPTIONS}, allowedHeaders = "*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("signup")
    public UserResponse create(@RequestBody CreateUserRequest request){
        User saved = userService.signUp(request.getEmail(), request.getPassword(), request.getPasswordCheck(), request.getNickname(), request.getProfilePicture());
        return UserResponse.of(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        String loginResult = userService.login(loginRequest);
        return ResponseEntity.ok(loginResult);
    }
    @GetMapping(("/{id}"))
    public UserResponse findById(@PathVariable Long id){
        return UserResponse.of(userService.findById(id));
    }

    @PatchMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UpdateUserRequest request){
        User updatedUser = userService.update(id, request.getNickname(), request.getProfilePicture());
        return UserResponse.of(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
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

//    @Data
//    public static class LoginRequest {
//        private String email;
//        private String password;
//    }
}
