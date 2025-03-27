package org.zerock.b01.dto.memberDTO;

import lombok.Getter;
import lombok.ToString;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;

import java.util.List;

@Getter
@ToString
public class MemberDataDTO {
    TrainerViewDTO findTrainer;
    RecruitDTO findRecruit;
}
