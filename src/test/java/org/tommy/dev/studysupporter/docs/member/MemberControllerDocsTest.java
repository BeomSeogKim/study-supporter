package org.tommy.dev.studysupporter.docs.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.tommy.dev.studysupporter.api.controller.member.MemberController;
import org.tommy.dev.studysupporter.api.controller.member.request.WebMemberSaveRequest;
import org.tommy.dev.studysupporter.api.service.member.MemberService;
import org.tommy.dev.studysupporter.docs.RestDocsSupport;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerDocsTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }

    @DisplayName("회원을 생성하는 API")
    @Test
    void saveMember() throws Exception {
        WebMemberSaveRequest request
            = new WebMemberSaveRequest("tester@naver.com", "tester", "male");

        this.mockMvc.perform(
            post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("member-save",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(STRING).description("email address"),
                    fieldWithPath("nickName").type(STRING).description("Nick Name"),
                    fieldWithPath("gender").type(STRING).description("gender")
                ),
                responseFields(
                    fieldWithPath("code").type(NUMBER).description("code"),
                    fieldWithPath("status").type(STRING).description("status"),
                    fieldWithPath("message").type(STRING).description("message"),
                    fieldWithPath("data").type(NULL).description("response data")

                )
                ))
        ;
    }
}
