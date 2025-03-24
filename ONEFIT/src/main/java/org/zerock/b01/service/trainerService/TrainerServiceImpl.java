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
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.member.User_Member;
import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.domain.trainer.Trainer_Thumbnails;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;
import org.zerock.b01.repository.trainerRepository.TrainerRepository;
import org.zerock.b01.repository.trainerRepository.Trainer_ThumbnailsRepository;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class TrainerServiceImpl implements TrainerService {
    private final Trainer_ThumbnailsRepository trainer_ThumbnailsRepository;
    @Value("${org.zerock.upload.thumbnailPath}")
    private String thumbnailPath;

    List<String> filePaths = new ArrayList<>();
    private final ModelMapper modelMapper;
    private final TrainerRepository trainerRepository;

    @Override
    public Long registerTrainer(TrainerDTO trainerDTO) {
        // UserId -> UserMember 수동 매핑 없을시 추가
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
        if (trainerDTO.getThumbnails() != null && (trainerDTO.getOriginalThumbnails() == null || trainerDTO.getOriginalThumbnails().isEmpty())) {
            log.info("Thumbnail Exists");
            try {
                for (MultipartFile file : trainerDTO.getThumbnails()) {
                    log.info("Thumbnail Count");
                    if (!file.isEmpty()) {
                        String tuuId = UUID.randomUUID().toString();
                        log.info(tuuId + "_" + file.getOriginalFilename());
                        trainer.addImage(tuuId, file.getOriginalFilename());    // imageSet 에 OneToMany

                        // 업로드
                        Path path = Paths.get(thumbnailPath, tuuId + "_" + file.getOriginalFilename());
                        file.transferTo(path);
                        filePaths.add(path.toString());
                    }
                }
                log.info(trainer.getImageSet());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (trainerDTO.getOriginalThumbnails() != null && !trainerDTO.getOriginalThumbnails().isEmpty()) {
            for (String filePath : trainerDTO.getOriginalThumbnails()) {
                String uuid = filePath.substring(0, filePath.indexOf("_"));
                String fileName = filePath.substring(filePath.indexOf("_") + 1);
                trainer.addImage(uuid, fileName);
            }
        }

        return trainerRepository.save(trainer).getTrainerId();
    }

    @Override
    public TrainerViewDTO viewOne(Long tid) {
        // UserMember -> UserId 수동 매핑 없을시 추가
        if (modelMapper.getTypeMap(Trainer.class, TrainerViewDTO.class) == null) {
            modelMapper.addMappings(new PropertyMap<Trainer, TrainerViewDTO>() {
                @Override
                protected void configure() {
                    map(source.getUserMember().getUserId(), destination.getUserId());
                }
            });
        }

        Optional<Trainer> result = trainerRepository.findTrainerById(tid);
        Trainer trainer = result.orElseThrow();

        TrainerViewDTO trainerViewDTO = modelMapper.map(trainer, TrainerViewDTO.class);

        // 썸네일 파일 이름들
        List<String> filePaths = trainer.getImageSet().stream().sorted()
                .map(trainerThumbnails ->
                        trainerThumbnails.getThumbnailUuid() + "_" + trainerThumbnails.getImgname()
                ).collect(Collectors.toList());

        // 파일 이름들을 넘겨줌
        trainerViewDTO.setThumbnails(filePaths);

        Optional<User_Member> userMember = trainerRepository.findUserMemberById(trainer.getUserMember().getUserId());
        User_Member user = userMember.orElseThrow();

        Optional<All_Member> allMember = trainerRepository.findAllMemberById(user.getAllMember().getAllId());
        All_Member all = allMember.orElseThrow();

        trainerViewDTO.setName(all.getName());
        trainerViewDTO.setGender(user.getUResident().toString().charAt(0) == '1' ? "남" : "여");
        trainerViewDTO.setBirthday(user.getUBirthday());
        trainerViewDTO.setEmail(all.getEmail());
        trainerViewDTO.setPhone(all.getAPhone().toString());
        trainerViewDTO.setAddress(user.getUAddress());

        return trainerViewDTO;
    }

    @Override
    public void removeTrainer(Long trainerId) {
        trainerRepository.deleteById(trainerId);
    }
}
