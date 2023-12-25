package org.tommy.dev.studysupporter.api.service.group.request;

import org.tommy.dev.studysupporter.domain.group.Group;
import org.tommy.dev.studysupporter.domain.group.StudyTheme;

import java.time.LocalDateTime;

public record GroupSaveRequest(
    String name,
    int maximumMember,
    String theme
) {
    public Group createGroup() {
        LocalDateTime now = LocalDateTime.now();

        return Group.createGroup(name, now, maximumMember, StudyTheme.valueOf(theme.toUpperCase()));
    }

}
