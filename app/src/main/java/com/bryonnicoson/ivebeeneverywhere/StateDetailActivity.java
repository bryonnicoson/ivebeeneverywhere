package com.bryonnicoson.ivebeeneverywhere;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class StateDetailActivity extends AppCompatActivity {

    public TextView abbreviation;
    public TextView area;
    public TextView capital;
    public ImageView flag;
    public TextView mostPopulousCity;
    public TextView name;
    public TextView population;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        abbreviation = (TextView) findViewById(R.id.detail_abbreviation);
        area = (TextView) findViewById(R.id.detail_area);
        capital = (TextView) findViewById(R.id.detail_capital);
        flag = (ImageView) findViewById(R.id.detail_flag);
        mostPopulousCity = (TextView) findViewById(R.id.detail_mostPopulousCity);
        name = (TextView) findViewById(R.id.detail_name);
        population = (TextView) findViewById(R.id.detail_population);

        State state = (State) getIntent().getSerializableExtra("STATE");

        abbreviation.setText(state.getAbbreviation());
        area.setText(String.format(Locale.ENGLISH, "%,d sq. mi.", state.getArea()));
        capital.setText(state.getCapital());
        Picasso.with(getBaseContext())
                .load(state.getFlagURL())
                .placeholder(R.drawable.jc)
                .resize(600,600)                // maintain
                .centerInside()                 // aspect ratio
                .into(flag);
        mostPopulousCity.setText(state.getMostPopulousCity());
        name.setText(state.getName());
        population.setText(String.format(Locale.ENGLISH, "%,d", state.getPopulation()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void beenThere(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }
}
