package com.vn.em.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vn.em.domain.entity.common.DateAuditing;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = true)
    private String avatar;

    //Link to table Role
    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USER_ROLE"))
    private Role role;

}
