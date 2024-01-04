package com.vn.em.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDto {
    private Integer id;
    private Integer roomId;
    private String message;
    private String createdDate;
    private String lastModifiedDate;
    private Integer createdBy;
    private Integer lastModifiedBy;
    private List<FileDto> fileDtos;

}
