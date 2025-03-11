package org.zerock.b01.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard_Reply is a Querydsl query type for Board_Reply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard_Reply extends EntityPathBase<Board_Reply> {

    private static final long serialVersionUID = -1614025186L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard_Reply board_Reply = new QBoard_Reply("board_Reply");

    public final org.zerock.b01.domain.QBaseEntity _super = new org.zerock.b01.domain.QBaseEntity(this);

    public final org.zerock.b01.domain.QAll_Member all_member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> moddate = _super.moddate;

    public final QNotice_Board notice_board;

    public final QQna_Board qna_board;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regdate = _super.regdate;

    public final NumberPath<Long> reply_id = createNumber("reply_id", Long.class);

    public final StringPath replyText = createString("replyText");

    public QBoard_Reply(String variable) {
        this(Board_Reply.class, forVariable(variable), INITS);
    }

    public QBoard_Reply(Path<? extends Board_Reply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard_Reply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard_Reply(PathMetadata metadata, PathInits inits) {
        this(Board_Reply.class, metadata, inits);
    }

    public QBoard_Reply(Class<? extends Board_Reply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.all_member = inits.isInitialized("all_member") ? new org.zerock.b01.domain.QAll_Member(forProperty("all_member")) : null;
        this.notice_board = inits.isInitialized("notice_board") ? new QNotice_Board(forProperty("notice_board"), inits.get("notice_board")) : null;
        this.qna_board = inits.isInitialized("qna_board") ? new QQna_Board(forProperty("qna_board"), inits.get("qna_board")) : null;
    }

}

