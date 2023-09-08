package com.example.baseball.team.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.hitterRecord.repository.HitterRecordRepository;
import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.pitcherRecord.repository.PitcherRecordRepository;
import com.example.baseball.team.Request.TeamPostRequestPart;
import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.team.exception.SameTeamNameException;
import com.example.baseball.team.repository.TeamRepository;
import com.example.baseball.team.response.TeamListShowByTeamIdResponse;
import com.example.baseball.team.response.TeamListShowResponse;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import com.example.baseball.teamMember.enumType.TeamFounderAcceptRole;
import com.example.baseball.teamMember.repository.TeamMemberRepository;
import com.example.baseball.teamPerformance.TeamPerformanceEntity;
import com.example.baseball.teamPerformance.repository.TeamPerformanceRepository;
import com.example.baseball.user.entity.UserEntity;
import com.example.baseball.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository ;
    private final PitcherRecordRepository pitcherRecordRepository;

    private final HitterRecordRepository hitterRecordRepository;

    private final TeamPerformanceRepository teamPerformanceRepository;

    private final ObjectMapper objectMapper;

    private final AmazonS3 amazonS3;




    public TeamService(UserRepository userRepository, TeamRepository teamRepository, TeamMemberRepository teamMemberRepository, PitcherRecordRepository pitcherRecordRepository, HitterRecordRepository hitterRecordRepository, TeamPerformanceRepository teamPerformanceRepository, ObjectMapper objectMapper, AmazonS3 amazonS3) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.pitcherRecordRepository = pitcherRecordRepository;
        this.hitterRecordRepository = hitterRecordRepository;
        this.teamPerformanceRepository = teamPerformanceRepository;
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
    public TeamEntity postTeam(TeamPostRequestPart request, MultipartFile teamImg1,MultipartFile teamLogo) throws SameTeamNameException, NoTeamByOneException, JsonProcessingException {



        String name = username();
        String nickname = userNickname();
        String mainCoach = request.getMainCoach();
        String teamName = request.getTeamName();

        // bucket 를 사용해서 값 넘기기
        LocalDateTime currentTime = LocalDateTime.now();

        // 중복된 팀 이름을 확인
        boolean isTeamNameUnique = !teamRepository.existsByTeamName(teamName);

        if (!isTeamNameUnique) {
            throw new SameTeamNameException("팀의 이름이 중복되었습니다. 다시 시도해 주세요");
        }

        Optional<UserEntity> existingEmail = userRepository.findByNickname(nickname);
        UserEntity user;
        // s3 부분
        String createTeamImg = uploadImageToS3(teamImg1);
        String createTeamLogo = uploadImageToS3(teamLogo);

        if (existingEmail.isPresent()) {
            user = existingEmail.get();
        } else {
            // 사용자를 찾지 못한 경우에 대한 처리
            user = null; // 또는 다른 값으로 설정
        }

        if ("미정".equals(request.getMainCoach())) {
            mainCoach = "공석";
        }

        var team = TeamEntity.builder()
                .masterName(name)
                .masterNickname(nickname)
                .teamDescription(request.getTeamDescription())
                .teamName(teamName)
                .mainCoach(mainCoach)
                .teamImg(createTeamImg)
                .teamLogoImage(createTeamLogo)
                .registerDt(currentTime)
                .id(user)
                .build();

        teamRepository.save(team);

        // 창설자 팀 멤버 등록

        // teamName -> 예외처리로 인해 중복된 팀 네임은 해결 될 수 있음
        TeamEntity findByTeamName = (TeamEntity) teamRepository.findByTeamName(teamName)
                .orElseThrow(() -> new NoTeamByOneException("팀정보가 없습니다"));

        var teamPerfomance = TeamPerformanceEntity.builder()
                .team(findByTeamName)
                .win(0.0)
                .lose(0.0)
                .winRate(0.0)
                .build();

        teamPerformanceRepository.save(teamPerfomance);

        var teamMember = TeamMemberEntity.builder()
                .name(name)
                .nickname(nickname)
                // TEAM_MEMBER_OK => 창설자는 따로 인증절차를 걸치지 않고 등록이 가능하다
                .teamFounderAcceptRole(TeamFounderAcceptRole.TEAM_MEMBER_OK)
                .jerseyNumber(request.getJerseyNumber())
                .height(request.getHeight())
                .weight(request.getWeight())
                .reasonForTeamMembership(request.getReasonForTeamMembership())
                .determinationForTheFuture(request.getDeterminationForTheFuture())
                .registerDt(currentTime)
                .team(findByTeamName)
                .build();

        teamMemberRepository.save(teamMember);

        TeamMemberEntity teamMemberByTeamNickname = (TeamMemberEntity) teamMemberRepository.findByTeamAndNickname(findByTeamName,nickname)
                .orElseThrow(() -> new NoTeamByOneException("팀멤버가 없습니다"));

        var pitcherRecord = PitcherRecordEntity.builder()
                .teamName(teamName)
                .name(name)
                .nickname(nickname)
                .atBat(0.0).hit(0.0).doubleHit(0.0).tripleHit(0.0).homeRun(0.0).strikeout(0.0).unintentionalWalk(0.0).intentionalWalk(0.0).runsAllowed(0.0).earnedRun(0.0).earnedRunAverage(0.0).whip(0.0).strikeoutPercent(0.0)
                .team(findByTeamName)
                .teamMember(teamMemberByTeamNickname)
                .build();

        var hitterRecord = HitterRecordEntity.builder()
                .teamName(teamName)
                .name(name)
                .nickname(nickname)
                .atBat(0.0).hit(0.0).doubleHit(0.0).tripleHit(0.0).homeRun(0.0).unintentionalWalk(0.0).intentionalWalk(0.0).hitByPitch(0.0).runsBattedIn(0.0).runs(0.0).stolenBases(0.0).attempts(0.0).strikeout(0.0).battingAverage(0.0).sluggingPercentage(0.0).onBasePercentage(0.0).stolenBaseSuccessRate(0.0)
                .team(findByTeamName)
                .teamMember(teamMemberByTeamNickname)
                .build();

        pitcherRecordRepository.save(pitcherRecord);
        hitterRecordRepository.save(hitterRecord);

        return team;
    }



    public List<TeamListShowResponse> getAllTeamList() {
        List<TeamEntity> teamList = teamRepository.findAll();

        List<TeamListShowResponse> teamListShowResponse = new ArrayList<>();

        for(TeamEntity teamEntityes :  teamList ){
            TeamListShowResponse response = new TeamListShowResponse();
            response.setTeamId(teamEntityes.getTeamId());
            response.setTeamName(teamEntityes.getTeamName());
            response.setTeamDescription(teamEntityes.getTeamDescription());
            response.setTeamImg(teamEntityes.getTeamImg());

            teamListShowResponse.add(response);
        }
        return teamListShowResponse;

    }

    public TeamListShowByTeamIdResponse getTeamByTeamId(Integer teamId) throws NoTeamByOneException {
        Optional<TeamEntity> optionalTeam = teamRepository.findById(teamId);

        if(!(optionalTeam.isPresent())){
            throw new NoTeamByOneException("팀 정보가 없습니다. ");
        }

        TeamListShowByTeamIdResponse response =
                TeamListShowByTeamIdResponse.builder()
                        .teamId(optionalTeam.get().getTeamId())
                        .teamName(optionalTeam.get().getTeamName())
                        .mainCoach(optionalTeam.get().getMainCoach())
                        .teamDescription(optionalTeam.get().getTeamDescription())
                        .teamLogoImage(optionalTeam.get().getTeamLogoImage())
                        .teamImg(optionalTeam.get().getTeamImg())
                        .build();



            return response;

    }






}
