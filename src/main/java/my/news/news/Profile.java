package my.news.news;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
@SuppressWarnings("serial")
public class Profile implements Serializable, Cloneable {

	

	/** The name. */
	private String name= "";

	/** The topic. */
	private String topic = null;
	
	/** The language. */
	private String language = null;

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/** The words. */
	private ArrayList<String> words = null;
	
	/** The sources. */
	private ArrayList<Sources> sources = null;
	
	/** The sources user list. */
	private List<Sources> sourcesUserList = null;


	/**
	 * Gets the sources user list.
	 *
	 * @return the sources user list
	 */
	public List<Sources> getSourcesUserList() {
		return sourcesUserList;
	}

	/**
	 * Sets the sources user list.
	 *
	 * @param sourceListUser the new sources user list
	 */
	public void setSourcesUserList(List<Sources> sourceListUser) {
		this.sourcesUserList = sourceListUser;
	}

	/**
	 * Get the value of name.
	 *
	 * @return the value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the sources.
	 *
	 * @return the sources
	 */
	public ArrayList<Sources> getSources() {
		return sources;
	}

	/**
	 * Sets the sources.
	 *
	 * @param sources the new sources
	 */
	public void setSources(ArrayList<Sources> sources) {
		this.sources = sources;
	}

	/**
	 * Set the value of name.
	 *
	 * @param name            new value of name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the value of topic.
	 *
	 * @return the value of topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Set the value of words.
	 *
	 * @param words            new value of words
	 */
	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
	
	/**
	 * Gets the words.
	 *
	 * @return the words
	 */
	public ArrayList<String> getWords() {
		return words;
	}

	/**
	 * Set the value of topic.
	 *
	 * @param topic            new value of topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	
	
}