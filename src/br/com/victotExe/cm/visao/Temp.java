package br.com.victotExe.cm.visao;

import br.com.victotExe.cm.modelo.Tabuleiro;

public class Temp {
	
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(3, 3, 9);
		
		tabuleiro.registrarObservador(e -> {
			if(e.isGanhou()) {
				System.out.println("ganhou :)");
			}else {
				System.out.println("perdeu :(");
			}
		});
		
		tabuleiro.alterarMarcacao(0, 0);
		tabuleiro.alterarMarcacao(0, 1);
		tabuleiro.alterarMarcacao(0, 2);
		tabuleiro.alterarMarcacao(1, 0);
		tabuleiro.alterarMarcacao(1, 1);
		tabuleiro.alterarMarcacao(1, 2);
		tabuleiro.alterarMarcacao(2, 0);
		tabuleiro.alterarMarcacao(2, 1);
		tabuleiro.abrir(2, 2);
//		tabuleiro.alterarMarcacao(2, 2);
	}
}
