package it.polito.tdp.nyc.model;

import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

public class LocationPrimario {
	private String location; 
	private LatLng coordinate;
	
	public LocationPrimario(String location, LatLng coordinate) {
		this.location = location;
		this.coordinate = coordinate;	}

	public String getLocation() {
		return location;  }

	public LatLng getCoordinate() {
		return coordinate;  }  
}