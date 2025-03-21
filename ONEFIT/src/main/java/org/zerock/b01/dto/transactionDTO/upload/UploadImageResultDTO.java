package org.zerock.b01.dto.transactionDTO.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadImageResultDTO {
    private String uuid;

    private String fileName;

    private boolean img;

    public String getLink() {
        if (img) {
            return "s_" + uuid + "_" + fileName;
        } else {
            return uuid + "_" + fileName;
        }
    }
}
