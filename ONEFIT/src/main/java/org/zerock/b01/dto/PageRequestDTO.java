package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type; //검색의 종류

    private String keyword;

    private String gender; // 채용 공고 성별 필터

    private String age; // 채용 공고 나이 필터

    private String jobTypeFull; // 채용 공고 정규직 필터

    private String jobTypeFree; // 채용 공고 프리 필터

    private String jobTypePart; // 채용 공고 파트 필터

    private String jobTypeAlba; // 채용 공고 알바 필터

    private String jobTypeTrainee; // 채용 공고 연습생 필터

    private String workDays; // 채용 공고 근무 요일 필터

    private String dutyDays; // 채용 공고 당직 유무 필터

    private String startTime; // 채용 공고 근무 시작 시간 필터

    private String endTime; // 채용 공고 근무 종료 시간 필터

    private String timeNegotiable; // 채용 공고 시간 협의 가능 필터

    private String industry; // 채용 공고 업직종 필터

    private String regDateFilter; // 채용 공고 등록일 필터

    private LocalDate startDate; //날짜 검색: 시작일

    private LocalDate endDate; // 날짜 검색: 시작일

    public String[] getTypes() {
        if(type == null || type.isEmpty()) {
            return null;
        }
        return type.split("");
    }

    public Pageable getPageable(String...props) {
        return PageRequest.of(this.page -1, this.size, Sort.by(props).descending());
    }

    private String link;

    public String getLink() {
        if(link == null) {
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);

            if(type != null && type.length() > 0) {
                builder.append("&type=" + type);
            }

            if(keyword != null) {
                try{
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e){

                }
            }

            //날짜 검색
            if (startDate != null) {
                builder.append("&startDate=").append(startDate);
            }
            if (endDate != null) {
                builder.append("&endDate=").append(endDate);
            }

            link = builder.toString();
        }
        return link;
    }
}
