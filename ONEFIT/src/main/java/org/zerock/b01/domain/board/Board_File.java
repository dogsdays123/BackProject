package org.zerock.b01.domain.board;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board_File {
    @Id
    @Column(length = 100, nullable = false)
    private String uuid;

    @Column(length = 100, nullable = false)
    private String fileName;

    @Column(nullable = false)
    private int ord;

    @ManyToOne
    @JoinColumn(name = "qna_id")
    private Qna_Board qna_board;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice_Board notice_board;
}
