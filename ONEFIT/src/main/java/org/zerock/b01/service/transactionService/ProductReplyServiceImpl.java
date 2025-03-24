package org.zerock.b01.service.transactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.transaction.Product;
import org.zerock.b01.domain.transaction.ProductReply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.transactionDTO.ProductDTO;
import org.zerock.b01.dto.transactionDTO.ProductReplyDTO;
import org.zerock.b01.repository.transactionRepository.ProductReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductReplyServiceImpl implements ProductReplyService {

    private final ProductReplyRepository productReplyRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long registerProductReply(ProductReplyDTO productReplyDTO) {
        ProductReply productReply = modelMapper.map(productReplyDTO, ProductReply.class);

        Long productId = productReplyDTO.getProductId();
        Product product = Product.builder().productId(productId).build();
        productReply.setProduct(product);

        String allId = productReplyDTO.getAllId();
        All_Member allMember = All_Member.builder().allId(allId).build();
        productReply.setAllMember(allMember);

        Long productReplyId = productReplyRepository.save(productReply).getProductReplyId();

        return productReplyId;
    }

    @Override
    public ProductReplyDTO readProductReply(Long productReplyId) {
        return null;
    }

    @Override
    public void modifyProductReply(ProductReplyDTO productReplyDTO) {
        ProductReply productReply = productReplyRepository.findById(productReplyDTO.getProductReplyId()).orElseThrow();
        productReply.changeText(productReplyDTO.getPReplyText());

        productReplyRepository.save(productReply);
    }

    @Override
    @Transactional
    public void removeProductReply(Long productReplyId) {
        productReplyRepository.deleteById(productReplyId);
    }

    @Override
    public PageResponseDTO<ProductReplyDTO> getListOfProduct(Long productId, PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setSize(5); // 한 페이지당 댓글 5개

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0 ? 0 :
                        pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("productReplyId").ascending());

        Page<ProductReply> result = productReplyRepository.listOfProduct(productId, pageable);

        List<ProductReplyDTO> dtoList = result.getContent().stream().map(this::entityToDto).collect(Collectors.toList());

        return PageResponseDTO.<ProductReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
