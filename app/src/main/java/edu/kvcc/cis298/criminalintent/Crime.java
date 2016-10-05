package edu.kvcc.cis298.criminalintent;


import java.util.UUID;

public class Crime
{
	private UUID id;
	private String title;

	public Crime(String title)
	{
		this.id = UUID.randomUUID();
		this.title = title;
	}

	public UUID getId()
	{
		return id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}
}
