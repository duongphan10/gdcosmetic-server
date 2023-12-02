package com.vn.em.service.impl;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.UserRoomRequestDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.UserRoomDto;
import com.vn.em.domain.entity.Room;
import com.vn.em.domain.entity.User;
import com.vn.em.domain.entity.UserRoom;
import com.vn.em.domain.entity.UserRoomKey;
import com.vn.em.domain.mapper.UserRoomMapper;
import com.vn.em.exception.AlreadyExistException;
import com.vn.em.exception.ForbiddenException;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.RoomRepository;
import com.vn.em.repository.UserRepository;
import com.vn.em.repository.UserRoomRepository;
import com.vn.em.service.UserRoomService;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoomServiceImpl implements UserRoomService {

    private final UserRoomRepository userRoomRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomMapper userRoomMapper;

    @Override
    public PaginationResponseDto<UserRoomDto> getAllByRoomId(Integer roomId, PaginationFullRequestDto paginationFullRequestDto) {
        roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Room.ERR_NOT_FOUND_ID, new String[]{roomId.toString()}));

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto);
        Page<UserRoom> userRoomPage = userRoomRepository.getAllUserByRoomId(roomId, pageable);
        PagingMeta meta = PaginationUtil.buildPagingMeta(paginationFullRequestDto, userRoomPage);

        List<UserRoomDto> userRoomDtos = userRoomMapper.mapUserRoomsToUserRoomDtos(userRoomPage.getContent());
        return new PaginationResponseDto<>(meta, userRoomDtos);
    }

    @Override
    public UserRoomDto create(UserRoomRequestDto roomRequestDto) {
        Room room = roomRepository.findById(roomRequestDto.getRoomId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Room.ERR_NOT_FOUND_ID, new String[]{roomRequestDto.getRoomId().toString()}));
        User user = userRepository.findById(roomRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{roomRequestDto.getUserId().toString()}));
        UserRoomKey userRoomKey = new UserRoomKey(roomRequestDto.getUserId(), roomRequestDto.getRoomId());
        if (userRoomRepository.existsById(userRoomKey)) {
            throw new AlreadyExistException(ErrorMessage.UserRoom.ERR_ALREADY_EXIST);
        }

        UserRoom userRoom = userRoomMapper.mapUserRoomRequestDtoToUserRoom(roomRequestDto);
        userRoom.setId(userRoomKey);
        userRoom.setRoom(room);
        userRoom.setUser(user);
        return userRoomMapper.mapUserRoomToUserRoomDto(userRoomRepository.save(userRoom));
    }

    @Override
    public UserRoomDto changeNickname(UserRoomRequestDto roomUpdateDto) {
        UserRoom userRoom = userRoomRepository.findById(new UserRoomKey(roomUpdateDto.getUserId(), roomUpdateDto.getRoomId()))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.UserRoom.ERR_NOT_FOUND));
        if (roomUpdateDto.getNickname().isEmpty()) {
            userRoom.setNickname(null);
        } else {
            userRoom.setNickname(roomUpdateDto.getNickname());
        }
        return userRoomMapper.mapUserRoomToUserRoomDto(userRoomRepository.save(userRoom));
    }

    @Override
    public CommonResponseDto delete(Integer userId, UserRoomRequestDto userRoomRequestDto) {
        UserRoom userRoom = userRoomRepository.findById(new UserRoomKey(userRoomRequestDto.getUserId(), userRoomRequestDto.getRoomId()))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.UserRoom.ERR_NOT_FOUND));
        if (!userId.equals(userRoomRequestDto.getUserId()) && !userId.equals(userRoom.getCreatedBy())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        userRoomRepository.delete(userRoom);
        return new CommonResponseDto(true, MessageConstant.DELETE_USER_ROOM_SUCCESSFULLY);
    }

}
