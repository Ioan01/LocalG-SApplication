package upt.backend.controllers;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upt.backend.services.FirebaseDatabase;
import upt.backend.services.RegisterService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RegisterController
{
    @Autowired
    FirebaseDatabase firebaseDatabase;

    @Autowired
    private RegisterService registerService;
    @PostMapping("/register")
    public ResponseEntity<String> Register(@RequestParam("user") String user,@RequestParam("password") String password)
    {
        if (user != null && password != null)
        {
            return registerService.addUser(user,password);
        }
        else return new ResponseEntity<String>("User and password malformed",HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/hallo")
    public String Hello()
    {

        return "hello";
    }
}
