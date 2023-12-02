package com.vn.em.domain.dto.request;

import com.vn.em.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomCreateDto {
    private boolean type;
    private String name;
    private Set<Integer> users;

    @AssertTrue(message = ErrorMessage.INVALID_USER_LIST_SIZE)
    private boolean isValidType() {
        if (type) {
            return users != null && users.size() >= 3;
        }
        return users != null && users.size() == 2;
    }

}
