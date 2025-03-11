package org.zerock.b01.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAll_Member is a Querydsl query type for All_Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAll_Member extends EntityPathBase<All_Member> {

    private static final long serialVersionUID = -1020906557L;

    public static final QAll_Member all_Member = new QAll_Member("all_Member");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath a_member_id = createString("a_member_id");

    public final NumberPath<Long> a_phone = createNumber("a_phone", Long.class);

    public final StringPath a_psw = createString("a_psw");

    public final BooleanPath a_social = createBoolean("a_social");

    public final NumberPath<Long> all_id = createNumber("all_id", Long.class);

    public final StringPath email = createString("email");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> moddate = _super.moddate;

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regdate = _super.regdate;

    public final StringPath roles = createString("roles");

    public QAll_Member(String variable) {
        super(All_Member.class, forVariable(variable));
    }

    public QAll_Member(Path<? extends All_Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAll_Member(PathMetadata metadata) {
        super(All_Member.class, metadata);
    }

}

