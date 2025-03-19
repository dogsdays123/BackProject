package org.zerock.b01.dto.trainerDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.b01.domain.member.User_Member;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDTO {
    private Long trainerId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String academyFinal;
    @NotEmpty
    private String academy;
    @NotNull
    private int careerPeriod;
    private String career;
    private String license;
    private String prize;
    @NotEmpty
    private String wantJob;
    @NotEmpty
    private String wantType;
    @NotEmpty
    private String wantLegion;
    @NotNull
    private Double wantTime;
    @NotNull
    private int wantDay;
    @NotEmpty
    private String wantDayType;
    @NotEmpty
    private String wantSalType;
    @NotNull
    private int wantSal;
    private String content;
    @NotNull
    private Long userId;

    private LocalDateTime regdate;
    private LocalDateTime moddate;
}
