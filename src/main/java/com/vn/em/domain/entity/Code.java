package com.vn.em.domain.entity;

import com.vn.em.domain.entity.common.DateAuditing;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "codes")
public class Code extends DateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String verificationCode;
    @Column(nullable = false)
    private LocalDateTime expirationTime;
    @Column(nullable = false)
    private Boolean valid;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

}
