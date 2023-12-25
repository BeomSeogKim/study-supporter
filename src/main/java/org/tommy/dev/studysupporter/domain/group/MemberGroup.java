package org.tommy.dev.studysupporter.domain.group;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tommy.dev.studysupporter.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id")
    Group group;

    private MemberGroup(Member member, Group group) {
        this.member = member;
        this.group = group;
    }

    public static MemberGroup of(Member member, Group group) {
        return new MemberGroup(member, group);
    }
}
