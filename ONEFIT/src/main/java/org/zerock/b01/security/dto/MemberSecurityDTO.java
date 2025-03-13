package org.zerock.b01.security.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User implements OAuth2User {

    private String name;
    private String email;
    private String a_member_id;
    private String a_psw;
    private int a_phone;
    private String member_type;
    private boolean del;
    private boolean a_social;

    private Map<String, Object> props;

    public MemberSecurityDTO(String name, String userid, String password, String email,
                             int a_phone, String member_type, boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {

        super(userid, password, authorities);

        this.name = name;
        this.a_phone = a_phone;
        this.member_type = member_type;
        this.a_member_id = userid;
        this.a_psw = password;
        this.email = email;
        this.del = del;
        this.a_social = social;
    }

    @Override
    public Map<String, Object> getAttributes(){
        return this.getProps();
    }

    //그냥 연습용으로 id가져옴
    @Override
    public String getName() {
        return this.a_member_id;
    }
}
