package upt.backend.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.database.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class RegisterService
{
    @Autowired
    FirebaseDatabase database;

    public ResponseEntity<String> addUser(@NotNull String username,@NotNull String password)
    {
        Optional<CollectionReference> reference = database.getCollection("users");
        CollectionReference collection = reference.orElseThrow(()->{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        });

        if (StreamSupport.stream(collection.listDocuments().spliterator(),false)
                .anyMatch(documentReference -> documentReference.getId().equals(username)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already registered");
        else
        {
            ApiFuture<WriteResult> apiFuture = collection.document(username)
                    .set(Map.of("username", username, "password", password));
            return new ResponseEntity<String>(String.format("%s account registered",username), HttpStatus.ACCEPTED);
        }
    }
}
