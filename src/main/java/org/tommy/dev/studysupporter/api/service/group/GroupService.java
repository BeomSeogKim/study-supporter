package org.tommy.dev.studysupporter.api.service.group;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tommy.dev.studysupporter.api.service.group.request.GroupSaveRequest;
import org.tommy.dev.studysupporter.domain.group.Group;
import org.tommy.dev.studysupporter.domain.group.MemberGroup;
import org.tommy.dev.studysupporter.domain.group.MemberGroupRepository;
import org.tommy.dev.studysupporter.domain.member.Member;
import org.tommy.dev.studysupporter.domain.member.MemberRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GroupService {

    private final MemberGroupRepository memberGroupRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public long save(GroupSaveRequest request, long memberId) {
        Member member = findMember(memberId);
        Group group = request.createGroup();
        MemberGroup memberGroup = MemberGroup.of(member, group);

        return memberGroupRepository.save(memberGroup).getGroup().getId();
    }

    private Member findMember(long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
            () -> new EntityNotFoundException("invalid member id")
        );
    }
}
