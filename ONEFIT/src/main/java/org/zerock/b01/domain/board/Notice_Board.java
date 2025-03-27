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
@ToString
public class Notice_Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(length = 400, nullable = false)
    private String nTitle;

    @Column(length = 4000, nullable = false)
    private String nContent;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int nHits;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember;

    @OneToMany (mappedBy = "noticeBoard" , cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<Board_File> boardFileSet = new HashSet<>();

    public void changeNotice(String nTitle, String nContent) {
        this.nTitle = nTitle;
        this.nContent = nContent;
    }

    public void addNoticeFiles(String uuid, String fileName) {

        Board_File board_file = Board_File.builder()
                .uuid(uuid)
                .fileName(fileName)
                .noticeBoard(this)
                .ord(boardFileSet.size())
                .build();

        boardFileSet.add(board_file);
    }

    public void  clearNoticeFiles() {

        boardFileSet.forEach(board_file -> board_file.changeNoticeBoard(null));

        this.boardFileSet.clear();
    }

    public void increaseNoticeHits() {

        this.nHits++;
    }

    //날짜는 baseEntity
    //날짜는 baseEntity
    //날짜는 baseEntity
}
