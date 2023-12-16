package org.tommy.dev.studysupporter.api.controller.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.tommy.dev.studysupporter.ControllerTestSupport;
import org.tommy.dev.studysupporter.api.controller.member.request.WebMemberSaveRequest;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTestSupport {


    @DisplayName("회원을 등록한다.")
    @Test
    void save() throws Exception {
        // given
        WebMemberSaveRequest request =
            new WebMemberSaveRequest("tester@naver.com", "tester", "male");

        // when && then
        this.mockMvc.perform(
                post("/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("successfully created"));

    }

    private static Stream<Arguments> provideWebMemberSaveRequestInvalidEmail() {
        return Stream.of(
            Arguments.of("", "tester", "female"),
            Arguments.of(null, "tester", "male")
        );
    }

    @DisplayName("save 시 email은 empty이면 안된다.")
    @MethodSource("provideWebMemberSaveRequestInvalidEmail")
    @ParameterizedTest
    void saveInvalidEmail(String email, String nickName, String gender) throws Exception{
        // given
        WebMemberSaveRequest request = new WebMemberSaveRequest(email, nickName, gender);

        // when && then
        this.mockMvc.perform(
                post("/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("email must not be empty"))
        ;
    }

    private static Stream<Arguments> provideWebMemberSaveRequestInvalidEmailType() {
        return Stream.of(
            Arguments.of("test@", "tester", "female"),
            Arguments.of("test", "tester", "male"),
            Arguments.of("@com", "tester", "male")
        );
    }

    @DisplayName("save 시 email 형식이 아닌 값으로 요청을 하면 안된다.")
    @MethodSource("provideWebMemberSaveRequestInvalidEmailType")
    @ParameterizedTest
    void saveInvalidEmailType(String email, String nickName, String gender) throws Exception{
        // given
        WebMemberSaveRequest request = new WebMemberSaveRequest(email, nickName, gender);

        // when && then
        this.mockMvc.perform(
                post("/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("invalid email type"))
        ;
    }

    private static Stream<Arguments> provideWebMemberSaveRequestInvalidNickName() {
        return Stream.of(
            Arguments.of("tester@naver.com", "", "female"),
            Arguments.of("tester@naver.com", null, "male")
        );
    }

    @DisplayName("save 시 nickName은 empty이면 안된다.")
    @MethodSource("provideWebMemberSaveRequestInvalidNickName")
    @ParameterizedTest
    void saveInvalidNickName(String email, String nickName, String gender) throws Exception{
        // given
        WebMemberSaveRequest request = new WebMemberSaveRequest(email, nickName, gender);

        // when && then
        this.mockMvc.perform(
                post("/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("nickName must not be empty"))
        ;
    }

    private static Stream<Arguments> provideWebMemberSaveRequestInvalidGender() {
        return Stream.of(
            Arguments.of("tester@naver.com", "tester", ""),
            Arguments.of("tester@naver.com", "tester", null),
            Arguments.of("tester@naver.com", "tester", "fe")
        );
    }

    @DisplayName("save 시 gender의 경우 male, female 외의 값은 예외가 발생해야 한다.")
    @MethodSource("provideWebMemberSaveRequestInvalidGender")
    @ParameterizedTest
    void saveInvalidGender(String email, String nickName, String gender) throws Exception{
        // given
        WebMemberSaveRequest request = new WebMemberSaveRequest(email, nickName, gender);

        // when && then
        this.mockMvc.perform(
                post("/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("invalid gender please check API"))
        ;
    }



}