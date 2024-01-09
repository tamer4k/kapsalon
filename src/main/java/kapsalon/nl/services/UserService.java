package kapsalon.nl.services;

import kapsalon.nl.models.dto.UserDTO;

import java.util.List;

public interface UserService {
    public List<UserDTO> getAllUsers();
    public UserDTO getUsertById(Long id);
    public UserDTO createUser(UserDTO userDTO);
    public UserDTO updateUser(Long id, UserDTO userDTO);
    public void deleteUser(Long id);
}
