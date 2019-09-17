package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.PollOptions;
import hr.fer.zemris.java.hw14.model.Polls;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {

	/**
	 * Polls getter.
	 * 
	 * @return
	 */
	List<Polls> getPolls();

	/**
	 * PollOptions getter
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public List<PollOptions> getPollOptions(long id) throws DAOException;

	/**
	 * Getter for poll with given id.
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Polls getPollByID(long id) throws DAOException;

}