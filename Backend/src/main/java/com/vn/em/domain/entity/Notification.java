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
@Table(name = "notifications")
public class Notification extends UserDateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, columnDefinition = "TINYINT")
    private Integer type;
    @Column(nullable = true)
    private String message;
    @Column(nullable = false)
    private Boolean seen;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_NOTIFICATION_USER"))
    private User user;

}
