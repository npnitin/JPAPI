package com.example.web;

import com.example.models.User;
import com.example.repository.UserRepository;
import com.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/firebase")
public class FireBaseController {

    @Autowired
    private FileService fileService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/resume")
    @CrossOrigin(origins = "*")
    public ResponseEntity create(@RequestParam("userId") String userId, @RequestParam("selectedFile") MultipartFile file) {
        String fileName = null;
        try {
            fileName = fileService.saveTest(file);
            User user = userRepository.findById(userId).get();
            if(user == null){
                return null;
            }
            user.setResumeUrl("https://firebasestorage.googleapis.com/v0/b/jobportal-1606662293333.appspot.com/o/"+fileName+"?alt=media&token="+fileName);
            userRepository.save(user);

        } catch (Exception e) {
            System.out.println("Error:"+e.getMessage());
        }
        return ResponseEntity.ok().body(fileName);
    }

}
