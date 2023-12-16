package org.tommy.dev.studysupporter.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tommy.dev.studysupporter.IntegrationTestSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("기본 Member 생성 테스트")
    @Test
    void createMember() {
        // given
        Member member = Member.valueOf("test@naver.com", "tommy", Gender.MALE);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(savedMember.getId())
            .orElseThrow();
        assertAll(
            () -> assertThat(findMember).isNotNull(),
            () -> assertThat(findMember.getEmail()).isEqualTo("test@naver.com"),
            () -> assertThat(findMember.getNickName()).isEqualTo("tommy"),
            () -> assertThat(findMember.getGender()).isEqualTo(Gender.MALE)
        );

    }

}