package org.zerock.b01.dto.recruitDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.domain.trainer.Trainer;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitApplyDTO {

    private Long re_apply_id;

    private LocalDateTime regdate;

    private Recruit_Register recruit_register;

    private Trainer trainer;

}
