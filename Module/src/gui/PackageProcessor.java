package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import logic.Package;
import logic.WrongInputException;

public class PackageProcessor extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnNorth;
	private JPanel pnCenter;
	private JPanel pnSouth;
	private JPanel pnEast;
	private JLabel lblTitle;
	private JPanel pnInfo;
	private JButton btnBack;
	private MainWindow mW;
	private JDialog aC = this;
	private JPanel pnType;
	private Package pack;
	private JPanel pnAdults;
	private JLabel lblAdults;
	private JPanel pnChildren;
	private JLabel lblChildren;
	private JScrollPane scpRequest;
	private JTextArea txtRequest;
	private JPanel pnDate;
	private JPanel pnDays;
	private JLabel lblDays;
	private JComboBox<String> cbDays;
	private JPanel pnMonth;
	private JLabel lblMonth;
	private JComboBox<String> cbMonth;
	private JPanel pnYear;
	private JLabel lblYear;
	private JComboBox<Integer> cbYear;
	private JPanel pnDetails;
	private JPanel pnDaysOfStay;
	private JLabel lblDaysOfStay;
	private JSpinner spnDaysOfStay;
	private JPanel pnNumberOfReservations;
	private JLabel lblNOfReservations;
	private JSpinner spnRevervations;
	private JScrollPane scpDescription;
	private JTextArea textArea;
	private JPanel pnImage;
	private JLabel lblImage;
	private JButton btnConfirm;
	private JDialog thisDialog = this;
	private JSpinner spnAdults;
	private JSpinner spnChildren;
	private boolean isEdition;
	private JButton btnHelp;

	/**
	 * Create the dialog.
	 */
	public PackageProcessor(MainWindow mW, Package pack, boolean isEdition) {
		setModal(true);
		this.mW = mW;
		this.pack = pack;
		this.isEdition = isEdition;
		this.setTitle("Package reservation");
		setBounds(100, 100, 1280, 720);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnNorth(), BorderLayout.NORTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		getContentPane().add(getPnSouth(), BorderLayout.SOUTH);
		getContentPane().add(getPnEast(), BorderLayout.EAST);
		mW.getHelpBroker().enableHelpKey(getRootPane(), "pcR", mW.getHelpSet());
		mW.getHelpBroker().enableHelpOnButton(getBtnHelp(), "pcR", mW.getHelpSet());

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
			pnCenter.add(getPnInfo(), BorderLayout.NORTH);
			pnCenter.add(getScpRequest(), BorderLayout.SOUTH);
			pnCenter.add(getScpDescription(), BorderLayout.CENTER);
		}
		return pnCenter;
	}

	private JPanel getPnSouth() {
		if (pnSouth == null) {
			pnSouth = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnSouth.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnSouth.add(getBtnConfirm());
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
			lblTitle = new JLabel(pack.getName());
			lblTitle.setFont(new Font("Dialog", Font.BOLD, 20));
			lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblTitle;
	}

	private JPanel getPnInfo() {
		if (pnInfo == null) {
			pnInfo = new JPanel();
			FlowLayout fl_pnInfo = (FlowLayout) pnInfo.getLayout();
			fl_pnInfo.setAlignOnBaseline(true);
			fl_pnInfo.setAlignment(FlowLayout.LEFT);
			pnInfo.add(getPnImage());
			pnInfo.add(getPnType());
			pnInfo.add(getPnDate());
			pnInfo.add(getPnDetails());
		}
		return pnInfo;
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
			btnBack.setToolTipText("Return to the catalog.");
			btnBack.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return btnBack;
	}

	private JPanel getPnType() {
		if (pnType == null) {
			pnType = new JPanel();
			pnType.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Guests:", TitledBorder.LEADING,
					TitledBorder.TOP, null, new Color(51, 51, 51)));
			pnType.setLayout(new BoxLayout(pnType, BoxLayout.Y_AXIS));
			pnType.add(getPnAdults());
			pnType.add(getPanel_1_1());
		}
		return pnType;
	}

	private JPanel getPnAdults() {
		if (pnAdults == null) {
			pnAdults = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnAdults.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnAdults.add(getLblAdults());
			pnAdults.add(getSpnAdults());
		}
		return pnAdults;
	}

	private JLabel getLblAdults() {
		if (lblAdults == null) {
			lblAdults = new JLabel("Adults:");
			lblAdults.setDisplayedMnemonic('d');
		}
		return lblAdults;
	}

	private JPanel getPanel_1_1() {
		if (pnChildren == null) {
			pnChildren = new JPanel();
			pnChildren.add(getLblChildren());
			pnChildren.add(getSpnChildren());
		}
		return pnChildren;
	}

	private JLabel getLblChildren() {
		if (lblChildren == null) {
			lblChildren = new JLabel("Children:");
			lblChildren.setDisplayedMnemonic('C');
		}
		return lblChildren;
	}

	private JScrollPane getScpRequest() {
		if (scpRequest == null) {
			scpRequest = new JScrollPane();
			scpRequest.setViewportView(getTxtRequest());
			scpRequest.setPreferredSize(new Dimension(600, 100));
		}
		return scpRequest;
	}

	private JTextArea getTxtRequest() {
		if (txtRequest == null) {
			txtRequest = new JTextArea();
			txtRequest.setToolTipText("Write here your requests for the accommodation.");
			txtRequest.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtRequest.setText("");
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (txtRequest.getText().equals("")) {
						txtRequest.setText("Make here your requests...");
					}
				}
			});
			txtRequest.setLineWrap(true);
			txtRequest.setText("Make here your requests...");

		}
		return txtRequest;
	}

	private JPanel getPnDate() {
		if (pnDate == null) {
			pnDate = new JPanel();
			pnDate.setBorder(new TitledBorder(null, "Date:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnDate.setLayout(new BoxLayout(pnDate, BoxLayout.Y_AXIS));
			pnDate.add(getPnDays());
			pnDate.add(getPnMonth());
			pnDate.add(getPnYear());
		}
		return pnDate;
	}

	private JPanel getPnDays() {
		if (pnDays == null) {
			pnDays = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnDays.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnDays.add(getLblDays());
			pnDays.add(getCbDays());
		}
		return pnDays;
	}

	private JLabel getLblDays() {
		if (lblDays == null) {
			lblDays = new JLabel("Days:");
			lblDays.setLabelFor(getCbDays());
			lblDays.setDisplayedMnemonic('d');
		}
		return lblDays;
	}

	private JComboBox<String> getCbDays() {
		if (cbDays == null) {
			cbDays = new JComboBox<String>();
			cbDays.setToolTipText("Set the days.");
			getCbMonth();
			fillCbDays();
		}
		return cbDays;
	}

	private void fillCbDays() {

		int finalDay = 0;
		switch (getCbMonth().getSelectedItem().toString()) {
		case "January":
			finalDay = 31;
			break;
		case "February":
			finalDay = 28;
			break;
		case "March":
			finalDay = 31;
			break;
		case "April":
			finalDay = 30;
			break;
		case "May":
			finalDay = 31;
			break;
		case "June":
			finalDay = 30;
			break;
		case "July":
			finalDay = 31;
			break;
		case "August":
			finalDay = 31;
			break;
		case "September":
			finalDay = 30;
			break;
		case "October":
			finalDay = 31;
			break;
		case "November":
			finalDay = 30;
			break;
		case "December":
			finalDay = 31;
			break;
		}

		for (int i = 1; i <= finalDay; i++) {
			String value = "";
			if (i < 10) {
				value = "0" + i;
			} else {
				value = String.valueOf(i);
			}
			getCbDays().addItem(value);
		}

		getCbDays().setSelectedIndex(0);
		// set current day
		String[] date = new Date(System.currentTimeMillis()).toString().split("-");
		getCbDays().setSelectedIndex(Integer.parseInt(date[date.length - 1]));
	}

	private JPanel getPnMonth() {
		if (pnMonth == null) {
			pnMonth = new JPanel();
			pnMonth.add(getLblMonth());
			pnMonth.add(getCbMonth());
		}
		return pnMonth;
	}

	private JLabel getLblMonth() {
		if (lblMonth == null) {
			lblMonth = new JLabel("Month:");
			lblMonth.setLabelFor(getCbMonth());
			lblMonth.setDisplayedMnemonic('M');
		}
		return lblMonth;
	}

	private JComboBox<String> getCbMonth() {
		if (cbMonth == null) {
			cbMonth = new JComboBox<String>();
			cbMonth.setToolTipText("Set the month.");
			cbMonth.setModel(new DefaultComboBoxModel<String>(new String[] { "January", "February", "March", "April",
					"May", "June", "July", "August", "September", "October", "November", "December" }));
			// set current month
			String[] date = new Date(System.currentTimeMillis()).toString().split("-");
			System.out.println(new Date(System.currentTimeMillis()).toString());
			cbMonth.setSelectedIndex(Integer.parseInt(date[date.length - 2]));
			cbMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fillCbDays();
				}
			});
		}
		return cbMonth;
	}

	private JPanel getPnYear() {
		if (pnYear == null) {
			pnYear = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnYear.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnYear.add(getLblYear());
			pnYear.add(getCbYear());
		}
		return pnYear;
	}

	private JLabel getLblYear() {
		if (lblYear == null) {
			lblYear = new JLabel("Year:");
			lblYear.setLabelFor(getCbYear());
			lblYear.setDisplayedMnemonic('r');
		}
		return lblYear;
	}

	private JComboBox<Integer> getCbYear() {
		if (cbYear == null) {
			cbYear = new JComboBox<Integer>();
			cbYear.setToolTipText("Set the year.");
			for (int i = 2018; i < 2100; i++) {
				cbYear.addItem(i);
			}
		}
		return cbYear;
	}

	private JPanel getPnDetails() {
		if (pnDetails == null) {
			pnDetails = new JPanel();
			pnDetails.setBorder(new TitledBorder(null, "Details:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnDetails.setLayout(new BoxLayout(pnDetails, BoxLayout.Y_AXIS));
			pnDetails.add(getPnDaysOfStay());
			pnDetails.add(getPnNumberOfReservations());
		}
		return pnDetails;
	}

	private JPanel getPnDaysOfStay() {
		if (pnDaysOfStay == null) {
			pnDaysOfStay = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnDaysOfStay.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnDaysOfStay.add(getLblDaysOfStay());
			pnDaysOfStay.add(getSpnDaysOfStay());
		}
		return pnDaysOfStay;
	}

	private JLabel getLblDaysOfStay() {
		if (lblDaysOfStay == null) {
			lblDaysOfStay = new JLabel("Days of Stay:");
			lblDaysOfStay.setLabelFor(getSpnDaysOfStay());
			lblDaysOfStay.setDisplayedMnemonic('f');
		}
		return lblDaysOfStay;
	}

	private JSpinner getSpnDaysOfStay() {
		if (spnDaysOfStay == null) {
			spnDaysOfStay = new JSpinner();
			spnDaysOfStay.setToolTipText("Set the number of days.");
			spnDaysOfStay
					.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(365), new Integer(1)));
		}
		return spnDaysOfStay;
	}

	private JPanel getPnNumberOfReservations() {
		if (pnNumberOfReservations == null) {
			pnNumberOfReservations = new JPanel();
			pnNumberOfReservations.add(getLblNOfReservations());
			pnNumberOfReservations.add(getSpnRevervations());
		}
		return pnNumberOfReservations;
	}

	private JLabel getLblNOfReservations() {
		if (lblNOfReservations == null) {
			lblNOfReservations = new JLabel("N. of Reservations:");
			lblNOfReservations.setDisplayedMnemonic('s');
			lblNOfReservations.setLabelFor(getSpnRevervations());
		}
		return lblNOfReservations;
	}

	private JSpinner getSpnRevervations() {
		if (spnRevervations == null) {
			spnRevervations = new JSpinner();
			spnRevervations.setToolTipText("Stablish the number of reservations with the current data.");
			spnRevervations
					.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(100), new Integer(1)));
			if (isEdition) {
				spnRevervations.setEnabled(false);
			}
		}
		return spnRevervations;
	}

	private JScrollPane getScpDescription() {
		if (scpDescription == null) {
			scpDescription = new JScrollPane();
			scpDescription.setViewportView(getTextArea());
		}
		return scpDescription;
	}

	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setWrapStyleWord(true);
			textArea.setLineWrap(true);
			textArea.setFont(new Font("Dialog", Font.PLAIN, 18));
			textArea.setText("Name: " + pack.getName() + "\n");
			textArea.append("Park: " + mW.db.getParkByCode(pack.getParkCode()).getName() + "\n");
			textArea.append(
					"Accommodation: " + mW.db.getAccommodationByCode(pack.getAccommodationCode()).getName() + "\n");
			textArea.append("Price for children: " + pack.getChildrenPrice() + "\n");
			textArea.append("Price for adults: " + pack.getAdultPrice() + "\n");
			textArea.append("Park description: \n");
			textArea.append(mW.db.getParkByCode(pack.getParkCode()).getDescription());
		}
		return textArea;
	}

	private void setAdaptedImage(JLabel label, String rutaImagen) {
		Image imgOriginal = new ImageIcon(this.getClass().getResource(rutaImagen)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance((int) (label.getParent().getPreferredSize().getWidth()),
				(int) (label.getParent().getPreferredSize().getHeight()), Image.SCALE_FAST);
		label.setIcon(new ImageIcon(imgEscalada));
		label.setVisible(true);
	}

	private JPanel getPnImage() {
		if (pnImage == null) {
			pnImage = new JPanel();
			pnImage.add(getLblImage());
			lblImage.setPreferredSize(new Dimension(400, 150));
			setAdaptedImage(lblImage, pack.getImage());
		}
		return pnImage;
	}

	private JLabel getLblImage() {
		if (lblImage == null) {
			lblImage = new JLabel("");
		}
		return lblImage;
	}

	private JButton getBtnConfirm() {
		if (btnConfirm == null) {
			btnConfirm = new JButton("Confirm");
			btnConfirm.setToolTipText("COnfirm your purchase.");
			btnConfirm.setMnemonic('n');
			btnConfirm.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						addProduct();
						thisDialog.dispose();
					} catch (WrongInputException e) {
						JOptionPane.showMessageDialog(thisDialog, e.getMessage(), "Input error",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			});
		}
		return btnConfirm;
	}

	private void addProduct() throws WrongInputException {
		int numberOfChildren = (int) getSpnChildren().getValue();
		int numberOfAdults = (int) getSpnAdults().getValue();
		String date = getCbDays().getSelectedItem().toString();
		switch (getCbMonth().getSelectedItem().toString()) {
		case "January":
			date += "-01-";
			break;
		case "February":
			date += "-02-";
			break;
		case "March":
			date += "-03-";
			break;
		case "April":
			date += "-04-";
			break;
		case "May":
			date += "-05-";
			break;
		case "June":
			date += "-06-";
			break;
		case "July":
			date += "-07-";
			break;
		case "August":
			date += "-08-";
			break;
		case "September":
			date += "-09-";
			break;
		case "October":
			date += "-10";
			break;
		case "November":
			date += "-11-";
			break;
		case "December":
			date += "-12-";
			break;
		}
		date += getCbYear().getSelectedItem().toString();
		int numberOfDays = (int) getSpnDaysOfStay().getValue();
		int numberOfReservations = (int) getSpnRevervations().getValue();
		if (!isEdition) {
			mW.addPack(pack, numberOfAdults, numberOfChildren, date, numberOfDays, numberOfReservations);
		} else {
			mW.editPack(numberOfAdults, numberOfChildren, date, numberOfDays);
		}
	}

	private JSpinner getSpnAdults() {
		if (spnAdults == null) {
			spnAdults = new JSpinner(
					new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(100), new Integer(1)));
			spnAdults.setToolTipText("Set the number of adults.");
		}
		return spnAdults;
	}

	private JSpinner getSpnChildren() {
		if (spnChildren == null) {
			spnChildren = new JSpinner(
					new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(100), new Integer(1)));
			spnChildren.setToolTipText("Set the number of Children");
		}
		return spnChildren;
	}

	private JButton getBtnHelp() {
		if (btnHelp == null) {
			btnHelp = new JButton("Help");
			btnHelp.setToolTipText("See the help view");
			btnHelp.setMnemonic('e');
		}
		return btnHelp;
	}
}
