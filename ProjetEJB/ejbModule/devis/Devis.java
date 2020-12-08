package devis;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.ArrayList;


/**
 * Session Bean implementation class Devis
 */
@Stateful(mappedName = "Devis")
@LocalBean
public class Devis implements DevisRemote {

	private ArrayList<Article> devis = new ArrayList<Article>();
	@EJB
    NouveauMontant montant;
	
    public Devis() {
    }
    
    public void addArtcile(int id, String libelle, String prix) {
    	Article article = new Article(id, libelle, prix);
    	devis.add(article);
    }

    public void removeArticle(int id) {
    	for (int i = 0; i < devis.size(); i++) {
    		if (devis.get(i).getId() == id) {
    			devis.remove(i);
    		}
    	}
    }
    
    public ArrayList<Article> getDevis() {
    	return this.devis;
    }
    
    public int getMontant() {
    	return montant.calculMontant(this.devis); 
    }


}
