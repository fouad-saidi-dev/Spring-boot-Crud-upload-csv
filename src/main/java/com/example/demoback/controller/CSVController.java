package com.example.demoback.controller;

import com.example.demoback.dto.UserDto;
import com.example.demoback.entities.UserEntity;
import com.example.demoback.helper.CSVHelper;
import com.example.demoback.repositories.CSVRepository;
import com.example.demoback.requests.UserRequest;
import com.example.demoback.responses.MessageResponse;
import com.example.demoback.responses.UserResponse;
import com.example.demoback.service.CSVService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("users")
public class CSVController {


    @Autowired
    CSVService csvService;
    @Autowired
    CSVRepository csvRepository;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                csvService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
    }


    @PostMapping("/add")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){

        UserDto tutorialDto = new UserDto();
        BeanUtils.copyProperties(userRequest,tutorialDto);

        UserDto createUser = csvService.createUser(tutorialDto);

        UserResponse userResponse = new UserResponse();

        BeanUtils.copyProperties(createUser,userResponse);

        return new ResponseEntity<UserResponse>(userResponse,HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<UserEntity>> getAllTutorials() {
        try {
            List<UserEntity> users = csvService.getAllTutorials();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/log")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity userData) {

        System.out.println(userData);
        UserEntity user= csvRepository.findByEmail(userData.getEmail());
        if(user.getPassword().equals(userData.getPassword()))
            return ResponseEntity.ok(user);


        return (ResponseEntity<?>) ResponseEntity.internalServerError();

    }

}
