package org.zerock.b01.controller.board;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.boardDTO.boardUpload.BoardUploadFileDTO;
import org.zerock.b01.dto.boardDTO.boardUpload.BoardUploadResultDTO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class BoardUpDownController {

    @Value("C:\\upload\\board") //import 시에 springframework으로 시작하는 value
    private String boardUploadPath;

    @Operation(description = "POST 방식으로 파일 업로드")
    @PostMapping(value = "/upload_board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<BoardUploadResultDTO> uploadBoard(BoardUploadFileDTO boardUploadFileDTO) {

        log.info(boardUploadFileDTO);

        if (boardUploadFileDTO.getFiles() != null) {

            final List<BoardUploadResultDTO> list = new ArrayList<>();

            boardUploadFileDTO.getFiles().forEach(multipartFile -> {

                String originalName = multipartFile.getOriginalFilename();
                log.info(originalName);

                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(boardUploadPath, uuid+"_" + originalName);

                try {
                    multipartFile.transferTo(savePath); //실제 파일 저장

                    // 업로드 결과 리스트에 추가
                    list.add(BoardUploadResultDTO.builder()
                            .uuid(uuid)
                            .fileName(originalName)
                            .build()
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }); //end each
            return list;
        } //end if
        return null;
    }

    @Operation(description = "GET방식으로 업로드된 파일 조회")
    @GetMapping("/view_board/{fileName}")
    public ResponseEntity<Resource> viewBoardFileGET(@PathVariable String fileName) {

        Resource resource = new FileSystemResource(boardUploadPath + File.separator + fileName);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            //internalServerError() 500error
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @Operation(description = "DELETE 방식으로 파일 삭제")
    @DeleteMapping("/remove_board/{fileName}")
    public Map<String, Boolean> removeBoardFile(@PathVariable String fileName) {

        Resource resource = new FileSystemResource(boardUploadPath + File.separator + fileName);
        log.info(resource);
        String resourceName = resource.getFilename();

        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;

        try {
            String contentType = Files.probeContentType(resource.getFile().toPath());
            log.info(resource.getFile().toPath());
            removed = resource.getFile().delete();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        resultMap.put("result", removed);

        return resultMap;
    }

}
