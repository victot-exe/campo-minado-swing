package br.com.victotExe.cm.visao;

import javax.swing.JFrame;

import br.com.victotExe.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{
	
	public TelaPrincipal() {
//		definindo o tabuleiro
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 5);
//		adicionando o tabuleiro na janela
		add(new PainelTabuleiro(tabuleiro));
		
		
		
		setTitle("Campo Minado");
//		definirOTamanho(largura, altura)
		setSize(690, 438);
//		posição da janela(null = centralizar)
		setLocationRelativeTo(null);
//		botão X fecha
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new TelaPrincipal();
	}
}
