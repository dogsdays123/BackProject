package org.zerock.b01.service.trainerService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;
import org.zerock.b01.repository.trainerRepository.TrainerRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class TrainerServiceImpl implements TrainerService {
    @Value("${org.zerock.upload.thumbnailPath}")
    private String thumbnailPath;

    List<String> filePaths = new ArrayList<>();
    private final ModelMapper modelMapper;
    private final TrainerRepository trainerRepository;

    @Override
    public Long registerTrainer(TrainerDTO trainerDTO) {
        // UserMember 수동 매핑 없을시 추가
        if (modelMapper.getTypeMap(TrainerDTO.class, Trainer.class) == null) {
            modelMapper.addMappings(new PropertyMap<TrainerDTO, Trainer>() {
                @Override
                protected void configure() {
                    map(source.getUserId(), destination.getUserMember().getUserId());
                }
            });
        }

        // ModelMapper 매핑
        Trainer trainer = modelMapper.map(trainerDTO, Trainer.class);

        // 썸네일 파일 처리
        if (trainerDTO.getThumbnails() != null) {
            try {
                for (MultipartFile file : trainerDTO.getThumbnails()) {
                    if (!file.isEmpty()) {
                        String tuuId = UUID.randomUUID().toString();
                        log.info(tuuId + "_" + file.getOriginalFilename());
                        trainer.addImage(tuuId, file.getOriginalFilename());    // imageSet 에 OneToMany

                        Path path = Paths.get(thumbnailPath, tuuId + "_" + file.getOriginalFilename());
                        Files.copy(file.getInputStream(), path);
                        filePaths.add(path.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        return trainerRepository.save(trainer).getTrainerId();
    }
}
