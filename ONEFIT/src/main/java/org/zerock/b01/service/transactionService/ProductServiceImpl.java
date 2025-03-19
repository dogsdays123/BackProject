package org.zerock.b01.service.transactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.transaction.Category;
import org.zerock.b01.domain.transaction.Equipment;
import org.zerock.b01.domain.transaction.Facility;
import org.zerock.b01.domain.transaction.Product;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.transactionDTO.EquipmentDTO;
import org.zerock.b01.dto.transactionDTO.FacilityDTO;
import org.zerock.b01.dto.transactionDTO.ProductListAllDTO;
import org.zerock.b01.repository.All_MemberRepository;
import org.zerock.b01.repository.transactionRepository.CategoryRepository;
import org.zerock.b01.repository.transactionRepository.EquipmentRepository;
import org.zerock.b01.repository.transactionRepository.FacilityRepository;
import org.zerock.b01.repository.transactionRepository.ProductRepository;

import java.util.Optional;

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

    // (거래 - 상품) [기구] 판매 게시글 등록
    @Override
    public Long registerEquipment(EquipmentDTO equipmentDTO) {
        Product product = modelMapper.map(equipmentDTO, Product.class);
        Equipment equipment = modelMapper.map(equipmentDTO, Equipment.class);
        Optional<Category> category = categoryRepository.findById(equipmentDTO.getCategoryId());

        // 회원 아이디 임시 세팅
        Optional<All_Member> member = all_MemberRepository.findById("member1");
        product.setAllMember(member.orElseThrow()); // (회원) 외래키 세팅
        product.setCategory(category.orElseThrow()); // (카테고리) 외래키 세팅

        if (equipmentDTO.getImageFileNames() != null) {
            equipmentDTO.getImageFileNames().forEach(fileName -> {
                // 파일명을 "_" 기준으로 분리(UUID, 실제 파일명)
                String[] parts = fileName.split("_");
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

        // 회원 아이디 임시 세팅
        Optional<All_Member> member = all_MemberRepository.findById("member1");
        product.setAllMember(member.orElseThrow()); // (회원) 외래키 세팅
        product.setCategory(category.orElseThrow()); // (카테고리) 외래키 세팅

        if (facilityDTO.getImageFileNames() != null) {
            facilityDTO.getImageFileNames().forEach(fileName -> {
                // 파일명을 "_" 기준으로 분리(UUID, 실제 파일명)
                String[] parts = fileName.split("_");
                // 분리된 정보를 이용하여 Product에 이미지 추가
                product.addImageFile(parts[0], parts[1]);
            });
        }

        productRepository.save(product);

        facility.setProduct(product);

        return facilityRepository.save(facility).getFacilityId();
    }

    @Override
    public PageResponseDTO<ProductListAllDTO> listWithAllProducts(PageRequestDTO pageRequestDTO) {
        // 검색 타입 배열 가져오기 (제목, 내용, 작성자 등)
        String[] types = pageRequestDTO.getTypes();
        // 검색 키워드 가져오기
        String keyword = pageRequestDTO.getKeyword();
        // 페이지 정보를 생성 (정렬 기준: 게시글 번호)
        Pageable pageable = pageRequestDTO.getPageable("productId");

        // 검색 조건과 페이지 정보를 이용하여 데이터 조회
        Page<ProductListAllDTO> result = productRepository.searchWithAllProducts(types, keyword, pageable);

        // 조회 결과를 PageRequeestDTO 객체로 변환하여 반환
        return PageResponseDTO.<ProductListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO) // 요청 페이지 정보 설정
                .dtoList(result.getContent()) // 조회된 DTO 리스트 설정
                .total((int) result.getTotalElements())
                .build();
    }
}
