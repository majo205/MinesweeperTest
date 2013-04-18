package minesweeper.swingui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

import minesweeper.Minesweeper;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;

public class BestTimesDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea;
	private Minesweeper minesweeper;

	/**
	 * Create the dialog.
	 * 
	 * @param b
	 * @param swingUI
	 * @param b
	 * @param swingUI
	 */
	public BestTimesDialog(SwingUI swingUI, boolean b) {
		setBounds(100, 100, 450, 300);
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
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				textArea = new JTextArea();
				scrollPane.setViewportView(textArea);
				
				textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
				textArea.setText("No.	Time	Name\n");
				textArea.append(minesweeper.getInstance().getBestTimes()
						.toString());
			}
		}
	}

	private class OkButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			dispose();
		}
	}
}
