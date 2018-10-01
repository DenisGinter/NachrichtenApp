package my.news.news;

// TODO: Auto-generated Javadoc
/**
 * The Class NewsObject.
 */
public class NewsObject {
	
	/** The title. */
	private String title;
	
	/** The url. */
	private String url;
	
	/** The description. */
	private String description;
	
	/** The source. */
	private String source;
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Instantiates a new news object.
	 *
	 * @param title the title
	 * @param url the url
	 * @param description the description
	 * @param source the source
	 */
	public NewsObject(String title, String url, String description, String source) {
		super();
		this.title = title;
		this.url = url;
		this.description = description;
		this.source = source;
	}
	
	

}
