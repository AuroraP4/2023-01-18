package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private Graph<Location, DefaultWeightedEdge> grafo; 
	private List<LocationPrimario> location;
	private List<Location> vertici; 
	private NYCDao dao; 
	private Map<String, Location> mappaLocation;
	
	
	public Model() {
		this.dao = new NYCDao(); } 
	
	public List<String> getAllProvider(){	
		return this.dao.getAllProvider(); }
	
	public void creaGrafo (double x, String provider) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.location = this.dao.getLocationByProvider(provider);
		this.vertici = new ArrayList<>();
		this.mappaLocation = new HashMap<>(); 
		
		for(LocationPrimario l: location) {
			if(mappaLocation.get(l.getLocation()) == null) {
				List<LatLng> lat = new ArrayList<>(); 
				lat.add(l.getCoordinate());
				Location loc = new Location(l.getLocation(), lat);
				mappaLocation.put(l.getLocation(), loc);	
			}
			else {
				mappaLocation.get(l.getLocation()).getListaCoordinate().add(l.getCoordinate());  
			}
		}
		
		Graphs.addAllVertices(this.grafo, this.mappaLocation.values()); 
		
		System.out.println(this.grafo.vertexSet().size());
		
//		for(Location l: this.grafo.vertexSet()) {
//			System.out.println(l.toString());
//		}
		
		for(Location l1: this.mappaLocation.values()) {
			for(Location l2: this.mappaLocation.values()) {
				if(!l1.getLocation().equals(l2.getLocation())) {
					double distanza = LatLngTool.distance(l1.calcolaLatLngMedia(), l2.calcolaLatLngMedia(), LengthUnit.KILOMETER); 
					if(distanza <= x) {
						Graphs.addEdgeWithVertices(this.grafo, l1, l2, distanza); 
					}
				}
			}
		}
		
		System.out.println(this.grafo.edgeSet().size());
	}
	
	
	public Map<Location, Integer> analizzaGrafo(){
		int numMax =0;
		Map<Location, Integer> mappa = new HashMap<>(); 
		Map<Location, Integer> result = new HashMap<>();
		
		for(Location l: this.grafo.vertexSet()) {
			int numVicini = Graphs.neighborListOf(this.grafo, l).size();
			mappa.put(l, numVicini); 
			if(numVicini > numMax ) {
				numMax = numVicini; }
			}
		
		for(Location l: mappa.keySet()) {
			if(mappa.get(l) == numMax) {
				result.put(l, numMax); 	}
			}	
		return result; }
	
	public int nVertici() {
		return this.grafo.vertexSet().size(); }
	
	public int nArchi() {
		return this.grafo.edgeSet().size();  }
	
}