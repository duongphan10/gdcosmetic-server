package com.vn.em.domain.dto.request;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.validator.annotation.ValidListFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageRequestDto {
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Integer roomId;
    private String message;
    @ValidListFile
    private List<MultipartFile> files;

    @AssertTrue(message = ErrorMessage.INVALID_MESSAGE)
    private boolean isValid() {
        return (message != null && !message.trim().isEmpty()) || (files != null && !files.isEmpty());
    }

}
