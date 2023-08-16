package com.example.baseball.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerRequest {

    private String name;

    private String email;

    private String password;

    private String nickname;

}
