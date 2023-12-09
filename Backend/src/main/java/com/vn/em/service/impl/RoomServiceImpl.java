package com.vn.em.service.impl;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.MessageConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.pagination.PagingMeta;
import com.vn.em.domain.dto.request.RoomCreateDto;
import com.vn.em.domain.dto.request.RoomUpdateDto;
import com.vn.em.domain.dto.response.CommonResponseDto;
import com.vn.em.domain.dto.response.RoomDto;
import com.vn.em.domain.entity.Room;
import com.vn.em.domain.entity.User;
import com.vn.em.domain.entity.UserRoom;
import com.vn.em.domain.entity.UserRoomKey;
import com.vn.em.domain.mapper.RoomMapper;
import com.vn.em.exception.ForbiddenException;
import com.vn.em.exception.InvalidException;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.RoomRepository;
import com.vn.em.repository.UserRepository;
import com.vn.em.repository.UserRoomRepository;
import com.vn.em.service.RoomService;
import com.vn.em.util.PaginationUtil;
import com.vn.em.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    private final RoomMapper roomMapper;
    private final UploadFileUtil uploadFileUtil;

    @Override
    public RoomDto getById(Integer id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Room.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        return roomMapper.mapRoomToRoomDto(room);
    }

    @Override
    public PaginationResponseDto<RoomDto> getAllMyConversation(Integer userId, PaginationFullRequestDto paginationFullRequestDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));

        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto);
        Page<Room> roomPage = roomRepository.getAllMyConversation(userId, pageable);
        PagingMeta meta = PaginationUtil.buildPagingMeta(paginationFullRequestDto, roomPage);

        List<RoomDto> roomDtos = roomMapper.mapRoomsToRoomDtos(roomPage.getContent());

        return new PaginationResponseDto<>(meta, roomDtos);
    }


    @Override
    public RoomDto create(Integer myId, RoomCreateDto roomCreateDto) {
        if (!roomCreateDto.getUsers().contains(myId)) {
            throw new InvalidException(ErrorMessage.Room.ERR_INVALID_USER_LIST);
        }

        Room room = roomMapper.mapRoomCreateDtoToRoom(roomCreateDto);
        roomRepository.save(room);

        for (Integer userId : roomCreateDto.getUsers()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId.toString()}));
            UserRoom userRoom = new UserRoom();
            userRoom.setId(new UserRoomKey(userId, room.getId()));
            userRoom.setRoom(room);
            userRoom.setUser(user);
            userRoomRepository.save(userRoom);
        }

        return roomMapper.mapRoomToRoomDto(room);
    }

    @Override
    public RoomDto updateById(Integer id, RoomUpdateDto roomUpdateDto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Room.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        roomMapper.update(room, roomUpdateDto);
        if (roomUpdateDto.getAvatar() != null) {
            if (room.getRoomAvatar() != null) {
                uploadFileUtil.destroyFileWithUrl(room.getRoomAvatar());
            }
            room.setRoomAvatar(uploadFileUtil.uploadFile(roomUpdateDto.getAvatar()));
        }
        return roomMapper.mapRoomToRoomDto(roomRepository.save(room));
    }

    @Override
    public CommonResponseDto deleteById(Integer id, Integer userId) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Room.ERR_NOT_FOUND_ID, new String[]{id.toString()}));
        if (!room.getCreatedBy().equals(userId)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        if (room.getRoomAvatar() != null) {
            uploadFileUtil.destroyFileWithUrl(room.getRoomAvatar());
        }
        roomRepository.delete(room);
        return new CommonResponseDto(true, MessageConstant.DELETE_ROOM_SUCCESSFULLY);
    }

}
