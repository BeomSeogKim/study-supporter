package org.tommy.dev.studysupporter.api.controller.group.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.tommy.dev.studysupporter.api.service.group.request.GroupSaveRequest;
import org.tommy.dev.studysupporter.common.enumvalidator.EnumValue;
import org.tommy.dev.studysupporter.domain.group.StudyTheme;

public record WebGroupSaveRequest(
    @NotEmpty(message = "name must not be empty")
    String name,

    @Positive(message = "maximumMember must be positive")
    @NotNull(message = "maximumMember must not be null")
    Integer maximumMember,

    @NotNull(message = "theme must not be null")
    @EnumValue(enumClass = StudyTheme.class, message = "invalid theme please check API", ignoreCase = true)
    String theme
) {

    public GroupSaveRequest toService() {
        return new GroupSaveRequest(name, maximumMember, theme);
    }
}
