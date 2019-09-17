package hr.fer.zemris.java.hw14.model;

/**
 * Class PollOptions defines data structure of poll options.
 * 
 * @author Filip
 */
public class PollOptions {

	private long id;
	private String optionTitle;
	private String optionLink;
	private long pollID;
	private long votesCount;

	/**
	 * Default constructor.
	 */
	public PollOptions() {
	}

	/**
	 * Getter for id.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for optionTitle.
	 * 
	 * @return optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter for optionTitle.
	 * 
	 * @param optionTitle
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter for optionLink.
	 * 
	 * @return optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter for optionLink.
	 * 
	 * @param optionLink
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Getter for pollID.
	 * 
	 * @return pollID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter for pollID.
	 * 
	 * @param pollID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Getter for votesCount.
	 * 
	 * @return votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter for votesCount.
	 * 
	 * @param votesCount
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

}
