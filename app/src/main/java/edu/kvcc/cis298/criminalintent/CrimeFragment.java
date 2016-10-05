package edu.kvcc.cis298.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class CrimeFragment
	extends Fragment
{
	private Crime crime;
	private EditText etTitle;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		crime = new Crime("test");
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									 @Nullable Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_crime, container, false);
		etTitle = (EditText) v.findViewById(R.id.fragment_crime_et_title);
		etTitle.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
				// do nothing
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				crime.setTitle(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				// do nothing
			}
		});
		return v;
	}
}
