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
    private String allId;

    private String name;

    private String email;

    private String aPsw;

    private int aPhone;

    private String memberType;

    private boolean del;

    private boolean aSocial;
}
