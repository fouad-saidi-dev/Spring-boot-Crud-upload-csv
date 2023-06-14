package com.example.demoback.service;

import com.example.demoback.dto.UserDto;
import com.example.demoback.entities.UserEntity;
import com.example.demoback.helper.CSVHelper;
import com.example.demoback.repositories.CSVRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {

    @Autowired
    CSVRepository csvRepository;

    public void save(MultipartFile file) {
        try {
            List<UserEntity> tutorials = CSVHelper.csvToUsers(file.getInputStream());
            csvRepository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<UserEntity> getAllTutorials() {
        return csvRepository.findAll();
    }

    public UserDto createUser(UserDto user) {

        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user,userEntity);

        UserEntity newUser = csvRepository.save(userEntity);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(newUser,userDto);

        return userDto;
    }
}
