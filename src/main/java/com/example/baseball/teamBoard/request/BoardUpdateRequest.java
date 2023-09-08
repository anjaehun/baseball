package com.example.baseball.teamBoard.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequest {
    private String teamBoardTitle;
    private String teamBoardContent;
    private int noticeOrContent;
}
