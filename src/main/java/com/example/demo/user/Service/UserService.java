package com.example.demo.user.Service;// user/service/UserService.java
import com.example.demo.disease.domain.Disease;
import com.example.demo.disease.repository.DiseaseRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    // 마이페이지 조회
    public UserDto.MyPageResponse getMyPage(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        List<String> diseaseNames = user.getUserDiseases().stream()
                .map(ud -> ud.getDisease().getName())
                .toList();

        return UserDto.MyPageResponse.builder()
                .nickname(user.getNickname())
                .gender(user.getGender())
                .age(user.getAge())
                .height(user.getHeight())
                .weight(user.getWeight())
                .diseaseNames(diseaseNames)
                .medications(user.getMedications())
                .isCooksAtHome(user.isCooksAtHome())
                .build();
    }

    // 정보 수정
    @Transactional
    public void updateProfile(Integer userId, UserDto.UpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        // 기본 정보 및 약 정보 업데이트
        user.updateProfile(request.getAge(), request.getHeight(), request.getWeight(),
                request.isCooksAtHome(), request.getMedications());

        // 질병 정보 초기화 후 재설정
        user.clearDiseases();
        if (request.getDiseaseNames() != null) {
            request.getDiseaseNames().forEach(name -> {
                Disease disease = diseaseRepository.findByName(name)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질병명: " + name));
                user.addDisease(disease);
            });
        }
    }

}