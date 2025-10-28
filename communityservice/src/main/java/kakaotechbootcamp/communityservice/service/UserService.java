package kakaotechbootcamp.communityservice.service;

import kakaotechbootcamp.communityservice.controller.UserController;
import kakaotechbootcamp.communityservice.dto.LoginRequest;
import kakaotechbootcamp.communityservice.exception.BadRequestException;
import kakaotechbootcamp.communityservice.exception.ConflictException;
import kakaotechbootcamp.communityservice.exception.UnprocessableEntityException;
import org.springframework.transaction.annotation.Transactional;
import kakaotechbootcamp.communityservice.entity.User;
import kakaotechbootcamp.communityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
//    사용자 회원가입
    @Transactional
    public User signUp(String email,  String password, String passwordCheck, String nickname,String profilePicture) {
        User user = new User(email, password, passwordCheck, nickname, profilePicture);
        if (!password.equals(passwordCheck)) {
//            비밀번호 확인이 일치해야함
            throw new UnprocessableEntityException("비밀번호 확인과 다릅니다");

        }
        return userRepository.save(user);
    }

    public String login (LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new BadRequestException("이메일을 확인해주세요"));

        if(!user.getPassword().equals(loginRequest.getPassword())){
            throw new BadRequestException("비밀번호를 확인해주세요");
        }
        return "로그인 성공";
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User getReferenceById(Long id) {
        return userRepository.getReferenceById(id);
    }
//    사용자 닉네입, 프로필 사진 변경
    @Transactional
    public User update(Long id, String nickname, String profilePicture) {
        User user = findById(id);
        if (nickname != null) {
            user.setNickname(nickname);
        }
        if (profilePicture != null) {
            user.setProfilePicture(profilePicture);
        }

        return user;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
