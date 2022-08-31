package main;

import java.awt.EventQueue;

import gui.ContasTable;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContasTable window = new ContasTable();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}