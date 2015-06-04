package com.example.sergio.caminando.ui;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sergio.caminando.R;
import com.example.sergio.caminando.endpoints.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.sergio.caminando.util.LogUtils.LOGE;
import static com.example.sergio.caminando.util.LogUtils.makeLogTag;

public class CreateRouteActivity extends BaseActivity {

    private static final String TAG = makeLogTag(CreateRouteActivity.class);

    // How is this Activity being used?
    private static final int MODE_EXPLORE = 0; // as top-level "Explore" screen
    private static final int MODE_TIME_FIT = 1; // showing sessions that fit in a time interval

    private int mMode = MODE_EXPLORE;

    private String mEmailAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routes);

        Toolbar toolbar = getActionBarToolbar();
        toolbar.setTitle(R.string.title_settings);
        toolbar.setNavigationIcon(R.drawable.ic_up);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateUpToFromChild(CreateRouteActivity.this,
                        IntentCompat.makeMainActivity(new ComponentName(CreateRouteActivity.this,
                                BrowseSessionsActivity.class)));
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CreateRouteFragment())
                    .commit();
        }

        mEmailAccount = Utils.getEmailAccount(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_route, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public static class CreateRouteFragment extends Fragment implements View.OnClickListener{

        //Declarate UI Rerferences

        private EditText mRouteName;
        private EditText mCityName;
        private EditText mDescriptionRoute;
        private EditText mTopicsRoute ;
        private EditText mStartDate;
        private ImageButton mImageButtonStartDate;
        private EditText mEndDate;
        private ImageButton mImageButtonEndDate;
        private EditText mMaxAttendees;

        private DatePickerDialog startDatePickerDialog;
        private DatePickerDialog endDatePickerDialog;

        private SimpleDateFormat dateFormatter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_create_route, container, false);

            mRouteName = (EditText) root.findViewById(R.id.route_name_editext);
            mCityName = (EditText) root.findViewById(R.id.route_city_name_editext);
            mDescriptionRoute = (EditText) root.findViewById(R.id.description_route_editext);
            mTopicsRoute = (EditText) root.findViewById(R.id.topics_routes);

            mStartDate = (EditText) root.findViewById(R.id.start_date_route_editext);
            mStartDate.setInputType(InputType.TYPE_NULL);
            mImageButtonStartDate = (ImageButton) root.findViewById(R.id.imageButtonStartDate);

            mEndDate = (EditText) root.findViewById(R.id.end_date_route_editext);
            mEndDate.setInputType(InputType.TYPE_NULL);
            mImageButtonEndDate = (ImageButton) root.findViewById(R.id.imageButtoEndDate);

            mMaxAttendees = (EditText) root.findViewById(R.id.max_attendees_editext);

            return root;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            LOGE(TAG, "onActivityCreated");
            dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

            setDateTimeField();
        }

        private void setDateTimeField() {
            mImageButtonStartDate.setOnClickListener(this);
            mImageButtonEndDate.setOnClickListener(this);


            Calendar newCalendar = Calendar.getInstance();
            startDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    mStartDate.setText(dateFormatter.format(newDate.getTime()));
                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            endDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    mEndDate.setText(dateFormatter.format(newDate.getTime()));
                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        }

        @Override
        public void onClick(View view) {
            LOGE(TAG,"in onclick");
            if(view == mImageButtonStartDate) {

                startDatePickerDialog.show();
            } else if(view == mImageButtonEndDate) {
                endDatePickerDialog.show();
            }
        }
    }


}