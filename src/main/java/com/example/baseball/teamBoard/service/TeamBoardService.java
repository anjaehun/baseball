package com.example.baseball.teamBoard.service;

import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.team.repository.TeamRepository;
import com.example.baseball.teamBoard.entity.TeamBoardEntity;
import com.example.baseball.teamBoard.enumType.BoardCategoryEnum;
import com.example.baseball.teamBoard.exception.NoBoardByOneException;
import com.example.baseball.teamBoard.exception.NotTheAuthorOfThePostException;
import com.example.baseball.teamBoard.repository.TeamBoardRepository;
import com.example.baseball.teamBoard.request.BoardRequest;
import com.example.baseball.teamBoard.request.BoardUpdateRequest;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import com.example.baseball.teamMember.repository.TeamMemberRepository;
import com.example.baseball.user.entity.UserEntity;
import com.example.baseball.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeamBoardService {

    private final UserRepository userRepository;

    private final TeamRepository teamRepository ;
    private final TeamMemberRepository teamMemberRepository;

    private final TeamBoardRepository teamBoardRepository;

    public TeamBoardService(UserRepository userRepository, TeamRepository teamRepository, TeamMemberRepository teamMemberRepository, TeamBoardRepository teamBoardRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.teamBoardRepository = teamBoardRepository;
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

        // System.out.println("email: " + email);

        String name = "기본 이름";
        Optional<UserEntity> existingEmail = userRepository.findByEmail(email);

        if (existingEmail.isPresent()) {
            UserEntity user = existingEmail.get();
            System.out.println(user);
            name = user.getName(); // 사용자의 닉네임을 얻음
        }

        return name;
    }

    public TeamBoardEntity createTeamBoardGeneral(Long teamId, BoardRequest boardRequest) throws NoTeamByOneException {
        // 사용자 이름 가져오기
        String name = username();
        String nickname = userNickname();

        // teamId와 teamMemberId를 사용하여 팀 및 팀 멤버를 가져오는 코드 (이전 예제와 동일)
        TeamEntity team = teamRepository.findById(Math.toIntExact(teamId))
                .orElseThrow(() -> new NoTeamByOneException("팀정보가 없습니다"));

        TeamMemberEntity teamMember = (TeamMemberEntity) teamMemberRepository.findByTeamAndNickname(team, nickname)
                .orElseThrow(() -> new NoTeamByOneException("팀 멤버가 아닙니다"));

        // boardRequest에서 필요한 정보 추출
        String title = boardRequest.getTeamBoardTitle();
        String content = boardRequest.getTeamBoardContent();
        LocalDateTime currentTime = LocalDateTime.now();

        // TeamBoardEntity 생성
        var teamBoardPost = TeamBoardEntity.builder()
                .boardTitle(title)
                .boardContent(content)
                .teamMembername(name)
                .teamMemberNickname(nickname)
                .teamPostCategory(BoardCategoryEnum.GENERAL)
                .teamPostDate(currentTime)
                .modificationDate(currentTime)
                .team(team)
                .teamMember(teamMember)
                .build();

        return teamBoardRepository.save(teamBoardPost);
    }

    public TeamBoardEntity createTeamBoardNotice(Long teamId, BoardRequest boardRequest) throws NoTeamByOneException {
        String name = username();
        String nickname = userNickname();

        // teamId와 teamMemberId를 사용하여 팀 및 팀 멤버를 가져오는 코드 (이전 예제와 동일)
        TeamEntity team = teamRepository.findById(Math.toIntExact(teamId))
                .orElseThrow(() -> new NoTeamByOneException("팀정보가 없습니다"));

        TeamMemberEntity teamMember = (TeamMemberEntity) teamMemberRepository.findByTeamAndNickname(team, nickname)
                .orElseThrow(() -> new NoTeamByOneException("팀 멤버가 아닙니다"));

        // boardRequest에서 필요한 정보 추출
        String title = boardRequest.getTeamBoardTitle();
        String content = boardRequest.getTeamBoardContent();
        LocalDateTime currentTime = LocalDateTime.now();

        // TeamBoardEntity 생성
        var teamBoardPost = TeamBoardEntity.builder()
                .boardTitle(title)
                .boardContent(content)
                .teamMembername(name)
                .teamMemberNickname(nickname)
                .teamPostCategory(BoardCategoryEnum.NOTICE)
                .teamPostDate(currentTime)
                .modificationDate(currentTime)
                .team(team)
                .teamMember(teamMember)
                .build();

        return teamBoardRepository.save(teamBoardPost);
    }

    public List<TeamBoardEntity> getAllTeamBoards(Long teamId) throws NoTeamByOneException {
        TeamEntity team = teamRepository.findById(Math.toIntExact(teamId))
                .orElseThrow(() -> new NoTeamByOneException("팀 정보가 없습니다"));
        return teamBoardRepository.findAllByTeam(team);
    }

    public TeamBoardEntity getTeamBoardById(Long teamId, Integer boardId) throws NoTeamByOneException, NoBoardByOneException {
        TeamEntity team = teamRepository.findById(Math.toIntExact(teamId))
                .orElseThrow(() -> new NoTeamByOneException("팀 정보가 없습니다"));
        Optional<TeamBoardEntity> board = teamBoardRepository.findByTeamAndTeamBoardId(team, boardId);

        return board.orElseThrow(() -> new NoBoardByOneException("게시글을 찾을 수 없습니다"));
    }


    public TeamBoardEntity updateTeamBoard(Long teamId, Integer boardId, BoardUpdateRequest boardUpdateRequest) throws NotTheAuthorOfThePostException, NoBoardByOneException, NoTeamByOneException {
        String nickname = userNickname();


        TeamEntity team = teamRepository.findById(Math.toIntExact(teamId))
                .orElseThrow(() -> new NoTeamByOneException("팀 정보가 없습니다"));

        Optional<TeamBoardEntity> boardOptional = teamBoardRepository.findByTeamAndTeamBoardId(team, boardId);

        TeamBoardEntity board = boardOptional.orElseThrow(() -> new NoBoardByOneException("게시글을 찾을 수 없습니다"));

        if(!(nickname.equals(board.getTeamMemberNickname()))) {
            throw new NotTheAuthorOfThePostException("게시글작성자가 아닙니다.");
        }


            int noticeOrContent = boardUpdateRequest.getNoticeOrContent();
            String noticeOrContentFin = String.valueOf(BoardCategoryEnum.GENERAL);

            if(noticeOrContent == 1){
                noticeOrContentFin = String.valueOf(BoardCategoryEnum.NOTICE);
            }

            // 업데이트할 내용 적용
            board.setTeamPostCategory(BoardCategoryEnum.valueOf(noticeOrContentFin));
            board.setBoardTitle(boardUpdateRequest.getTeamBoardTitle());
            board.setBoardContent(boardUpdateRequest.getTeamBoardContent());
            board.setModificationDate(LocalDateTime.now());

            return teamBoardRepository.save(board);
    }

    public void deleteTeamBoardById(Long teamId, Integer boardId) throws NoTeamByOneException, NoBoardByOneException, NotTheAuthorOfThePostException {
        String nickname = userNickname();
        TeamEntity team = teamRepository.findById(Math.toIntExact(teamId))
                .orElseThrow(() -> new NoTeamByOneException("팀 정보가 없습니다"));

        Optional<TeamBoardEntity> boardOptional = teamBoardRepository.findByTeamAndTeamBoardId(team, boardId);

        if (boardOptional.isPresent()) {
            TeamBoardEntity board = boardOptional.get();
            if(!(nickname.equals(board.getTeamMemberNickname()))){
                throw new NotTheAuthorOfThePostException("게시글작성자가 아닙니다.");
            }


            // 삭제 로직을 수행합니다.
            teamBoardRepository.delete(board);
        } else {
            throw new NoBoardByOneException("게시글을 찾을 수 없습니다");
        }


    }
}
