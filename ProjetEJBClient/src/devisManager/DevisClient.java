package devisManager;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import devis.Article;
import devis.DevisRemote;

public class DevisClient{
	
	private ArrayList<String> devis=new ArrayList<String>();
	ArrayList<DevisRemote> listDevis = new ArrayList<DevisRemote>();

	private JFrame frame;
	private JTable table; 
	private JTextField textIdArticleSuppression;
	private JTextField textIdArticleAjout;
	private JTextField textLibelle;
	private JTextField textPrix;
	private JComboBox devisList;
	private JLabel labelCommercial = new JLabel("Devis r\u00E9alis\u00E9 par :");
	private JLabel labelDate = new JLabel("Le 13 Juillet 2019");
	
	private TableDevisModel model=new TableDevisModel();
	private JTextField textMontant;
	public static Context context;
	public DevisRemote ejb;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					context = DevisClient.connexionServeurWildfly();
					DevisClient window = new DevisClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public DevisClient() {
		ejb = TableDevisModel.getEjbStateful(context);
		listDevis.add(ejb);
		initialize();
	}
	
	public void nouveauDevis() {
		ejb = TableDevisModel.getEjbStateful(context);
		listDevis.add(ejb);
		devis.clear();
		textMontant.setText("0.00 �");	
		devisList.addItem(ejb);
		devisList.setSelectedItem(ejb);
		model.fireTableDataChanged();
	}
	
	public void changeDevis(DevisRemote devisR) {
		ejb = devisR;
		devis.clear();
		textMontant.setText(""+ejb.getMontant());
		ArrayList<Article> devisA = ejb.getDevis();
		for (Article a : devisA) {
			devis.add(a.getId()+"");
			devis.add(a.getLibelle());
			devis.add(a.getPrix());
		}
		model.fireTableDataChanged();	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 623, 559);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		labelCommercial.setBounds(10, 23, 384, 14);
		frame.getContentPane().add(labelCommercial);
		labelDate.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		labelDate.setBounds(389, 23, 208, 14);
		frame.getContentPane().add(labelDate);
		
		JLabel labelMontant = new JLabel("Montant total du devis");
		labelMontant.setBounds(10, 59, 142, 14);
		frame.getContentPane().add(labelMontant);
		
		JLabel lblListeDesPices = new JLabel("LISTE DES ARTICLES DU DEVIS");
		lblListeDesPices.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblListeDesPices.setBounds(10, 103, 277, 14);
		frame.getContentPane().add(lblListeDesPices);
		
		model.setDevis(devis);
		table = new JTable(model);
		table.setRowSelectionAllowed(false);
		table.setBackground(new Color(204, 204, 153));
		TableColumn column = null;
		column = table.getColumnModel().getColumn(0);
        column.setWidth(40);   
		column = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(400);
		column = table.getColumnModel().getColumn(2);
        column.setPreferredWidth(109);
		JScrollPane scrollPaneTable = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(0);
		scrollPaneTable.setBounds(10, 128, 587, 206);
		frame.getContentPane().add(scrollPaneTable);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 204, 153));
		panel.setBounds(11, 466, 586, 45);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblIdArticle = new JLabel("ID article \u00E0 supprimer");
		lblIdArticle.setBounds(21, 15, 166, 14);
		panel.add(lblIdArticle);
		
		textIdArticleSuppression = new JTextField();
		textIdArticleSuppression.setBounds(197, 12, 106, 20);
		panel.add(textIdArticleSuppression);
		textIdArticleSuppression.setColumns(10);
		
		JButton boutonSupprimer = new JButton("Supprimer du devis");
		boutonSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supprimerArticle(textIdArticleSuppression.getText());
			}
		});
		boutonSupprimer.setBounds(419, 11, 157, 23);
		panel.add(boutonSupprimer);
		
		JLabel lblSuppressionDarticleDu = new JLabel("SUPPRESSION D'ARTICLE DU DEVIS");
		lblSuppressionDarticleDu.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSuppressionDarticleDu.setForeground(new Color(0, 0, 0));
		lblSuppressionDarticleDu.setBounds(11, 444, 307, 14);
		frame.getContentPane().add(lblSuppressionDarticleDu);
		
		JLabel lblAjoutDarticleDans = new JLabel("AJOUT/MODIFICATION D'ARTICLE DANS LE DEVIS");
		lblAjoutDarticleDans.setForeground(Color.BLACK);
		lblAjoutDarticleDans.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAjoutDarticleDans.setBounds(12, 345, 400, 14);
		frame.getContentPane().add(lblAjoutDarticleDans);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 204, 153));
		panel_1.setBounds(10, 365, 586, 63);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblIdArticle_1 = new JLabel("ID article \u00E0 ajouter / modifier");
		lblIdArticle_1.setBounds(10, 11, 179, 14);
		panel_1.add(lblIdArticle_1);
		
		JLabel lblLibellArticle = new JLabel("Libell\u00E9 article");
		lblLibellArticle.setBounds(10, 33, 119, 14);
		panel_1.add(lblLibellArticle);
		
		JLabel lblPrixDeLarticle = new JLabel("Prix");
		lblPrixDeLarticle.setBounds(328, 11, 34, 14);
		panel_1.add(lblPrixDeLarticle);
		
		textIdArticleAjout = new JTextField();
		textIdArticleAjout.setBounds(191, 8, 127, 20);
		panel_1.add(textIdArticleAjout);
		textIdArticleAjout.setColumns(10);
		
		textLibelle = new JTextField();
		textLibelle.setBounds(191, 30, 236, 20);
		panel_1.add(textLibelle);
		textLibelle.setColumns(10);
		
		textPrix = new JTextField();
		textPrix.setBounds(365, 8, 62, 20);
		panel_1.add(textPrix);
		textPrix.setColumns(10);
		
		JButton boutonAjouter = new JButton("Ajouter au devis");
		boutonAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajouterArticle();
			}
		});
		boutonAjouter.setBounds(437, 29, 139, 23);
		panel_1.add(boutonAjouter);
		// Initialisation du devis avec le nom du commercial et la date
		String name = JOptionPane.showInputDialog(frame, "Quel est votre nom?", null);
		labelCommercial.setText("Devis r\u00E9alis\u00E9 par :"+name);
		String today=LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		labelDate.setText("Le "+today);
		
		textMontant = new JTextField();
		textMontant.setFont(new Font("Tahoma", Font.BOLD, 13));
		textMontant.setBackground(new Color(204, 204, 153));
		textMontant.setEditable(false);
		textMontant.setBounds(155, 55, 96, 20);
		frame.getContentPane().add(textMontant);
		textMontant.setColumns(10);
		textMontant.setText("0.00 �");	
		
		JButton buttonNouveauDevis = new JButton("Nouveau Devis");
		buttonNouveauDevis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nouveauDevis();
			}
		});
		buttonNouveauDevis.setBounds(301, 55, 139, 23);
		frame.getContentPane().add(buttonNouveauDevis);
		
		Object[] array = listDevis.toArray(new Object[listDevis.size()]);
		devisList = new JComboBox(array);
		devisList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DevisRemote ejb = (DevisRemote)devisList.getSelectedItem();
				changeDevis(ejb);
			}
		});
		devisList.setBounds(470, 55, 139, 23);
		frame.getContentPane().add(devisList);


	}

	private void ajouterArticle()
	{
		// Ajout d'un article au devis en cours
		devis.add(textIdArticleAjout.getText());
		devis.add(textLibelle.getText());
		devis.add(textPrix.getText());
		ejb.addArtcile(Integer.parseInt(textIdArticleAjout.getText()), textLibelle.getText(), textPrix.getText());
		
		// Calcul du montant du devis
		textMontant.setText(""+ejb.getMontant());
		
		// Mise � jour du tableau
		model.fireTableDataChanged();	
	}
	
	private void supprimerArticle(String id) {
		// Suppression d'un article au devis en cours
		ejb.removeArticle(Integer.parseInt(id));
		devis.clear();
		ArrayList<Article> devisA = ejb.getDevis();
		for (Article a : devisA) {
			devis.add(a.getId()+"");
			devis.add(a.getLibelle());
			devis.add(a.getPrix());
		}
		
		// Calcul du montant du devis
		textMontant.setText(""+ejb.getMontant());
				
		// Mise � jour du tableau
		model.fireTableDataChanged();	
	}
	
	public static Context connexionServeurWildfly()
	{
		// Connexion au serveur et récupération du pointeur distant
		System.out.println("***************** Connexion au serveur d'applications *************************\n"
				+ "Récupération des informations de connexion au serveur dans le fichier jboss-ejb_client.properties");
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
		System.out.println("Connexion au serveur");
		Context context=null;
		try {
			context = new InitialContext(jndiProperties);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return context;
	}
	
}
