package devis;

import java.io.Serializable;

public class Article implements Serializable {
	
	private int id;
	private String libelle;
	private String prix;
	
	public Article (int id, String libelle, String prix) {
		this.id = id;
		this.libelle = libelle;
		this.prix = prix;
	}
	
	public int getId() { 
		return this.id;
	}
	
	public String getLibelle() {
		return this.libelle;
	}
	
	public String getPrix() {
		return this.prix;
	}
}
