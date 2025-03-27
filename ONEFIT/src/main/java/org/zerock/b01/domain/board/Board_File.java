package org.zerock.b01.domain.board;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"qnaBoard", "noticeBoard"})
public class Board_File implements Comparable<Board_File> {
    @Id
    @Column(length = 100, nullable = false)
    private String uuid;

    @Column(length = 100, nullable = false)
    private String fileName;

    @Column(nullable = false)
    private int ord;

    @ManyToOne
    @JoinColumn(name = "qna_id")
    private Qna_Board qnaBoard;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice_Board noticeBoard;

    @Override
    public int compareTo(Board_File other) { return this.ord - other.ord; }
    public void changeNoticeBoard(Notice_Board notice_board) { this.noticeBoard = notice_board; }
    public void changeQnaBoard(Qna_Board qna_board) { this.qnaBoard = qna_board; }
}
