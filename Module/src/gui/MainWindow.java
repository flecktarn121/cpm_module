package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jvnet.substance.SubstanceLookAndFeel;

import logic.Accommodation;
import logic.Database;
import logic.Package;
import logic.Shoppable;
import logic.ShoppingCart;
import logic.ThemePark;
import logic.Ticket;
import logic.WrongInputException;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.Toolkit;

public class MainWindow extends JFrame {

	private static final int ICON_HEIGH = 500;

	private static final int ICON_WIDTH = 1000;

	/**
	 * TODO:
	 * - Fix the calculations for final prices
	 * - Change the name of the bill
	 * - Check an exception that is eventually thrown
	 * - Extend the helpset
	 * - Add the option to exit and retart the application to the menu
	 * - Try to iniciate the third dialogs with the current time.
	 * - Fix the god damm discount.
	 * - Be carefull with the accommodation capacity
	 */
	private static final long serialVersionUID = 1L;

	private JPanel pnMain;
	private JPanel pnPresentation;
	private JButton btnBegin;
	private JPanel pnHome;
	private JPanel pnCenter;
	private JPanel pnButtons;
	private JButton btnConfirm;
	private JButton btnRestart;
	private JPanel pnSelection;
	private JButton btnAccommodations;
	private JButton btnPackages;
	private JButton btnParks;
	private JPanel pnData;
	private JPanel pnAcceptAndCancel;
	private JButton btnAccept;
	private JButton btnCancel;
	private JPanel pnRegistration;
	private JPanel pnTitle;
	private JLabel lblTitle;
	private JPanel pnFields;
	private JPanel pnName;
	private JLabel lblName;
	private JTextField txtName;
	private JPanel pnSurname;
	private JLabel lblSurname;
	private JTextField txtSurname;
	private JPanel pnNIF;
	private JLabel lblNif;
	private JTextField txtNIF;
	private JButton btnConfirmData;
	private JScrollPane spBill;
	private JTextArea txtBill;
	Database db;
	private ShoppingCart shoppingCart;
	private JDialog accommodationCatalog;
	private JDialog parkCatalog;
	MainWindow mw = this;
	private JScrollPane spCart;
	JList cart;
	private DefaultListModel<String> cartModel = null;
	private JDialog packageCalog;
	private JPanel pnInformation;
	private JPanel pnButtonInfo;
	private JButton btnEdit;
	private JScrollPane scpInfo;
	private JTextArea txtInfo;
	private JPanel pnBegin;
	private JPanel panel_1;
	private JLabel lblShoppingCart;
	private JPanel pnConfirmation;
	private JPanel pnFinalText;
	private JPanel pnFinalButton;
	private JButton btnDone;
	private JButton btnRemove;
	private JPanel pnIcon;
	private JLabel lblIcon;
	private JMenuBar menuBar;
	private JMenu mnHelp;
	private JMenuItem mntmUserSupport;
	private JSeparator separator;
	private JMenuItem mntmAbout;
	private HelpBroker hb;

	private HelpSet hs;
	private JPanel pnSuccess;
	private JLabel lblSuccess;
	private JPanel pnFinalIcon;
	private JLabel lblFinalIcon;

	//TODO: 
	//
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);
					SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.CremeCoffeeSkin");
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/img/logo_title.png")));
		setTitle("Preparing the holidays");
		db = new Database();

		shoppingCart = new ShoppingCart(db);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		setJMenuBar(getMenuBar_1());
		pnMain = new JPanel();
		pnMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnMain);
		pnMain.setLayout(new CardLayout(0, 0));
		pnMain.add(getPnPresentation(), "pnPresentation");
		pnMain.add(getPnHome(), "pnHome");
		pnMain.add(getPnData(), "pnData");
		pnMain.add(getPnConfirmation(), "pnConfirmation");
		cargaAyuda();
		try {
			db.loadDatabase("src/file/paquetes.dat", "src/file/alojamientos.dat", "src/file/tematicos.dat",
					"src/file/entradas.dat");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Internal error", "There has been a problem loading the database.",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

	}

	private JPanel getPnPresentation() {
		if (pnPresentation == null) {
			pnPresentation = new JPanel();
			pnPresentation.setLayout(new BorderLayout(0, 0));
			pnPresentation.add(getPnBegin(), BorderLayout.SOUTH);
			pnPresentation.add(getPnIcon(), BorderLayout.CENTER);
		}
		return pnPresentation;
	}

	private JButton getBtnBegin() {
		if (btnBegin == null) {
			btnBegin = new JButton("Begin");
			btnBegin.setMnemonic('B');
			btnBegin.setToolTipText("Start with your purchase.");
			btnBegin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout) pnMain.getLayout()).show(pnMain, "pnHome");
				}
			});
		}
		return btnBegin;
	}

	private JPanel getPnHome() {
		if (pnHome == null) {
			pnHome = new JPanel();
			pnHome.setLayout(new BorderLayout(0, 0));
			pnHome.add(getPnButtons(), BorderLayout.SOUTH);
			pnHome.add(getPnCenter(), BorderLayout.CENTER);
			pnHome.add(getPnInformation(), BorderLayout.EAST);
			pnHome.add(getPanel_1(), BorderLayout.NORTH);
		}
		return pnHome;
	}

	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setLayout(new BorderLayout(0, 0));
			pnCenter.add(getPnSelection(), BorderLayout.SOUTH);
			pnCenter.add(getSpCart(), BorderLayout.CENTER);
		}
		return pnCenter;
	}

	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnButtons.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnButtons.add(getBtnConfirm());
			pnButtons.add(getBtnRestart());
		}
		return pnButtons;
	}

	private JButton getBtnConfirm() {
		if (btnConfirm == null) {
			btnConfirm = new JButton("Confirm");
			btnConfirm.setMnemonic('n');
			btnConfirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout) pnMain.getLayout()).show(pnMain, "pnData");
				}
			});
			btnConfirm.setToolTipText("Confirm your request.");
			btnConfirm.setEnabled(false);
		}
		return btnConfirm;
	}

	private JButton getBtnRestart() {
		if (btnRestart == null) {
			btnRestart = new JButton("Restart");
			btnRestart.setMnemonic('s');
			btnRestart.setToolTipText("Restart the application.");
			btnRestart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// TODO: the whole application needs to be restarted
					((CardLayout) pnMain.getLayout()).show(pnMain, "pnPresentation");
					shoppingCart.restart();
					cartModel.removeAllElements();
					txtInfo.setText("");
					getTxtName().setText("");
					getTxtSurname().setText("");
					getTxtNIF().setText("");
					getTxtBill().setText("");
					btnConfirmData.setEnabled(false);
					getBtnConfirm().setEnabled(false);

				}
			});
		}
		return btnRestart;
	}

	private JPanel getPnSelection() {
		if (pnSelection == null) {
			pnSelection = new JPanel();
			pnSelection.add(getBtnAccommodations());
			pnSelection.add(getBtnPackages());
			pnSelection.add(getBtnParks());
		}
		return pnSelection;
	}

	private JButton getBtnAccommodations() {
		if (btnAccommodations == null) {
			btnAccommodations = new JButton("Accommodations");
			btnAccommodations.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					accommodationCatalog = new AccommCatalog(mw);
					accommodationCatalog.setVisible(true);
				}
			});
			btnAccommodations.setMnemonic('c');
			btnAccommodations.setToolTipText("See the list of accommodatios");
		}
		return btnAccommodations;

	}

	private JButton getBtnPackages() {
		if (btnPackages == null) {
			btnPackages = new JButton("Packages");
			btnPackages.setToolTipText("See the list of available packages.");
			btnPackages.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					packageCalog = new PackageCatalog(mw);
					packageCalog.setVisible(true);

				}
			});
			btnPackages.setMnemonic('P');
			btnPackages.setPreferredSize(new Dimension(153, 25));
		}
		return btnPackages;
	}

	private JButton getBtnParks() {
		if (btnParks == null) {
			btnParks = new JButton("Parks");
			btnParks.setToolTipText("See the list of available parks.");
			btnParks.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parkCatalog = new ParkCatalog(mw);
					parkCatalog.setVisible(true);
				}
			});
			btnParks.setMnemonic('r');
			btnParks.setPreferredSize(new Dimension(153, 25));
		}
		return btnParks;
	}

	private JPanel getPnData() {
		if (pnData == null) {
			pnData = new JPanel();
			pnData.setLayout(new BorderLayout(0, 0));
			pnData.add(getPnAcceptAndCancel(), BorderLayout.SOUTH);
			pnData.add(getPnRegistration(), BorderLayout.CENTER);
			pnData.add(getPnTitle(), BorderLayout.NORTH);
		}
		return pnData;
	}

	private JPanel getPnAcceptAndCancel() {
		if (pnAcceptAndCancel == null) {
			pnAcceptAndCancel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnAcceptAndCancel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnAcceptAndCancel.add(getBtnAccept());
			pnAcceptAndCancel.add(getBtnCancel());
		}
		return pnAcceptAndCancel;
	}

	private JButton getBtnAccept() {
		if (btnAccept == null) {
			btnAccept = new JButton("Accept");
			btnAccept.setToolTipText("Proceed with the reservation");
			btnAccept.setMnemonic('p');
			btnAccept.setEnabled(false);
			btnAccept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) pnMain.getLayout()).show(pnMain, "pnConfirmation");
					db.save("bill.txt", getTxtBill().getText());
				}
			});
		}
		return btnAccept;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setToolTipText("Return to the cart.");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) pnMain.getLayout()).show(pnMain, "pnHome");
					getTxtName().setText("");
					getTxtSurname().setText("");
					getTxtNIF().setText("");
					getTxtBill().setText("");
					btnConfirmData.setEnabled(false);
				}
			});
			btnCancel.setMnemonic('l');
		}
		return btnCancel;
	}

	private JPanel getPnRegistration() {
		if (pnRegistration == null) {
			pnRegistration = new JPanel();
			pnRegistration.setLayout(new BorderLayout(0, 0));
			pnRegistration.add(getPanel_1_1(), BorderLayout.NORTH);
			pnRegistration.add(getSpBill(), BorderLayout.CENTER);
		}
		return pnRegistration;
	}

	private JPanel getPnTitle() {
		if (pnTitle == null) {
			pnTitle = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnTitle.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnTitle.add(getLblTitle());
		}
		return pnTitle;
	}

	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Registration Data");
			lblTitle.setFont(new Font("Dialog", Font.BOLD, 20));
		}
		return lblTitle;
	}

	private JPanel getPanel_1_1() {
		if (pnFields == null) {
			pnFields = new JPanel();
			pnFields.add(getPanel_1_2());
			pnFields.add(getPanel_1_3());
			pnFields.add(getPnNIF());
			pnFields.add(getBtnConfirmData());
		}
		return pnFields;
	}

	private JPanel getPanel_1_2() {
		if (pnName == null) {
			pnName = new JPanel();
			pnName.add(getLblName());
			pnName.add(getTxtName());
		}
		return pnName;
	}

	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Name:");
			lblName.setLabelFor(getTxtName());
			lblName.setDisplayedMnemonic('N');
		}
		return lblName;
	}

	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setColumns(10);
		}
		return txtName;
	}

	private JPanel getPanel_1_3() {
		if (pnSurname == null) {
			pnSurname = new JPanel();
			pnSurname.add(getLblSurname());
			pnSurname.add(getTxtSurname());
		}
		return pnSurname;
	}

	private JLabel getLblSurname() {
		if (lblSurname == null) {
			lblSurname = new JLabel("Surname:");
			lblSurname.setLabelFor(getTxtSurname());
			lblSurname.setDisplayedMnemonic('S');
		}
		return lblSurname;
	}

	private JTextField getTxtSurname() {
		if (txtSurname == null) {
			txtSurname = new JTextField();
			txtSurname.setColumns(10);
		}
		return txtSurname;
	}

	private JPanel getPnNIF() {
		if (pnNIF == null) {
			pnNIF = new JPanel();
			pnNIF.add(getLblNif());
			pnNIF.add(getTxtNIF());
		}
		return pnNIF;
	}

	private JLabel getLblNif() {
		if (lblNif == null) {
			lblNif = new JLabel("NIF:");
			lblNif.setLabelFor(getTxtNIF());
			lblNif.setDisplayedMnemonic('F');
		}
		return lblNif;
	}

	private JTextField getTxtNIF() {
		if (txtNIF == null) {
			txtNIF = new JTextField();
			txtNIF.setColumns(10);
		}
		return txtNIF;
	}

	private JButton getBtnConfirmData() {
		if (btnConfirmData == null) {
			btnConfirmData = new JButton("Confirma Data");
			btnConfirmData.setToolTipText("Confirm your personal data.");
			btnConfirmData.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (txtName.getText().equals("") || txtSurname.getText().equals("")
							|| txtNIF.getText().equals("")) {
						JOptionPane.showMessageDialog(mw, "Please introduce all the necessary data.", "Missing data",
								JOptionPane.WARNING_MESSAGE);
					} else {
						shoppingCart.setCustomresName(getTxtName().getText() + " " + getTxtSurname().getText());
						shoppingCart.setCustomersNIF(getTxtNIF().getText());
						txtBill.append(shoppingCart.printBill());
						getBtnAccept().setEnabled(true);
					}
				}
			});
			btnConfirmData.setMnemonic('C');
		}
		return btnConfirmData;
	}

	private JScrollPane getSpBill() {
		if (spBill == null) {
			spBill = new JScrollPane();
			spBill.setViewportView(getTxtBill());
		}
		return spBill;
	}

	private JTextArea getTxtBill() {
		if (txtBill == null) {
			txtBill = new JTextArea();
			txtBill.setWrapStyleWord(true);
			txtBill.setLineWrap(true);
			txtBill.setEditable(false);
		}
		return txtBill;
	}

	private JScrollPane getSpCart() {
		if (spCart == null) {
			spCart = new JScrollPane();
			spCart.setViewportView(getCart());
		}
		return spCart;
	}

	private JList getCart() {
		if (cart == null) {
			cartModel = new DefaultListModel<String>();
			cartModel.addListDataListener(new ConfirmationListener());
			cart = new JList(cartModel);
			cart.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent arg0) {
					if (cart.getSelectedValue() != null) {
						getTxtInfo().setText("");
						getTxtInfo().append(shoppingCart.getShoppableByShoopingName(cart.getSelectedValue().toString())
								.displayContents());
						getBtnEdit().setEnabled(true);
						getBtnRemove().setEnabled(true);
						getBtnConfirm().setEnabled(true);
					} else {
						getTxtInfo().setText("");
					}
				}
			});

		}
		return cart;
	}

	void addAcc(Accommodation acc, int numberOfAdults, int numberOfChildren, String initialDate, int numberOfDays,
			int numBerOfReservations) throws WrongInputException {
		for (int i = 0; i < numBerOfReservations; i++) {
			Accommodation newAcc = new Accommodation(acc.getCode(), acc.getType(), acc.getCategory(), acc.getName(),
					acc.getThemeParkCode(), acc.getCapacity(), acc.getPrice());
			shoppingCart.addAccommodation(newAcc, numberOfAdults, numberOfChildren, initialDate, numberOfDays);
			cartModel.addElement(newAcc.getShoppingName());
		}

	}

	public void addPark(ThemePark park, int numberOfAdults, int numberOfChildren, String date, int numberOfDays,
			int numberOfReservations) throws WrongInputException {
		Ticket ticket = db.getTicketByCode(park.getCode());
		for (int i = 0; i < numberOfReservations; i++) {

			Ticket newTicket = new Ticket(ticket.getCode(), ticket.getParkCode(), ticket.getAdultPrice(),
					ticket.getChildrenPrice());
			shoppingCart.addTicket(newTicket, numberOfChildren, numberOfAdults, date, numberOfDays);
			cartModel.addElement(newTicket.getShoppingName());
		}

	}

	public void addPack(Package pack, int numberOfAdults, int numberOfChildren, String date, int numberOfDays,
			int numberOfReservations) throws WrongInputException {
		for (int i = 0; i < numberOfReservations; i++) {
			Package newPack = new Package(pack.getCode(), pack.getName(), pack.getParkCode(),
					pack.getAccommodationCode(), pack.getDays(), pack.getAdultPrice(), pack.getChildrenPrice());
			shoppingCart.addPackage(newPack, numberOfChildren, numberOfAdults, date, numberOfDays);
			cartModel.addElement(newPack.getShoppingName());
		}

	}

	private void setAdaptedImage(JLabel label, String rutaImagen) {
		Image imgOriginal = new ImageIcon(this.getClass().getResource(rutaImagen)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance((int) (label.getPreferredSize().getWidth()),
				(int) (label.getPreferredSize().getHeight()), Image.SCALE_FAST);
		label.setIcon(new ImageIcon(imgEscalada));
		label.setVisible(true);
	}

	private JPanel getPnInformation() {
		if (pnInformation == null) {
			pnInformation = new JPanel();
			pnInformation.setLayout(new BorderLayout(0, 0));
			pnInformation.add(getPnButtonInfo(), BorderLayout.NORTH);
			pnInformation.add(getScpInfo());
		}
		return pnInformation;
	}

	private JPanel getPnButtonInfo() {
		if (pnButtonInfo == null) {
			pnButtonInfo = new JPanel();
			FlowLayout fl_pnButtonInfo = new FlowLayout(FlowLayout.CENTER, 5, 5);
			fl_pnButtonInfo.setAlignOnBaseline(true);
			pnButtonInfo.setLayout(fl_pnButtonInfo);
			pnButtonInfo.add(getBtnEdit());
			pnButtonInfo.add(getBtnRemove());
		}
		return pnButtonInfo;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("Edit");
			btnEdit.setToolTipText("Edit your purchase.");
			btnEdit.setMnemonic('d');
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Shoppable item = shoppingCart.getShoppableByShoopingName(getCart().getSelectedValue().toString());
					if (item instanceof Accommodation) {
						new AccommodationProcessor(mw, (Accommodation) item, true).setVisible(true);
					} else if (item instanceof Package) {
						new PackageProcessor(mw, (Package) item, true).setVisible(true);
					}
				}
			});
			btnEdit.setEnabled(false);
		}
		return btnEdit;
	}

	private JScrollPane getScpInfo() {
		if (scpInfo == null) {
			scpInfo = new JScrollPane();
			scpInfo.setPreferredSize(new Dimension(200, 700));
			scpInfo.setViewportView(getTxtInfo());
		}
		return scpInfo;
	}

	private JTextArea getTxtInfo() {
		if (txtInfo == null) {
			txtInfo = new JTextArea();
			txtInfo.setEditable(false);
		}
		return txtInfo;
	}

	private JPanel getPnBegin() {
		if (pnBegin == null) {
			pnBegin = new JPanel();
			pnBegin.add(getBtnBegin());
		}
		return pnBegin;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panel_1.add(getLblShoppingCart());
		}
		return panel_1;
	}

	private JLabel getLblShoppingCart() {
		if (lblShoppingCart == null) {
			lblShoppingCart = new JLabel("Shopping Cart");
			lblShoppingCart.setFont(new Font("Dialog", Font.BOLD, 20));
		}
		return lblShoppingCart;
	}

	class ConfirmationListener implements ListDataListener {

		@Override
		public void contentsChanged(ListDataEvent arg0) {
			if (cartModel.isEmpty()) {
				getBtnConfirm().setEnabled(false);
			} else {
				getBtnConfirm().setEnabled(true);
			}

		}

		@Override
		public void intervalAdded(ListDataEvent arg0) {
			if (cartModel.isEmpty()) {
				getBtnConfirm().setEnabled(false);
			} else {
				getBtnConfirm().setEnabled(true);
			}

		}

		@Override
		public void intervalRemoved(ListDataEvent arg0) {
			if (cartModel.isEmpty()) {
				getBtnConfirm().setEnabled(false);
			} else {
				getBtnConfirm().setEnabled(true);
			}

		}

	}

	private void updateCart() {
		getTxtInfo().setText("");
		cartModel.removeAllElements();
		for (Shoppable item : shoppingCart.getCart()) {
			cartModel.addElement(item.getShoppingName());
		}
	}

	public void editAcc(int numberOfAdults, int numberOfChildren, String date, int numberOfDays)
			throws WrongInputException {
		shoppingCart.editAccommodation(numberOfAdults, numberOfChildren, date, numberOfDays,
				getCart().getSelectedValue().toString());
		updateCart();

	}

	public void editPack(int numberOfAdults, int numberOfChildren, String date, int numberOfDays)
			throws WrongInputException {
		shoppingCart.editPack(numberOfAdults, numberOfChildren, date, numberOfDays,
				getCart().getSelectedValue().toString());
		updateCart();

	}

	public void editPark(int numberOfAdults, int numberOfChildren, String date, int numberOfDays)
			throws WrongInputException {
		shoppingCart.editPark(numberOfAdults, numberOfChildren, date, numberOfDays,
				getCart().getSelectedValue().toString());
		updateCart();

	}

	private JPanel getPnConfirmation() {
		if (pnConfirmation == null) {
			pnConfirmation = new JPanel();
			pnConfirmation.setLayout(new BorderLayout(0, 0));
			pnConfirmation.add(getPnFinalText(), BorderLayout.CENTER);
			pnConfirmation.add(getPnFinalButton(), BorderLayout.SOUTH);
		}
		return pnConfirmation;
	}

	private JPanel getPnFinalText() {
		if (pnFinalText == null) {
			pnFinalText = new JPanel();
			pnFinalText.setLayout(new BorderLayout(0, 0));
			pnFinalText.add(getPnSuccess(), BorderLayout.SOUTH);
			pnFinalText.add(getPnFinalIcon(), BorderLayout.CENTER);
		}
		return pnFinalText;
	}

	private JPanel getPnFinalButton() {
		if (pnFinalButton == null) {
			pnFinalButton = new JPanel();
			pnFinalButton.add(getBtnDone());
		}
		return pnFinalButton;
	}

	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton("Done!");
			btnDone.setToolTipText("Finish your purchase");
			btnDone.setMnemonic('D');
			btnDone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout) pnMain.getLayout()).show(pnMain, "pnPresentation");
					shoppingCart.restart();
					cartModel.removeAllElements();
					txtInfo.setText("");
				}
			});
		}
		return btnDone;
	}

	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("Remove");
			btnRemove.setMnemonic('m');
			btnRemove.setToolTipText("Delete the selected product.");
			btnRemove.setEnabled(false);
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Shoppable item = shoppingCart.getShoppableByShoopingName(getCart().getSelectedValue().toString());
					shoppingCart.remove(item);
					cartModel.removeElementAt(getCart().getSelectedIndex());
				}
			});
		}
		return btnRemove;
	}

	private JPanel getPnIcon() {
		if (pnIcon == null) {
			pnIcon = new JPanel();
			pnIcon.add(getLblIcon());
		}
		return pnIcon;
	}

	private JLabel getLblIcon() {
		if (lblIcon == null) {
			lblIcon = new JLabel("");
			lblIcon.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGH));
			setAdaptedImage(lblIcon, "/img/logo.png");
		}
		return lblIcon;
	}

	private void cargaAyuda() {

		URL hsURL;
		HelpSet hs;

		try {
			File fichero = new File("help/Ayuda.hs");
			hsURL = fichero.toURI().toURL();
			hs = new HelpSet(null, hsURL);
		}

		catch (Exception e) {
			System.out.println("Ayuda no encontrada");
			return;
		}

		HelpBroker hb = hs.createHelpBroker();
		this.hb = hb;
		this.hs = hs;

		hb.enableHelpKey(getRootPane(), "welcome", hs);
		hb.enableHelpOnButton(getMntmUserSupport(), "welcome", hs);
		hb.enableHelp(getMntmUserSupport(), "welcome", hs);
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnHelp());
		}
		return menuBar;
	}

	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Help");
			mnHelp.setMnemonic('e');
			mnHelp.add(getMntmUserSupport());
			mnHelp.add(getSeparator());
			mnHelp.add(getMntmAbout());
		}
		return mnHelp;
	}

	private JMenuItem getMntmUserSupport() {
		if (mntmUserSupport == null) {
			mntmUserSupport = new JMenuItem("User support");
			mntmUserSupport.setMnemonic('s');
		}
		return mntmUserSupport;
	}

	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}

	private JMenuItem getMntmAbout() {
		if (mntmAbout == null) {
			mntmAbout = new JMenuItem("About");
			mntmAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(mw,
							"Application developed by Ã�ngel GarcÃ­a MenÃ©ndez. " + "\n" + "Contact: uo258654@uniovi.es");
				}
			});
			mntmAbout.setMnemonic('b');
		}
		return mntmAbout;
	}

	HelpBroker getHelpBroker() {
		return hb;
	}

	HelpSet getHelpSet() {
		return hs;
	}

	private JPanel getPnSuccess() {
		if (pnSuccess == null) {
			pnSuccess = new JPanel();
			pnSuccess.add(getLblSuccess());
		}
		return pnSuccess;
	}

	private JLabel getLblSuccess() {
		if (lblSuccess == null) {
			lblSuccess = new JLabel(" You have succesfully completed your reservation");
			lblSuccess.setFont(new Font("Dialog", Font.BOLD, 18));
		}
		return lblSuccess;
	}

	private JPanel getPnFinalIcon() {
		if (pnFinalIcon == null) {
			pnFinalIcon = new JPanel();
			pnFinalIcon.add(getLblFinalIcon());
		}
		return pnFinalIcon;
	}

	private JLabel getLblFinalIcon() {
		if (lblFinalIcon == null) {
			lblFinalIcon = new JLabel("");
			lblFinalIcon.setPreferredSize(new Dimension(ICON_WIDTH, ICON_HEIGH));
			setAdaptedImage(lblFinalIcon, "/img/logo.png");
		}
		return lblFinalIcon;
	}
}
