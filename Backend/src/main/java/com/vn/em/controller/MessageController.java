package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.MessageRequestDto;
import com.vn.em.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class MessageController {

    private final MessageService messageService;

    @Tag(name = "message-controller")
    @Operation(summary = "API get message by room")
    @GetMapping(UrlConstant.Message.GET_ALL_BY_ROOM_ID)
    public ResponseEntity<?> getAllMessageByRoomId(@PathVariable Integer roomId,
                                                   @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(messageService.getAllByRoomId(roomId, paginationFullRequestDto));
    }

    @Tag(name = "message-controller")
    @Operation(summary = "API send message")
    @PostMapping(UrlConstant.Message.SEND)
    public ResponseEntity<?> sendMessage(@Valid @ModelAttribute MessageRequestDto messageRequestDto) {
        return VsResponseUtil.success(messageService.sendMessage(messageRequestDto));
    }

}
