package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import connection.ConnectionFactory;
import domain.ContaBanco;

public class ContaDAO {

	public static void create(ContaBanco acc) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO accounts (number, owner, type, balance) values (?,?,?,?)");
			stmt.setString(1, acc.getNumConta());
			stmt.setString(2, acc.getNomeDono());
			stmt.setString(3, acc.getTipoConta().name());
			stmt.setDouble(4, acc.getSaldo());
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Registrado com sucesso");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERRO AO REGISTRAR: "+e);
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
	}
	
	public List<ContaBanco> read () {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ContaBanco> accounts = new ArrayList<>();
		try {
			stmt = con.prepareStatement("Select * from accounts");
			rs = stmt.executeQuery();
			while (rs.next()) {
				ContaBanco acc = new ContaBanco();
				acc.setNomeDono(rs.getString("owner"));
				acc.setNumConta(rs.getString("number"));
				acc.setTipoConta(rs.getString("type"));
				acc.setSaldo(rs.getDouble("balance"));
				accounts.add(acc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionFactory.closeConnection(con, stmt, rs);
		}
		return accounts;
	}
}