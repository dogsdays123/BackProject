package org.zerock.b01.dto.boardDTO.boardUpload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardUploadResultDTO {

    private String uuid;

    private String fileName;

    public String getLink() {
        return uuid + "_" +fileName;
    }
}
