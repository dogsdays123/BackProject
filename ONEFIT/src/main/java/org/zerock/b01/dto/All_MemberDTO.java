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
    private String all_id;

    private String name;

    private String email;

    private String a_psw;

    private int a_phone;

    private String member_type;

    private boolean del;

    private boolean a_social;
}
