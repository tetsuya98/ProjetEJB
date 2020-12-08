package devis;

import java.util.ArrayList;
import javax.ejb.Local;
import devis.Article;

@Local
public interface NouveauMontantLocal {
	public int calculMontant(ArrayList<Article> devis);
}
