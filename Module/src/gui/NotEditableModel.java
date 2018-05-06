package gui;

import javax.swing.table.DefaultTableModel;

public class NotEditableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public NotEditableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount); 
   }
	@Override
	public boolean isCellEditable(int row, int column) {
        return false;
    }
}
