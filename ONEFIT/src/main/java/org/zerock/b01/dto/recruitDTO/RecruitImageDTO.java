package org.zerock.b01.dto.recruitDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitImageDTO {

    private String re_img_id;

    private String re_img_title;

    private int re_img_ord;
}
