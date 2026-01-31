package com.example.demo.user.Service;// user/service/UserService.java
import com.example.demo.disease.domain.Disease;
import com.example.demo.disease.repository.DiseaseRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserDisease;
import com.example.demo.user.domain.UserDiseaseId;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.repository.UserDiseaseRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// user/service/UserService.java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final DiseaseRepository diseaseRepository;
    private final UserDiseaseRepository userDiseaseRepository; // 추가 필수

    @Transactional
    public UserDto.Response join(UserDto.CreateRequest request) {
        // 1. 유저를 먼저 저장하여 생성된 ID를 확보
        User user = userRepository.save(User.builder()
                .nickname(request.getNickname())
                .gender(request.getGender())
                .age(request.getAge())
                .height(request.getHeight())
                .weight(request.getWeight())
                .isCooksAtHome(request.isCooksAtHome())
                .medications(request.getMedications())
                .build());

        // 2. 질병 매핑 정보를 하나씩 생성하여 직접 저장
        if (request.getDiseaseNames() != null) {
            for (String name : request.getDiseaseNames()) {
                Disease disease = diseaseRepository.findByName(name)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질병: " + name));

                // 복합키 생성 및 매핑 엔티티 저장
                UserDiseaseId userDiseaseId = new UserDiseaseId(user.getId().intValue(), disease.getId().intValue());
                userDiseaseRepository.save(new UserDisease(userDiseaseId));
            }
        }

        return UserDto.Response.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}