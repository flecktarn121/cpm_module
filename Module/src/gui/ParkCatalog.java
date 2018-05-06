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
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import logic.Accommodation;
import logic.ThemePark;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ParkCatalog extends JDialog {
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
	private DefaultTableModel tableModel;
	private JList visitList;
	private JPanel pnCountry;
	private JPanel pnCity;
	private JLabel lblCity;
	private JComboBox cbCity;
	private JPanel pnPrice;
	private JPanel pnMax;
	private JCheckBox chckbxMax;
	private JSlider sliderMax;
	private JSpinner spnMax;
	private JDialog cP = this;
	private DefaultComboBoxModel<Object> modelCountries;
	private DefaultComboBoxModel<Object> modelCities;
	private JPanel pnDescriptionImage;
	private JScrollPane scpDescription;
	private JTextArea txtDescription;
	private JPanel pnApply;
	private JButton btnApply;
	private DefaultTableModel catalogModel;
	private JButton btnHelp;

	/**
	 * Create the dialog.
	 */
	public ParkCatalog(MainWindow mW) {
		setTitle("Them parks catalog");
		setModal(true);
		this.mW = mW;
		setBounds(100, 100, 1280, 720);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnNorth(), BorderLayout.NORTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		getContentPane().add(getPnSouth(), BorderLayout.SOUTH);
		getContentPane().add(getPnEast(), BorderLayout.EAST);
		mW.getHelpBroker().enableHelpKey(getRootPane(), "prC", mW.getHelpSet());
		mW.getHelpBroker().enableHelpOnButton(getBtnHelp(), "prC", mW.getHelpSet());

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
			pnCenter.add(getPnDescriptionImage(), BorderLayout.SOUTH);
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
			lblTitle = new JLabel("ThemeParks");
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
			txtSearch.setToolTipText("Search by name");
			txtSearch.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (!txtSearch.getText().equals("")) {
						List<ThemePark> searched = mW.db.searchPark(txtSearch.getText());
						String[] columns = { "Name", "Country", "City", "Adult Price", "Children Price" };
						catalogModel = new NotEditableModel(columns, 0);
						addRows(searched);
						table.setModel(catalogModel);
					} else {
						String[] columns = { "Name", "Type", "Category", "Park", "capacity", "Price" };
						catalogModel = new NotEditableModel(columns, 0);
						addRows(mW.db.getParks());
						table.setModel(catalogModel);
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
			table = new JTable();
			String[] columns = { "Name", "Country", "City", "Adult Price", "Children Price" };
			catalogModel = new NotEditableModel(columns, 0);
			addRows(mW.db.getParks());
			table.setModel(catalogModel);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (arg0.getClickCount() == 2) {
						String name = getSelectedName();
						if (name != "") {
							ThemePark park = mW.db.getParkByName(name);
							new ParkProcessor(mW, park, false).setVisible(true);

						}
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

	private void addRows(List<ThemePark> parks) {
		for (ThemePark park : parks) {
			String name = park.getName();
			String country = park.getCity();
			String city = park.getCity();
			String priceAdult = String.valueOf(mW.db.getTicketByCode(park.getCode()).getAdultPrice());
			String priceChildren = String.valueOf(mW.db.getTicketByCode(park.getCode()).getChildrenPrice());
			String[] parkData = { name, country, city, priceAdult, priceChildren };
			catalogModel.addRow(parkData);
		}

	}

	private JPanel getPnFilters() {
		if (pnFilters == null) {
			pnFilters = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnFilters.getLayout();
			flowLayout.setAlignOnBaseline(true);
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnFilters.add(getPnLocalisation());
			pnFilters.add(getPnPrice());
			pnFilters.add(getPnApply());
		}
		return pnFilters;
	}

	private JPanel getPnLocalisation() {
		if (pnLocalisation == null) {
			pnLocalisation = new JPanel();
			pnLocalisation.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Localisation:",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
			pnLocalisation.setLayout(new BoxLayout(pnLocalisation, BoxLayout.Y_AXIS));
			pnLocalisation.add(getPnCountry());
			pnLocalisation.add(getPnCity());
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
				public void actionPerformed(ActionEvent arg0) {
					cP.dispose();
				}
			});
			btnBack.setToolTipText("Return to the shopping cart.");
			btnBack.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return btnBack;
	}

	private JPanel getPnCountry() {
		if (pnCountry == null) {
			pnCountry = new JPanel();
			pnCountry.add(getLblCountry());
			pnCountry.add(getCbLocalisation());
		}
		return pnCountry;
	}

	private JPanel getPnCity() {
		if (pnCity == null) {
			pnCity = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnCity.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnCity.add(getLblCity());
			pnCity.add(getCbCity());
		}
		return pnCity;
	}

	private JLabel getLblCity() {
		if (lblCity == null) {
			lblCity = new JLabel("City:");
			lblCity.setLabelFor(getCbCity());
			lblCity.setDisplayedMnemonic('t');
		}
		return lblCity;
	}

	private JComboBox getCbCity() {
		if (cbCity == null) {
			modelCities = new DefaultComboBoxModel<Object>();
			modelCities.addElement(mW.db.NO_FILTER_KEYWORD);
			for (ThemePark park : mW.db.getParks()) {
				modelCities.addElement(park.getCity());
			}
			cbCity = new JComboBox();
			modelCities.addElement(mW.db.NO_FILTER_KEYWORD);
			cbCity.setModel(modelCities);
		}
		return cbCity;
	}

	private JPanel getPnPrice() {
		if (pnPrice == null) {
			pnPrice = new JPanel();
			pnPrice.setBorder(new TitledBorder(null, "Price:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnPrice.setLayout(new BoxLayout(pnPrice, BoxLayout.Y_AXIS));
			pnPrice.add(getPnMax());
		}
		return pnPrice;
	}

	private JPanel getPnMax() {
		if (pnMax == null) {
			pnMax = new JPanel();
			pnMax.add(getChckbxMax());
			pnMax.add(getSliderMax());
			pnMax.add(getSpnMax());
		}
		return pnMax;
	}

	private JCheckBox getChckbxMax() {
		if (chckbxMax == null) {
			chckbxMax = new JCheckBox("Max");
			chckbxMax.setToolTipText("Allow to filter by price.");
			chckbxMax.setMnemonic('M');
			chckbxMax.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					if ((chckbxMax).isSelected()) {
						sliderMax.setEnabled(true);
						spnMax.setEnabled(true);
					} else {
						sliderMax.setEnabled(false);
						spnMax.setEnabled(false);
					}
				}
			});
		}
		return chckbxMax;
	}

	private JSlider getSliderMax() {
		if (sliderMax == null) {
			sliderMax = new JSlider();
			sliderMax.setToolTipText("Set the desired maximun price.");
			sliderMax.setPreferredSize(new Dimension(225, 50));
			sliderMax.setMaximum(100);
			sliderMax.setEnabled(false);
			sliderMax.setPaintTicks(true);
			sliderMax.setPaintLabels(true);
			sliderMax.setMinorTickSpacing(25);
			sliderMax.setMajorTickSpacing(50);
			sliderMax.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					spnMax.setValue(sliderMax.getValue());
				}
			});
		}
		return sliderMax;
	}

	private JSpinner getSpnMax() {
		if (spnMax == null) {
			spnMax = new JSpinner(
					new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(100), new Integer(10)));
			spnMax.setToolTipText("Set the desired maximun price.");
			spnMax.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					sliderMax.setValue(Integer.parseInt(spnMax.getValue().toString()));
				}
			});
			spnMax.setEnabled(false);
		}
		return spnMax;
	}

	private JPanel getPnDescriptionImage() {
		if (pnDescriptionImage == null) {
			pnDescriptionImage = new JPanel();
			pnDescriptionImage.add(getScpDescription());
		}
		return pnDescriptionImage;
	}

	private JScrollPane getScpDescription() {
		if (scpDescription == null) {
			scpDescription = new JScrollPane();
			scpDescription.add(getTxtDescription());
		}
		return scpDescription;
	}

	private JTextArea getTxtDescription() {
		if (txtDescription == null) {
			txtDescription = new JTextArea();

		}
		return txtDescription;
	}

	private JPanel getPnApply() {
		if (pnApply == null) {
			pnApply = new JPanel();
			pnApply.setBorder(new TitledBorder(null, "Apply:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnApply.add(getBtnApply());
		}
		return pnApply;
	}

	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton("Apply FIlters");
			btnApply.setMnemonic('l');
			btnApply.setToolTipText("Apply the current filters");
			btnApply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filter();
				}
			});
		}
		return btnApply;
	}

	private void filter() {
		List<ThemePark> newList = mW.db.getParks();
		newList = mW.db.filterParkByCountry(newList, cbLocalisation.getSelectedItem().toString());
		newList = mW.db.filterParkCity(newList, cbCity.getSelectedItem().toString());

		if (chckbxMax.isSelected()) {
			newList = mW.db.filterParkByPrice(newList, (Integer) spnMax.getValue());
		}
		String[] columns = { "Name", "Country", "City", "Adult Price", "Children Price" };
		catalogModel = new NotEditableModel(columns, 0);
		addRows(newList);
		table.setModel(catalogModel);
	}

	private JButton getBtnHelp() {
		if (btnHelp == null) {
			btnHelp = new JButton("Help");
			btnHelp.setToolTipText("See the help view.");
			btnHelp.setMnemonic('e');
		}
		return btnHelp;
	}
}
