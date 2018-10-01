package my.news.news;

// TODO: Auto-generated Javadoc
/**
 * The Class Sources.
 */
public class Sources {

	/** The id. */
	private String id;
	
	/** The name. */
	private String name;
	
	/** The category. */
	private String category;
	
	/** The language. */
	private String language;
	
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
	
	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * Instantiates a new sources.
	 *
	 * @param id the id
	 * @param name the name
	 * @param category the category
	 * @param language the language
	 */
	public Sources(String id, String name, String category, String language) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.language = language;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
