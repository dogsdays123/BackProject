package org.zerock.b01.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class All_MemberDTO {
    private Long all_id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String a_member_id;

    @NotEmpty
    private String a_psw;

    private Long a_phone;

    private boolean a_social;

    @NotEmpty
    private String roles;

    private LocalDateTime regdate;
    private LocalDateTime moddate;
}
