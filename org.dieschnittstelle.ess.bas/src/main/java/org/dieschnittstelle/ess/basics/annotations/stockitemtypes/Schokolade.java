package org.dieschnittstelle.ess.basics.annotations.stockitemtypes;

import org.dieschnittstelle.ess.basics.annotations.*;

@StockItem
public class Schokolade {

	@Units
	@DisplayAs("AMOUNT")
	private int anzahlStuecke;

	private String marke;

	public int getAnzahlStuecke() {
		return anzahlStuecke;
	}

	public void setAnzahlStuecke(int anzahlStuecke) {
		this.anzahlStuecke = anzahlStuecke;
	}

	@Brandname
	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}

	@Initialise
	public void insLagerUebernehmen(int units, String name) {
		this.anzahlStuecke = units;
		this.marke = name;
	}

	/**
	 * toString
	 */
	public String toString() {
		return "<Schokolade " + this.marke + " " + this.anzahlStuecke + ">";
	}
}
