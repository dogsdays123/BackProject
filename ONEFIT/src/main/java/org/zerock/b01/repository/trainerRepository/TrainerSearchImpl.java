package org.zerock.b01.repository.trainerRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.trainer.QTrainer;
import org.zerock.b01.domain.trainer.QTrainer_Thumbnails;
import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TrainerSearchImpl extends QuerydslRepositorySupport implements TrainerSearch {
    public TrainerSearchImpl() {
        super(Trainer.class);
    }

    @Override
    public Page<TrainerViewDTO> search(String[] filters, Pageable pageable) {
        QTrainer trainer = QTrainer.trainer;
        JPQLQuery<Trainer> query = from(trainer);

        // 필터 조건이 존재한다면
        if (filters != null && filters.length > 0) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String filter : filters) {
                String key = filter.substring(0, filter.indexOf("-"));
                String value = filter.substring(filter.indexOf("-") + 1);

                if (value.equals("no")) {
                    continue;
                }

                switch (key) {
                    case "c":   // 경력기간
                        booleanBuilder.and(trainer.careerPeriod.eq(Integer.parseInt(value)));
                        break;
                    case "a":   // 최종학력
                        booleanBuilder.and(trainer.academyFinal.eq(value));
                        break;
                    case "l":   // 희망지역
                        booleanBuilder.and(trainer.wantLegion.eq(value));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        getQuerydsl().applyPagination(pageable, query);
        List<Trainer> trainerList = query.fetch();

        List<TrainerViewDTO> dtoList = trainerList.stream().map(tr -> {

            List<String> thumbnails = tr.getImageSet().stream().sorted()
                    .map(trainerThumbnails ->
                            trainerThumbnails.getThumbnailUuid() + "_" + trainerThumbnails.getImgname()
                    ).collect(Collectors.toList());

            TrainerViewDTO dto = TrainerViewDTO.builder()
                    .trainerId(tr.getTrainerId())
                    .title(tr.getTitle())
                    .academyFinal(tr.getAcademyFinal())
                    .academy(tr.getAcademy())
                    .careerPeriod(tr.getCareerPeriod())
                    .career(tr.getCareer())
                    .license(tr.getLicense())
                    .prize(tr.getPrize())
                    .wantJob(tr.getWantJob())
                    .wantType(tr.getWantType())
                    .wantLegion(tr.getWantLegion())
                    .wantTime(tr.getWantTime())
                    .wantDay(tr.getWantDay())
                    .wantDayType(tr.getWantDayType())
                    .wantSalType(tr.getWantSalType())
                    .wantSal(tr.getWantSal())
                    .content(tr.getContent())
                    .userId(tr.getUserMember().getUserId())
                    .thumbnails(thumbnails)
                    .name(tr.getUserMember().getAllMember().getName())
                    .gender(tr.getUserMember().getUResident() / ((tr.getUserMember().getUResident().toString().length() - 1) * 10L) % 2 == 1 ? "남" : "여")
                    .birthday(tr.getUserMember().getUBirthday())
                    .email(tr.getUserMember().getAllMember().getEmail())
                    .phone(tr.getUserMember().getAllMember().getAPhone().toString())
                    .address(tr.getUserMember().getUAddress())
                    .regDate(tr.getRegDate())
                    .modDate(tr.getModDate())
                    .build();

            return dto;
        }).collect(Collectors.toList());

        long totalCount = query.fetchCount();
        return new PageImpl<>(dtoList, pageable, totalCount);
    }
}
