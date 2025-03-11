package org.zerock.b01.domain.recruit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruit_Register is a Querydsl query type for Recruit_Register
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruit_Register extends EntityPathBase<Recruit_Register> {

    private static final long serialVersionUID = -1467958081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruit_Register recruit_Register = new QRecruit_Register("recruit_Register");

    public final org.zerock.b01.domain.member.QBusiness_Member business_member;

    public final StringPath re_admin_email = createString("re_admin_email");

    public final StringPath re_admin_name = createString("re_admin_name");

    public final StringPath re_admin_phone = createString("re_admin_phone");

    public final StringPath re_age = createString("re_age");

    public final StringPath re_apply_method = createString("re_apply_method");

    public final StringPath re_company = createString("re_company");

    public final DatePath<java.time.LocalDate> re_deadline = createDate("re_deadline", java.time.LocalDate.class);

    public final StringPath re_education = createString("re_education");

    public final StringPath re_gender = createString("re_gender");

    public final StringPath re_industry = createString("re_industry");

    public final StringPath re_job_type = createString("re_job_type");

    public final NumberPath<Integer> re_num_hiring = createNumber("re_num_hiring", Integer.class);

    public final StringPath re_preference = createString("re_preference");

    public final NumberPath<Integer> re_salary = createNumber("re_salary", Integer.class);

    public final StringPath re_salary_detail = createString("re_salary_detail");

    public final StringPath re_title = createString("re_title");

    public final StringPath re_work_days = createString("re_work_days");

    public final StringPath re_work_hours = createString("re_work_hours");

    public final NumberPath<Long> recruit_id = createNumber("recruit_id", Long.class);

    public final DatePath<java.time.LocalDate> regdate = createDate("regdate", java.time.LocalDate.class);

    public QRecruit_Register(String variable) {
        this(Recruit_Register.class, forVariable(variable), INITS);
    }

    public QRecruit_Register(Path<? extends Recruit_Register> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruit_Register(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruit_Register(PathMetadata metadata, PathInits inits) {
        this(Recruit_Register.class, metadata, inits);
    }

    public QRecruit_Register(Class<? extends Recruit_Register> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.business_member = inits.isInitialized("business_member") ? new org.zerock.b01.domain.member.QBusiness_Member(forProperty("business_member"), inits.get("business_member")) : null;
    }

}

