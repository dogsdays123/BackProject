package org.zerock.b01.service.transactionService;

import org.zerock.b01.domain.transaction.ProductReply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.transactionDTO.ProductReplyDTO;

public interface ProductReplyService {
    Long registerProductReply(ProductReplyDTO productReplyDTO);

    ProductReplyDTO readProductReply(Long productReplyId);

    void modifyProductReply(ProductReplyDTO productReplyDTO);

    void removeProductReply(Long productReplyId);

    PageResponseDTO<ProductReplyDTO> getListOfProduct(Long productId, PageRequestDTO pageRequestDTO);

    default ProductReplyDTO entityToDto(ProductReply productReply) {
        ProductReplyDTO productReplyDTO = ProductReplyDTO.builder()
                .productReplyId(productReply.getProductReplyId())
                .pReplyText(productReply.getPReplyText())
                .productId(productReply.getProduct().getProductId())
                .allId(productReply.getAllMember().getAllId())
                .regDate(productReply.getRegDate())
                .modDate(productReply.getModDate())
                .build();

        return productReplyDTO;
    }
}
