package com.vn.em.service.impl;

import com.vn.em.constant.ErrorMessage;
import com.vn.em.constant.SortByDataConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.pagination.PaginationResponseDto;
import com.vn.em.domain.dto.response.UserDto;
import com.vn.em.domain.entity.User;
import com.vn.em.domain.mapper.UserMapper;
import com.vn.em.exception.NotFoundException;
import com.vn.em.repository.UserRepository;
import com.vn.em.security.UserPrincipal;
import com.vn.em.service.UserService;
import com.vn.em.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        return userMapper.toUserDto(user);
    }

    @Override
    public PaginationResponseDto<UserDto> getCustomers(PaginationFullRequestDto request) {
        //Pagination
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.USER);
        //Create Output
        return new PaginationResponseDto<>(null, null);
    }

    @Override
    public UserDto getCurrentUser(UserPrincipal principal) {
        User user = userRepository.getUser(principal);
        return userMapper.toUserDto(user);
    }

}
