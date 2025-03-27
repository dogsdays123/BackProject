package org.zerock.b01.dto.boardDTO.boardUpload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardUploadFileDTO {

    private List<MultipartFile> files;
}
