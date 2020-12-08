package hw;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Stateless
@LocalBean
public class GestionDeStock implements GestionDeStockRemote {
	public GestionDeStock() {}

	@PersistenceContext
	EntityManager em;

	@Override
	public Produit ajouter(Produit produit) {
		em.persist(produit);
		return produit;
	}
	@Override
	public List<Produit> listerTousLesProduits() {
		return em.createNamedQuery("findAllProducts").getResultList();
	}
	@Override
	public Produit rechercherProduit(int id) {
		return em.find(Produit.class, id);
	}
}