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
@Table(name = "files")
public class File extends UserDateAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long size;

    @ManyToOne
    @JoinColumn(name = "message_id", foreignKey = @ForeignKey(name = "FK_FILE_MESSAGE"))
    private Message message;

}
