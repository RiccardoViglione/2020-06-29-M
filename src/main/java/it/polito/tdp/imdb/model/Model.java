package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
private ImdbDAO dao;
private Map<Integer,Director>idMap;
private Graph<Director,DefaultWeightedEdge>grafo;

public Model() {
	dao=new ImdbDAO();
	idMap=new HashMap<>();
	dao.listAllDirectors(idMap);
}
	public void CreaGrafo(int anno) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	Graphs.addAllVertices(this.grafo, this.dao.getVertici(anno, idMap));
	for(Adiacenza a:this.dao.getArchi(anno, idMap)) {
		Graphs.addEdgeWithVertices(this.grafo, a.getD1(), a.getD2(),a.getPeso());
	}
	}
	public List<Director> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	public List<Adiacenza>getAdiacenti(Director d ){
		List<Director>vicini=Graphs.neighborListOf(grafo, d);
		List<Adiacenza>result=new ArrayList<>();
		for(Director di:vicini) {
			result.add(new Adiacenza(di,(int) this.grafo.getEdgeWeight(this.grafo.getEdge(d, di))));
			
		}
	Collections.sort(result);
	return result;
	}
	
}
