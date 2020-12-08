package devis;

import javax.ejb.Remote;
import devis.Article;
import java.util.ArrayList;

@Remote
public interface DevisRemote {
	public void addArtcile(int id, String libelle, String prix);
	public void removeArticle(int id);
	public ArrayList<Article> getDevis();
	public int getMontant();
}
