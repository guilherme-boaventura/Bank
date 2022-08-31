package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import domain.ContaBanco;
import persistence.ContaDAO;
import javax.swing.UIManager;

public class ContasTable {

	public JFrame frame;
	private static JTable table;
	private JTextField txtNome;
	private JTextField txtTipo;

	/**
	 * Create the application.
	 */
	public ContasTable() {
		initialize();
		readTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setBackground(Color.GRAY);
		frame.setTitle("Contas");
		frame.setBounds(100, 100, 900, 668);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 280, 700, 300);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(getModel());
		table.setBackground(Color.WHITE);

		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(100, 44, 67, 27);
		frame.getContentPane().add(lblNewLabel);

		txtNome = new JTextField();
		txtNome.setBounds(100, 71, 129, 27);
		frame.getContentPane().add(txtNome);
		txtNome.setColumns(30);

		txtTipo = new JTextField();
		txtTipo.setColumns(30);
		txtTipo.setBounds(304, 71, 129, 27);
		frame.getContentPane().add(txtTipo);

		JLabel lblTipo = new JLabel("Tipo da conta");
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipo.setBounds(304, 44, 107, 27);
		frame.getContentPane().add(lblTipo);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = txtNome.getText();
				String tipo = txtTipo.getText().toUpperCase();
				ContaBanco.abrirConta(nome,tipo);
				readTable();
			}
		});
		btnNewButton.setBounds(100, 185, 115, 34);
		frame.getContentPane().add(btnNewButton);
	}

	public static DefaultTableModel getModel() {
		return new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"number", "owner", "type", "balance"
				}
				) {
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
	}

	public static void readTable() {
		DefaultTableModel modelo = getModel();
		modelo.setNumRows(0);
		ContaDAO cDAO = new ContaDAO();
		for (ContaBanco acc : cDAO.read()) {
			modelo.addRow(new Object[] {
					acc.getNumConta(),
					acc.getNomeDono(),
					acc.getTipoConta().toString(),
					acc.getSaldo()
			});
		}
		table.setModel(modelo);
	}
}