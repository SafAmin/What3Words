package com.example.safaa.w3w;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapOptions;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.TextSymbol;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    String[] wordsArr = new String[3];
    ArrayList<String> wordsArrayList = new ArrayList<String>();
    MapView mMapView;
    Button wordToTextBtn;
    TextView wordToText;
    GraphicsLayer currentLocationGraphicLayer;
    Location location;
    String LONGITUDE_PARM = "";
    String LATITUDE_PARM = "";
    String What3Words="What3Words";
    int flag = 0;
    // The basemap switching menu items.
    MenuItem mStreetsMenuItem = null;
    MenuItem mTopoMenuItem = null;
    MenuItem mGrayMenuItem = null;
    MenuItem mOceansMenuItem = null;
    // Create MapOptions for each type of basemap.
    final MapOptions mTopoBasemap = new MapOptions(MapOptions.MapType.TOPO);
    final MapOptions mStreetsBasemap = new MapOptions(MapOptions.MapType.STREETS);
    final MapOptions mGrayBasemap = new MapOptions(MapOptions.MapType.GRAY);
    final MapOptions mOceansBasemap = new MapOptions(MapOptions.MapType.OCEANS);
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Log.d(TAG, "onCreate ...............................");
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            getActivity().finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mMapView = (MapView) rootView.findViewById(R.id.map);
        // el map fe menha 2 types(dynamic- tilled)el far2 en tilled btkon cashed wel dynamic la2..msh fakra
        mMapView.addLayer(new ArcGISDynamicMapServiceLayer("http://54.187.60.111/arcgis/rest/services/KSAProject_Arabic_Final_Mobile/MapServer"));

        wordToText = (TextView) rootView.findViewById(R.id.w3wText);
        wordToTextBtn = (Button) rootView.findViewById(R.id.convertButton);
        wordToTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchWordsTask().execute();
/*                if (flag == 0)
                    wordToText.setText("Lat: "+LATITUDE_PARM+ " , "+ "Long: "+LONGITUDE_PARM + " \n \n What3Words: " + wordsArr[0] + "." + wordsArr[1] + "." + wordsArr[2]);
                else {
                    wordToText.setText("Lat: " + LATITUDE_PARM + " , " + "Long: " + LONGITUDE_PARM + " \n \n What3Words: " + wordsArr[0] + "." + wordsArr[1] + "." + wordsArr[2]);
                    Toast.makeText(getActivity(), "Ooh! GPS issue." + "\n Please, Change your place", Toast.LENGTH_LONG).show();
                }*/
                // wordToText.setText("Lat: 30.000013 , Long: 31.000016"+" \n \n What3Words: wages."+"rattler."+



//                    // get prompts.xml view
//
//                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//
//                    View promptView = layoutInflater.inflate(R.layout.prompts, null);
//
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
//
//                    // set prompts.xml to be the layout file of the alertdialog builder
//
//                    alertDialogBuilder.setView(promptView);
//
//                    final EditText input = (EditText) promptView.findViewById(R.id.userInput);
//
//                    // setup a dialog window
//
//                    alertDialogBuilder
//
//                            .setCancelable(false)
//
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface dialog, int id) {
//
//                                    // get user input and set it to result
//
//                                    Log.v("get3W", ">>> " + input.getText());
//
//                                }
//
//                            })
//
//                            .setNegativeButton("Cancel",
//
//                                    new DialogInterface.OnClickListener() {
//
//                                        public void onClick(DialogInterface dialog, int id) {
//
//                                            dialog.cancel();
//
//                                        }
//
//                                    });
//
//                    // create an alert dialog
//
//                    AlertDialog alertD = alertDialogBuilder.create();
//
//                    alertD.show();

            }
        });

        updateUI();

        currentLocationGraphicLayer = new GraphicsLayer();
        mMapView.addLayer(currentLocationGraphicLayer);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Get the basemap switching menu items.
        mStreetsMenuItem = menu.getItem(0);
        mTopoMenuItem = menu.getItem(1);
        mGrayMenuItem = menu.getItem(2);
        mOceansMenuItem = menu.getItem(3);

        // Also set the topo basemap menu item to be checked, as this is the default.
        mTopoMenuItem.setChecked(true);
       // return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (item.getItemId()) {
            case R.id.World_Street_Map:
                mMapView.setMapOptions(mStreetsBasemap);
                mStreetsMenuItem.setChecked(true);
                Log.v("mStreetsMenuItem","mStreetsMenuItem");
                return true;
            case R.id.World_Topo:
                mMapView.setMapOptions(mTopoBasemap);
                mTopoMenuItem.setChecked(true);
                return true;
            case R.id.Gray:
                mMapView.setMapOptions(mGrayBasemap);
                mGrayMenuItem.setChecked(true);
                return true;
            case R.id.Ocean_Basemap:
                mMapView.setMapOptions(mOceansBasemap);
                mOceansMenuItem.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        //return super.onOptionsItemSelected(item);
    }
    public static Point getMapPoint(Context context, Location location) {
        Point wgspoint = new Point(location.getLongitude(), location.getLatitude());

        return (Point) GeometryEngine.project(wgspoint, SpatialReference.create(4326), SpatialReference.create(32637));
    }

    public void showLocation(Location location) {

        currentLocationGraphicLayer.removeAll();
        Point mapPoint = getMapPoint(getActivity(), location);
        Point mapPointText = getMapPoint(getActivity(), location);
        Graphic locationGraphic = new Graphic(mapPoint, new PictureMarkerSymbol(ContextCompat.getDrawable(getContext(), R.mipmap.ic_filter)));

        //text symbol which defines text size, the text and color
        TextSymbol txtSymbol = new TextSymbol(35, What3Words , Color.BLUE);

        //create a graphic from the point and symbol
        Graphic gr = new Graphic(mapPointText, txtSymbol);

        //add the graphic to the map
        currentLocationGraphicLayer.addGraphic(gr);
        currentLocationGraphicLayer.addGraphic(locationGraphic);
        zoomToPoint(mMapView, mapPointText, 1);
        zoomToPoint(mMapView, mapPoint, 0);
    }

    public void zoomToPoint(MapView mMapView, Point mapPoint,int f) {
        if(f == 0) {
            int factor = 100;
            Envelope stExtent = new Envelope(mapPoint.getX() - factor, mapPoint.getY() - factor, mapPoint.getX() + factor, mapPoint.getY() + factor);
            mMapView.setExtent(stExtent);
        }
        else {
            int factor = 200;
            Envelope stExtent = new Envelope(mapPoint.getX() - (factor+10), mapPoint.getY() - factor, mapPoint.getX() + factor, mapPoint.getY() + (factor+50));
            mMapView.setExtent(stExtent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();


    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        showLocation(mCurrentLocation);
        updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {


            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
            LONGITUDE_PARM = lng;
            LATITUDE_PARM = lat;
            Log.v("LocateME", "At Time: " + mLastUpdateTime + "\n" +
                            "Latitude: " + lat + "\n" +
                            "Longitude: " + lng + "\n"
//                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
//                    "Provider: " + mCurrentLocation.getProvider()
            );
        } else {
            Log.d(TAG, "location is null ...............");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }


    // AsyncTask method will parse String value(URL) and return Array of String
    public class FetchWordsTask extends AsyncTask<Void, Void, String[]> {
        private final String LOG_TAG = FetchWordsTask.class.getSimpleName();

        private String[] getWordsFromJSON(String wordsJsonStr) throws
                JSONException {

            final String results = "words";
            final String id0 = "0";
            final String id1 = "1";
            final String id2 = "2";

            JSONObject wordsJSON = new JSONObject(wordsJsonStr);
            JSONArray wordsArray = wordsJSON.getJSONArray(results);
            Log.v("No", " wordsArraylength >> " + wordsArray);
            String[] wordsResult = new String[(wordsArray.length())];
            for (int i = 0; i < wordsArray.length(); i++) {

                String word0 = wordsArray.optString(0);
                String word1 = wordsArray.optString(1);
                String word2 = wordsArray.optString(2);
                Log.v("No", "word1 >> " + word0);
                wordsArr[0] = word0;
                wordsArr[1] = word1;
                wordsArr[2] = word2;
            }

            for (int i = 0; i < wordsArr.length; i++) {
                Log.v(LOG_TAG, "Words Info: " + wordsArr[i]);
            }

            return wordsArr;
        }

        @Override
        protected String[] doInBackground(Void... params) {
            /*if (params.length == 0) {
                return null;
            }*/

            /**
             * These two need to be declared outside the try/catch
             * so that they can be closed in the finally block.
             */
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            /* Will contain the raw JSON response as a string.*/
            String wordsJsonStr = null;


            try {
                // Construct the URL for the TheMovieDataBase query
                final String PLACE_BASE_URL = "https://api.what3words.com/position?";
                final String POSTION_PARAM = "position";
                final String APPID_PARAM = "key";
                Uri builtUri = Uri.parse(PLACE_BASE_URL).buildUpon().build().buildUpon()
                        .appendQueryParameter(APPID_PARAM, "MXI2JG7Q")
                        .appendQueryParameter(POSTION_PARAM, LATITUDE_PARM + "," + LONGITUDE_PARM)
                        .build();
                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "BuiltURL" + builtUri.toString());


                // Create the request to TheW3W, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    wordsJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                wordsJsonStr = buffer.toString();
                Log.v(LOG_TAG, "wordsArraylength: " + wordsJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the word, there's no point in attemping
                // to parse it.
                wordsJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {

                return getWordsFromJSON(wordsJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {

                wordToText.setText("Lat: "+LATITUDE_PARM+ " , "+ "Long: "+LONGITUDE_PARM + " \n \n What3Words: " + wordsArr[0] + "." + wordsArr[1] + "." + wordsArr[2]);
               What3Words=wordsArr[0] + "." + wordsArr[1] + "." + wordsArr[2];
                Log.v("FinalArray", ".........");

            } else
            {
                Log.v("No", "Array empty");
                Toast.makeText(getActivity(), "Ooh! GPS issue." + "\n Please, Change your place", Toast.LENGTH_LONG).show();
            }



        }
    }



}
