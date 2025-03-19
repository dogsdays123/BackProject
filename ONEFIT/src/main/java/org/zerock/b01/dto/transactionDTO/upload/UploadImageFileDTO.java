package org.zerock.b01.dto.transactionDTO.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadImageFileDTO {
    private List<MultipartFile> files;
}
