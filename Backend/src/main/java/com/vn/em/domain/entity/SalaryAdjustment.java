package com.vn.em.domain.entity;

import com.vn.em.domain.entity.common.UserDateAuditing;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "salary_adjustments")
public class SalaryAdjustment extends UserDateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Long newSalary;
    @Column(nullable = false)
    private String reason;

    @Column(nullable = true)
    private String rejectionReason;

    @ManyToOne
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "FK_SALARY_ADJUSTMENT_EMPLOYEE"))
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "status_id", foreignKey = @ForeignKey(name = "FK_SALARY_ADJUSTMENT_STATUS"))
    private Status status;

}
