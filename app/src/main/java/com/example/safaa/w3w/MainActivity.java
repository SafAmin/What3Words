package com.example.safaa.w3w;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.esri.android.runtime.ArcGISRuntime;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
//    // The basemap switching menu items.
//    MenuItem mStreetsMenuItem = null;
//    MenuItem mTopoMenuItem = null;
//    MenuItem mGrayMenuItem = null;
//    MenuItem mOceansMenuItem = null;
   // MapView mMapView;
//    // Create MapOptions for each type of basemap.
//    final MapOptions mTopoBasemap = new MapOptions(MapOptions.MapType.TOPO);
//    final MapOptions mStreetsBasemap = new MapOptions(MapOptions.MapType.STREETS);
//    final MapOptions mGrayBasemap = new MapOptions(MapOptions.MapType.GRAY);
//    final MapOptions mOceansBasemap = new MapOptions(MapOptions.MapType.OCEANS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       // mMapView = (MapView)findViewById(R.id.map);
        ArcGISRuntime.setClientId("ZdAenGlEGDRSxUDv");//key
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.menu_main, menu);
//        // Get the basemap switching menu items.
//        mStreetsMenuItem = menu.getItem(0);
//        mTopoMenuItem = menu.getItem(1);
//        mGrayMenuItem = menu.getItem(2);
//        mOceansMenuItem = menu.getItem(3);
//
//        // Also set the topo basemap menu item to be checked, as this is the default.
//        mTopoMenuItem.setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
//        switch (item.getItemId()) {
//            case R.id.World_Street_Map:
//                mMapView.setMapOptions(mStreetsBasemap);
//                mStreetsMenuItem.setChecked(true);
//                Log.v("mStreetsMenuItem","mStreetsMenuItem");
//                return true;
//            case R.id.World_Topo:
//                mMapView.setMapOptions(mTopoBasemap);
//                mTopoMenuItem.setChecked(true);
//                return true;
//            case R.id.Gray:
//                mMapView.setMapOptions(mGrayBasemap);
//                mGrayMenuItem.setChecked(true);
//                return true;
//            case R.id.Ocean_Basemap:
//                mMapView.setMapOptions(mOceansBasemap);
//                mOceansMenuItem.setChecked(true);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

        return super.onOptionsItemSelected(item);
    }
}
