package multiplicacao.matriz.server.rmi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MatrizUtil {
	
	public static final byte LINE = 0;
	public static final byte COLUNM = 1;
	
	public static long[][] carregar(int aLinhas, int aColunas, String aCaminho) throws Exception {
		long[][] lMatriz = new long[aLinhas][aColunas];
		FileReader file = new FileReader(aCaminho);//("src/matA.txt");
		BufferedReader bufFile = new BufferedReader(file);
		
		// Lê a primeira linha
		String line = bufFile.readLine();
		int lLinhas =  0;
		int lColunas = 0;
		while (line != null) {
			lMatriz[lLinhas][lColunas] = Integer.parseInt(line);
			lColunas++;
			if (lColunas >= aColunas) {
				lLinhas++;
				lColunas = 0;
			}
			if(lLinhas == aLinhas) {
				line = null;
			} else {
				line = bufFile.readLine();
			}
		}
		bufFile.close();

		return lMatriz;
	}
	
	public static boolean gravar(long[][] aMatrizResult, String aCaminho) {
		try {
			File fOut = new File(aCaminho);
			BufferedWriter writer = new BufferedWriter(new FileWriter(fOut));
			for (int i = 0; i < aMatrizResult.length; i++) {
				for (int j = 0; j < aMatrizResult[COLUNM].length; j++) {
					writer.write(String.valueOf(aMatrizResult[i][j]));
					if ((i ==  aMatrizResult.length - 1) && (j == aMatrizResult[COLUNM].length - 1)) {
						continue;
					} else {
						writer.newLine();
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.err.print("\n\tErro: "+e.getMessage());
			System.exit(1);
			return false;
		}
		return true;
	}
	
	public static String showVetor(long[] vetor) {
		String resultado = "";
		for(long valor : vetor) {
			resultado += valor + ", ";
		}
		return resultado;
	}
	
	public static long[][] caregarMatriz(final int aLinha, final int aColunas,String aCaminho) {
		long[][] mat = null;
		try {
			mat = MatrizUtil.carregar(aLinha, aColunas, aCaminho);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return mat;
	}
	
	public static long[] extrairLinha(int aLinha, long[][] aMatrizA) {
		long[] lResultLines = new long[aMatrizA[COLUNM].length];
		for (int indice = 0; indice < aMatrizA[COLUNM].length; indice++) {
			lResultLines[indice] = aMatrizA[aLinha][indice];
		}
		return lResultLines;
	}

	public static long[][] cortar(int totalLinhas, long[][] aMatrizA, int aPosicaoInicial, int lPosicaoFInal) {
		long[][] lMatriz = new long[totalLinhas][aMatrizA[COLUNM].length];
		int aLinhaAtual = 0;
		for (int indiceLinha = aPosicaoInicial; indiceLinha < lPosicaoFInal; indiceLinha++) {
			for (int indiceColuna = 0; indiceColuna< aMatrizA[COLUNM].length; indiceColuna++) {
				lMatriz[aLinhaAtual][indiceColuna] = aMatrizA[indiceLinha][indiceColuna];
			}
			aLinhaAtual++;
		}
		return lMatriz;
	}
	

	public static long[][] converterVetorLinhaMatriz(int aLinha, long[] aVetor, long[][] aMatrizAtualizada) {
		for (int indice = 0; indice < aVetor.length; indice++) {
			aMatrizAtualizada[aLinha][indice] = aVetor[indice];
		}
		return aMatrizAtualizada;
	}
	
	public static long[][] tranformarMetade(int aPosicaoInicial, int aPosicaoFinal, long[][] aMatrizCalculada, long[][] aMatrizAtualizada) {
		int aLinhaAtual = 0;
		for (int indice = aPosicaoInicial; indice < aPosicaoFinal; indice++) {
			for(int indiceColuna = 0; indiceColuna < aMatrizCalculada[COLUNM].length; indiceColuna++) {
				aMatrizAtualizada[indice][indiceColuna] = aMatrizCalculada[aLinhaAtual][indiceColuna];
			}
			aLinhaAtual++;
		}
		return aMatrizAtualizada;
	}
}
