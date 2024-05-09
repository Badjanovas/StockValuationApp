package com.example.StockValueApp.service.mappingService;

import com.example.StockValueApp.dto.UserRequestDTO;
import com.example.StockValueApp.dto.UserResponseDTO;
import com.example.StockValueApp.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMappingService {

    public User mapToEntity(final UserRequestDTO requestDTO) {
        return User.builder()
                .userName(requestDTO.getUserName())
                .password(requestDTO.getPassword())
                .email(requestDTO.getEmail())
                .build();
    }

    public List<UserResponseDTO> mapToResponse(List<User> allUsers) {
        List<UserResponseDTO> mappedUsers = new ArrayList<>();
        for (User user : allUsers) {
            UserResponseDTO dto = UserResponseDTO.builder()
                    .id(user.getId())
                    .creationDate(user.getCreationDate())
                    .userName(user.getUsername())
                    .email(user.getEmail())
                    .build();

            mappedUsers.add(dto);
        }
        return mappedUsers;
    }
}
