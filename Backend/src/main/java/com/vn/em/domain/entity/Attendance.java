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
@Table(name = "attendances")
public class Attendance extends UserDateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer year;
    @Column(nullable = false)
    private Integer month;
    @Column(nullable = false, columnDefinition = "FLOAT(3, 1)")
    private Float actualWorkingDays;
    @Column(nullable = false)
    private Integer lateArrival;
    @Column(nullable = false, columnDefinition = "FLOAT(3, 1)")
    private Float workingDaysOfMonth;
    @Column(nullable = true)
    private String note;

    @ManyToOne
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "FK_ATTENDANCE_EMPLOYEE"))
    private Employee employee;

}
