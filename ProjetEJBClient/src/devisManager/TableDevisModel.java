package devisManager;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.table.AbstractTableModel;

import devis.DevisRemote;
import devis.Devis;


public class TableDevisModel extends AbstractTableModel {
	String[] columnNames = {"ID article",
            "Libell�",
            "Prix"};

	//EJB
	public static DevisRemote getEjbStateful(Context context)
	{
		DevisRemote ejb=null;
		final String appName = "";
		final String moduleName = "ProjetEJB";
		final String distinctName = "";
		final String beanName = Devis.class.getSimpleName();
		final String viewClassName = DevisRemote.class.getName();
		System.out.println("***************** 2. Récupération de l'ejb *************************");
		String jndi="ejb:/" + moduleName + "/" + beanName + "!" + viewClassName+"?stateful";
		System.out.println(jndi);
		try {
			ejb = (DevisRemote) context.lookup(jndi);
		} catch (NamingException e) {
			e.printStackTrace();
		}	
		return ejb;
	}
	
	
	// Devis
	private ArrayList<String> mesDevis;
	
	public TableDevisModel() {
		super();
	}
    public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
	public int getRowCount() {
		return mesDevis.size()/3;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}
	
	 public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String valeur="";
		int x= 3*(rowIndex)+columnIndex;
		valeur=mesDevis.get(x);
		return valeur;
	}
	
	public void setDevis(ArrayList<String> devis) {
		this.mesDevis = devis;
	}	
	
	
}
