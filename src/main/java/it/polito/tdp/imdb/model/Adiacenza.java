package it.polito.tdp.imdb.model;

public class Adiacenza implements Comparable<Adiacenza>{
private Director d1;
private Director d2;
private int peso;
public Adiacenza(Director d1, Director d2, int peso) {
	super();
	this.d1 = d1;
	this.d2 = d2;
	this.peso = peso;
}

public Adiacenza(Director d1, int peso) {
	super();
	this.d1 = d1;
	this.peso = peso;
}

public Director getD1() {
	return d1;
}
public void setD1(Director d1) {
	this.d1 = d1;
}
public Director getD2() {
	return d2;
}
public void setD2(Director d2) {
	this.d2 = d2;
}
public int getPeso() {
	return peso;
}
public void setPeso(int peso) {
	this.peso = peso;
}
@Override
public String toString() {
	return this.d1.getId()+"-"+this.d1.getFirstName()+"-"+this.d1.getLastName()+"- # attori condivisi: "+this.getPeso();
}
@Override
public int compareTo(Adiacenza a) {
	return a.getPeso()-this.getPeso();
}
}