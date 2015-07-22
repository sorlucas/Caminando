package com.example.sergio.caminando.maps;

import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RoutePaletteFragment.OnFragmentPaletteListener} interface
 * to handle interaction events.
 * Use the {@link RoutePaletteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutePaletteFragment extends Fragment
        implements
        AdapterView.OnItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = makeLogTag(MapsActivity.class);

    private static final String ARG_LAT = "latitude";
    private static final String ARG_LON = "longitude";

    private OnFragmentPaletteListener mListener;
    private GoogleApiClient mGoogleApiClient;

    // Autocomplete Places
    private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocompleteView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param latitude Actual latitude position
     * @param longitude Actual longitude position
     * @return A new instance of fragment BlankFragment.
     */
    public static RoutePaletteFragment newInstance(Double latitude, Double longitude) {
        RoutePaletteFragment fragment = new RoutePaletteFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_LAT, latitude);
        args.putDouble(ARG_LON, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    public RoutePaletteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (getArguments() != null) {
            mAdapter = new PlaceAutocompleteAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    mGoogleApiClient,
                    new LatLngBounds(
                            new LatLng(getArguments().getDouble(ARG_LAT), getArguments().getDouble(ARG_LON)),
                            new LatLng(getArguments().getDouble(ARG_LAT),getArguments().getDouble(ARG_LON))),
                    null);
        }



        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.palette, container, false);

        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        mAutocompleteView = (AutoCompleteTextView) root.findViewById(R.id.autocomplete_places);
        // Register a listener that receives callbacks when a suggestion has been selected
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);


        Spinner spinner = (Spinner) root.findViewById(R.id.layers_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.layers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentPaletteListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentPaletteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentPaletteListener {
        // TODO: Update argument type and name
        void onFragmentInteractionPalette(String layerName);
        void onChangeCameraView(LatLng latLng);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // This is also called by the Android framework in onResume(). The map may not be created at
        // this stage yet.

        if (mListener != null) {
            mListener.onFragmentInteractionPalette((String) parent.getItemAtPosition(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Autocomplete item selected: " + item.description);


            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getActivity(), "Clicked: " + item.description,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            Log.i(TAG, "Place details received: " + place.getName());

            if (mListener != null) {
                mListener.onChangeCameraView(place.getLatLng());
            }

            // TODO: remplace hidden soft
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mAutocompleteView.getWindowToken (), 0);

            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    @Override
    public void onConnected(Bundle bundle) {
        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.

        mAutocompleteView.setAdapter(mAdapter);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(getActivity(),
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }
}
