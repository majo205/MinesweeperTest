package minesweeper.swingui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import minesweeper.Minesweeper;
import minesweeper.Settings;

import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CustomSizeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Minesweeper minesweeper;
	private JTextField rowsTextField;
	private JTextField columnsTextField;
	private JTextField minesTextField;
	private JTextField txtRows;
	private JTextField txtColumns;
	private JTextField txtMines;

	/**
	 * Create the dialog.
	 * 
	 * @param b
	 * @param swingUI
	 * @param b
	 * @param swingUI
	 */
	public CustomSizeDialog(SwingUI swingUI, boolean b) {
		setTitle("Custom Settings");		
		setBounds(100, 100, 271, 227);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new OkButtonActionListener());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			setLocationRelativeTo(contentPanel);
		}
		
		rowsTextField = new JTextField();
		rowsTextField.setText("10");
		rowsTextField.setColumns(10);
		
		columnsTextField = new JTextField();
		columnsTextField.setText("10");
		columnsTextField.setColumns(10);
		
		minesTextField = new JTextField();
		minesTextField.setText("10");
		minesTextField.setColumns(10);
		
		txtRows = new JTextField();
		txtRows.setEditable(false);
		txtRows.setBorder(null);
		txtRows.setText("Rows :");
		txtRows.setColumns(10);
		
		txtColumns = new JTextField();
		txtColumns.setText("Columns :");
		txtColumns.setEditable(false);
		txtColumns.setColumns(10);
		txtColumns.setBorder(null);
		
		txtMines = new JTextField();
		txtMines.setText("Mines :");
		txtMines.setEditable(false);
		txtMines.setColumns(10);
		txtMines.setBorder(null);
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtRows, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addContainerGap()
								.addComponent(txtColumns, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtMines, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(minesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(columnsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(rowsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(59))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(rowsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtRows, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(columnsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtColumns, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(minesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMines, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(53, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}

	private class OkButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			int rowCount = Integer.parseInt(rowsTextField.getText());
			int columnCount = Integer.parseInt(columnsTextField.getText());
			int mineCount = Integer.parseInt(minesTextField.getText());
			
			
			if (mineCount> (rowCount*columnCount)/3){
				mineCount= (rowCount*columnCount)/3;
			}
			
			Minesweeper.getInstance().setSetting(new Settings(rowCount, columnCount, mineCount));
			Minesweeper.getInstance().newGame();
			
			setVisible(false);
			dispose();
		}
	}
}
