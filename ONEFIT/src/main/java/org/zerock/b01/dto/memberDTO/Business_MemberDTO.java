package org.zerock.b01.dto.memberDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.All_Member;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Business_MemberDTO {
    private Long businessId;

    private String bAddress;

    private String bAddressExtra;

    private String bName;

    private Long bRegNum;

    private String bExponent;

    private Long bPhone;

    private Long bAverage;

    private Long bEmployees;

    private Long bAssets;

    private String bSize;

    private LocalDate bEstaDate;

    private String bHomepage;

    @NotNull
    private String allId;
}
