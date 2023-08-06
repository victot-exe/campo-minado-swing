package br.com.victotExe.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {// um quadradinho do campo

	private final int linha;
	private final int coluna;

	private boolean aberto = false;// se abriu ou n�o
	private boolean minado = false;// se tem bomba ou n�o
	private boolean marcado = false;// se est� marcado ou n�o

	private List<Campo> vizinhos = new ArrayList<>();
	//       Set funcionaria bem tambem pois não deixaria ser adicionado o mesmo abservador mais de uma vez
	private List<CampoObservador> observadores = new ArrayList<>();

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(CampoEvento evento) {
		observadores.stream()
			.forEach(o -> o.eventoOcorreu(this, evento));
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;

		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	
	public void alternarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
			
			if(marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			}else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
		}
	}
	
	public boolean abrir() {
		
		if(!aberto && !marcado) {
			
			if(minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			
			setAberto(true);
			
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());//passa um consumer que vai abrir os vizinhos de forma recursiva, at� encontrar um vizinho que n�o possa ser aberto
				
			}
			
			return true;
		}else {
			return false;
		}
	}
	
	public boolean vizinhancaSegura() {
		//Predicate<Campo> seguro = a -> a.minado == false;//minha tentativa
		return vizinhos.stream().noneMatch(v -> v.minado);//se nenhum for minado ele retorna true
				//.anyMatch(seguro);//minha tentativa
	}
	
	void minar() {
		this.minado = true;
	}
	
	public boolean isMinado() {
		return this.minado;
	}
	
	public boolean isMarcado() {//m�todo getter de atributos boolean
		return this.marcado;
	}
	
//	public void setMinado(boolean b) {//minha altera��o para mudar o valor de campo.minado
//		this.minado = b;
//	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
		
		if(aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}
	
	public boolean isAberto() {
		return this.aberto;
	}
	
	public boolean isFechado() {
		return !this.isAberto();//fechado � o contrario do aberto
	}
//testar
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	public int minasNaVizinhanca() {
		return (int)vizinhos.stream().filter(v -> v.minado).count();//conta quantos est�o minados e retorna
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}
//toString removido	
}
