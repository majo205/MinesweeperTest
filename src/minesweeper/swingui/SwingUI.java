package minesweeper.swingui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import minesweeper.BestTimes;
import minesweeper.core.Field;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import minesweeper.Minesweeper;
import minesweeper.core.GameState;
import minesweeper.Settings;
import minesweeper.UserInterface;

public class SwingUI extends javax.swing.JFrame implements UserInterface,
		MouseListener {
	private Field field;
	private TileComponent[][] tileComponent;
	private BestTimes bestTimes;
	private javax.swing.Timer timer;

	public SwingUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		initComponents();

		setIconImage(new javax.swing.ImageIcon(getClass().getResource(
				"/img/logo.gif")).getImage());
		setVisible(true);

		if (Minesweeper.getInstance().getSetting().equals(Settings.BEGINNER)) {
			beginnerMenuItem.setSelected(true);
		} else if (Minesweeper.getInstance().getSetting()
				.equals(Settings.INTERMEDIATE)) {
			intermediateMenuItem.setSelected(true);
		} else if (Minesweeper.getInstance().getSetting()
				.equals(Settings.EXPERT)) {
			expertMenuItem.setSelected(true);
		}

		timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (field.getState() == GameState.NEW
						|| field.getState() == GameState.PLAYING) {
					setTimeLabelText();

				}
			}
		});
		timer.start();
	}

	public void newGameStarted(Field field) {
		this.field = field;

		contentPanel.removeAll();
		contentPanel.setLayout(new GridLayout(field.getRowCount(), field
				.getColumnCount()));

		tileComponent = new TileComponent[field.getRowCount()][field
				.getColumnCount()];

		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				tileComponent[row][column] = new TileComponent(field.getTile(
						row, column), row, column);

				contentPanel.add(tileComponent[row][column]);

				tileComponent[row][column].addMouseListener(this);
			}
		}
		update();
		pack();
		setLocationRelativeTo(null);
	}

	public void mousePressed(MouseEvent e) {
		if (field.getState() == GameState.PLAYING
				| field.getState() == GameState.NEW) {
			TileComponent button = (TileComponent) e.getSource();

			if (SwingUtilities.isLeftMouseButton(e)) {
				field.openTile(button.getRow(), button.getColumn());
			}
			if (SwingUtilities.isRightMouseButton(e)) {
				field.markTile(button.getRow(), button.getColumn());
			}
			if (e.getModifiersEx() == (MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK)) {
				field.openAdjacentTiles(button.getRow(), button.getColumn());
			}
		}

		if (field.getState() == GameState.FAILED) {
			JOptionPane.showMessageDialog(null, "Game over.",
					"Warning message", JOptionPane.ERROR_MESSAGE);
			for (int row = 0; row < field.getRowCount(); row++) {
				for (int column = 0; column < field.getColumnCount(); column++) {
					if (field.getTile(row, column) instanceof Mine) {
						field.openTile(row, column);
					}
				}
			}
		}
		if (field.getState() == GameState.SOLVED) {
			JOptionPane.showMessageDialog(null, "You have won your game.",
					"Warning message", JOptionPane.INFORMATION_MESSAGE);
			bestTimes = new BestTimes();
			bestTimes.addPlayerTime(System.getProperty("user.name"),
					field.getPlayingSeconds(), Minesweeper.getInstance()
							.getSetting().toString());
		}
		update();
	}

	private void setMinesLeftLabelText() {
		minesLeftLabel.setText(String.format("%03d",
				field.getRemainingMineCount()));
	}

	private void setTimeLabelText() {
		timeLabel.setText(String.format("%03d", field.getPlayingSeconds()));
	}

	public void update() {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				tileComponent[row][column].updateStyle();
			}
		}
		setMinesLeftLabelText();
	}

	// <editor-fold defaultstate="collapsed"
	// desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		buttonGroup = new javax.swing.ButtonGroup();
		jPanel1 = new javax.swing.JPanel();
		topPanel = new javax.swing.JPanel();
		infoPanel = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		minesLeftLabel = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		timeLabel = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		newButton = new javax.swing.JButton();
		contentPanel = new javax.swing.JPanel();
		menuBar = new javax.swing.JMenuBar();
		gameMenu = new javax.swing.JMenu();
		newMenuItem = new javax.swing.JMenuItem();
		saveMenuItem = new javax.swing.JMenuItem();
		loadMenuItem = new javax.swing.JMenuItem();
		helpMenuItem = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		customMenuItem = new javax.swing.JRadioButtonMenuItem();
		beginnerMenuItem = new javax.swing.JRadioButtonMenuItem();
		intermediateMenuItem = new javax.swing.JRadioButtonMenuItem();
		expertMenuItem = new javax.swing.JRadioButtonMenuItem();
		jSeparator3 = new javax.swing.JSeparator();
		bestTimesMenuItem = new javax.swing.JMenuItem();
		jSeparator2 = new javax.swing.JSeparator();
		exitMenuItem = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Minesweeper");
		setResizable(false);

		jPanel1.setLayout(new java.awt.BorderLayout());

		jPanel1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		topPanel.setLayout(new java.awt.BorderLayout());

		topPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				javax.swing.BorderFactory.createEmptyBorder(5, 5, 2, 5),
				javax.swing.BorderFactory
						.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
		infoPanel.setLayout(new java.awt.BorderLayout());

		infoPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4,
				4, 4));
		jPanel2.setLayout(new java.awt.BorderLayout());

		jPanel2.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		minesLeftLabel.setBackground(java.awt.Color.black);
		minesLeftLabel.setFont(new java.awt.Font("DialogInput", 1, 24));
		minesLeftLabel.setForeground(java.awt.Color.red);
		minesLeftLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		minesLeftLabel.setText("888");
		minesLeftLabel.setMaximumSize(new java.awt.Dimension(50, 30));
		minesLeftLabel.setMinimumSize(new java.awt.Dimension(50, 30));
		minesLeftLabel.setOpaque(true);
		minesLeftLabel.setPreferredSize(new java.awt.Dimension(50, 30));
		jPanel2.add(minesLeftLabel, java.awt.BorderLayout.CENTER);

		infoPanel.add(jPanel2, java.awt.BorderLayout.WEST);

		jPanel3.setLayout(new java.awt.BorderLayout());

		jPanel3.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		timeLabel.setBackground(java.awt.Color.black);
		timeLabel.setFont(new java.awt.Font("DialogInput", 1, 24));
		timeLabel.setForeground(java.awt.Color.red);
		timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		timeLabel.setText("888");
		timeLabel.setMaximumSize(new java.awt.Dimension(50, 30));
		timeLabel.setMinimumSize(new java.awt.Dimension(50, 30));
		timeLabel.setOpaque(true);
		timeLabel.setPreferredSize(new java.awt.Dimension(50, 30));
		jPanel3.add(timeLabel, java.awt.BorderLayout.CENTER);

		infoPanel.add(jPanel3, java.awt.BorderLayout.EAST);

		newButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/smile.gif")));
		newButton.setFocusPainted(false);
		newButton.setFocusable(false);
		newButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
		newButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newButtonActionPerformed(evt);
			}
		});

		jPanel4.add(newButton);

		infoPanel.add(jPanel4, java.awt.BorderLayout.CENTER);

		topPanel.add(infoPanel, java.awt.BorderLayout.CENTER);

		jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);

		contentPanel
				.setBorder(javax.swing.BorderFactory.createCompoundBorder(
						javax.swing.BorderFactory.createEmptyBorder(3, 5, 5, 5),
						javax.swing.BorderFactory
								.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
		jPanel1.add(contentPanel, java.awt.BorderLayout.CENTER);

		getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

		gameMenu.setMnemonic('g');
		gameMenu.setText("Game");
		newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F2, 0));
		newMenuItem.setMnemonic('n');
		newMenuItem.setText("New");
		newMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newMenuItemActionPerformed(evt);
			}
		});

		gameMenu.add(newMenuItem);

		saveMenuItem.setMnemonic('s');
		saveMenuItem.setText("Save");
		saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveMenuItemActionPerformed(evt);
			}
		});
		gameMenu.add(saveMenuItem);

		loadMenuItem.setMnemonic('l');
		loadMenuItem.setText("Load");
		loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadMenuItemActionPerformed(evt);
				update();
			}
		});
		gameMenu.add(loadMenuItem);
		
		gameMenu.add(helpMenuItem);

		helpMenuItem.setMnemonic('h');
		helpMenuItem.setText("Help");
		helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				helpMenuItemActionPerformed(evt);
				update();
			}
		});

		gameMenu.add(jSeparator1);

		buttonGroup.add(customMenuItem);
		customMenuItem.setMnemonic('c');
		customMenuItem.setText("Custom");
		customMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				customMenuItemActionPerformed(evt);
			}
		});

		gameMenu.add(customMenuItem);
		
		buttonGroup.add(beginnerMenuItem);
		beginnerMenuItem.setMnemonic('b');
		beginnerMenuItem.setText("Beginner");
		beginnerMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				beginnerMenuItemActionPerformed(evt);
			}
		});

		gameMenu.add(beginnerMenuItem);

		buttonGroup.add(intermediateMenuItem);
		intermediateMenuItem.setMnemonic('i');
		intermediateMenuItem.setText("Intermediate");
		intermediateMenuItem
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						intermediateMenuItemActionPerformed(evt);
					}
				});

		gameMenu.add(intermediateMenuItem);

		buttonGroup.add(expertMenuItem);
		expertMenuItem.setMnemonic('e');
		expertMenuItem.setText("Expert");
		expertMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				expertMenuItemActionPerformed(evt);
			}
		});

		gameMenu.add(expertMenuItem);

		gameMenu.add(jSeparator3);

		bestTimesMenuItem.setText("Best times...");
		gameMenu.add(bestTimesMenuItem);
		bestTimesMenuItem
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						bestTimesMenuItemActionPerformed(evt);
					}
				});

		gameMenu.add(jSeparator2);

		exitMenuItem.setMnemonic('e');
		exitMenuItem.setText("Exit");
		exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitMenuItemActionPerformed(evt);
			}
		});

		gameMenu.add(exitMenuItem);

		menuBar.add(gameMenu);

		setJMenuBar(menuBar);

	}// </editor-fold>//GEN-END:initComponents

	private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newButtonActionPerformed
		newMenuItemActionPerformed(null);
	}// GEN-LAST:event_newButtonActionPerformed

	private void expertMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_expertMenuItemActionPerformed
		Minesweeper.getInstance().setSetting(Settings.EXPERT);
		Minesweeper.getInstance().newGame();
	}// GEN-LAST:event_expertMenuItemActionPerformed

	private void intermediateMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_intermediateMenuItemActionPerformed
		Minesweeper.getInstance().setSetting(Settings.INTERMEDIATE);
		Minesweeper.getInstance().newGame();
	}// GEN-LAST:event_intermediateMenuItemActionPerformed

	private void beginnerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_beginnerMenuItemActionPerformed
		Minesweeper.getInstance().setSetting(Settings.BEGINNER);
		Minesweeper.getInstance().newGame();
	}// GEN-LAST:event_beginnerMenuItemActionPerformed
	
	private void customMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_beginnerMenuItemActionPerformed
		new CustomSizeDialog(this, true).setVisible(true);
	}

	private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitMenuItemActionPerformed
		System.exit(0);
	}// GEN-LAST:event_exitMenuItemActionPerformed

	private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newMenuItemActionPerformed
		Minesweeper.getInstance().newGame();
	}// GEN-LAST:event_newMenuItemActionPerformed

	public void bestTimesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		new BestTimesDialog(this, true).setVisible(true);
	}

	public void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("save.ser"));
			oos.writeObject(field);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("save.ser"));

			field = (Field) ois.readObject();
			newGameStarted(field);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// End of variables declaration//GEN-END:variables
	
	public void helpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		Random random = new Random();
	
		while (true) {
			int randomRow = random.nextInt(field.getRowCount());
			int randomCol = random.nextInt(field.getColumnCount());
	
			Tile tile = field.getTile(randomRow, randomCol);
	
			if ((tile.getState().equals(Tile.State.CLOSED)) && !(tile instanceof Mine)) {
				field.openTile(randomRow, randomCol);
				break;
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JRadioButtonMenuItem beginnerMenuItem;
	private javax.swing.JMenuItem bestTimesMenuItem;
	private javax.swing.ButtonGroup buttonGroup;
	private javax.swing.JRadioButtonMenuItem customMenuItem;
	private javax.swing.JPanel contentPanel;
	private javax.swing.JMenuItem exitMenuItem;
	private javax.swing.JRadioButtonMenuItem expertMenuItem;
	private javax.swing.JMenu gameMenu;
	private javax.swing.JPanel infoPanel;
	private javax.swing.JRadioButtonMenuItem intermediateMenuItem;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JLabel minesLeftLabel;
	private javax.swing.JButton newButton;
	private javax.swing.JMenuItem newMenuItem;
	private javax.swing.JMenuItem saveMenuItem;
	private javax.swing.JMenuItem loadMenuItem;
	private javax.swing.JMenuItem helpMenuItem;
	private javax.swing.JLabel timeLabel;
	private javax.swing.JPanel topPanel;
}
