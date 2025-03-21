package org.zerock.b01.controller.trainer;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.b01.dto.trainerDTO.Trainer_ThumbnailsDTO;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trainer_thumbnail")
@Log4j2
public class TrainerThumbnailController {
    @Value("${org.zerock.upload.thumbnailPath}")
    private String thumbnailPath;

    List<String> filePaths = new ArrayList<>();

    @Operation(description = "Thumbnail Delete by Delete method")
    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteThumbnail(@PathVariable String filename) {
        log.info("deleteThumbnail");

        try {
            Path filePath = Paths.get(thumbnailPath, filename);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(404).body("파일이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Thumbnail Delete Failed: " + e.getMessage());
        }
    }

    @Operation(description = "GET방식으로 업로드된 파일 조회")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        log.info("viewFileGET");
        Resource resource = new FileSystemResource(thumbnailPath + File.separator + fileName);
        String resourceName = resource.getFilename();
        log.info(resourceName);
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath()));  //aaa.jpg -> aaa/jpg
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
