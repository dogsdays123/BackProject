package org.zerock.b01.dto.trainerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.trainer.Trainer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trainer_ThumbnailsDTO {
    private Long tthumbnailsId;
    private String imgname;
    private int ord;
    private Long trainerId;
}
