package org.tommy.dev.studysupporter.api.service.member.request;

import org.tommy.dev.studysupporter.domain.member.Gender;
import org.tommy.dev.studysupporter.domain.member.Member;

public record MemberSaveRequest(
    String email,
    String nickName,
    Gender gender
) {
    public Member toMember() {
        return Member.valueOf(email, nickName, gender);
    }
}
