/*
 * 
 */
package fvsl.memory.client.pages.createlobby;
/**
 * @author Stefano Leggio
 *
 */
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import fvsl.memory.client.pages.Page;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Request.LobbyCreationResult;
import fvsl.memory.common.util.StringResources;

// TODO: Auto-generated Javadoc
/**
 * This page allow to choose all lobby and game settings like (lobby name, turn timer, players number, pairs number,lobby password)  
 */
public class CreateLobbyPageView extends Page {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2666672662443557135L;

	/** The model. */
	private CreateLobbyPageModel model;

	/** The controller. */
	private CreateLobbyPageController controller;

	/**
	 * Instantiates a new creates the lobby page view.
	 */
	public CreateLobbyPageView() {
		super();
		controller = new CreateLobbyPageController();
	}

	/** The crea button. */
	private JButton creaButton;

	/** The back button. */
	private JButton backButton;

	/** The lobby name field. */
	private JTextField lobbyNameField;

	/** The password field. */
	private JTextField passwordField;

	/** The n g combo. */
	private JComboBox<Integer> nGCombo;

	/** The n coppie combo. */
	private JComboBox<Integer> nCoppieCombo;

	/** The timer combo. */
	private JComboBox<Integer> timerCombo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see fvsl.memory.client.pages.Page#loadComponents()
	 */
	@Override
	protected void loadComponents() {
		/**
		 * Setting up panels
		 */
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		JPanel settingPanel = new JPanel();
		JPanel titlePanel = new JPanel();
		panel.add(titlePanel, BorderLayout.NORTH);
		panel.add(settingPanel, BorderLayout.CENTER);
		settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.PAGE_AXIS));
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.LINE_AXIS));
		panel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(1, 2));

		lobbyNameField = new JTextField();
		lobbyNameField.setBorder((new TitledBorder(new EtchedBorder(), StringResources.loName.toString())));
		lobbyNameField.setColumns(10);
		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBorder((new TitledBorder(new EtchedBorder(), StringResources.psw.toString())));
		// I valori di default dovrebbe prenderli da server
		nGCombo = new JComboBox<Integer>();
		nGCombo.setBorder((new TitledBorder(new EtchedBorder(), StringResources.playerNum.toString())));
		nCoppieCombo = new JComboBox<Integer>();
		nCoppieCombo.setBorder((new TitledBorder(new EtchedBorder(), StringResources.coupleNum.toString())));
		timerCombo = new JComboBox<Integer>();
		timerCombo.setBorder((new TitledBorder(new EtchedBorder(), StringResources.timer.toString())));
		creaButton = new JButton(StringResources.createLo.toString());
		backButton = new JButton(StringResources.back.toString());
		
		/**
		 * Adding Component 
		 */
		titlePanel.add(Box.createRigidArea(new Dimension(350, 30)));
		settingPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		settingPanel.add(lobbyNameField);
		settingPanel.add(Box.createRigidArea(new Dimension(35, 30)));
		settingPanel.add(nGCombo);
		settingPanel.add(Box.createRigidArea(new Dimension(35, 30)));
		settingPanel.add(nCoppieCombo);
		settingPanel.add(Box.createRigidArea(new Dimension(35, 30)));
		settingPanel.add(timerCombo);
		settingPanel.add(Box.createRigidArea(new Dimension(35, 30)));
		settingPanel.add(passwordField);
		settingPanel.add(Box.createRigidArea(new Dimension(35, 30)));
		buttonPanel.add(backButton);
		buttonPanel.add(creaButton);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fvsl.memory.client.pages.Page#setUpListeners()
	 */
	@Override
	protected void setUpListeners() {

		lobbyNameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				notifyProperty();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				notifyProperty();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				notifyProperty();
			}

			public void notifyProperty() {
				model.getLobby().setName(lobbyNameField.getText());
			}
		});

		passwordField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				notifyProperty();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				notifyProperty();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				notifyProperty();
			}

			public void notifyProperty() {
				model.setPassword(passwordField.getText());
			}
		});

		nGCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.getLobby().setNumberOfPlayers((Integer) nGCombo.getSelectedItem());
			}
		});

		nCoppieCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.getLobby().setNumberOfPairs((Integer) nCoppieCombo.getSelectedItem());
			}
		});

		timerCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.getLobby().setTurnTimer((Integer) timerCombo.getSelectedItem());
			}
		});

		creaButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LobbyCreationResult result = controller.attemptToCreateLobby(model.getLobby(), model.getPassword());

				if (!(result == LobbyCreationResult.Accepted)) {
					JOptionPane.showMessageDialog(container, StringResources.unJoinLo + result.toString());
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				controller.backToMainPage();

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fvsl.memory.client.pages.Page#loadData()
	 */
	@Override
	protected void loadData() {
		model = new CreateLobbyPageModel();
		model.setLobby(new Lobby("New Lobby", model.getPossiblePlayers()[0], model.getPossiblePairs()[0], model.getPossibleTimers()[0], null));
		// Prendi valori di "default" qui (dal controller) e mettili nel model
		// (vanno creati altri campi)
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fvsl.memory.client.pages.Page#populateViews()
	 */
	@Override
	protected void populateViews() {
		// Imposta i valori qui (li prendi dal model e li metti nella view)
		lobbyNameField.setText(model.getLobby().getName());
		passwordField.setText(model.getPassword());
		nGCombo.setModel(new DefaultComboBoxModel<Integer>(model.getPossiblePlayers()));
		nCoppieCombo.setModel(new DefaultComboBoxModel<Integer>(model.getPossiblePairs()));
		timerCombo.setModel(new DefaultComboBoxModel<Integer>(model.getPossibleTimers()));
	}

	/**
	 * Gets the controller.
	 * 
	 * @return the controller
	 */
	public CreateLobbyPageController getController() {
		return controller;
	}

	/**
	 * Sets the controller.
	 * 
	 * @param controller
	 *            the new controller
	 */
	public void setController(CreateLobbyPageController controller) {
		this.controller = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fvsl.memory.client.pages.Page#bufferize(java.lang.Object)
	 */
	@Override
	protected void bufferize(Object o) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fvsl.memory.client.pages.Page#onExit()
	 */
	@Override
	protected void onExit() {
		// TODO Auto-generated method stub

	}

}
