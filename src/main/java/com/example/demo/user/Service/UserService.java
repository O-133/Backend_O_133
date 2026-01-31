package com.example.demo.user.Service;// user/service/UserService.java
import com.example.demo.disease.domain.Disease;
import com.example.demo.disease.repository.DiseaseRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //
public class UserService {

    private final UserRepository userRepository;
    private final DiseaseRepository diseaseRepository;

    @Transactional //
    public UserDto.Response join(UserDto.CreateRequest request) {
        User user = User.builder()
                .nickname(request.getNickname())
                .gender(request.getGender())
                .age(request.getAge())
                .height(request.getHeight())
                .weight(request.getWeight())
                .isCooksAtHome(request.isCooksAtHome())
                .medications(request.getMedications())
                .build();

        if (request.getDiseaseNames() != null) {
            for (String name : request.getDiseaseNames()) {
                Disease disease = diseaseRepository.findByName(name)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질병: " + name));
                user.addDisease(disease);
            }
        }
        User savedUser = userRepository.save(user);

        return UserDto.Response.builder()
                .id(savedUser.getId())
                .nickname(savedUser.getNickname())
                .build();
    }
}