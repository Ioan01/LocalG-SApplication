package upt.backend.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Scope("singleton")
public class FirebaseDatabase
{
    private final Firestore database;

    private final int index = 0;

    FirebaseDatabase() throws IOException
    {

        ClassLoader loader = FirebaseDatabase.class.getClassLoader();
        File file = new File(loader.getResource("jsonKey.json").getFile());

        FileInputStream serviceAccount =
                new FileInputStream(file.getAbsolutePath());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://sef-project-5f7ac-default-rtdb.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        database = FirestoreClient.getFirestore();
    }

    public Optional<CollectionReference> getCollection(String collection)
    {
        if (StreamSupport.stream(database.listCollections().spliterator(), false).anyMatch(
                collectionReference -> collectionReference.getId().equals(collection)))
            return Optional.of(database.collection(collection));
        return Optional.empty();
    }
}
