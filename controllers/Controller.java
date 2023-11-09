package controllers;

import java.util.Map.Entry;

import types.CampAspects;

public interface Controller {

	public Controller FilterUser(String userid); //
	public Controller FilterCamp(int campid); //adds user filter
	public Controller FilterAspect(Entry<CampAspects,? extends Object> filter);
}
