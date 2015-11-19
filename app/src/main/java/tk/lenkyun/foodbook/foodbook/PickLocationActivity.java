package tk.lenkyun.foodbook.foodbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class PickLocationActivity extends AppCompatActivity {
    public static final int INTENT_ID = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);

        EditText editText = (EditText) findViewById(R.id.search_location);
        //editText.getText().toString();
    }
}
