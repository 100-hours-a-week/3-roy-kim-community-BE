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
    public User signUp(String email,  String password, String nickname,String profilePicture) {
        User user = new User(email, password, nickname, profilePicture);
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
