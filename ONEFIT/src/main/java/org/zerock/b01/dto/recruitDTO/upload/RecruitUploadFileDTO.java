package org.zerock.b01.dto.recruitDTO.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;
import java.util.List;

@Data
public class RecruitUploadFileDTO {

    private List<MultipartFile> files;
}
