package br.com.victotExe.cm.visao;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.victotExe.cm.modelo.Campo;
import br.com.victotExe.cm.modelo.CampoEvento;
import br.com.victotExe.cm.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador{
										//implementa CampoObservador pois ela é a classe responsável por notificar sempre que acontece algo
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCADO = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);
	
	
	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		
		campo.registrarObservador(this);//toda vez que um evento acontecer com campo vai chamar esse metodo e definir o estilo correto
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch(evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadrao();
		}
	}

	private void aplicarEstiloPadrao() {
		// TODO Auto-generated method stub
		
	}

	private void aplicarEstiloExplodir() {
		// TODO Auto-generated method stub
		
	}

	private void aplicarEstiloMarcar() {
		// TODO Auto-generated method stub
		
	}

	private void aplicarEstiloAbrir() {
		// TODO Auto-generated method stub
		
	}
}
