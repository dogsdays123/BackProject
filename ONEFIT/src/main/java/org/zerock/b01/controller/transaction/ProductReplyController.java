package org.zerock.b01.controller.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.transactionDTO.ProductReplyDTO;
import org.zerock.b01.service.transactionService.ProductReplyService;

import java.net.URI;

@RestController
@RequestMapping("/product_reply")
@Log4j2
@RequiredArgsConstructor
public class ProductReplyController {

    private final ProductReplyService productReplyService;

    @PostMapping("/eq_register")
    public ResponseEntity<Void> eqRegisterPost(@ModelAttribute ProductReplyDTO productReplyDTO) {
        log.info("*****************************************************************");
        log.info("/product_reply/eq_register - POST");
        log.info("*****************************************************************");

        log.info(productReplyDTO);

        Long productReplyId = productReplyService.registerProductReply(productReplyDTO);

        log.info("productReplyId: {}", productReplyId);

        // 댓글이 등록된 후 게시글 상세 페이지로 리다이렉트
        URI redirectUri = URI.create("/transaction/transa_eq_read?productId=" + productReplyDTO.getProductId());
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }

    @PostMapping("/fa_register")
    public ResponseEntity<Void> faRegisterPost(@ModelAttribute ProductReplyDTO productReplyDTO) {
        log.info("*****************************************************************");
        log.info("/product_reply/fa_register - POST");
        log.info("*****************************************************************");

        log.info(productReplyDTO);

        Long productReplyId = productReplyService.registerProductReply(productReplyDTO);

        log.info("productReplyId: {}", productReplyId);

        // 댓글이 등록된 후 게시글 상세 페이지로 리다이렉트
        URI redirectUri = URI.create("/transaction/transa_fa_read?productId=" + productReplyDTO.getProductId());
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }

    @PostMapping("/eq_modify")
    public ResponseEntity<Void> eqModifyPost(@ModelAttribute ProductReplyDTO productReplyDTO) {
        log.info("*****************************************************************");
        log.info("/product_reply/eq_modify - POST");
        log.info("*****************************************************************");

        log.info(productReplyDTO);

        productReplyService.modifyProductReply(productReplyDTO);

        // 댓글이 수정된 후 게시글 상세 페이지로 리다이렉트
        URI redirectUri = URI.create("/transaction/transa_eq_read?productId=" + productReplyDTO.getProductId());
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }

    @PostMapping("/fa_modify")
    public ResponseEntity<Void> faModifyPost(@ModelAttribute ProductReplyDTO productReplyDTO) {
        log.info("*****************************************************************");
        log.info("/product_reply/fa_modify - POST");
        log.info("*****************************************************************");

        log.info(productReplyDTO);

        productReplyService.modifyProductReply(productReplyDTO);

        // 댓글이 수정된 후 게시글 상세 페이지로 리다이렉트
        URI redirectUri = URI.create("/transaction/transa_fa_read?productId=" + productReplyDTO.getProductId());
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeReplyPost(@ModelAttribute ProductReplyDTO productReplyDTO) {
        log.info("***************************************************************");
        log.info("/product_reply/remove - POST");
        log.info("***************************************************************");

        log.info(productReplyDTO);
        productReplyService.removeProductReply(productReplyDTO.getProductReplyId());

        // 댓글이 삭제된 후 게시글 상세 페이지로 리다이렉트
        URI redirectUri = URI.create("/transaction/transa_" + productReplyDTO.getProductRole()
                + "_read?productId=" + productReplyDTO.getProductId());
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }
}
