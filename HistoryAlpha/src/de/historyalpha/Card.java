package de.historyalpha;

public class Card {
	
	int cardId;
	String schlagwort;
	String text;
	
	public Card(int cardId, String schlagwort, String text) {
		this.cardId = cardId;
		this.schlagwort = schlagwort;
		this.text = text;
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

	public String toString() {
		return "Cards [cardId=" + cardId + ", schlagwort=" + schlagwort
				+ ", text=" + text + "]";
	}	

}
