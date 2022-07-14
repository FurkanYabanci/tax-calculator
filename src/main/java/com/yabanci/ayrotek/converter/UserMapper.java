package com.yabanci.ayrotek.converter;

import com.yabanci.ayrotek.dto.UserDto;
import com.yabanci.ayrotek.dto.request.UserSaveRequestDto;
import com.yabanci.ayrotek.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(UserSaveRequestDto userSaveRequestDto);
    UserDto convertToUserDto(User user);
    List<UserDto> convertToUserDtoList(List<User> users);
}
