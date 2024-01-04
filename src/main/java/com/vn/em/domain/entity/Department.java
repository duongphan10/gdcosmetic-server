package com.vn.em.domain.entity;

import com.vn.em.domain.entity.common.UserDateAuditing;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "departments")
public class Department extends UserDateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private String description;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Position> positions;

}
