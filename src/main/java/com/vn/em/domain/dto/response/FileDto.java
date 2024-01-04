package com.vn.em.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileDto {
    private Integer id;
    private String path;
    private String name;
    private Long size;
    private String createdDate;
    private String lastModifiedDate;
    private Integer createdBy;
    private Integer lastModifiedBy;

}
