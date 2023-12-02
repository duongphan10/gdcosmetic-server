package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.RoomCreateDto;
import com.vn.em.domain.dto.request.RoomUpdateDto;
import com.vn.em.domain.dto.request.UserRoomRequestDto;
import com.vn.em.security.CurrentUser;
import com.vn.em.security.UserPrincipal;
import com.vn.em.service.RoomService;
import com.vn.em.service.UserRoomService;
import com.vn.em.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class RoomController {
    private final RoomService roomService;
    private final UserRoomService userRoomService;
    private final UserService userService;

    @Tag(name = "room-controller")
    @Operation(summary = "API get room by id")
    @GetMapping(UrlConstant.Room.GET_BY_ID)
    public ResponseEntity<?> getRoomById(@PathVariable Integer id) {
        return VsResponseUtil.success(roomService.getById(id));
    }

    @Tag(name = "room-controller")
    @Operation(summary = "API get all my conversation")
    @GetMapping(UrlConstant.Room.GET_ALL_MY_CONVERSATION)
    public ResponseEntity<?> getAllMyConversation(@Parameter(name = "principal", hidden = true)
                                                  @CurrentUser UserPrincipal user,
                                                  @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(roomService.getAllMyConversation(user.getId(), paginationFullRequestDto));
    }

    @Tag(name = "room-controller")
    @Operation(summary = "API get all users by room id")
    @GetMapping(UrlConstant.Room.GET_ALL_USER)
    public ResponseEntity<?> getAllUserByRoomId(@PathVariable Integer id,
                                                @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(userRoomService.getAllByRoomId(id, paginationFullRequestDto));
    }

    @Tag(name = "room-controller")
    @Operation(summary = "API search other user not in the room")
    @GetMapping(UrlConstant.Room.SEARCH_OTHER_USER)
    public ResponseEntity<?> searchOtherUser(@PathVariable Integer id,
                                             @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(userService.searchOtherUser(id, paginationFullRequestDto));
    }

    @Tag(name = "room-controller")
    @Operation(summary = "API create room")
    @PostMapping(UrlConstant.Room.CREATE)
    public ResponseEntity<?> createRoom(@Parameter(name = "principal", hidden = true)
                                        @CurrentUser UserPrincipal user,
                                        @Valid @RequestBody RoomCreateDto roomCreateDto) {
        return VsResponseUtil.success(roomService.create(user.getId(), roomCreateDto));
    }

    @Tag(name = "room-controller")
    @Operation(summary = "API add user in the room")
    @PostMapping(UrlConstant.Room.ADD_USER)
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRoomRequestDto userRoomRequestDto) {
        return VsResponseUtil.success(userRoomService.create(userRoomRequestDto));
    }

    @Tag(name = "room-controller")
    @Operation(summary = "API update room by id")
    @PatchMapping(UrlConstant.Room.UPDATE)
    public ResponseEntity<?> updateRoom(@PathVariable Integer id,
                                        @Valid @RequestBody RoomUpdateDto roomUpdateDto) {
        return VsResponseUtil.success(roomService.updateById(id, roomUpdateDto));
    }

    @Tag(name = "room-controller")
    @Operation(summary = "API change nickname user in room")
    @PatchMapping(UrlConstant.Room.CHANGE_NICKNAME)
    public ResponseEntity<?> changeNickname(@Valid @RequestBody UserRoomRequestDto userRoomRequestDto) {
        return VsResponseUtil.success(userRoomService.changeNickname(userRoomRequestDto));
    }

    @Tag(name = "room-controller")
    @Operation(summary = "API delete room by id")
    @DeleteMapping(UrlConstant.Room.DELETE)
    public ResponseEntity<?> deleteRoom(@PathVariable Integer id,
                                        @Parameter(name = "principal", hidden = true)
                                        @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(roomService.deleteById(id, user.getId()));
    }

    @Tag(name = "room-controller")
    @Operation(summary = "API delete or leave the room")
    @DeleteMapping(UrlConstant.Room.LEAVE)
    public ResponseEntity<?> leaveRoom(@Parameter(name = "principal", hidden = true)
                                       @CurrentUser UserPrincipal user,
                                       @Valid @RequestBody UserRoomRequestDto userRoomRequestDto) {
        return VsResponseUtil.success(userRoomService.delete(user.getId(), userRoomRequestDto));
    }

}
