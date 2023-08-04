package br.com.victotExe.cm.visao;

import java.awt.GridLayout;

import javax.swing.JPanel;

import br.com.victotExe.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {// JPanel é um container(componente que agrupa outros componentes)
	
	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(
				tabuleiro.getLinhas(), tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
		}
}