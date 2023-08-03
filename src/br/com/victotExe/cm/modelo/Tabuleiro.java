package br.com.victotExe.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private int minas;

	private final List<Campo> campos = new ArrayList<>();// foi utilizada uma List ao inv�s de matriz para podermos
															// utilizar o API de Streams
	// quanto mais restrito for a visibilidade melhor fica, pois o código fica mais
	// independente tornando a manutenção mais facil

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		// ap�s o tabuleiro ser iniciado ele vai executar alguns m�todos
		// automaticamente.
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		
		try {
			campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
		}catch (Exception e) {
			//FIXME Ajustar a implementação do método abrir
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}		
	
	public void alterarMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}
	
	private void gerarCampos() {//m�todo que vai gerar os campos
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));
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
	
}
