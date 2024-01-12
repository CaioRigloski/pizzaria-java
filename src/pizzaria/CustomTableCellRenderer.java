package pizzaria;

import java.awt.Component;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if(column == 3 || column == 4) {
        	if(value != null) {
        		value = Double.parseDouble((String) value);
        		setValue(value);
        	}
        }
   
        return c;
    }
    
    @Override
    public void setValue(Object value) {
    	Locale ptBr = Locale.of("pt", "BR");
    	NumberFormat moeda = NumberFormat.getCurrencyInstance(ptBr);


		setText(value instanceof Double ? moeda.format(value) : "");
    }
};