package tk.lenkyun.foodbook.foodbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

public class PickLocationActivity extends AppCompatActivity {
    public static final int INTENT_ID = 400;
    private RecyclerView recyclerView;
    private Place place = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_pick_location);
        TextView textView = (TextView) findViewById(R.id.search_location);
    }

    private void initLocationList(){
        recyclerView = (RecyclerView) findViewById(R.id.location_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    }

    public static class LocationListHolder extends RecyclerView.ViewHolder {
        public TextView LocationName;

        public LocationListHolder(View itemView) {
            super(itemView);
            LocationName = (TextView) itemView.findViewById(R.id.placeName);

        }
    }
}
