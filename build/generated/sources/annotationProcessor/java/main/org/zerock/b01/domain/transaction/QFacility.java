package org.zerock.b01.domain.transaction;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFacility is a Querydsl query type for Facility
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFacility extends EntityPathBase<Facility> {

    private static final long serialVersionUID = -388784482L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFacility facility = new QFacility("facility");

    public final NumberPath<Long> facility_id = createNumber("facility_id", Long.class);

    public final NumberPath<java.math.BigDecimal> p_admin_cost = createNumber("p_admin_cost", java.math.BigDecimal.class);

    public final StringPath p_center_name = createString("p_center_name");

    public final NumberPath<java.math.BigDecimal> p_cont_area = createNumber("p_cont_area", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> p_deposit = createNumber("p_deposit", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> p_month_rent = createNumber("p_month_rent", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> p_real_area = createNumber("p_real_area", java.math.BigDecimal.class);

    public final StringPath p_reason_sale = createString("p_reason_sale");

    public final QProduct product;

    public QFacility(String variable) {
        this(Facility.class, forVariable(variable), INITS);
    }

    public QFacility(Path<? extends Facility> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFacility(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFacility(PathMetadata metadata, PathInits inits) {
        this(Facility.class, metadata, inits);
    }

    public QFacility(Class<? extends Facility> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

