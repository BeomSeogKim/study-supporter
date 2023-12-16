package org.tommy.dev.studysupporter.api.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tommy.dev.studysupporter.api.service.member.request.MemberSaveRequest;
import org.tommy.dev.studysupporter.domain.member.Member;
import org.tommy.dev.studysupporter.domain.member.MemberRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public long save(MemberSaveRequest request) {
        Member savedMember = memberRepository.save(request.toMember());

        return savedMember.getId();
    }

}
