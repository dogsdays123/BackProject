package org.zerock.b01.service.trainerService;

import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;

public interface TrainerService {
    Long registerTrainer(TrainerDTO trainerDTO);

    default Trainer dtoToEntity(TrainerDTO trainerDTO) {
        Trainer trainer = Trainer.builder()
                .trainerId(trainerDTO.getTrainerId())
                .title(trainerDTO.getTitle())
                .academyFinal(trainerDTO.getAcademyFinal())
                .academy(trainerDTO.getAcademy())
                .careerPeriod(trainerDTO.getCareerPeriod())
                .career(trainerDTO.getCareer())
                .license(trainerDTO.getLicense())
                .prize(trainerDTO.getPrize())
                .wantJob(trainerDTO.getWantJob())
                .wantType(trainerDTO.getWantType())
                .wantLegion(trainerDTO.getWantLegion())
                .wantTime(trainerDTO.getWantTime())
                .wantDay(trainerDTO.getWantDay())
                .wantDayType(trainerDTO.getWantDayType())
                .wantSalType(trainerDTO.getWantSalType())
                .wantSal(trainerDTO.getWantSal())
                .content(trainerDTO.getContent())
                .userMember(trainerDTO.getUserMember())
                .build();

        return trainer;
    }

    default TrainerDTO entityToDto(Trainer trainer) {
        TrainerDTO trainerDTO = TrainerDTO.builder()
                .trainerId(trainer.getTrainerId())
                .title(trainer.getTitle())
                .academyFinal(trainer.getAcademyFinal())
                .academy(trainer.getAcademy())
                .careerPeriod(trainer.getCareerPeriod())
                .career(trainer.getCareer())
                .license(trainer.getLicense())
                .prize(trainer.getPrize())
                .wantJob(trainer.getWantJob())
                .wantType(trainer.getWantType())
                .wantLegion(trainer.getWantLegion())
                .wantTime(trainer.getWantTime())
                .wantDay(trainer.getWantDay())
                .wantDayType(trainer.getWantDayType())
                .wantSalType(trainer.getWantSalType())
                .wantSal(trainer.getWantSal())
                .content(trainer.getContent())
                .userMember(trainer.getUserMember())
                .regdate(trainer.getRegdate())
                .moddate(trainer.getModdate())
                .build();

        return trainerDTO;
    }
}
