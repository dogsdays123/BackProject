package org.zerock.b01.dto.memberDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.All_Member;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User_MemberDTO {
    private Long userId;

    private String uNickname;

    private LocalDate uBirthday;

    private String uAddress;

    private Long uResident;

    @NotNull
    private String allId;
}
