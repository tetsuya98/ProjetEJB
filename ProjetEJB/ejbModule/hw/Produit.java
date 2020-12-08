package hw;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries ({ 
	@NamedQuery(name="findAllProducts", query="SELECT p FROM Produit p ORDER BY p.quantiteEnStock"),
    })
public class Produit implements Serializable {
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO) 
	private int id;
	private String libelle;
	private int quantiteEnStock;
	public Produit() {}
	public Produit(String libelle, int quantiteEnStock) {
	      this.libelle = libelle;
	      this.quantiteEnStock = quantiteEnStock;
	   }
	public int getId() {return id;}	
	public void setId(int nid){id=nid;}
	public String getLibelle() {return libelle;}
	public void setLibelle(String libelle) {this.libelle = libelle;}
	public int getQuantiteEnStock() {return quantiteEnStock;}
	public void setQuantiteEnStock(int quantiteEnStock){this.quantiteEnStock = quantiteEnStock;}
	@Override
	public String toString() {
		return "Produit n°"+id+" - "+libelle+" - quantite disponible : "+ quantiteEnStock;
	}
}