package com.example.sergio.caminando.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.utils.ConferenceException;
import com.example.sergio.caminando.endpoints.utils.ConferenceUtils;
import com.example.sergio.caminando.endpoints.utils.StorageException;
import com.example.sergio.caminando.endpoints.utils.StorageUtils;
import com.example.sergio.caminando.util.AccountUtils;
import com.example.sergio.myapplication.backend.domain.conference.model.Conference;
import com.example.sergio.myapplication.backend.domain.conference.model.ConferenceForm;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    private TextView textViewFile;
    private ImageChooserManager imageChooserManager;
    private ProgressBar pbar;
    private String filePath;
    private int chooserType;

    // Url object public link
    private String mUrlPhotoLink;

    private CreateRouteTask mAuthTask;
    private UploadPhotoTask mAuthPhoto;

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
        textViewFile = (TextView) root.findViewById(R.id.textViewFile);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_create_route, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //To create route
        if (id == R.id.menu_create) {
            uploadRoute();
        }

        return super.onOptionsItemSelected(item);
    }

    private void uploadRoute(){

        // TODO: Cambiar unresponsabilidad mientras sube ruta. Volver rapido a fragment_routes
        // Cancel previously running ROUTE tasks.
        if (mAuthTask != null) {
            mAuthTask.cancel(true);
        }
        // Cancel previously running PHOTO tasks.
        if (mAuthPhoto != null) {
            mAuthPhoto.cancel(true);
        }

        // Start task to upload photo
        mAuthPhoto = new UploadPhotoTask();
        mAuthPhoto.execute("photos_routes",filePath);

        // Start task to check authorization.
        mAuthTask = new CreateRouteTask();

    }

    private ConferenceForm getDataConference(){
        //TODO: FIX listTopics to catch List<String>
        List<String> listTopics = new ArrayList<>();
        listTopics.add(mTopicsRoute.getText().toString());

        ConferenceForm conferenceForm = new ConferenceForm();

        conferenceForm.setName(mRouteName.getText().toString());
        conferenceForm.setDescription(mDescriptionRoute.getText().toString());
        conferenceForm.setTopics(listTopics);
        conferenceForm.setCity(mCityName.getText().toString());
        conferenceForm.setStartDate(startDate);
        conferenceForm.setUrlPhotoCover(mUrlPhotoLink);
        conferenceForm.setMaxAttendees(Integer.parseInt(mMaxAttendees.getText().toString()));
        return conferenceForm;
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
                    textViewFile.setText(image.getFilePathOriginal());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAuthTask != null) {
            mAuthTask.cancel(true);
            mAuthTask = null;
        }

        if (mAuthPhoto != null) {
            mAuthPhoto.cancel(true);
            mAuthPhoto = null;
        }
    }

    public class CreateRouteTask extends AsyncTask<ConferenceForm, Integer, Conference> {
        @Override
        protected Conference doInBackground(ConferenceForm... conferenceForms) {

            Log.i(TAG, "Background task started.");

            ConferenceForm conferenceForm = conferenceForms[0];
            // Ensure only one task is running at a time.
            mAuthTask = this;

            // Authorization check successful, get conferences.
            ConferenceUtils.build(getActivity(), AccountUtils.getActiveAccountName(getActivity()));

            try {
                ConferenceUtils.getProfile();
                return ConferenceUtils.createConference(conferenceForm);
            } catch (IOException e) {
                Log.e(TAG, "Failed to create Route", e);
            } catch (ConferenceException e) {
                // logged
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            mAuthTask = this;
        }
        @Override
        protected void onPostExecute(Conference conference) {
            Toast.makeText(getActivity(),"Upload Route",Toast.LENGTH_LONG).show();
            mAuthTask = null;
            getActivity().finish();
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public class UploadPhotoTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            mAuthPhoto = this;
            String urlPath = null;
            try {
                StorageUtils.build(getActivity());
                urlPath = StorageUtils.uploadPhotoToBucket(params[0],params[1]);
            } catch (IOException e) {
                Log.e(TAG, "Failed to create Route", e);
            } catch (StorageException e) {
                // logged
            } catch (Exception e){
                // create build to Storage
                Log.e(TAG, "Failed to create credential",e);
            }
            return urlPath;
        }

        @Override
        protected void onPostExecute(String urlPublicLink) {
            super.onPostExecute(urlPublicLink);
            Toast.makeText(getActivity(),"Foto subida: " + urlPublicLink ,Toast.LENGTH_LONG).show();
            mUrlPhotoLink = urlPublicLink;
            // Now I have mUrlPhotoLink to add in getDataConference()
            mAuthTask.execute(getDataConference());
        }

        @Override
        protected void onPreExecute() {
            mAuthPhoto = this;
        }
        @Override
        protected void onCancelled() {
            mAuthPhoto = null;
        }
    }
}