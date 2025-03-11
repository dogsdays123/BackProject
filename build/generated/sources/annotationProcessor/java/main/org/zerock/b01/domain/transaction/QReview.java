package org.zerock.b01.domain.transaction;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 825616915L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final org.zerock.b01.domain.QBaseEntity _super = new org.zerock.b01.domain.QBaseEntity(this);

    public final org.zerock.b01.domain.QAll_Member all_member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> moddate = _super.moddate;

    public final QProduct product;

    public final StringPath r_content = createString("r_content");

    public final NumberPath<Byte> r_score = createNumber("r_score", Byte.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regdate = _super.regdate;

    public final NumberPath<Long> review_id = createNumber("review_id", Long.class);

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.all_member = inits.isInitialized("all_member") ? new org.zerock.b01.domain.QAll_Member(forProperty("all_member")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

