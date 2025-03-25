package org.zerock.b01.dto.trainerDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.zerock.b01.dto.trainerDTO.TrainerPageRequestDTO;

import java.util.List;

@Getter
@ToString
public class TrainerPageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    private int start;
    private int end;

    private boolean prev;
    private boolean next;

    private List<E> dtoList;

    // 강사 리스트용
    @Builder(builderMethodName = "withAll")
    public TrainerPageResponseDTO(TrainerPageRequestDTO trainerPageRequestDTO, List<E> dtoList, int total) {
        if (total <= 0) {
            return;
        }

        this.page = trainerPageRequestDTO.getPage();
        this.size = trainerPageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;
        this.end = (int)(Math.ceil(this.page / (double) size)) * size;
        this.start = this.end - size + 1;  // 화면에서의 시작 번호
        this.end = Math.min(end, (int)(Math.ceil(total / (double)size)));
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}
