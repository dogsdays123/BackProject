package org.zerock.b01.dto.trainerDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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

    // thumbnails 는 받아올 때, original 은 원래 썸네일들의 정보를 담음
    // DB 에서 삭제되지 않으면 조회할 때에도 삭제되지 않는 원리를 이용
    private MultipartFile[] thumbnails;
    private List<String> originalThumbnails;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
