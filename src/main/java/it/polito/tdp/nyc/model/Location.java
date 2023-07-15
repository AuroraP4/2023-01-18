package it.polito.tdp.nyc.model;

import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

public class Location {
	
	private String location; 
	private List<LatLng> listaCoordinate;
	
	public Location(String location, List<LatLng> listaCoordinate) {
		super();
		this.location = location;
		this.listaCoordinate = listaCoordinate;
	}

	public String getLocation() {
		return location;
	}

	public List<LatLng> getListaCoordinate() {
		return listaCoordinate;
	}

	@Override
	public String toString() {
		return "Location [location=" + location + ", listaCoordinate=" + listaCoordinate + "]";
	} 
	
	public LatLng calcolaLatLngMedia() {
		double lat = 0; 
		double lng = 0; 
		for(LatLng l: this.listaCoordinate) {
			lat += l.getLatitude(); 
			lng += l.getLongitude(); 
		}
		
		double latMedia = lat/this.listaCoordinate.size(); 
		double lngMedia = lng/this.listaCoordinate.size();
		
		return new LatLng(latMedia, lngMedia); }
	
}