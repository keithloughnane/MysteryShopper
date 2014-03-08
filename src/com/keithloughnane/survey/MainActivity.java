package com.keithloughnane.survey;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.os.SystemClock;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	private int[] checklistValues = new int[11];

	CheckBox friendlyCheckBox;
	CheckBox specialsCheckBox;
	CheckBox optionsCheckBox;

	RadioGroup availableRadioGroup;
	RadioButton availableBadRadio;
	RadioButton availableOKRadio;
	RadioButton availableGoodRadio;

	Spinner problemSpinner;

	Button startChronometerButton;
	Button pauseChronometerButton;
	Button resetChronometerButton;

	Chronometer timeWaitingChronomter;

	//long secondsYouWaited = 0;

	TextView timeWaitingTextView;

	TextView scoreTextView;

	long secondsYouWaited = 0;

	private void updateScoreBasedOnTimeWaited(long secondsYouWaited)
	{
		checklistValues[9] = (secondsYouWaited > 10)?-2:2;

		setChecklist();
		updateScore();
	}

	private void setChecklist()
	{

		//VatET.setText(String.format("%.02f", iVAT));


		int checklistTotal = 0;
		for(int item: checklistValues)
			checklistTotal += item;
		Log.d("TempDebug", "n1");
		Log.d("TempDebug", "n2");
		scoreTextView.setText(String.format("%.02f", (double)checklistTotal));
		Log.d("TempDebug", "n3");
	}

	public void updateScore()
	{

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		scoreTextView= (TextView) findViewById(R.id.scoreTextView);

		friendlyCheckBox = (CheckBox)findViewById(R.id.friendlyCheckBox);
		specialsCheckBox= (CheckBox)findViewById(R.id.specialCheckBox);
		optionsCheckBox= (CheckBox)findViewById(R.id.optionCheckBox);

		setUpIntroCheckBoxes();

		availableRadioGroup = (RadioGroup)findViewById(R.id.availbleRadioGroup);

		availableBadRadio = (RadioButton)findViewById(R.id.availableBadRadio);
		availableOKRadio = (RadioButton)findViewById(R.id.availableOKRadio);
		availableGoodRadio = (RadioButton)findViewById(R.id.availableGoodRadio);

		addChangeListenerToRadios();


		problemSpinner = (Spinner)findViewById(R.id.problemsSpinner);

		addItemSelectedListenerToSpinner();

		startChronometerButton = (Button) findViewById(R.id.startChronometerButton);
		pauseChronometerButton = (Button) findViewById(R.id.pauseChonometerButton);
		resetChronometerButton = (Button) findViewById(R.id.resetChronometerButton);

		setButtonClickListeners();

		Log.d("TempDebug", "1");
		timeWaitingChronomter = (Chronometer)findViewById(R.id.timeWaitingChronometer);
		Log.d("TempDebug", "2");
		timeWaitingTextView = (TextView) findViewById(R.id.timeWaitingTextView);

		return true;
	}

	private void setUpIntroCheckBoxes()
	{
		friendlyCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				checklistValues[0] = (friendlyCheckBox.isChecked())?4:0;

				setChecklist();
				updateScore();
			}
		});

		specialsCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				checklistValues[1] = (specialsCheckBox.isChecked())?4:0;

				setChecklist();
				updateScore();
			}
		});

		optionsCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				checklistValues[2] = (optionsCheckBox.isChecked())?4:0;

				setChecklist();
				updateScore();
			}
		});
	}

	private void addChangeListenerToRadios()
	{
		availableRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				checklistValues[3] = (availableBadRadio.isChecked())?-1:0;
				checklistValues[4] = (availableOKRadio.isChecked())?2:0;
				checklistValues[5] = (availableGoodRadio.isChecked())?4:0;

				setChecklist();
				updateScore();

			}

		});

	}


	public void addItemSelectedListenerToSpinner()
	{
		problemSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {


				checklistValues[6] = (problemSpinner.getSelectedItem()).equals("Bad")?-1:0;
				checklistValues[7] = (problemSpinner.getSelectedItem()).equals("OK")?3:0;
				checklistValues[8] = (problemSpinner.getSelectedItem()).equals("Good")?6:0;
				setChecklist();
				updateScore();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setButtonClickListeners()
	{
		startChronometerButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				int stoppedMilliseconds = 0;
				String chronoText = timeWaitingChronomter.getText().toString();
				String array[] = chronoText.split(":");
				if(array.length == 2){
					stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000 + 
							Integer.parseInt(array[1]) * 1000;
				}
				else if (array.length == 3){
					stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 100+
							Integer.parseInt(array[1]) * 60 * 1000 +
							Integer.parseInt(array[2]) * 1000;
				}
				timeWaitingChronomter.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
				secondsYouWaited = Long.parseLong(array[1]);
				updateScoreBasedOnTimeWaited(secondsYouWaited);
				timeWaitingChronomter.start();
			}
		});
		pauseChronometerButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {

				timeWaitingChronomter.stop();
			}
		});
		resetChronometerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				timeWaitingChronomter.setBase(SystemClock.elapsedRealtime());
				secondsYouWaited = 0;
			}

		});
		}
	}

