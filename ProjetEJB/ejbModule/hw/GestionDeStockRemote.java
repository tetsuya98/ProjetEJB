package hw;
import java.util.List;
import javax.ejb.Remote;
@Remote
public interface GestionDeStockRemote {
	public Produit ajouter(Produit produit);
	public Produit rechercherProduit(int id);
	public List<Produit> listerTousLesProduits();
}