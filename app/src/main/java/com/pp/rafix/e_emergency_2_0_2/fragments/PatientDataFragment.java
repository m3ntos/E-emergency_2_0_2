package com.pp.rafix.e_emergency_2_0_2.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.pp.rafix.e_emergency_2_0_2.R;
import com.pp.rafix.e_emergency_2_0_2.models.PatientModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PatientDataFragment extends Fragment {

    @InjectView(R.id.editTextName) EditText firstName;
    @InjectView(R.id.editTextSurname) EditText lastName;
    @InjectView(R.id.checkBoxNoName) CheckBox noName;
    @InjectView(R.id.editTextAge) EditText age;
    @InjectView(R.id.radioGroupSex) RadioGroup sex;
    @InjectView(R.id.editTextPhoneNr) EditText phoneNr;
    @InjectView(R.id.editTextPesel) EditText PESEL;
    @InjectView(R.id.editTextInsuranceNr) EditText insuranceNr;
    @InjectView(R.id.checkBoxAgreementOnHelp) CheckBox agreementOnHelp;
    @InjectView(R.id.editTextDate) EditText date;
    @InjectView(R.id.editTextTime) EditText time;
    @InjectView(R.id.spinnerDestinationSOR) Spinner destinationSOR;

    ArrayAdapter<CharSequence> destinationSORAdapter;

    public PatientDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_data, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //set destination SOR list
        //TODO: set real list

        // Create an ArrayAdapter using the string array and a default spinner layout
        destinationSORAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sor_array, android.R.layout.simple_spinner_item);

        destinationSORAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationSOR.setAdapter(destinationSORAdapter);

    }

    @OnClick(R.id.buttonSaveChanges)
    public void submitChanges() {

        PatientModel model = PatientModel.getInstance();

        model.setFirstName(firstName.getText().toString());
        model.setLastName(lastName.getText().toString());
        model.setNoName(noName.isChecked());
        model.setAge(age.getText().toString());

        switch (sex.getCheckedRadioButtonId()){
            case R.id.radioButtonMan:
                model.setSex("M");
                break;
            case R.id.radioButtonWoman:
                model.setSex("F");
                break;
            default:
        }

        model.setPhoneNr(phoneNr.getText().toString());
        model.setPESEL(PESEL.getText().toString());
        model.setInsuranceNumber(insuranceNr.getText().toString());
        model.setAgreementOnAsistance(agreementOnHelp.isChecked());
        model.setHelpDate(date.getText().toString());
        model.setHelpTime(time.getText().toString());
        model.setDestinationSor(destinationSORAdapter.getItem(destinationSOR.getSelectedItemPosition()).toString());
    }

    @OnClick(R.id.editTextDate)
    public void chooseDate() {

        DialogFragment newFragment = new DatePickerFragment(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                date.setText(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
            }
        };
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @OnClick(R.id.editTextTime)
    public void chooseTime() {

        DialogFragment newFragment = new TimePickerFragment(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                time.setText(new SimpleDateFormat("kk:mm").format(c.getTime()));
            }
        };
        newFragment.show(getFragmentManager(), "timePicker");

    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the date chosen by the user
        }
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
