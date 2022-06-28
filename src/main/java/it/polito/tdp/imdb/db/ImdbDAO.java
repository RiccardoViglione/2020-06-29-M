package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void listAllDirectors(Map<Integer,Director>idMap){
		String sql = "SELECT * FROM directors";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
if(!idMap.containsKey(res.getInt("id"))) {
				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
idMap.put(director.getId(), director);
}
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		throw new RuntimeException("SQl error");
		}
	}
	
	public List<Director> getVertici(int anno,Map<Integer,Director>idMap){
		String sql = "select distinct director_id as id "
				+ "from `movies_directors`m,movies mo "
				+ "where m.`movie_id`=mo.`id`and mo.`year`=? "
				+ "order by director_id ";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {

				
				
				result.add(idMap.get(res.getInt("id")));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQl error");
		}
	}
	
	
	
	public List<Adiacenza> getArchi(int anno,Map<Integer,Director>idMap){
		String sql = "select distinct m.director_id as id1,m2.`director_id` as id2,count(*)as peso "
				+ "from `movies_directors`m,movies mo,`movies_directors`m2,movies mo2,roles r,roles r2 "
				+ "where m.`movie_id`=mo.`id`and mo.`year`=? and m2.`movie_id`=mo2.`id`and mo2.`year`=? and m.`director_id`>m2.`director_id`and mo.id=r.`movie_id`and mo2.id=r2.`movie_id`and r.`actor_id`=r2.`actor_id` "
				+ "group by  m.director_id ,m2.`director_id` ";
	
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				
				result.add(new Adiacenza(idMap.get(res.getInt("id1")),idMap.get(res.getInt("id2")),res.getInt("peso")));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQl error");
		}
	}
	
	
	
}
