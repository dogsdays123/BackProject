package org.zerock.b01.controller.trainer;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.b01.dto.trainerDTO.Trainer_ThumbnailsDTO;

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

    @Operation(description = "Thumbnail Upload POST")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadThumbnail(@RequestParam("thumbnails") MultipartFile[] files) {
        log.info("uploadThumbnail");

        if (files.length == 0) {
            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
        }

        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    Path path = Paths.get(thumbnailPath, UUID.randomUUID().toString() + file.getOriginalFilename());
                    Files.copy(file.getInputStream(), path);
                    filePaths.add(path.toString());
                }
            }
            return ResponseEntity.ok("Thumbnail Upload Complete: " + filePaths);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Thumbnail Upload Failed: " + e.getMessage());
        }
    }

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
}
