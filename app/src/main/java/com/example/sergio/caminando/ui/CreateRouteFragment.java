package com.example.sergio.caminando.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sergio.caminando.R;
import com.example.sergio.myapplication.backend.domain.conference.model.ConferenceForm;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.google.api.client.util.DateTime;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by sergio on 30/05/15.
 */
public class CreateRouteFragment extends Fragment implements com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String DATEPICKER_TAG_START = "datepickerstart";
    public static final String DATEPICKER_TAG_END = "datepickerend";
    public static final String TIMEPICKER_TAG_START = "timepickerstart";
    public static final String TIMEPICKER_TAG_END = "timepickerend";

    //Declarate UI Rerferences

    private EditText mRouteName;
    private EditText mCityName;
    private EditText mDescriptionRoute;
    private EditText mTopicsRoute;
    private EditText mStartDate;
    private ImageButton mImageButtonStartDate;
    private ImageButton mImageButtonStartTime;
    private EditText mEndDate;
    private ImageButton mImageButtonEndDate;
    private ImageButton mImageButtonEndTime;
    private EditText mMaxAttendees;

    private SimpleDateFormat dateFormatter;
    private DateTime startDate;
    private DateTime endDate;

    public interface Callbacks {
        public void uploadRoute(ConferenceForm conference);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void uploadRoute(ConferenceForm conference) {}
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

        mRouteName = (EditText) root.findViewById(R.id.route_name_editext);
        mCityName = (EditText) root.findViewById(R.id.route_city_name_editext);
        mDescriptionRoute = (EditText) root.findViewById(R.id.description_route_editext);
        mTopicsRoute = (EditText) root.findViewById(R.id.topics_routes);

        mStartDate = (EditText) root.findViewById(R.id.start_date_route_editext);
        mImageButtonStartDate = (ImageButton) root.findViewById(R.id.imageButtonStartDate);
        mImageButtonStartTime = (ImageButton) root.findViewById(R.id.imageButtonStartTime);

        mEndDate = (EditText) root.findViewById(R.id.end_date_route_editext);
        mImageButtonEndDate = (ImageButton) root.findViewById(R.id.imageButtoEndDate);
        mImageButtonEndTime = (ImageButton) root.findViewById(R.id.imageButtonEndTime);

        mMaxAttendees = (EditText) root.findViewById(R.id.max_attendees_editext);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            DatePickerDialog dpdS = (DatePickerDialog) getFragmentManager().findFragmentByTag(DATEPICKER_TAG_START);
            DatePickerDialog dpdE = (DatePickerDialog) getFragmentManager().findFragmentByTag(DATEPICKER_TAG_END);
            TimePickerDialog tpdS = (TimePickerDialog) getFragmentManager().findFragmentByTag(TIMEPICKER_TAG_START);
            TimePickerDialog tpdE = (TimePickerDialog) getFragmentManager().findFragmentByTag(TIMEPICKER_TAG_END);

            if (dpdS != null) {
                dpdS.setOnDateSetListener(this);
            }
            if (dpdE != null) {
                dpdE.setOnDateSetListener(this);
            }
            if (tpdS != null) {
                tpdS.setOnTimeSetListener(this);
            }
            if (tpdE != null) {
                tpdE.setOnTimeSetListener(this);
            }
        }
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        setDateTimeField();
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


        mImageButtonEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(true); //Add vibrate
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG_END);
            }
        });

        mImageButtonEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.setVibrate(true);
                timePickerDialog.setCloseOnSingleTapMinute(false);
                timePickerDialog.show(getFragmentManager(), TIMEPICKER_TAG_END);
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
                startDate = new DateTime(newDate.getTime());
                break;
            case DATEPICKER_TAG_END:
                Toast.makeText(getActivity(), "End date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
                newDate.set(year, month, day);
                mEndDate.setText(dateFormatter.format(newDate.getTime()));
                endDate = new DateTime(newDate.getTime());
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
        if (id == R.id.menu_create){
            createRoute();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createRoute(){

        //Implememt anything to check form
        mCallbacks.uploadRoute(getDataConference());
        getActivity().finish();
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
        conferenceForm.setEndDate(endDate);
        conferenceForm.setMaxAttendees(Integer.parseInt(mMaxAttendees.getText().toString()));
        return conferenceForm;
    }

}