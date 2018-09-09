package my.news.news;

public class NewsObject {
	
	private String title;
	private String url;
	private String description;
	private String source;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public NewsObject(String title, String url, String description, String source) {
		super();
		this.title = title;
		this.url = url;
		this.description = description;
		this.source = source;
	}
	
	

}
