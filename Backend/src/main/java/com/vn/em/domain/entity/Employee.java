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
@Table(name = "employees")
public class Employee extends UserDateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String employeeCode;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private Boolean gender;
    @Column(nullable = false)
    private LocalDate birthday;
    @Column(nullable = false)
    private String hometown;
    @Column(nullable = false)
    private String ethnicity;
    @Column(nullable = false)
    private String religion;
    @Column(nullable = false)
    private String nationality;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private String idCardNumber;
    @Column(nullable = false)
    private LocalDate idCardIssuedDate;
    @Column(nullable = false)
    private String idCardIssuedLocation;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Long salary;

    @ManyToOne
    @JoinColumn(name = "position_id", foreignKey = @ForeignKey(name = "FK_EMPLOYEE_POSITION"))
    private Position position;

    @ManyToOne
    @JoinColumn(name = "status_id", foreignKey = @ForeignKey(name = "FK_EMPLOYEE_STATUS"))
    private Status status;

    @OneToOne(mappedBy = "employee")
    private User user;

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<SalaryAdjustment> salaryAdjustments;

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Recognition> recognitions;

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Attendance> attendances;

}
