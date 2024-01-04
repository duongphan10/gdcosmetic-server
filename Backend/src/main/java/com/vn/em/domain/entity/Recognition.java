package com.vn.em.domain.entity;

import com.vn.em.domain.entity.common.UserDateAuditing;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "recognitions")
public class Recognition extends UserDateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Boolean type;
    @Column(nullable = false)
    private String reason;
    @Column(nullable = true)
    private Long amount;
    @Column(nullable = true)
    private LocalDateTime date;
    @Column(nullable = true)
    private String rejectionReason;

    @ManyToOne
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "FK_RECOGNITION_EMPLOYEE"))
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "status_id", foreignKey = @ForeignKey(name = "FK_RECOGNITION_STATUS"))
    private Status status;

}
