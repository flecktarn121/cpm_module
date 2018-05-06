package gui;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logic.Accommodation;
import logic.Database;
import logic.Package;
import logic.ThemePark;
import logic.TypeOfAccomodation;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Types;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PackageCatalog extends JDialog {
	private JPanel pnNorth;
	private JPanel pnCenter;
	private JPanel pnSouth;
	private JPanel pnEast;
	private JLabel lblTitle;
	private JPanel pnFiltersAndSearch;
	private JPanel pnSearch;
	private JLabel lblSearch;
	private JTextField txtSearch;
	private JScrollPane spList;
	private JTable table;
	private JPanel pnFilters;
	private JPanel pnLocalisation;
	private JLabel lblCountry;
	private JComboBox cbLocalisation;
	private JButton btnBack;
	private MainWindow mW;
	private JPanel pnAccommodation;
	private JPanel pnType;
	private JPanel pnBreakfast;
	private JLabel lblType;
	private JComboBox cbType;
	private JCheckBox chckbxBreakfast;
	private JDialog pC = this;
	private JPanel pnPark;
	private JLabel lblName;
	private JComboBox cbPark;
	private DefaultTableModel packagesModel;
	private DefaultComboBoxModel<Object> modelCountries;
	private DefaultComboBoxModel<String> moldelParks;
	private JPanel pnApplyFilters;
	private JButton btnApply;
	private JButton btnHelp;

	/**
	 * Create the dialog.
	 */
	public PackageCatalog(MainWindow mW) {
		setTitle("Catalog of packages");
		setModal(true);
		this.mW = mW;
		setBounds(100, 100, 1280, 720);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnNorth(), BorderLayout.NORTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		getContentPane().add(getPnSouth(), BorderLayout.SOUTH);
		getContentPane().add(getPnEast(), BorderLayout.EAST);
		mW.getHelpBroker().enableHelpKey(getRootPane(), "pcC", mW.getHelpSet());
		mW.getHelpBroker().enableHelpOnButton(getBtnHelp(), "pcC", mW.getHelpSet());


	}

	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnNorth.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnNorth.add(getLblTitle());
		}
		return pnNorth;
	}

	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setLayout(new BorderLayout(0, 0));
			pnCenter.add(getPnFiltersAndSearch(), BorderLayout.NORTH);
			pnCenter.add(getSpList(), BorderLayout.CENTER);
		}
		return pnCenter;
	}

	private JPanel getPnSouth() {
		if (pnSouth == null) {
			pnSouth = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnSouth.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnSouth.add(getBtnBack());
			pnSouth.add(getBtnHelp());
		}
		return pnSouth;
	}

	private JPanel getPnEast() {
		if (pnEast == null) {
			pnEast = new JPanel();
		}
		return pnEast;
	}

	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Packages");
			lblTitle.setFont(new Font("Dialog", Font.BOLD, 20));
			lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblTitle;
	}

	private JPanel getPnFiltersAndSearch() {
		if (pnFiltersAndSearch == null) {
			pnFiltersAndSearch = new JPanel();
			pnFiltersAndSearch.setLayout(new BorderLayout(0, 0));
			pnFiltersAndSearch.add(getPnSearch(), BorderLayout.SOUTH);
			pnFiltersAndSearch.add(getPnFilters(), BorderLayout.CENTER);
		}
		return pnFiltersAndSearch;
	}

	private JPanel getPnSearch() {
		if (pnSearch == null) {
			pnSearch = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnSearch.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnSearch.add(getLblSearch());
			pnSearch.add(getTxtSearch());
		}
		return pnSearch;
	}

	private JLabel getLblSearch() {
		if (lblSearch == null) {
			lblSearch = new JLabel("Search:");
			lblSearch.setDisplayedMnemonic('S');
			lblSearch.setLabelFor(getTxtSearch());
		}
		return lblSearch;
	}

	private JTextField getTxtSearch() {
		if (txtSearch == null) {
			txtSearch = new JTextField();
			txtSearch.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
						if (!txtSearch.getText().equals("")) {
							List<Package> searched = mW.db.searchPack(txtSearch.getText());
							String[] columns = { "Name", "Type", "Category", "Park", "capacity", "Price" };
							packagesModel = new NotEditableModel(columns, 0);
							addRows(searched);
							table.setModel(packagesModel);
						} else {
							String[] columns = { "Name", "Type", "Category", "Park", "capacity", "Price" };
							packagesModel = new NotEditableModel(columns, 0);
							addRows(mW.db.getPackages());
							table.setModel(packagesModel);
						}
					}
				}
			});

			txtSearch.setColumns(10);
		}
		return txtSearch;
	}

	private JScrollPane getSpList() {
		if (spList == null) {
			spList = new JScrollPane();
			spList.setViewportView(getTable());
		}
		return spList;
	}

	private JTable getTable() {
		if (table == null) {
			String[] columns = { "Name", "Park", "Accommodation", "Price Adult", "Price Children" };
			packagesModel = new NotEditableModel(columns, 0);
			addRows(mW.db.getPackages());
			table = new JTable(packagesModel);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (arg0.getClickCount() == 2) {
						Package pack = mW.db.getPackByName(getSelectedName());
						new PackageProcessor(mW, pack, false).setVisible(true);
						;
					}
				}
			});
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return table;
	}

	private String getSelectedName() {
		int row = table.getSelectedRow();
		if (row != -1) {
			return (String) table.getValueAt(row, 0);
		}
		return "";
	}

	private void addRows(List<Package> packages) {
		for (Package pack : packages) {
			String name = pack.getName();
			String parkName = mW.db.getParkByCode(pack.getParkCode()).getName();
			String accommodationName = mW.db.getAccommodationByCode(pack.getAccommodationCode()).getName();
			String adultPrice = String.valueOf(pack.getAdultPrice());
			String childrenPrice = String.valueOf(pack.getChildrenPrice());
			String[] packData = { name, parkName, accommodationName, adultPrice, childrenPrice };
			packagesModel.addRow(packData);
		}

	}

	private JPanel getPnFilters() {
		if (pnFilters == null) {
			pnFilters = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnFilters.getLayout();
			flowLayout.setAlignOnBaseline(true);
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnFilters.add(getPnLocalisation());
			pnFilters.add(getPnAccommodation());
			pnFilters.add(getPnPark());
			pnFilters.add(getPnApplyFilters());
		}
		return pnFilters;
	}

	private JPanel getPnLocalisation() {
		if (pnLocalisation == null) {
			pnLocalisation = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnLocalisation.getLayout();
			flowLayout.setAlignOnBaseline(true);
			pnLocalisation.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Localisation:",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
			pnLocalisation.add(getLblCountry());
			pnLocalisation.add(getCbLocalisation());
		}
		return pnLocalisation;
	}

	private JLabel getLblCountry() {
		if (lblCountry == null) {
			lblCountry = new JLabel("Country:");
			lblCountry.setDisplayedMnemonic('C');
			lblCountry.setLabelFor(getCbLocalisation());
		}
		return lblCountry;
	}

	private JComboBox getCbLocalisation() {
		if (cbLocalisation == null) {
			cbLocalisation = new JComboBox();
			cbLocalisation.setToolTipText("Select the country.");
			modelCountries = new DefaultComboBoxModel<Object>();
			modelCountries.addElement(mW.db.NO_FILTER_KEYWORD);
			for (String country : mW.db.getCountries()) {
				modelCountries.addElement(country);
			}
			cbLocalisation.setModel(modelCountries);
		}
		return cbLocalisation;
	}

	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
			btnBack.setMnemonic('k');
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pC.dispose();
				}
			});
			btnBack.setToolTipText("Return to the shopping cart.");
			btnBack.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return btnBack;
	}

	private JPanel getPnAccommodation() {
		if (pnAccommodation == null) {
			pnAccommodation = new JPanel();
			pnAccommodation.setBorder(
					new TitledBorder(null, "Accommodation:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnAccommodation.setLayout(new BoxLayout(pnAccommodation, BoxLayout.Y_AXIS));
			pnAccommodation.add(getPnType());
			pnAccommodation.add(getPnBreakfast());
		}
		return pnAccommodation;
	}

	private JPanel getPnType() {
		if (pnType == null) {
			pnType = new JPanel();
			pnType.add(getLblType());
			pnType.add(getCbType());
		}
		return pnType;
	}

	private JPanel getPnBreakfast() {
		if (pnBreakfast == null) {
			pnBreakfast = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBreakfast.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnBreakfast.add(getChckbxBreakfast());
		}
		return pnBreakfast;
	}

	private JLabel getLblType() {
		if (lblType == null) {
			lblType = new JLabel("Type:");
			lblType.setLabelFor(getCbType());
			lblType.setDisplayedMnemonic('T');
		}
		return lblType;
	}

	private JComboBox getCbType() {
		if (cbType == null) {
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement(Database.NO_FILTER_KEYWORD);
			for (TypeOfAccomodation type : TypeOfAccomodation.values()) {
				model.addElement(type.toString().toLowerCase());
			}
			cbType = new JComboBox<String>(model);

		}
		return cbType;
	}

	private JCheckBox getChckbxBreakfast() {
		if (chckbxBreakfast == null) {
			chckbxBreakfast = new JCheckBox("Breakfast");
			chckbxBreakfast.setMnemonic('B');
		}
		return chckbxBreakfast;
	}

	private JPanel getPnPark() {
		if (pnPark == null) {
			pnPark = new JPanel();
			pnPark.setBorder(new TitledBorder(null, "Park:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnPark.add(getLblName());
			pnPark.add(getCbPark());
		}
		return pnPark;
	}

	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Name:");
			lblName.setLabelFor(getCbPark());
			lblName.setDisplayedMnemonic('N');
		}
		return lblName;
	}

	private JComboBox getCbPark() {
		if (cbPark == null) {
			moldelParks = new DefaultComboBoxModel<String>();
			moldelParks.addElement(mW.db.NO_FILTER_KEYWORD);
			for (ThemePark park : mW.db.getParks()) {
				moldelParks.addElement(park.getName());
			}
			cbPark = new JComboBox();
			cbPark.setModel(moldelParks);

		}
		return cbPark;
	}

	private void filter() {
		List<Package> newList = mW.db.getPackages();
		newList = mW.db.filterPackByCountry(newList, getCbLocalisation().getSelectedItem().toString());
		newList = mW.db.filterPackByPark(newList, getCbPark().getSelectedItem().toString());
		newList = mW.db.filterPackByTypeOfAcc(newList, getCbType().getSelectedItem().toString());

		if (chckbxBreakfast.isSelected()) {
			newList = mW.db.filterPacksByBreakfast(newList);
		}

		String[] columns = { "Name", "Park", "Accommodation", "Price Adult", "Price Children" };
		packagesModel = new NotEditableModel(columns, 0);
		addRows(newList);
		table.setModel(packagesModel);

	}

	private JPanel getPnApplyFilters() {
		if (pnApplyFilters == null) {
			pnApplyFilters = new JPanel();
			pnApplyFilters.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Apply :",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
			pnApplyFilters.add(getBtnApply());
		}
		return pnApplyFilters;
	}

	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton("Apply Filters");
			btnApply.setMnemonic('y');
			btnApply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					filter();
				}
			});
		}
		return btnApply;
	}
	private JButton getBtnHelp() {
		if (btnHelp == null) {
			btnHelp = new JButton("Help");
			btnHelp.setMnemonic('e');
			btnHelp.setToolTipText("See the help view");
		}
		return btnHelp;
	}
}
