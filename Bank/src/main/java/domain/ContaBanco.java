package domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import connection.ConnectionFactory;
import persistence.ContaDAO;

public class ContaBanco {

	private String nomeDono;
	protected tipoContaEnum tipoConta;
	public String numConta;	
	private double saldo;
	
	public ContaBanco() {}

	public ContaBanco(String nome, String tipo) {
		this.setNomeDono(nome);
		this.setTipoConta(tipo);
		this.setNumConta();
		this.saldo = 0;
	}
	
	public ContaBanco(String nome, tipoContaEnum tipo) {
		this.setNomeDono(nome);
		this.setTipoConta(tipo);
		this.setNumConta();
		this.saldo = 0;
	}

	public ContaBanco(String nome, tipoContaEnum tipo, String numConta) {
		this.nomeDono = nome;
		this.tipoConta = tipo;
		this.numConta = numConta;
	}

	public tipoContaEnum getTipoConta() {
		return this.tipoConta;
	}

	public static tipoContaEnum inputTipoConta() {
		String tipoConta = JOptionPane.showInputDialog(null, "Qual tipo de conta deseja abrir?\n"
				+ "(Digite 'cp' para conta poupan�a e 'cc' para conta corrente.)").toUpperCase();
		
		while(!(tipoConta.contentEquals("CC")) && !(tipoConta.contentEquals("CP"))){
			tipoConta = JOptionPane.showInputDialog(null, "Tipo de conta inválido.\n"
					+ "(Digite 'cp' para conta poupança e 'cc' para conta corrente.)").toUpperCase();
		}
		if (tipoConta.contentEquals("CC")) {
			return tipoContaEnum.CC;
		}else{
			return tipoContaEnum.CP;
		}
	}
	
	public void setTipoConta(tipoContaEnum tipo) {
		this.tipoConta=tipo;
	}

	public void setTipoConta(String tipo) {
		if (tipo.contentEquals("CC")) {
			this.tipoConta = tipoContaEnum.CC;
		}else{
			this.tipoConta = tipoContaEnum.CP;
		}
		
	}

	public String getNomeDono() {
		return nomeDono;
	}
	
	public static String inputNomeDono() {
		return JOptionPane.showInputDialog(null, "Insira o nome do dono da conta");
	}

	public void setNomeDono(String nome) {
		this.nomeDono = nome;
	}

	public String nomeFormatado() {
		String[] s = new String[3];
		s[0] = this.nomeDono.substring(0, this.nomeDono.indexOf(" "));
		s[1] = this.nomeDono.substring(this.nomeDono.lastIndexOf(" "), this.nomeDono.length());
		s[2] = s[0] + s[1];
		return s[2];
	}

	public double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getNumConta() {
		return this.numConta;
	}

	public void setNumConta() {
		Random rnd = new Random();
		this.numConta = Integer.toString(Math.abs((rnd.nextInt())));
	}
	
	public void setNumConta(String num) {
		this.numConta = num;
	}

	public static void abrirConta() {
		ContaBanco acc = new ContaBanco(ContaBanco.inputNomeDono(), ContaBanco.inputTipoConta());
		ContaDAO.create(acc);
	}
	
	public static void abrirConta(String nome, String tipo) {
		ContaBanco acc = new ContaBanco(nome, tipo);
		ContaDAO.create(acc);
	}

	private void saveAccount() throws SQLException {
		Connection cn = ConnectionFactory.getConnection();
		cn.createStatement().execute("insert into accounts values ('" + this.numConta + "','" + this.nomeDono + "','" + this.tipoConta + "','" + this.saldo + "');");
		cn.close();
	}

	public void fecharConta() {
		//if(this.status == true) {
		if(this.saldo >= 0) {
			Scanner sc = new Scanner(System.in);
			System.out.println("");
			if(this.saldo > 0) {
				System.out.println("Para fechar a conta � necess�rio o saque de todo o dinheiro depositado."
						+ "\nDeseja realizar o saque de todo o saldo?"
						+ "\n(Digite 1 para sim e 2 para n�o)"
						+ "\n1) sim"
						+ "\n2) n�o");
				String confirm = sc.next();

				while(!(confirm.contentEquals("1")) && !(confirm.contentEquals("2"))) {
					System.out.println("Op��o inv�lida inserida, insira novamente."
							+ "\n(Digite 1 para sim e 2 para n�o)"
							+ "\n1) sim"
							+ "\n2) n�o");
					confirm = sc.next();
				}

				switch (confirm) {
				case "1":
					this.saldo = 0;
					//this.status = false;
					System.out.println("Saldo zerado."
							+ "\nConta fechada com sucesso.");
					break;

				case "2":
					System.out.println("Opera��o de fechamento de conta cancelada.");
					break;
				}
			}else {
				System.out.println("Confirmar fechamento de conta?"
						+ "\n(Digite 1 para sim e 2 para n�o)"
						+ "\n1) sim"
						+ "\n2) n�o");
				String confirm = sc.next();

				while(!(confirm.contentEquals("1")) && !(confirm.contentEquals("2"))) {
					System.out.println("Op��o inv�lida inserida, insira novamente."
							+ "\n(Digite 1 para sim e 2 para n�o)"
							+ "\n1) sim"
							+ "\n2) n�o");
					confirm = sc.next();
				}

				switch (confirm) {
				case "1":
					//this.status = false;
					System.out.println("Conta fechada com sucesso.");
					break;

				case "2":
					System.out.println("Opera��o de fechamento de conta cancelada.");
					break;
				}
			}
		}else {
			System.out.println("Conta em d�bito, imposs�vel realizar o fechamento.");
		}
		//	}else {
		System.out.println("Para fechar uma conta, primeiramente, ela precisa ser aberta.");
	}

	public double depositar(int presente) {
		return 2;
	}

	public void sacar() {
	}

	public void sair() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Deseja realmente sair do sistema?"
				+ "\n(Digite 1 para sim e 2 para n�o)"
				+ "\n1) sim"
				+ "\n2) n�o");
		String confirm = sc.next();

		while(!(confirm.contentEquals("1")) && !(confirm.contentEquals("2"))) {
			System.out.println("Op��o inv�lida inserida, insira novamente."
					+ "\n(Digite 1 para sim e 2 para n�o)"
					+ "\n1) sim"
					+ "\n2) n�o");
			confirm = sc.next();
		}

		switch (confirm) {
		case "1":
			System.exit(0);
			break;

		case "2":
			this.menu();
			break;
		}
	}

	private void login() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Informe o número da conta para fazer login:");
		String num = sc.nextLine();
	}

	public void menu() throws SQLException {
		login();
		Scanner sc = new Scanner(System.in);
		short choice;
		if(this.nomeDono != null) {
			System.out.println("Bem-vindo ao Banco Sodalita, o que deseja?"
					+ "\n1) Saque"
					+ "\n2) Dep�sito"
					+ "\n3) Verificar saldo"
					+ "\n4) Verificar n�mero de conta"
					+ "\n5) Abrir conta"
					+ "\n6) Fechar conta"
					+ "\n7) Sair");
			choice = sc.nextShort();
		}else {
			System.out.println("Bem-vindo ao Banco Sodalita, o que deseja?"
					+ "\n1) Saque"
					+ "\n2) Dep�sito"
					+ "\n3) Verificar saldo"
					+ "\n4) Verificar n�mero de conta"
					+ "\n5) Abrir conta"
					+ "\n6) Fechar conta"
					+ "\n7) Sair");
			choice = sc.nextShort();
		}

		while(choice < 1 || choice > 9) {
			System.out.println("Op��o inv�lida inserida, insira novamente:"
					+ "\n1) Saque"
					+ "\n2) Dep�sito"
					+ "\n3) Verificar saldo"
					+ "\n4) Verificar n�mero de conta"
					+ "\n5) Abrir conta"
					+ "\n6) Fechar conta"
					+ "\n7) Sair");
			choice = sc.nextShort();
		}

		switch (choice) {
		case 1:
			this.sacar();
			System.out.println("");
			this.menu();
			break;

		case 2:
			this.depositar(0);
			System.out.println("");
			this.menu();
			break;

		case 3:
			//System.out.println(this.getSaldo());
			System.out.println("");
			this.menu();
			break;

		case 4:
			System.out.println(this.getNumConta());
			System.out.println("");
			this.menu();
			break;

		case 5:
			this.abrirConta();
			System.out.println("");
			this.menu();
			break;

		case 6:
			this.fecharConta();
			System.out.println("");
			this.menu();
			break;

		case 7:
			this.sair();
			System.out.println("");
		}
	}
}