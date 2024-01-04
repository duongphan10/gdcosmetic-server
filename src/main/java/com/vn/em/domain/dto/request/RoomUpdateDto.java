package com.vn.em.domain.dto.request;

import com.vn.em.validator.annotation.ValidFileImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomUpdateDto {
    private String name;
    @ValidFileImage
    private MultipartFile avatar;

}
