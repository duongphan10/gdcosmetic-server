package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.security.CurrentUser;
import com.vn.em.security.UserPrincipal;
import com.vn.em.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class NotificationController {

    private final NotificationService notificationService;

    @Tag(name = "notification-controller")
    @Operation(summary = "API get all my notification")
    @GetMapping(UrlConstant.Notification.GET_ALL)
    public ResponseEntity<?> getAllMyNotification(@Parameter(name = "principal", hidden = true)
                                                  @CurrentUser UserPrincipal user,
                                                  @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(notificationService.getAllMyNotification(user.getId(), paginationFullRequestDto));
    }

    @Tag(name = "notification-controller")
    @Operation(summary = "API count my unread notification")
    @GetMapping(UrlConstant.Notification.GET_UNREAD)
    public ResponseEntity<?> countMyUnreadNotification(@Parameter(name = "principal", hidden = true)
                                                       @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(notificationService.countUnreadNotification(user.getId()));
    }

    @Tag(name = "notification-controller")
    @Operation(summary = "API seen all my notification")
    @PatchMapping(UrlConstant.Notification.SEEN_ALL)
    public ResponseEntity<?> seenAllMyNotification(@Parameter(name = "principal", hidden = true)
                                                   @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(notificationService.seenAllNotification(user.getId()));
    }

}
