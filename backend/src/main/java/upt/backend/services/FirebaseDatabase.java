package upt.backend.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Component
@Scope("singleton")
public class FirebaseDatabase
{
    private Firestore database;

    private int index = 0;

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

        database= FirestoreClient.getFirestore();
    }

    public void write(Map map)
    {
        DocumentReference ref = database.collection("test").document("test" + index++);
        ref.set(map);

    }
}
