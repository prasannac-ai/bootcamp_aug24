package com.connectritam.fooddonation.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.connectritam.fooddonation.userservice.dto.CreateUsersDTO;
import com.connectritam.fooddonation.userservice.dto.UsersDTO;
import com.connectritam.fooddonation.userservice.exception.ErrorKey;
import com.connectritam.fooddonation.userservice.exception.ResourceNotFoundException;
import com.connectritam.fooddonation.userservice.mapper.UserMapper;
import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // public List<UsersDTO> getAllUsers() {
    // List<Users> users = userRepository.findAll();
    // return users.stream()
    // .map(this::convertToDTO)
    // .collect(Collectors.toList());
    // }

    public List<UsersDTO> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public List<UsersDTO> getAllUsers(int page, int size, String sortItem, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortItem).ascending()
                : Sort.by(sortItem).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Users> users = userRepository.findAll(pageable);
        List<UsersDTO> usersDTOList = new ArrayList<>();
        for (Users user : users) {
            usersDTOList.add(UserMapper.INSTANCE.toDTO(user));
        }
        return usersDTOList;
    }

    private UsersDTO convertToDTO(Users user) {
        UsersDTO userDTO = new UsersDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

    public Users getUserById(UUID id) {

        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ResourceNotFoundException(ErrorKey.USER_NOT_FOUND);

        }
    }

    public Users createUser(CreateUsersDTO userDTO) {
        Users user = UserMapper.INSTANCE.toFullEntity(userDTO);
        return userRepository.save(user);
    }

    public Users updateUser(UUID id, CreateUsersDTO userDetails) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword()); // FIXIT
            user.setRole(userDetails.getRole());
            user.setMobile(userDetails.getMobile());
            user.setAddress(userDetails.getAddress());
            user.setLatitude(userDetails.getLatitude());
            user.setLongitude(userDetails.getLongitude());
            return userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}