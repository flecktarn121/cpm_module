package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import logic.Accommodation;
import logic.ThemePark;

import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AccommCatalog extends JDialog {
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
	private JPanel pnCountry;
	private JPanel pnThemePark;
	private JLabel lblThemePark;
	private JComboBox cbThemePark;
	private JDialog aC = this;
	private JPanel pnType;
	private JCheckBox chckbxHotel;
	private JCheckBox chckbxMotel;
	private JCheckBox chckbxApartahotel;
	private JPanel pnQuality;
	private JCheckBox chckbxBreakfast;
	private JPanel pnStars;
	private JLabel lblStars;
	private JSpinner spinner;
	private JPanel pnBreakfast;
	private JPanel pnPrice;
	private DefaultComboBoxModel<String> modelCountries;
	private DefaultComboBoxModel<String> modelParks;
	private JPanel pnMin;
	private JCheckBox chckbxMax;
	private JSlider sliderMax;
	private JSpinner spnMax;
	private NotEditableModel accommodationsModel;
	private JPanel pnApply;
	private JButton btnApply;
	private JButton btnHelp;

	/**
	 * Create the dialog.
	 */
	public AccommCatalog(MainWindow mW) {
		setModal(true);
		this.mW = mW;
		this.setTitle("Accommodations' Catalog");
		setBounds(100, 100, 1280, 720);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnNorth(), BorderLayout.NORTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		getContentPane().add(getPnSouth(), BorderLayout.SOUTH);
		getContentPane().add(getPnEast(), BorderLayout.EAST);
		mW.getHelpBroker().enableHelpKey(getRootPane(), "accC", mW.getHelpSet());
		mW.getHelpBroker().enableHelpOnButton(getBtnHelp(), "accC", mW.getHelpSet());

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
			lblTitle = new JLabel("Accommodations");
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
			txtSearch.setToolTipText("Search by the name of the accommodation.");
			txtSearch.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
						if(!txtSearch.getText().equals("")) {
							List<Accommodation> searched = mW.db.searchAcc(txtSearch.getText());
							String[] columns = { "Name", "Type", "Category", "Park", "capacity", "Price" };
							accommodationsModel = new NotEditableModel(columns, 0);
							addRows(searched);
							table.setModel(accommodationsModel);
						}else {
							String[] columns = { "Name", "Type", "Category", "Park", "capacity", "Price" };
							accommodationsModel = new NotEditableModel(columns, 0);
							addRows(mW.db.getAccommodations());
							table.setModel(accommodationsModel);
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
			String[] columns = { "Name", "Type", "Category", "Park", "capacity", "Price" };
			accommodationsModel = new NotEditableModel(columns, 0);
			addRows(mW.db.getAccommodations());
			table = new JTable(accommodationsModel);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (arg0.getClickCount() == 2) {
						String name = getSelectedName();
						if (name != "") {
							Accommodation accommodation = mW.db.getAccommodationByName(name);
							new AccommodationProcessor(mW, accommodation, false).setVisible(true);
							;
						}
					}
				}
			});
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return table;
	}

	private void addRows(List<Accommodation> accommodations) {
		for (Accommodation acc : accommodations) {
			String name = acc.getName();
			String type = acc.getType().toString().toLowerCase();
			String category = new Integer((int) acc.getCategory()).toString();
			String park = mW.db.getParkByCode(acc.getThemeParkCode()).getName();
			String capacity = String.valueOf(acc.getCapacity());
			String price = String.valueOf(acc.getPrice());
			String[] accData = { name, type, category, park, capacity, price };
			accommodationsModel.addRow(accData);
		}

	}

	private JPanel getPnFilters() {
		if (pnFilters == null) {
			pnFilters = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnFilters.getLayout();
			flowLayout.setAlignOnBaseline(true);
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnFilters.add(getPnLocalisation());
			pnFilters.add(getPnType());
			pnFilters.add(getPnQuality());
			pnFilters.add(getPanel_1_2());
			pnFilters.add(getPnApply());
		}
		return pnFilters;
	}

	private JPanel getPnLocalisation() {
		if (pnLocalisation == null) {
			pnLocalisation = new JPanel();
			pnLocalisation.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Localisation:",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
			pnLocalisation.setLayout(new GridLayout(2, 1, 0, 0));
			pnLocalisation.add(getPnCountry());
			pnLocalisation.add(getPnThemePark());
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
			modelCountries = new DefaultComboBoxModel<String>();
			modelCountries.addElement(mW.db.NO_FILTER_KEYWORD);
			for (String country : mW.db.getCountries()) {
				modelCountries.addElement(country);
			}
			cbLocalisation = new JComboBox(modelCountries);
			cbLocalisation.setToolTipText("Select the country.");
		}
		return cbLocalisation;
	}

	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
			btnBack.setMnemonic('k');
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aC.dispose();
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
			pnCountry.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			pnCountry.add(getLblCountry());
			pnCountry.add(getCbLocalisation());
		}
		return pnCountry;
	}

	private JPanel getPnThemePark() {
		if (pnThemePark == null) {
			pnThemePark = new JPanel();
			pnThemePark.add(getLblThemePark());
			pnThemePark.add(getCbThemePark());
		}
		return pnThemePark;
	}

	private JLabel getLblThemePark() {
		if (lblThemePark == null) {
			lblThemePark = new JLabel("ThemePark:");
			lblThemePark.setLabelFor(getCbThemePark());
			lblThemePark.setDisplayedMnemonic('h');
		}
		return lblThemePark;
	}

	private JComboBox getCbThemePark() {
		if (cbThemePark == null) {
			modelParks = new DefaultComboBoxModel<String>();
			modelParks.addElement(mW.db.NO_FILTER_KEYWORD);
			for (ThemePark park : mW.db.getParks()) {
				modelParks.addElement(park.getName());
			}
			cbThemePark = new JComboBox(modelParks);
			cbThemePark.setToolTipText("Select the thempark.");
		}
		return cbThemePark;
	}

	private JPanel getPnType() {
		if (pnType == null) {
			pnType = new JPanel();
			pnType.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Type:", TitledBorder.LEADING,
					TitledBorder.TOP, null, new Color(51, 51, 51)));
			pnType.setLayout(new BoxLayout(pnType, BoxLayout.Y_AXIS));
			pnType.add(getChckbxHotel());
			pnType.add(getChckbxMotel());
			pnType.add(getChckbxApartahotel());
		}
		return pnType;
	}

	private JCheckBox getChckbxHotel() {
		if (chckbxHotel == null) {
			chckbxHotel = new JCheckBox("Hotel");
			chckbxHotel.setToolTipText("Show the hotels.");
			chckbxHotel.setMnemonic('o');
		}
		return chckbxHotel;
	}

	private JCheckBox getChckbxMotel() {
		if (chckbxMotel == null) {
			chckbxMotel = new JCheckBox("Motel");
			chckbxMotel.setToolTipText("Show the motels.");
			chckbxMotel.setMnemonic('l');
		}
		return chckbxMotel;
	}

	private JCheckBox getChckbxApartahotel() {
		if (chckbxApartahotel == null) {
			chckbxApartahotel = new JCheckBox("ApartaHotel");
			chckbxApartahotel.setToolTipText("Show the apartahotels.");
			chckbxApartahotel.setMnemonic('p');
		}
		return chckbxApartahotel;
	}

	private JPanel getPnQuality() {
		if (pnQuality == null) {
			pnQuality = new JPanel();
			pnQuality.setBorder(new TitledBorder(null, "Quality:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnQuality.setLayout(new BoxLayout(pnQuality, BoxLayout.Y_AXIS));
			pnQuality.add(getPanel_1_1());
			pnQuality.add(getPnStars());
		}
		return pnQuality;
	}

	private JCheckBox getChckbxBreakfast() {
		if (chckbxBreakfast == null) {
			chckbxBreakfast = new JCheckBox("Breakfast");
			chckbxBreakfast.setToolTipText("Show opnly stablishments which allow breakfast.");
			chckbxBreakfast.setMnemonic('B');
			chckbxBreakfast.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return chckbxBreakfast;
	}

	private JPanel getPnStars() {
		if (pnStars == null) {
			pnStars = new JPanel();
			pnStars.add(getLblStars());
			pnStars.add(getSpinner());
		}
		return pnStars;
	}

	private JLabel getLblStars() {
		if (lblStars == null) {
			lblStars = new JLabel("Stars:");
			lblStars.setDisplayedMnemonic('S');
			lblStars.setLabelFor(getSpinner());
		}
		return lblStars;
	}

	private JSpinner getSpinner() {
		if (spinner == null) {
			spinner = new JSpinner();
			spinner.setToolTipText("Filter by the number of the stars of the stablishment.");
			spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		}
		return spinner;
	}

	private JPanel getPanel_1_1() {
		if (pnBreakfast == null) {
			pnBreakfast = new JPanel();
			pnBreakfast.add(getChckbxBreakfast());
		}
		return pnBreakfast;
	}

	private JPanel getPanel_1_2() {
		if (pnPrice == null) {
			pnPrice = new JPanel();
			pnPrice.setBorder(new TitledBorder(null, "Price:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnPrice.setLayout(new BoxLayout(pnPrice, BoxLayout.Y_AXIS));
			pnPrice.add(getPnMin());
		}
		return pnPrice;
	}

	private JPanel getPnMin() {
		if (pnMin == null) {
			pnMin = new JPanel();
			pnMin.add(getChckbxMax());
			pnMin.add(getSliderMax());
			pnMin.add(getSpnMax());
		}
		return pnMin;
	}

	private JCheckBox getChckbxMax() {
		if (chckbxMax == null) {
			chckbxMax = new JCheckBox("Max. Price");
			chckbxMax.setToolTipText("Allow to filter by price.");
			chckbxMax.setMnemonic('x');
			chckbxMax.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					if (chckbxMax.isSelected()) {
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
			sliderMax.setToolTipText("Filter by price.");
			sliderMax.setPreferredSize(new Dimension(225, 50));
			sliderMax.setMaximum(1500);
			sliderMax.setEnabled(false);
			sliderMax.setPaintTicks(true);
			sliderMax.setPaintLabels(true);
			sliderMax.setMinorTickSpacing(250);
			sliderMax.setMajorTickSpacing(500);
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
			spnMax = new JSpinner();
			spnMax.setToolTipText("Filter by price.");
			spnMax.setEnabled(false);
			spnMax.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(1500), new Integer(10)));
			spnMax.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					sliderMax.setValue(Integer.parseInt(spnMax.getValue().toString()));
				}
			});
		}
		return spnMax;
	}

	private String getSelectedName() {
		int row = table.getSelectedRow();
		if (row != -1) {
			return (String) table.getValueAt(row, 0);
		}
		return "";
	}

	private void filter() {
		List<Accommodation> newList = mW.db.getAccommodations();
		newList = mW.db.filterAccByCountry(newList, cbLocalisation.getSelectedItem().toString());
		newList = mW.db.filterAccByThemePark(newList, cbThemePark.getSelectedItem().toString());
		newList = mW.db.filterAccByType(newList, chckbxApartahotel.isSelected(), chckbxHotel.isSelected(),
				chckbxMotel.isSelected());

		if (chckbxMax.isSelected()) {
			newList = mW.db.filterAccByPrice(newList, (Integer) spnMax.getValue());
		}
		String[] columns = { "Name", "Type", "Category", "Park", "capacity", "Price" };
		accommodationsModel = new NotEditableModel(columns, 0);
		addRows(newList);
		table.setModel(accommodationsModel);
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
			btnApply = new JButton("Apply Filters");
			btnApply.setToolTipText("Apply the previous filters");
			btnApply.setMnemonic('F');
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
			btnHelp.setToolTipText("Open the help.");
			btnHelp.setMnemonic('e');
		}
		return btnHelp;
	}
}
