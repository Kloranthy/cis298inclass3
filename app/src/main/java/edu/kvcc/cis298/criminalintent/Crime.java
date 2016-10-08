package edu.kvcc.cis298.criminalintent;


import java.util.Date;
import java.util.UUID;

public class Crime
{
	// private variables
	private UUID    id;
	private String  title;
	private Date    date;
	private boolean isSolved;

	// public constructors
	public Crime()
	{
		this.id = UUID.randomUUID();
		date = new Date();
	}

	// public methods
	public UUID getId()
	{
		return id;
	}


	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public boolean isSolved()
	{
		return isSolved;
	}

	public void setSolved(boolean solved)
	{
		isSolved = solved;
	}
}
