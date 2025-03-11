package org.zerock.b01.domain.trainer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrainer is a Querydsl query type for Trainer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrainer extends EntityPathBase<Trainer> {

    private static final long serialVersionUID = 1460483761L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrainer trainer = new QTrainer("trainer");

    public final StringPath academy = createString("academy");

    public final StringPath academy_final = createString("academy_final");

    public final StringPath career = createString("career");

    public final NumberPath<Integer> career_period = createNumber("career_period", Integer.class);

    public final StringPath content = createString("content");

    public final StringPath license = createString("license");

    public final DateTimePath<java.time.LocalDateTime> make_date = createDateTime("make_date", java.time.LocalDateTime.class);

    public final StringPath prize = createString("prize");

    public final StringPath title = createString("title");

    public final NumberPath<Long> trainer_id = createNumber("trainer_id", Long.class);

    public final org.zerock.b01.domain.member.QUser_Member user_member;

    public final NumberPath<Integer> want_day = createNumber("want_day", Integer.class);

    public final StringPath want_job = createString("want_job");

    public final StringPath want_legion = createString("want_legion");

    public final NumberPath<Integer> want_sal = createNumber("want_sal", Integer.class);

    public final StringPath want_sal_type = createString("want_sal_type");

    public final NumberPath<Integer> want_time = createNumber("want_time", Integer.class);

    public final StringPath want_type = createString("want_type");

    public QTrainer(String variable) {
        this(Trainer.class, forVariable(variable), INITS);
    }

    public QTrainer(Path<? extends Trainer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrainer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrainer(PathMetadata metadata, PathInits inits) {
        this(Trainer.class, metadata, inits);
    }

    public QTrainer(Class<? extends Trainer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user_member = inits.isInitialized("user_member") ? new org.zerock.b01.domain.member.QUser_Member(forProperty("user_member"), inits.get("user_member")) : null;
    }

}

