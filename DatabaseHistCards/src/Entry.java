
public class Entry {

	private String id;	
	private String titel;
	private String text;
	private int jahr;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitel() {
		return titel;
	}
	
	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int getJahr() {
		return jahr;
	}
	
	public void setJahr(int jahr) {
		this.jahr = jahr;
	}
	
	public String toString(){
		String output = "ID: "+id + ", Titel: " + titel + 
				", Frage: " + text + "" + ", Lösung: " + jahr;
		return output;
	}
	
}
