package org.zerock.b01.controller.bang;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.b01.dto.trainerDTO.Trainer_ThumbnailsDTO;

import java.util.List;

@RestController
@Log4j2
public class UpDownController {
    @Value("${org.zerock.upload.thumbnailPath}")
    private String filePath;

    @Operation(description = "Thumbnail Upload POST")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Trainer_ThumbnailsDTO> upload() {
        return null;
    }
}
