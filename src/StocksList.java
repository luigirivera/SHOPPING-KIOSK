import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class StocksList extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel stocksList;
	private JTable stocksTable;
	private DefaultTableModel modelStockssTable;
	private JScrollPane scrollStockssTable;
	private JComboBox <String> listFilter;
	
	public StocksList(String s)
	{
		super(s);
	}
}
