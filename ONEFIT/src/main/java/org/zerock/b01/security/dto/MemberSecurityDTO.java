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

    private String all_id;
    private String a_psw;
    private String email;
    private String name;
    private int a_phone;
    private String member_type;
    private boolean del;
    private boolean a_social;

    private Map<String, Object> props;

    public MemberSecurityDTO(String userid, String password, String email, String name,
                             int a_phone, String member_type, boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {

        super(userid, password, authorities);

        this.all_id = userid;
        this.a_psw = password;
        this.email = email;
        this.name = name;
        this.a_phone = a_phone;
        this.member_type = member_type;
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
        return this.all_id;
    }
}
