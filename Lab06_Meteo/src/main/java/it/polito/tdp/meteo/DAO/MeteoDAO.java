package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data").toLocalDate(), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
			List<Rilevamento> result=new ArrayList<Rilevamento>();
			final String sql= "SELECT Data, Umidita "
					+ "FROM situazione "
					+ "WHERE MONTH(DATA)=?  AND localita=? "
					+ "ORDER BY DATA ";
			
			try {
				Connection conn=ConnectDB.getConnection();
				PreparedStatement st=conn.prepareStatement(sql);
				st.setInt(1, mese);
				st.setString(2, localita);
				ResultSet rs=st.executeQuery();
				while(rs.next()) {
					Rilevamento r=new Rilevamento(localita, rs.getDate("Data").toLocalDate(), rs.getInt("Umidita"));
					result.add(r);
				}
				rs.close();
				st.close();
				conn.close();
				return result;
			}catch(SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		
	}

	public List<Citta> getAllCitta(int mese){
		List<Citta> result=new ArrayList<Citta>();
		final String sql="SELECT DISTINCT Localita FROM situazione";
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				Citta c=new Citta(rs.getString("Localita"), this.getAllRilevamentiLocalitaMese(mese, rs.getString("Localita")));
				result.add(c);
			}
			rs.close();
			st.close();
			conn.close();
			return result;
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public Map<String, Double> getTempMedie(int mese){
		Map<String, Double> result=new HashMap<String, Double>();
		final String sql= "SELECT Localita, avg( Umidita) AS media "
				+ "FROM situazione "
				+ "WHERE MONTH(DATA)=? "
				+ "GROUP BY localita ";
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				result.put(rs.getString("localita"), rs.getDouble("media"));
			}
			rs.close();
			st.close();
			conn.close();
			return result;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
