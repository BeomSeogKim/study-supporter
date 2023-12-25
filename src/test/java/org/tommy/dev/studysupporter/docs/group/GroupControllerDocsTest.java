package org.tommy.dev.studysupporter.docs.group;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.tommy.dev.studysupporter.api.controller.group.GroupController;
import org.tommy.dev.studysupporter.api.service.group.GroupService;
import org.tommy.dev.studysupporter.api.service.group.request.GroupSaveRequest;
import org.tommy.dev.studysupporter.docs.RestDocsSupport;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GroupControllerDocsTest extends RestDocsSupport {
    private final GroupService groupService = mock(GroupService.class);

    @Override
    protected Object initController() {
        return new GroupController(groupService);
    }

    @DisplayName("그룹을 생성하는 API")
    @Test
    void saveGroup() throws Exception{
        GroupSaveRequest request =
            new GroupSaveRequest("coding test 초보 탈출", 5, "CODING_TEST");

        this.mockMvc.perform(
            post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .queryParam("memberId", "1")
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("group-save",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("memberId").description("id of member")
                ),
                requestFields(
                    fieldWithPath("name").type(STRING).description("group name"),
                    fieldWithPath("maximumMember").type(NUMBER).description("maximum Member of group"),
                    fieldWithPath("theme").type(STRING).description("theme of group")
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
