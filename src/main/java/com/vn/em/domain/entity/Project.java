package com.vn.em.domain.entity;

import com.vn.em.domain.entity.common.UserDateAuditing;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "projects")
public class Project extends UserDateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String purpose;
    @Column(nullable = false)
    private String requirement;
    @Column(nullable = true)
    private String stakeholder;
    @Column(nullable = true)
    private Long budget;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate dueDate;
    @Column(nullable = true)
    private LocalDate timelineStart;
    @Column(nullable = true)
    private LocalDate timelineEnd;
    @Column(nullable = true)
    private String note;

    @ManyToOne
    @JoinColumn(name = "project_manager_id", foreignKey = @ForeignKey(name = "FK_PROJECT_EMPLOYEE"))
    private Employee projectManager;

    @ManyToOne
    @JoinColumn(name = "status_id", foreignKey = @ForeignKey(name = "FK_PROJECT_STATUS"))
    private Status status;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Task> tasks;

}
