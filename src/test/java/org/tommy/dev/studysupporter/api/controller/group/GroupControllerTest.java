package org.tommy.dev.studysupporter.api.controller.group;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.tommy.dev.studysupporter.ControllerTestSupport;
import org.tommy.dev.studysupporter.api.controller.group.request.WebGroupSaveRequest;
import org.tommy.dev.studysupporter.api.controller.member.request.WebMemberSaveRequest;
import org.tommy.dev.studysupporter.api.service.group.request.GroupSaveRequest;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GroupControllerTest extends ControllerTestSupport {

    @DisplayName("그룹을 생성할 수 있다. ")
    @Test
    void save() throws Exception{
        // given
        WebGroupSaveRequest request =
            new WebGroupSaveRequest("sparta coding test", 5, "CODING_TEST");

        // when && then
        this.mockMvc.perform(
                post("/groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .queryParam("memberId", "1")
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("successfully created"))
        ;

    }

    private static Stream<Arguments> provideWebGroupSaveRequestEmptyName() {
        return Stream.of(
            Arguments.of("", 4, "CODING_TEST"),
            Arguments.of(null, 5, "CODING_TEST")
        );
    }
    @DisplayName("그룹생성 시 name은 Empty이면 안된다.")
    @MethodSource("provideWebGroupSaveRequestEmptyName")
    @ParameterizedTest
    void save_EmptyName(String name, Integer maximumNumber, String theme) throws Exception{
        // given
        WebGroupSaveRequest request =
            new WebGroupSaveRequest(name,maximumNumber, theme);

        // when && then
        this.mockMvc.perform(
                post("/groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .queryParam("memberId", "1")
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("name must not be empty"))
        ;
    }

    private static Stream<Arguments> provideWebGroupSaveRequestInvalidMaximumMember() {
        return Stream.of(
            Arguments.of("sparta coding test", null, "CODING_TEST", "maximumMember must not be null"),
            Arguments.of("sparta coding test", 0, "CODING_TEST", "maximumMember must be positive"),
            Arguments.of("sparta coding test", -1, "CODING_TEST", "maximumMember must be positive")
        );
    }
    @DisplayName("그룹생성 시 maximumMember는 Null이어서는 안되며, 양수여야 한다.")
    @MethodSource("provideWebGroupSaveRequestInvalidMaximumMember")
    @ParameterizedTest
    void save_InvalidMaximumMember(String name, Integer maximumNumber, String theme, String message) throws Exception{
        // given
        WebGroupSaveRequest request =
            new WebGroupSaveRequest(name,maximumNumber, theme);

        // when && then
        this.mockMvc.perform(
                post("/groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .queryParam("memberId", "1")
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
        ;

    }

    private static Stream<Arguments> provideWebGroupSaveRequestInvalidTheme() {
        return Stream.of(
            Arguments.of("sparta coding test", 4, null, "theme must not be null"),
            Arguments.of("sparta coding test", 4, "CODI", "invalid theme please check API")
        );
    }
    @DisplayName("그룹생성 시 theme 은 null 이어서는 안되며 주어진 theme 외에는 예외가 발생한다.")
    @MethodSource("provideWebGroupSaveRequestInvalidTheme")
    @ParameterizedTest
    void save_InvalidTheme(String name, Integer maximumNumber, String theme, String message) throws Exception{
        // given
        WebGroupSaveRequest request =
            new WebGroupSaveRequest(name,maximumNumber, theme);

        // when && then
        this.mockMvc.perform(
                post("/groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .queryParam("memberId", "1")
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
        ;

    }

}