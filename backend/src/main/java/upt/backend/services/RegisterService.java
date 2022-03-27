package upt.backend.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterService
{
    public ResponseEntity<String> addUser(String username,String password)
    {
        return new ResponseEntity<String>("", HttpStatus.ACCEPTED);
    }
}
