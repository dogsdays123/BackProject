package org.zerock.b01.dto.memberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Business_Member_DataDTO {
    private String b_no;         // 사업자 번호
    private String b_stt;        // 사업자 상태
    private String tax_type;     // 과세 유형
    private String rbf_tax_type; // 예상 과세 유형
    private String utcc_yn;      // 사용 여부
}
