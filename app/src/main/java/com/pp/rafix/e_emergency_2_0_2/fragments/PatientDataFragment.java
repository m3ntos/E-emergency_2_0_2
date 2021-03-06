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
import android.widget.Toast;

import com.pp.rafix.e_emergency_2_0_2.EemergencyAplication;
import com.pp.rafix.e_emergency_2_0_2.R;
import com.pp.rafix.e_emergency_2_0_2.models.PatientModel;
import com.pp.rafix.e_emergency_2_0_2.models.SolrModel;
import com.pp.rafix.e_emergency_2_0_2.rest.RestService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    @InjectView(R.id.radioGroupTransport) RadioGroup transport;

    ArrayAdapter<SolrModel> destinationSORAdapter;

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

        destinationSORAdapter =  new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, EemergencyAplication.getSorList());
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
        model.setDestinationSor(destinationSORAdapter.getItem(destinationSOR.getSelectedItemPosition()));

        switch (transport.getCheckedRadioButtonId()){
            case R.id.buttonNoTransport:
                model.setNoTransport(true);
                break;
            case R.id.buttonArrival:
                model.setArrival(true);
                break;
            default:
        }

        Toast.makeText(getActivity(),"Zapisano", Toast.LENGTH_SHORT).show();

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

    @OnClick(R.id.buttonIdentify)
    public void identify() {

        RestService service = EemergencyAplication.getRestClient().getRestService();

        PatientModel.getInstance().setPESEL(PESEL.getText().toString());
        PatientModel.getInstance().setInsuranceNumber(insuranceNr.getText().toString());

        service.getPatientData(PatientModel.getInstance(), new Callback<PatientModel>() {
            @Override
            public void success(PatientModel patientModel, Response response) {
                //Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();

                //set fields
                firstName.setText(patientModel.getFirstName());
                lastName.setText(patientModel.getLastName());
                age.setText(patientModel.getAge());

                if ("M".equals(patientModel.getSex())) sex.check(R.id.radioButtonMan);
                if ("F".equals(patientModel.getSex())) sex.check(R.id.radioButtonWoman);

                phoneNr.setText(patientModel.getPhoneNr());
                PESEL.setText(patientModel.getPESEL());
                insuranceNr.setText(patientModel.getInsuranceNumber());
            }

            @Override
            public void failure(RetrofitError error) {
                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });
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
