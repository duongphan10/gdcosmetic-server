package com.vn.em.domain.entity;

import com.vn.em.domain.entity.common.UserDateAuditing;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "salaries")
public class Salary extends UserDateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Long realSalary;
    @Column(nullable = false)
    private Long allowance;
    @Column(nullable = false)
    private Long bonus;
    @Column(nullable = false)
    private Long deduction;
    @Column(nullable = false)
    private Long insurance;
    @Column(nullable = false)
    private Long taxableIncome;
    @Column(nullable = false)
    private Long tax;
    @Column(nullable = false)
    private Long netSalary;
    @Column(nullable = false)
    private Boolean paymentStatus;
    @Column(nullable = true)
    private LocalDateTime paymentDate;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "attendance_id", referencedColumnName = "id")
    private Attendance attendance;

}
