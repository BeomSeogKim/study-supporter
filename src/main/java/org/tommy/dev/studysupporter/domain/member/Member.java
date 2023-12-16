package org.tommy.dev.studysupporter.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tommy.dev.studysupporter.domain.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Column(name = "nick_name", length = 20, nullable = false)
    private String nickName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", length = 10, nullable = false)
    private Gender gender;

    private Member(String email, String nickName, Gender gender) {
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
    }

    public static Member valueOf(String email, String nickName, Gender gender) {
        return new Member(email, nickName, gender);
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", nickName='" + nickName + '\'' +
            ", gender=" + gender +
            '}';
    }

}
