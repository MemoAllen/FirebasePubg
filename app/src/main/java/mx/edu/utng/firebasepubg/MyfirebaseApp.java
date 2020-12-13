package mx.edu.utng.firebasepubg;

import com.google.firebase.database.FirebaseDatabase;

public class MyfirebaseApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
