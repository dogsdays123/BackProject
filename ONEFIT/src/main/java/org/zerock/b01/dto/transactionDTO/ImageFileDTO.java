package org.zerock.b01.dto.transactionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageFileDTO {

    private String imageUuid;

    private String imageFileName;

    private int ord;
}
