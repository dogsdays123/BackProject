package org.zerock.b01.dto.trainerDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.zerock.b01.dto.trainerDTO.TrainerPageRequestDTO;

import java.util.Arrays;
import java.util.List;

@Getter
@ToString
public class TrainerPageResponseDTO<E> {

    private int page;
    private int size;
    private int maxPages;
    private int total;

    private int start;
    private int end;

    private boolean prev;
    private boolean next;

    private List<E> dtoList;
    private String[] filters;
    private String sorting;
    private boolean isSearching;
    private String link;

    // 강사 리스트용
    @Builder(builderMethodName = "withAll")
    public TrainerPageResponseDTO(TrainerPageRequestDTO trainerPageRequestDTO, List<E> dtoList, int total) {
        this.page = trainerPageRequestDTO.getPage();
        this.size = trainerPageRequestDTO.getSize();
        this.maxPages = trainerPageRequestDTO.getMaxPages();
        this.total = total;
        this.dtoList = dtoList;
        this.isSearching = false;
        this.link = trainerPageRequestDTO.getLink();

        this.filters = trainerPageRequestDTO.getFilters();
        if (this.filters == null || this.filters.length == 0) {
            this.filters = new String[0];
        } else {
            String[] checkFilters = new String[] {"c-no", "a-no", "l-no"};
            this.isSearching = !Arrays.equals(this.filters, checkFilters);
        }

        this.sorting = trainerPageRequestDTO.getSorting();
        if (this.sorting == null || this.sorting.isEmpty()) {
            this.sorting = "regDate";
        }

        // 현재 페이지 기준으로 라벨에 나와야 하는 마지막 페이지
        this.end = (int)(Math.ceil(this.page / (double) maxPages)) * maxPages;
        // 현재 라벨에서 처음에 나와야 하는 페이지
        this.start = this.end - maxPages + 1;
        // 최대로 가능한 페이지를 기준으로 계산된 실제 라벨에 나올 마지막 페이지 번호
        this.end = Math.min(end, (int)(Math.ceil(total / (double) size)));
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;

        // 검색 결과가 없다면 페이지는 항상 1로 고정됨
        if (total <= 0) {
            this.end = 1;
        }
    }
}
