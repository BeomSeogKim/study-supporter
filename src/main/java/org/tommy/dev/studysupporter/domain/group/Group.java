package org.tommy.dev.studysupporter.domain.group;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "maximum_member", nullable = false)
    private int maximumMember;

    @Column(name = "theme", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StudyTheme theme;

    private Group(String name, LocalDateTime startDate, int maximumMember, StudyTheme theme) {
        this.name = name;
        this.startDate = startDate;
        this.maximumMember = maximumMember;
        this.theme = theme;
    }

    public static Group createGroup(String name,LocalDateTime startDate,  int maximumMember, StudyTheme theme) {
        return new Group(name, startDate, maximumMember, theme);
    }

    @Override
    public String toString() {
        return "Group{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", startDate=" + startDate +
            ", maximumMember=" + maximumMember +
            ", theme=" + theme +
            '}';
    }
}
