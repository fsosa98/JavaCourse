package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Class Inicijalizacija is initialization class. Creates polls tables if they
 * not exists. Fills that tables with data.
 * 
 * @author Filip
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties properties = new Properties();
		InputStream is;
		String host = null;
		String port = null;
		String dbName = null;
		String user = null;
		String password = null;
		try {
			is = Files.newInputStream(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")));
			properties.load(is);
			host = properties.getProperty("host");
			port = properties.getProperty("port");
			dbName = properties.getProperty("name");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (host == null || port == null || dbName == null || user == null || password == null) {
			throw new RuntimeException("Invalid dbsettings.");
		}

		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";" + "user=" + user + ";password="
				+ password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		
		cpds.setJdbcUrl(connectionURL);
		try {
			ResultSet resultSet = cpds.getConnection().getMetaData().getTables(null, null, null, null);
			boolean exsist = false;
			while (resultSet.next()) {
				if (resultSet.getString("TABLE_NAME").equals("POLLS")) {
					exsist = true;
					break;
				}
			}
			if (!exsist) {
				createTablePolls(cpds.getConnection());
			}

			resultSet = cpds.getConnection().getMetaData().getTables(null, null, null, null);
			exsist = false;
			while (resultSet.next()) {
				if (resultSet.getString("TABLE_NAME").equals("POLLOPTIONS")) {
					exsist = true;
					break;
				}
			}
			if (!exsist) {
				createTablePollOptions(cpds.getConnection());
			}

			PreparedStatement pst = null;
			try {
				pst = cpds.getConnection().prepareStatement("SELECT * FROM POLLS");
				ResultSet rset = pst.executeQuery();
				try {
					boolean empty = true;
					while (rset.next()) {
						empty = false;
						break;
					}
					if (empty) {
						PreparedStatement ps = cpds.getConnection().prepareStatement(
								"INSERT INTO Polls (title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, "Glasanje za omiljeni bend:");
						ps.setString(2,
								"Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
						ps.executeUpdate();
						String fileName1 = sce.getServletContext().getRealPath("/WEB-INF/glasanje-definicija1.txt");
						String fileName2 = sce.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati1.txt");
						tableInsert(fileName1, fileName2, cpds.getConnection(), 1);

						PreparedStatement ps2 = cpds.getConnection().prepareStatement(
								"INSERT INTO Polls (title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS);
						ps2.setString(1, "Glasanje za omiljeni klub:");
						ps2.setString(2,
								"Od sljedećih klubova, koji Vam je klub najdraži? Kliknite na link kako biste glasali!");
						ps2.executeUpdate();
						fileName1 = sce.getServletContext().getRealPath("/WEB-INF/glasanje-definicija2.txt");
						fileName2 = sce.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati2.txt");
						tableInsert(fileName1, fileName2, cpds.getConnection(), 2);
					}
				} finally {
					try {
						rset.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					pst.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			try {
				cpds.getConnection().close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	/**
	 * Helper method for filling tables with given data.
	 * 
	 * @param fileName
	 * @param fileName2
	 * @param connection
	 * @param pollID
	 * @throws IOException
	 * @throws SQLException
	 */
	private void tableInsert(String fileName, String fileName2, Connection connection, long pollID)
			throws IOException, SQLException {
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<String> results = Files.readAllLines(Paths.get(fileName2));
		Map<String, String> resultMap = new HashMap<String, String>();
		results.forEach((r) -> {
			String[] parts = r.split("\t");
			resultMap.put(parts[0], parts[1]);
		});
		for (String line : lines) {
			String[] parts = line.split("\t");
			PreparedStatement pst = null;
			try {
				pst = connection.prepareStatement(
						"INSERT INTO PollOptions (optionTitle, optionLink,pollID,votesCount) values (?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, parts[1]);
				pst.setString(2, parts[2]);
				pst.setLong(3, pollID);
				pst.setLong(4, Long.parseLong(resultMap.get(parts[0])));
				pst.executeUpdate();

			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				try {
					pst.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * Helper method for creating table poll.
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	private void createTablePolls(Connection connection) throws SQLException {
		connection.createStatement()
				.execute("CREATE TABLE Polls" + "    (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
						+ "    title VARCHAR(150) NOT NULL," + "    message CLOB(2048) NOT NULL)");
	}

	/**
	 * Helper method for creating table poll.
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	private void createTablePollOptions(Connection connection) throws SQLException {
		connection.createStatement()
				.execute("CREATE TABLE PollOptions" + "    (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
						+ "    optionTitle VARCHAR(100) NOT NULL," + "    optionLink VARCHAR(150) NOT NULL,"
						+ "    pollID BIGINT,    votesCount BIGINT,"
						+ "    FOREIGN KEY (pollID) REFERENCES Polls(id))");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}