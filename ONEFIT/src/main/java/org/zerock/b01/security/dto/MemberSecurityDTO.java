package org.zerock.b01.security.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User implements OAuth2User {

    private String allId;
    private String aPsw;
    private String email;
    private String name;
    private String aPhone;
    private String memberType;
    private boolean del;
    private boolean aSocial;

    private Map<String, Object> props;

    public MemberSecurityDTO(String userid, String password, String email, String name,
                             String aPhone, String memberType, boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {

        super(userid, password, authorities);

        this.allId = userid;
        this.aPsw = password;
        this.email = email;
        this.name = name;
        this.aPhone = aPhone;
        this.memberType = memberType;
        this.del = del;
        this.aSocial = social;
    }

    @Override
    public Map<String, Object> getAttributes(){
        return this.getProps();
    }

    //그냥 연습용으로 id가져옴
    @Override
    public String getName() {
        return this.allId;
    }
}
