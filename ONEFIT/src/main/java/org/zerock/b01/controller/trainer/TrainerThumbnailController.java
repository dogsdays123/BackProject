package org.zerock.b01.controller.trainer;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.b01.domain.trainer.Trainer_Thumbnails;
import org.zerock.b01.dto.trainerDTO.Trainer_ThumbnailsDTO;
import org.zerock.b01.repository.trainerRepository.TrainerRepository;
import org.zerock.b01.repository.trainerRepository.Trainer_ThumbnailsRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trainer_thumbnail")
@RequiredArgsConstructor
@Log4j2
public class TrainerThumbnailController {
    @Value("${org.zerock.upload.thumbnailPath}")
    private String thumbnailPath;

    private final TrainerRepository trainerRepository;
    private final Trainer_ThumbnailsRepository trainerThumbnailsRepository;

    List<String> filePaths = new ArrayList<>();

    @Operation(description = "Thumbnail Delete by Delete method")
    @Transactional
    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteThumbnail(@PathVariable String filename, Long tid) {
        log.info("deleteThumbnail");
        log.info(tid);

        try {
            Path filePath = Paths.get(thumbnailPath, filename);
            String uuid = filename.substring(0, filename.indexOf("_"));

            if (Files.exists(filePath)) {
                log.info(uuid);
                int dl = trainerRepository.deleteThumbnailById(tid, uuid);

                if (dl > 0) {
                    log.info(dl + " Deleted.");
                    Files.delete(filePath);
                    return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
                } else {
                    throw new Exception();
                }
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

    @Operation(description = "GET 방식으로 파일 순서 수정")
    @PutMapping("/change/{fileName}")
    public ResponseEntity<String> changeRepresent(@PathVariable String fileName, Long tid) {
        log.info("changeRepresent");

        try {
            Path filePath = Paths.get(thumbnailPath, fileName);
            String uuid = fileName.substring(0, fileName.indexOf("_"));

            if (Files.exists(filePath)) {
                log.info(uuid);
                List<Trainer_Thumbnails> ttlist = trainerThumbnailsRepository.findByTid(tid).stream().map(Optional::orElseThrow).sorted().collect(Collectors.toList());

                if (ttlist != null && !ttlist.isEmpty()) {
                    log.info(ttlist);
                    int i = 1;
                    for (Trainer_Thumbnails t : ttlist) {
                        if (t.getThumbnailUuid().equals(uuid)) {
                            t.changeOrd(0);
                        } else {
                            t.changeOrd(i);
                            i = i + 1;
                        }
                        trainerThumbnailsRepository.save(t);
                    }
                    return ResponseEntity.ok("대표 이미지가 성공적으로 바뀌었습니다.");
                } else {
                    throw new Exception();
                }
            } else {
                return ResponseEntity.status(204).body("파일이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Thumbnail Represent Change Failed: " + e.getMessage());
        }
    }
}
