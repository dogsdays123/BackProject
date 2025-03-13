package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.member.MemberRole;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
public class All_Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long all_id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false, unique = true)
    private String a_member_id;

    @Column(length = 100, nullable = false)
    private String a_psw;

    private int a_phone;

    @Column(length = 20, nullable = false)
    private String member_type;

    private boolean del;
    private boolean a_social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changeName(String name) {this.name = name;}
    public void changeEmail(String email) {this.email = email;}
    public void changeA_member_id(String a_member_id) {this.a_member_id = a_member_id;}

    public void changeA_psw(String a_psw) {this.a_psw = a_psw;}
    public void changeMember_type(String member_type) {this.member_type = member_type;}
    public void changeA_phone(int a_phone) {this.a_phone = a_phone;}

    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }
    public void clearRoles(){
        this.roleSet.clear();
    }
}
