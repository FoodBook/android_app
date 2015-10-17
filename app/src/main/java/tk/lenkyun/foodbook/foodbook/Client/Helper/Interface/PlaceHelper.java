package tk.lenkyun.foodbook.foodbook.Client.Helper.Interface;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.LinkedList;
import java.util.List;

import tk.lenkyun.foodbook.foodbook.Client.Helper.Interface.Listener.ObjectListener;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class PlaceHelper {
    private static final int INTENT_ID = 122;
    private List<ObjectListener<Place>> placeListeners = new LinkedList<>();

    private Activity activity;
    public PlaceHelper(Activity activity){
        this.activity = activity;
    }

    public void registerPlaceListener(ObjectListener<Place> placeObjectListener){
        placeListeners.add(placeObjectListener);
    }

    public void pick(){
        if(activity != null) {
            Intent intent = null;
            try {
                intent = new PlacePicker.IntentBuilder().build(activity);
                activity.startActivityForResult(intent, INTENT_ID);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == INTENT_ID && resultCode == Activity.RESULT_OK){
            Place place = PlacePicker.getPlace(intent, activity.getApplicationContext());
            for(ObjectListener<Place> placeListener : placeListeners){
                placeListener.onTaken(place, 0);
            }
        }
    }
}
