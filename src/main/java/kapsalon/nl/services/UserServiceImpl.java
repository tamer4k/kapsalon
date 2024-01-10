package kapsalon.nl.services;

import kapsalon.nl.models.dto.UserDTO;
import kapsalon.nl.models.entity.User;
import kapsalon.nl.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public List<UserDTO> getAllUsers() {

        List<User> entityList = userRepository.findAll();
        List<UserDTO> dtoList = new ArrayList<>();
        for (User entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public UserDTO getUsertById(Long id) {
        Optional<User> entity = userRepository.findById(id);
        UserDTO dto;
        if (entity.isPresent()) {
            dto = fromEntityToDto(entity.get());

            return dto;
        }
        return  null;
    }


    @Override
    public UserDTO createUser(UserDTO dto) {
        User entity = userRepository.save(fromDtoToEntity(dto));
        return fromEntityToDto(entity);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        Optional<User> entityId = userRepository.findById(id);
        if (entityId.isPresent()) {
            User entity = entityId.get();
            entity.setRole(dto.getRole());
            entity.setRegisterDate(dto.getRegisterDate());
            entity.setPassword(dto.getPassword());


            User updatedEntity = userRepository.save(entity);
            return fromEntityToDto(updatedEntity);

        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public static UserDTO fromEntityToDto(User entity){
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setRole(entity.getRole());
        dto.setRegisterDate(entity.getRegisterDate());
        dto.setPassword(entity.getPassword());

        return  dto;
    }

    public static User fromDtoToEntity(UserDTO dto) {
        User entity = new User();

        entity.setId(dto.getId());
        entity.setRole(dto.getRole());
        entity.setRegisterDate(dto.getRegisterDate());
        entity.setPassword(dto.getPassword());

        return entity;
    }

}
