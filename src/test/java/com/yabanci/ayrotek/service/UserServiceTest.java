package com.yabanci.ayrotek.service;

import com.yabanci.ayrotek.dto.UserDto;
import com.yabanci.ayrotek.dto.request.UserSaveRequestDto;
import com.yabanci.ayrotek.dto.request.UserUpdateRequestDto;
import com.yabanci.ayrotek.exception.ItemNotFoundException;
import com.yabanci.ayrotek.model.User;
import com.yabanci.ayrotek.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private UserService userService;

    @Test
    void findAll() {
    }

    @Test
    void shouldSave() {
        UserSaveRequestDto userSaveRequestDto = mock(UserSaveRequestDto.class);
        User user = mock(User.class);

        when(userRepository.save(any())).thenReturn(user);

        UserDto result = userService.save(userSaveRequestDto);

        assertEquals(result.getFirstName(), userSaveRequestDto.getFirstName());
    }

    @Test
    void shouldUpdate() {
        UserUpdateRequestDto userUpdateRequestDto = Mockito.mock(UserUpdateRequestDto.class);
        User user = Mockito.mock(User.class);

        Mockito.doReturn(user).when(userService).findById(anyLong());
        when(userRepository.save(any())).thenReturn(user);

        UserDto result = userService.update(userUpdateRequestDto);

        assertEquals(result.getId(), userUpdateRequestDto.getId());
    }

    @Test
    void shouldNotUpdateWhenUserDoesNotExist() {

        UserUpdateRequestDto userUpdateRequestDto = mock(UserUpdateRequestDto.class);

        when(userRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> userService.update(userUpdateRequestDto));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void shouldDelete() {

        User user = Mockito.mock(User.class);

        Mockito.doReturn(user).when(userService).findById(anyLong());

        userService.delete(anyLong());

        Mockito.verify(userService).findById(anyLong());
        Mockito.verify(userRepository).delete(any());
    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(userRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> userService.delete(anyLong()));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void shouldFindById() {
        User user = Mockito.mock(User.class);

        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User result = userService.findById(anyLong());

        assertEquals(result.getId(), user.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist(){
        when(userRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> userService.findById(anyLong()));
        verify(userRepository).findById(anyLong());
    }
}