package pl.javastart.equipy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.javastart.equipy.model.*;
import pl.javastart.equipy.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll(){
        return userRepository
                .findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> findByLastName(String lastName){
        return userRepository
                .findAllByLastNameContainingIgnoreCase(lastName).stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto save(UserDto user) {
        Optional<User> userByPesel = userRepository
                .findByPesel(user.getPesel());
        userByPesel.ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Użytkownik z takim peselem już istnieje");
        });
        return mapAndSaveUser(user);
    }

    public UserDto update(UserDto user) {
        Optional<User> userByPesel = userRepository
                .findByPesel(user.getPesel());
        userByPesel.ifPresent(u -> {
            if(!u.getId().equals(user.getId()))
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Użytkownik z takim peselem już istnieje");
        });
        return mapAndSaveUser(user);
    }

    private UserDto mapAndSaveUser(UserDto user) {
        User userEntity = UserMapper.toEntity(user);
        User savedUser = userRepository
                .save(userEntity);
        return UserMapper.toDto(savedUser);
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto);
    }

    public List<UserAssignmentsDto> getUserAssignments(Long userId) {
        return userRepository.findById(userId)
                .map(User::getAssignments)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Podany użytkownik nie istnieje"))
                .stream()
                .map(UserAssignmentsMapper::toDto)
                .collect(Collectors.toList());
    }
}
