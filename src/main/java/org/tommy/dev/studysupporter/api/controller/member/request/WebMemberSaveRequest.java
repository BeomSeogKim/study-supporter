package org.tommy.dev.studysupporter.api.controller.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.tommy.dev.studysupporter.api.service.member.request.MemberSaveRequest;
import org.tommy.dev.studysupporter.common.enumvalidator.EnumValue;
import org.tommy.dev.studysupporter.domain.member.Gender;

public record WebMemberSaveRequest(
    @NotEmpty(message = "email must not be empty") @Email(message = "invalid email type")
    String email,
    @NotEmpty(message = "nickName must not be empty")
    String nickName,
    @EnumValue(enumClass = Gender.class, message = "invalid gender please check API", ignoreCase = true)
    String gender
) {

    public MemberSaveRequest toService() {
        return new MemberSaveRequest(email, nickName, Gender.valueOf(gender.toUpperCase()));
    }

}