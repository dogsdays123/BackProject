package org.zerock.b01.service.trainerService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.member.User_Member;
import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;
import org.zerock.b01.repository.trainerRepository.TrainerRepository;

import java.nio.file.Files;
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

    @Override
    public TrainerViewDTO viewOne(Long tid) {
        // UserMember -> UserId 수동 매핑 없을시 추가
        if (modelMapper.getTypeMap(Trainer.class, TrainerViewDTO.class) == null) {
            modelMapper.addMappings(new PropertyMap<Trainer, TrainerDTO>() {
                @Override
                protected void configure() {
                    map(source.getUserMember().getUserId(), destination.getUserId());
                }
            });
        }

        Optional<Trainer> result = trainerRepository.findTrainerById(tid);
        Trainer trainer = result.orElseThrow();

        TrainerViewDTO trainerViewDTO = modelMapper.map(trainer, TrainerViewDTO.class);

        // 썸네일 파일 경로
        // 문제가 생기면 thumbnailPath 를 Path 에서 따오지 말고 아래 new File() 에 파일 이름과 같이 집어넣을 것
        List<String> filePaths = trainer.getImageSet().stream().sorted()
                .map(trainerThumbnails ->
                        Paths.get(thumbnailPath, trainerThumbnails.getThumbnailUuid() +
                                "_" + trainerThumbnails.getImgname()
                        ).toString()
                ).collect(Collectors.toList());
//
//        trainerViewDTO.setThumbnails(filePaths);
//
//        Optional<User_Member> userMember = trainerRepository.findUserMemberById(trainer.getUserMember().getUserId());
//        User_Member user = userMember.orElseThrow();
//
//        Optional<All_Member> allMember = trainerRepository.findAllMemberById(user.getAll_member().getAllId());
//        All_Member all = allMember.orElseThrow();
//
//        trainerViewDTO.setName(all.getName());
//        trainerViewDTO.setGender(user.getUResident().toString().charAt(0) == '1' ? "남" : "여");
//        trainerViewDTO.setBirthday(user.getUBirthday());
//        trainerViewDTO.setEmail(all.getEmail());
//        trainerViewDTO.setPhone(all.getAPhone().toString());
//        trainerViewDTO.setAddress(user.getUAddress());

        return null;
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot != -1) {
            return fileName.substring(lastIndexOfDot + 1).toLowerCase();
        }
        return ""; // 확장자가 없을 경우 빈 문자열 반환
    }

    private String getMimeType(String fileExtension) {
        switch (fileExtension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "pdf":
                return "application/pdf";
            case "txt":
                return "text/plain";
            // 추가적인 확장자에 대한 MIME 타입을 여기에 추가할 수 있습니다.
            default:
                return "application/octet-stream"; // 기본 MIME 타입 (알 수 없는 파일 형식)
        }
    }
}
