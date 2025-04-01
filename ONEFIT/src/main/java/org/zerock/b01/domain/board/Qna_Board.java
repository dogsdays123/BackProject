package org.zerock.b01.domain.board;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "boardFileSet")
public class Qna_Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 400, nullable = false)
    private Long qnaId;

    @Column(length = 400, nullable = false)
    private String qTitle;

    @Column(length = 4000, nullable = false)
    private String qContent;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int qHits;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember;

    @OneToMany (mappedBy = "qnaBoard" , cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<Board_File> boardFileSet = new HashSet<>();

    public void changeQna(String qTitle, String qContent) {
        this.qTitle = qTitle;
        this.qContent = qContent;
    }

    public void addQnaFiles(String uuid, String fileName) {

        Board_File board_file = Board_File.builder()
                .uuid(uuid)
                .fileName(fileName)
                .qnaBoard(this)
                .ord(boardFileSet.size())
                .build();

        boardFileSet.add(board_file);
    }

    public void  clearQnaFiles() {

        boardFileSet.forEach(board_file -> board_file.changeQnaBoard(null));

        this.boardFileSet.clear();
    }

    public void increaseQnaHits() {
        this.qHits++;
    }

    //날짜는 BaseEntity
    //날짜는 BaseEntity
    //날짜는 BaseEntity
}
