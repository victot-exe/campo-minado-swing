package br.com.victotExe.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {

	private final int linhas;
	private final int colunas;
	private final int minas;

	private final List<Campo> campos = new ArrayList<>();// foi utilizada uma List ao inv�s de matriz para podermos
															// utilizar o API de Streams
	// quanto mais restrito for a visibilidade melhor fica, pois o código fica mais
	// independente tornando a manutenção mais facil
	private final List<Consumer<ResultadoEvento>> observadores =
			new ArrayList<>();
	
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		// após o tabuleiro ser iniciado ele vai executar alguns m�todos
		// automaticamente.
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	
	public void notificarObservadores(boolean resultado) {
		observadores.stream()
			.forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}
	
	public void abrir(int linha, int coluna) {
		campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
	}
	
	public void alterarMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}
	
	private void gerarCampos() {//método que vai gerar os campos
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
	}
	
	private void associarVizinhos() {
		for(Campo c1: campos) {//for que percorre todos os campos e associa os vizinhos
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas= 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {
			int aleatorio =(int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();//minar antes de contar se n�o o jogo fica com uma mina a mais
//			minasArmadas = (int)campos.stream().filter(minado).count();//jeito para fazer o cast(convers�o) de long para int
			minasArmadas = campos.stream().filter(minado).count();//aqui apenas mudamos o tipo do atributo na constru��o
		}while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		if(evento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		}else if(objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}
	
//	percorre todos os campos, separa os que tem mina e abre para mostrar o resultado do jogo
	private void mostrarMinas() {
		campos.stream()
			.filter(c -> c.isMinado() && !c.isMarcado())
			.forEach(c -> c.setAberto(true));
	}
	
}
