package com.example.sergio.caminando.ui;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.provider.RouteContract;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

/**
 * Created by sergio on 30/05/15.
 */
public class CreateRouteFragment extends Fragment implements
        com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        ImageChooserListener {

    private static final String TAG = makeLogTag("CreateRouteFragment");

    public static final String DATEPICKER_TAG_START = "datepickerstart";
    public static final String TIMEPICKER_TAG_START = "timepickerstart";

    //Declarate UI Rerferences

    private EditText mRouteName;
    private EditText mCityName;
    private EditText mDescriptionRoute;
    private EditText mTopicsRoute;
    private EditText mStartDate;
    private ImageButton mImageButtonStartDate;
    private ImageButton mImageButtonStartTime;
    private EditText mMaxAttendees;

    private SimpleDateFormat dateFormatter;
    private Long startDate;

    //Delarate Views to catch photo route
    private ImageView imageViewThumbnail;
    private ImageChooserManager imageChooserManager;
    private ProgressBar pbar;
    private String filePath;
    private int chooserType;

    public interface Callbacks {
        void onUploadRoute(HashMap<String,String> routeHasMap, String urlPhotoPath);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onUploadRoute(HashMap<String, String> routeHasMap, String urlPhotoPath) {}
    };

    private Callbacks mCallbacks = sDummyCallbacks;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new ClassCastException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_create_route, container, false);

        Button buttonTakePicture = (Button) root.findViewById(R.id.buttonTakePicture);
        buttonTakePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        Button buttonChooseImage = (Button) root.findViewById(R.id.buttonChooseImage);
        buttonChooseImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        imageViewThumbnail = (ImageView) root.findViewById(R.id.imageViewThumb);
        pbar = (ProgressBar) root.findViewById(R.id.progressBar);
        pbar.setVisibility(View.GONE);

        mRouteName = (EditText) root.findViewById(R.id.route_name_editext);
        mCityName = (EditText) root.findViewById(R.id.route_city_name_editext);
        mDescriptionRoute = (EditText) root.findViewById(R.id.description_route_editext);
        mTopicsRoute = (EditText) root.findViewById(R.id.topics_routes);

        mStartDate = (EditText) root.findViewById(R.id.start_date_route_editext);
        mImageButtonStartDate = (ImageButton) root.findViewById(R.id.imageButtonStartDate);
        mImageButtonStartTime = (ImageButton) root.findViewById(R.id.imageButtonStartTime);

        mMaxAttendees = (EditText) root.findViewById(R.id.max_attendees_editext);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            DatePickerDialog dpdS = (DatePickerDialog) getFragmentManager().findFragmentByTag(DATEPICKER_TAG_START);
            TimePickerDialog tpdS = (TimePickerDialog) getFragmentManager().findFragmentByTag(TIMEPICKER_TAG_START);

            if (dpdS != null) {
                dpdS.setOnDateSetListener(this);
            }
            if (tpdS != null) {
                tpdS.setOnTimeSetListener(this);
            }
        }
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        setDateTimeField();

        // Para ocultar el teclado al abrir lo de crear ruta. Se iba siempre a Route Name
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setDateTimeField() {
        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, true);

        mImageButtonStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(true); //Add vibrate
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG_START);
            }
        });

        mImageButtonStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.setVibrate(true);
                timePickerDialog.setCloseOnSingleTapMinute(false);
                timePickerDialog.show(getFragmentManager(), TIMEPICKER_TAG_START);
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        String tag = datePickerDialog.getTag();
        Calendar newDate = Calendar.getInstance();
        switch (tag) {
            case DATEPICKER_TAG_START:
                Toast.makeText(getActivity(), "Start date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
                newDate.set(year, month, day);
                mStartDate.setText(dateFormatter.format(newDate.getTime()));
                startDate = newDate.getTime().getTime();
                break;
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }

    /**
     * Methodo was used from CreateRoute to pass a Views from fragment_create_route
     * @param view View to be clicked
     */
    public void myClickMethod(View view){
        switch (view.getId()){
            case (R.id.ok_button):
                mCallbacks.onUploadRoute(getHashMapDataRoutes(), filePath);
                return;
        }
    }

    private HashMap<String,String> getHashMapDataRoutes(){
        HashMap<String,String> hashMapRoutes = new HashMap<>();
        hashMapRoutes.put(RouteContract.RouteEntry.COLUMN_NAME_ROUTE,mRouteName.getText().toString());
        hashMapRoutes.put(RouteContract.RouteEntry.COLUMN_DESCRIPTION,mDescriptionRoute.getText().toString());
        hashMapRoutes.put(RouteContract.RouteEntry.COLUMN_TOPICS,mTopicsRoute.getText().toString());
        hashMapRoutes.put(RouteContract.RouteEntry.COLUMN_CITY_NAME_INIT, mCityName.getText().toString());
        hashMapRoutes.put(RouteContract.RouteEntry.COLUMN_START_DATE,startDate.toString());
        hashMapRoutes.put(RouteContract.RouteEntry.COLUMN_URL_ROUTE_COVER,filePath);
        hashMapRoutes.put(RouteContract.RouteEntry.COLUMN_MAX_ATTENDEES, mMaxAttendees.getText().toString());
        return hashMapRoutes;
    }

    private void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, "myfolder", true);
        imageChooserManager.setImageChooserListener(this);
        try {
            pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, "myfolder", true);
        imageChooserManager.setImageChooserListener(this);
        try {
            pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else {
            pbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pbar.setVisibility(View.GONE);
                if (image != null) {
                    filePath = image.getFilePathOriginal();
                    imageViewThumbnail.setImageURI(Uri.parse(new File(image
                            .getFileThumbnail()).toString()));
                }
            }
        });
    }

    @Override
    public void onError(final String reason) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pbar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), reason,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    // Should be called if for some reason the ImageChooserManager is null (Due
    // to destroying of activity for low memory situations)
    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType,
                "myfolder", true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("chooser_type", chooserType);
        outState.putString("media_path", filePath);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("chooser_type")) {
                chooserType = savedInstanceState.getInt("chooser_type");
            }

            if (savedInstanceState.containsKey("media_path")) {
                filePath = savedInstanceState.getString("media_path");
            }
        }
        super.onViewStateRestored(savedInstanceState);
    }
}