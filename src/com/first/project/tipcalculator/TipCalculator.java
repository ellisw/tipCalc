package com.first.project.tipcalculator;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TipCalculator extends Activity {
	private double billAmount;
	private TextView tvResult;
	private TextView tvTotalResult;
	private TextView tvTipPercentageResult;
	private EditText editTextView;
	private SeekBar sb_tip_slider;
	private double tip_percentage;
	private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0.00");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calculator);

		tvResult = (TextView) findViewById(R.id.tv_result);
		tvTotalResult = (TextView) findViewById(R.id.tv_total_result);
		tvTipPercentageResult = (TextView) findViewById(R.id.tv_tip_percentage_result);
		
		editTextView = (EditText) findViewById(R.id.etv_total_bill);
		setUpButton(R.id.button_10p, 10);
		setUpButton(R.id.button_15p, 15);
		setUpButton(R.id.button_20p, 20);
		
		sb_tip_slider = (SeekBar) findViewById(R.id.sb_tip_slider);
		sb_tip_slider.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				calcResults(seekBar.getProgress() + 10);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				calcResults(seekBar.getProgress() + 10);
			}
		});
	}

	private void setUpButton(int resource, int percentage) {
		Button button = (Button) findViewById(resource);
		button.setOnClickListener(new TipOnClickListener(percentage));		
	}

	private void updateTotalBill(){
		String editTextViewResult = editTextView.getText().toString();
		if (editTextViewResult.length() > 0)
		    billAmount = Double.parseDouble(editTextViewResult);
	}
	
	private void calcResults(int percentage){
		tip_percentage = percentage;
		sb_tip_slider.setProgress(percentage - 10);
		tvTipPercentageResult.setText(tip_percentage + "%");
		updateTotalBill();
		double tipAmount = billAmount * (tip_percentage / 100.0);
		double totalAmount = billAmount + tipAmount;
		if(tipAmount > 0 && totalAmount > 0){
			tvResult.setText("$" + REAL_FORMATTER.format(tipAmount));
			tvTotalResult.setText("$" + REAL_FORMATTER.format(totalAmount));
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_calculator, menu);
		return true;
	}

	private class TipOnClickListener implements OnClickListener {
		private int percentage;
		
		public TipOnClickListener(int percentage) {
			this.percentage = percentage;
		}
		
		@Override
		public void onClick(View v) {
			calcResults(percentage);
		}
	}


}
