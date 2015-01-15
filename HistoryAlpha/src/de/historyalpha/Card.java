package de.historyalpha;

public class Card {

	int cardId;
	String schlagwort;
	String beschreibung;
	int jahr;
	boolean correct;

	public Card(int cardId, String schlagwort, String text, int jahr) {
		this.cardId = cardId;
		this.schlagwort = schlagwort;
		this.beschreibung = text;
		this.jahr = jahr;
		this.correct = true;
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

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String text) {
		this.beschreibung = text;
	}

	public int getJahr() {
		return jahr;
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
	}

	public String toString() {
		return "Card [cardId=" + cardId + ", schlagwort=" + schlagwort
				+ ", text=" + beschreibung + ", jahr=" + jahr + "]";
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
}
