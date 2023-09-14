package com.example.baseball.baseballStadiumAndReservation.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.baseball.baseballStadiumAndReservation.entity.BaseballStadiumEntity;
import com.example.baseball.baseballStadiumAndReservation.exception.SameStadiumNameException;
import com.example.baseball.baseballStadiumAndReservation.repository.BaseballStadiumRepository;
import com.example.baseball.baseballStadiumAndReservation.request.StadiumPostRequest;
import com.example.baseball.baseballStadiumAndReservation.response.BaseballStadiumListResponse;
import com.example.baseball.baseballStadiumAndReservation.response.BaseballStadiumPostResponse;
import com.example.baseball.user.entity.UserEntity;
import com.example.baseball.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BaseBallStadiumService {

    private final UserRepository userRepository;
    private final BaseballStadiumRepository baseballStadiumRepository;
    private final ObjectMapper objectMapper;
    private final AmazonS3 amazonS3;

    public BaseBallStadiumService(UserRepository userRepository, BaseballStadiumRepository baseballStadiumRepository, ObjectMapper objectMapper, AmazonS3 amazonS3) {
        this.userRepository = userRepository;
        this.baseballStadiumRepository = baseballStadiumRepository;
        this.objectMapper = objectMapper;
        this.amazonS3 = amazonS3;
    }


    /**
     * 회원 정보를 가져 오는 역활
     * -> 접속중인 유저의 닉네임을 가져온다
     * @return
     */
    public String userNickname(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = "기본 이메일";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername(); // 사용자 이메일 정보를 추출
        }

        // System.out.println("email: " + email);

        String nickname = "기본 닉네임";
        Optional<UserEntity> existingEmail = userRepository.findByEmail(email);

        if (existingEmail.isPresent()) {
            UserEntity user = existingEmail.get();
            System.out.println(user);
            nickname = user.getNickname(); // 사용자의 닉네임을 얻음
        }

        return nickname;
    }

    private String generateUniqueFileName(String originalFileName) {
        // 파일 이름을 고유하게 생성하는 로직
        // 예를 들어, UUID 또는 타임스탬프를 사용할 수 있습니다.
        return UUID.randomUUID().toString() + "-" + originalFileName;
    }

    public String uploadImageToS3(MultipartFile file) {
        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            String bucketNane = "baseball";
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(bucketNane, fileName, file.getInputStream(), metadata);

            // 업로드한 파일의 URL 생성
            String fileUrl = amazonS3.getUrl(bucketNane, fileName).toString();

            return fileUrl;
        } catch (IOException e) {
            // 업로드 실패 처리
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 회원 정보를 가져 오는 역활
     * -> 접속중인 유저의 이름을 가져온다
     * @return
     */
    public String username(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = "기본 이메일";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername(); // 사용자 이메일 정보를 추출
        }


        String name = "기본 이름";
        Optional<UserEntity> existingEmail = userRepository.findByEmail(email);

        if (existingEmail.isPresent()) {
            UserEntity user = existingEmail.get();
            System.out.println(user);
            name = user.getName(); // 사용자의 닉네임을 얻음
        }

        return name;
    }
    public BaseballStadiumPostResponse createStadiumCreate(StadiumPostRequest request, MultipartFile fileStadium) throws SameStadiumNameException {
        String name = username();
        String nickname = userNickname();

        String stadiumImg = uploadImageToS3(fileStadium);

        LocalDateTime currentTime = LocalDateTime.now();

        boolean isTeamNameUnique = !baseballStadiumRepository.existsByStadiumName(request.getStadiumName());

        if (!isTeamNameUnique) {
            throw new SameStadiumNameException("팀의 이름이 중복되었습니다. 다시 시도해 주세요");
        }

        var baseballStadium = BaseballStadiumEntity.builder()
                .stadiumOwnerName(name)
                .stadiumOwnerNickname(nickname)
                .stadiumName(request.getStadiumName())
                .stadiumDescription(request.getStadiumDescription())
                .stadiumReprecentImage(stadiumImg)
                .region(request.getRegion())
                .possibleReservationStartTime(request.getReservationStartTime())
                .possibleReservationEndTime(request.getReservationEndTime())
                .postDate(currentTime)
                .build();

        baseballStadiumRepository.save(baseballStadium);

        BaseballStadiumPostResponse response = new BaseballStadiumPostResponse();
        response.setPostOk("등록이 완료되었습니다.");

        return response;
    }

    public List<BaseballStadiumListResponse> getAllStadiums() {
        List<BaseballStadiumEntity> stadiumEntities = baseballStadiumRepository.findAll();
        return stadiumEntities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BaseballStadiumListResponse mapToResponse(BaseballStadiumEntity entity) {
        return BaseballStadiumListResponse.builder()
                .stadiumName(entity.getStadiumName())
                .stadiumDescription(entity.getStadiumDescription())
                .region(entity.getRegion())
                .stadiumReprecentImage(entity.getStadiumReprecentImage())
                .reservationStartTime(entity.getPossibleReservationStartTime())
                .reservationEndTime(entity.getPossibleReservationEndTime())
                .build();
    }
}
