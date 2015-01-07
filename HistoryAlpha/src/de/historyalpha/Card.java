package de.historyalpha;

public class Card {

	int cardId;
	String schlagwort;
	String text;
	int jahr;

	public Card(int cardId, String schlagwort, String text, int jahr) {
		this.cardId = cardId;
		this.schlagwort = schlagwort;
		this.text = text;
		this.jahr = jahr;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getSchlagwort() {
		return schlagwort;
	}

	public void setSchlagwort(String schlagwort) {
		this.schlagwort = schlagwort;
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

	public String toString() {
		return "Card [cardId=" + cardId + ", schlagwort=" + schlagwort
				+ ", text=" + text + ", jahr=" + jahr + "]";
	}
}
