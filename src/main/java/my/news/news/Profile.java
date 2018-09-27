package my.news.news;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
@SuppressWarnings("serial")
public class Profile implements Serializable, Cloneable {

	

	private String name= "";

	private String topic = "";

	private ArrayList<String> words = null;
	private ArrayList<Sources> sources = null;
	private List<Sources> sourcesUserList = null;


	public List<Sources> getSourcesUserList() {
		return sourcesUserList;
	}

	public void setSourcesUserList(List<Sources> sourceListUser) {
		this.sourcesUserList = sourceListUser;
	}

	/**
	 * Get the value of name
	 *
	 * @return the value of name
	 */
	public String getName() {
		return name;
	}

	public ArrayList<Sources> getSources() {
		return sources;
	}

	public void setSources(ArrayList<Sources> sources) {
		this.sources = sources;
	}

	/**
	 * Set the value of name
	 *
	 * @param name
	 *            new value of name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the value of topic
	 *
	 * @return the value of topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Set the value of words
	 *
	 * @param words
	 *            new value of words
	 */
	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
	
	public ArrayList<String> getWords() {
		return words;
	}

	/**
	 * Set the value of topic
	 *
	 * @param topic
	 *            new value of topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	
	
}