package org.tommy.dev.studysupporter.api.service.group;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tommy.dev.studysupporter.IntegrationTestSupport;
import org.tommy.dev.studysupporter.api.service.group.request.GroupSaveRequest;
import org.tommy.dev.studysupporter.domain.group.*;
import org.tommy.dev.studysupporter.domain.member.Gender;
import org.tommy.dev.studysupporter.domain.member.Member;
import org.tommy.dev.studysupporter.domain.member.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class GroupServiceTest extends IntegrationTestSupport {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MemberGroupRepository memberGroupRepository;

    @Autowired
    GroupService groupService;

    @DisplayName("그룹을 생성할 수 있다.")
    @Test
    void save() {
        // given
        Member member =
            Member.valueOf("tester@naver.com", "tester", Gender.MALE);
        Member savedMember = memberRepository.save(member);

        GroupSaveRequest groupSaveRequest = new GroupSaveRequest("Beginner Coding Test", 8, "CODING_TEST");

        // when
        long groupId = groupService.save(groupSaveRequest, savedMember.getId());

        // then
        Group findGroup = groupRepository.findById(groupId).orElseThrow();
        List<MemberGroup> memberGroups = memberGroupRepository.findAll();
        assertAll(
            () -> assertThat(findGroup.getName()).isEqualTo("Beginner Coding Test"),
            () -> assertThat(findGroup.getTheme()).isEqualTo(StudyTheme.CODING_TEST),
            () -> assertThat(findGroup.getMaximumMember()).isEqualTo(8),
            () -> assertThat(memberGroups.size()).isEqualTo(1)  // MemberGroup 도 같이 저장되어야 한다.
        );
    }

    @DisplayName("그룹 생성 시 잘못된 memberId일 경우 예외가 발생한다.")
    @Test
    void save_invalidMemberId() {
        // given
        Member member =
            Member.valueOf("tester@naver.com", "tester", Gender.MALE);
        Member savedMember = memberRepository.save(member);

        GroupSaveRequest groupSaveRequest = new GroupSaveRequest("Beginner Coding Test", 8, "CODING_TEST");

        // when && then
        assertThatThrownBy(
            () -> groupService.save(groupSaveRequest, savedMember.getId() + 1)
        ).isInstanceOf(EntityNotFoundException.class)
            .hasMessage("invalid member id");
    }

}