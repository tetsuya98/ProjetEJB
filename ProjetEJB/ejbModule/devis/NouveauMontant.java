package devis;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.ArrayList;


/**
 * Session Bean implementation class NouveauMontant
 */
@Stateless(mappedName = "NouveauMontant")
@LocalBean
public class NouveauMontant implements NouveauMontantLocal {
    
    public NouveauMontant() {
    }
    
    public int calculMontant(ArrayList<Article> devis) {
    	int montant = 0;
    	for(Article article : devis) {
    		montant = montant + Integer.parseInt(article.getPrix());
    	}
    	return montant;
    }

}
