package org.zerock.b01.service.transactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.transaction.*;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.transactionDTO.*;
import org.zerock.b01.repository.All_MemberRepository;
import org.zerock.b01.repository.transactionRepository.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final All_MemberRepository all_MemberRepository;
    private final CategoryRepository categoryRepository;
    private final EquipmentRepository equipmentRepository;
    private final FacilityRepository facilityRepository;
    private final ProductRepository productRepository;
    private final InterestRepository interestRepository;
    private final ProductReplyRepository productReplyRepository;

    // (거래 - 상품) [기구] 판매 게시글 등록
    @Override
    public Long registerEquipment(EquipmentDTO equipmentDTO) {
        Product product = modelMapper.map(equipmentDTO, Product.class);
        Equipment equipment = modelMapper.map(equipmentDTO, Equipment.class);
        Optional<Category> category = categoryRepository.findById(equipmentDTO.getCategoryId());

        // 회원 아이디
        Optional<All_Member> member = all_MemberRepository.findById(equipmentDTO.getAllId());
        product.setAllMember(member.orElseThrow()); // (회원) 외래키 세팅
        product.setCategory(category.orElseThrow()); // (카테고리) 외래키 세팅

        if (equipmentDTO.getImageFileNames() != null) {
            equipmentDTO.getImageFileNames().forEach(fileName -> {
                // 파일명을 "_" 기준으로 분리(UUID, 실제 파일명)
                String[] parts = fileName.split("_",2);
                // 분리된 정보를 이용하여 Product에 이미지 추가
                product.addImageFile(parts[0], parts[1]);
            });
        }

        productRepository.save(product);

        equipment.setProduct(product);

        return equipmentRepository.save(equipment).getEquipmentId();
    }

    // (거래 - 상품) [시설] 판매 게시글 등록
    @Override
    public Long registerFacility(FacilityDTO facilityDTO) {
        Product product = modelMapper.map(facilityDTO, Product.class);
        Facility facility = modelMapper.map(facilityDTO, Facility.class);
        Optional<Category> category = categoryRepository.findById(facilityDTO.getCategoryId());

        // 회원 아이디
        Optional<All_Member> member = all_MemberRepository.findById(facilityDTO.getAllId());
        product.setAllMember(member.orElseThrow()); // (회원) 외래키 세팅
        product.setCategory(category.orElseThrow()); // (카테고리) 외래키 세팅

        if (facilityDTO.getImageFileNames() != null) {
            facilityDTO.getImageFileNames().forEach(fileName -> {
                // 파일명을 "_" 기준으로 분리(UUID, 실제 파일명)
                String[] parts = fileName.split("_",2);
                // 분리된 정보를 이용하여 Product에 이미지 추가
                product.addImageFile(parts[0], parts[1]);
            });
        }

        productRepository.save(product);

        facility.setProduct(product);

        return facilityRepository.save(facility).getFacilityId();
    }

    @Override
    @Transactional
    public boolean registerInterest(InterestDTO interestDTO) {
        Product product = productRepository.findById(interestDTO.getProductId()).orElseThrow();
        All_Member member = all_MemberRepository.findById(interestDTO.getAllId()).orElseThrow();
        Interest interest = new Interest();
        interest.setProduct(product);
        interest.setAllMember(member);
        interest.setRegDate(LocalDateTime.now());

        boolean isRegistered = interestRepository.existsByProductAndAllMember(product, member);

        if (isRegistered) { //  관심상품 삭제
            interestRepository.deleteByProductAndAllMember(product, member);
        } else { // 관심상품 추가
            interestRepository.save(interest);
        }

        return isRegistered;
    }

    @Override
    public boolean isRegisteredInterest(Long productId, String allId) {
        Product product = productRepository.findById(productId).orElseThrow();
        All_Member member = all_MemberRepository.findById(allId).orElseThrow();

        return interestRepository.existsByProductAndAllMember(product, member);
    }

    // 관심상품 등록 수
    @Override
    public Long countInterest(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();

        return interestRepository.countByProduct(product);
    }

    // (거래 - 상품) [기구] 판매 게시글 읽기
    @Override
    public EquipmentDTO readEquipmentOne(Long productId) {
        Optional<Equipment> optionalEquipment = equipmentRepository.findByProduct_ProductId(productId);
        Equipment equipment = optionalEquipment.orElseThrow();
        EquipmentDTO equipmentDTO = modelMapper.map(equipment, EquipmentDTO.class);

        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = optionalProduct.orElseThrow();
        ProductDTO productDTO = entityToDto(product);

        productDTOIntoEqDto(productDTO, equipmentDTO);

        return equipmentDTO;
    }

    // (거래 - 상품) [시설] 판매 게시글 읽기
    @Override
    public FacilityDTO readFacilityOne(Long productId) {
        Optional<Facility> optionalFacility = facilityRepository.findByProduct_ProductId(productId);
        Facility facility = optionalFacility.orElseThrow();
        FacilityDTO facilityDTO = modelMapper.map(facility, FacilityDTO.class);
        facilityDTO.setFContAreaPyeong(convertToPyeong(facilityDTO.getFContArea())); // 계약면적 평수
        facilityDTO.setFRealAreaPyeong(convertToPyeong(facilityDTO.getFRealArea())); // 실 면적 평수

        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = optionalProduct.orElseThrow();
        ProductDTO productDTO = entityToDto(product);

        productDTOIntoFaDto(productDTO, facilityDTO);

        return facilityDTO;
    }

    // 제곱미터를 평수로 변환하는 함수
    public static BigDecimal convertToPyeong(BigDecimal squareMeter) {
        BigDecimal conversionFactor = new BigDecimal("0.3025");
        return squareMeter.multiply(conversionFactor).setScale(2, RoundingMode.HALF_UP); // 소수점 2자리 반올림
    }

    @Override
    public void modifyEquipment(EquipmentDTO equipmentDTO) {
        Product product = productRepository.findById(equipmentDTO.getProductId()).orElseThrow();
        Equipment equipment = equipmentRepository.findByProduct_ProductId(equipmentDTO.getProductId()).orElseThrow();
        Category category = categoryRepository.findById(equipmentDTO.getCategoryId()).orElseThrow();

        product.setCategory(category);

        // 기존 게시글의 이미지 파일 정보 불러옴
        Set<ImageFile> imageFiles = new HashSet<>(product.getImageSet()); // 복사본 생성

        // 새로 추가된 이미지를 저장하기
        if (equipmentDTO.getImageFileNames() != null) {
            Set<String> existingUuids = imageFiles.stream()
                    .map(ImageFile::getImageUuid)
                    .collect(Collectors.toSet());

            equipmentDTO.getImageFileNames().forEach(fileName -> {
                String[] parts = fileName.split("_");
                String uuid = parts[0];

                // 기존에 없는 이미지인 경우에만 추가
                if (!existingUuids.contains(uuid)) {
                    product.addImageFile(parts[0], parts[1]);
                }
            });
        }

        // 삭제된 이미지를 제거하기 (복사본을 사용하여 안전하게 삭제)
        if (equipmentDTO.getRemoveImageFileUuid() != null) {
            Set<ImageFile> imagesToRemove = imageFiles.stream()
                    .filter(image -> equipmentDTO.getRemoveImageFileUuid().contains(image.getImageUuid()))
                    .collect(Collectors.toSet());

            imagesToRemove.forEach(image -> product.getImageSet().remove(image));
        }

        product.change(equipmentDTO);

        equipment.setProduct(product);
        equipment.change(equipmentDTO);

        productRepository.save(product);
    }

    @Override
    public void modifyFacility(FacilityDTO facilityDTO) {
        Product product = productRepository.findById(facilityDTO.getProductId()).orElseThrow();
        Facility facility = facilityRepository.findByProduct_ProductId(facilityDTO.getProductId()).orElseThrow();
        Category category = categoryRepository.findById(facilityDTO.getCategoryId()).orElseThrow();

        product.setCategory(category);

        Set<ImageFile> imageFiles = new HashSet<>(product.getImageSet()); // 복사본 생성

        // 새로 추가된 이미지를 저장하기
        if (facilityDTO.getImageFileNames() != null) {
            Set<String> existingUuids = imageFiles.stream()
                    .map(ImageFile::getImageUuid)
                    .collect(Collectors.toSet());

            facilityDTO.getImageFileNames().forEach(fileName -> {
                String[] parts = fileName.split("_");
                String uuid = parts[0];

                // 기존에 없는 이미지인 경우에만 추가
                if (!existingUuids.contains(uuid)) {
                    product.addImageFile(parts[0], parts[1]);
                }
            });
        }

        // 삭제된 이미지를 제거하기 (복사본을 사용하여 안전하게 삭제)
        if (facilityDTO.getRemoveImageFileUuid() != null) {
            Set<ImageFile> imagesToRemove = imageFiles.stream()
                    .filter(image -> facilityDTO.getRemoveImageFileUuid().contains(image.getImageUuid()))
                    .collect(Collectors.toSet());

            imagesToRemove.forEach(image -> product.getImageSet().remove(image));
        }


        product.change(facilityDTO);

        facility.setProduct(product);
        facility.change(facilityDTO);

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();

        productReplyRepository.deleteByProduct(product);
        interestRepository.deleteByProduct(product);

        if(product.getPRoles() == 1) { // 기구 판매 게시글일 경우
            equipmentRepository.deleteByProduct_ProductId(productId);

        } else if(product.getPRoles() == 2) { // 시설 매매 게시글일 경우
            facilityRepository.deleteByProduct_ProductId(productId);
//            Facility facility = facilityRepository.findByProduct_ProductId(productId).orElseThrow();
        }

        productRepository.deleteById(productId);
    }

    // (거래 - 상품) 거래 게시판 리스트
    @Override
    public PageResponseDTO<ProductListAllDTO> listWithAllProducts(PageRequestDTO pageRequestDTO) {
        // 검색 타입 배열 가져오기 (제목, 내용, 작성자 등)
        String[] types = pageRequestDTO.getTypes();
        // 검색 키워드 가져오기
        String keyword = pageRequestDTO.getKeyword();

        // 카테고리 타입 가져오기
        String categoryType = pageRequestDTO.getCategoryType();
        // 카테고리 이름 가져오기
        String categoryName = pageRequestDTO.getCategoryName();

        // 가격 필터 가져오기
        Integer minPrice = pageRequestDTO.getMinPrice();
        Integer maxPrice = pageRequestDTO.getMaxPrice();

        // 판매 상태 가져오기
        Boolean onSale = pageRequestDTO.getOnSale();
        Boolean reserved = pageRequestDTO.getReserved();
        Boolean soldOut = pageRequestDTO.getSoldOut();
        // 관심상품 여부 가져오기
        Boolean interested = pageRequestDTO.getInterested();

        // 작성자 ID 가져오기
        String allId = pageRequestDTO.getAllId();

        // 지역 가져오기
        String metroGov = pageRequestDTO.getMetroGov(); // 시/도
        String muniGov = pageRequestDTO.getMuniGov(); // 시/군/구

        // 페이지 정보를 생성 (정렬 기준: 게시글 번호)
        Pageable pageable = pageRequestDTO.getPageable("productId");

        // 검색 조건과 페이지 정보를 이용하여 데이터 조회
        Page<ProductListAllDTO> result = productRepository.searchWithAllProducts(types, keyword, minPrice, maxPrice,
                categoryType, categoryName, onSale, reserved, soldOut, allId, interested, metroGov, muniGov, pageable);

        // 조회 결과를 PageRequeestDTO 객체로 변환하여 반환
        return PageResponseDTO.<ProductListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO) // 요청 페이지 정보 설정
                .dtoList(result.getContent()) // 조회된 DTO 리스트 설정
                .total((int) result.getTotalElements())
                .build();
    }
}
