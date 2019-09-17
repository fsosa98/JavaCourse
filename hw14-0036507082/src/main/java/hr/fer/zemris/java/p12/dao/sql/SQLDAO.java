package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.hw14.model.PollOptions;
import hr.fer.zemris.java.hw14.model.Polls;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<Polls> getPolls() {
		List<Polls> polls = new ArrayList<Polls>();

		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Polls poll = new Polls();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return polls;
	}

	@Override
	public Polls getPollByID(long id) throws DAOException {
		Polls poll = new Polls();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return poll;
	}

	@Override
	public List<PollOptions> getPollOptions(long id) throws DAOException {
		List<PollOptions> options = new ArrayList<PollOptions>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"select id,optionTitle, optionLink, pollID, votesCount from PollOptions where pollID=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOptions opt = new PollOptions();
						opt.setId(rs.getLong(1));
						opt.setOptionTitle(rs.getString(2));
						opt.setOptionLink(rs.getString(3));
						opt.setPollID(rs.getLong(4));
						opt.setVotesCount(rs.getLong(5));
						options.add(opt);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return options;
	}

}