package org.zerock.b01.controller.shin;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.transactionDTO.upload.UploadImageFileDTO;
import org.zerock.b01.dto.transactionDTO.upload.UploadImageResultDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class UpDownImageController {
    // import 시에 springframework로 시작하는 value
    @Value("C:\\upload")
    private String uploadPath;

    @Operation(description = "POST 방식으로 파일 업로드")
    @PostMapping(value = "/upload_transa", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadImageResultDTO> upload(UploadImageFileDTO uploadFileDTO) {
        log.info(uploadFileDTO);

        if (uploadFileDTO.getFiles() != null) {
            final List<UploadImageResultDTO> list = new ArrayList<>();

            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originalName = multipartFile.getOriginalFilename();
                log.info(multipartFile.getOriginalFilename());

                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

                boolean image = false; // 추가

                try {
                    multipartFile.transferTo(savePath); // 실제 파일 저장

                    // 이미지 파일의 종류라면
//                    if (Files.probeContentType(savePath).startsWith("image")) {
//
//                        // ******
//                        image = true;
//
//                        // 섬네일 파일도 함께 생성
//                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalName);
//
//                        // Thumbnailator: Java에서 간단하고 편리하게 이미지 썸네일을 생성할 수 있는 라이브러리
//                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
//                    }

                    list.add(UploadImageResultDTO.builder()
                            .uuid(uuid)
                            .fileName(originalName)
                            .img(image).build()
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                list.add(UploadResultDTO.builder()
//                        .uuid(uuid)
//                        .fileName(originalName)
//                        .img(image).build()
//                );
            }); // end each

            return list;

        } // end if

        return null;
    }

    @Operation(description = "GET 방식으로 업로드된 파일 조회")
    @GetMapping("/view_transa/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try {
            // ex) aaa.jpg -> aaa/jpg
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        } catch (Exception e) {
            // 500 error
            return ResponseEntity.internalServerError().build();
        }
        // 200 response 처리. 헤더 값 추가. 바디에 리소스 추가
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @Operation(description = "DELETE 방식으로 파일 삭제")
    @DeleteMapping("/remove_transa/{fileName}")
    public Map<String, Boolean> removeFile(@PathVariable String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        log.info(resource);
        String resourceName = resource.getFilename();

        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;

        try {
            String contentType = Files.probeContentType(resource.getFile().toPath());
            log.info(resource.getFile().toPath());
            removed = resource.getFile().delete();

            // 섬네일이 존재한다면
            if (contentType.startsWith("image")) {
                File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);
                // 섬네일도 함께 삭제
                thumbnailFile.delete();
            }
        } catch (Exception e) {
            log.error(e);
        } // end catch

        resultMap.put("result", removed);

        return resultMap;
    }
}
