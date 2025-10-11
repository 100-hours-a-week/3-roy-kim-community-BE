package kakaotechbootcamp.communityservice.service;

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
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        // 비밀번호 형식 검증: 8~20자, 대문자/소문자/숫자/특수문자 각각 최소 1개 포함
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        if (!password.matches(passwordPattern)) {
            throw new IllegalArgumentException("비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다.");
        }
        return userRepository.save(user);
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
