package multiplicacao.matriz.server.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import multiplicacao.matriz.server.rmi.model.MultiplicacaoMatrizResult;
import multiplicacao.matriz.server.rmi.util.FileUtil;
import multiplicacao.matriz.server.rmi.util.MatrizUtil;

public class MultiplicacaoMatrizClientRMI {
	
	private final static int mLinhas = 4096;
	private final static int mColunas = 4096;
	private static long[][] lMatrizC;
	private static int divisoes = 4;
	private static int cont = 0;
	
	public static void main(String[] args) {
		System.out.println("\nIniciando cliente da CalculadoraRemota...");
		try {
//			Carregando as matrizes;
			long[][] lMatrizA = MatrizUtil.caregarMatriz(mLinhas, mColunas, "src/matA.txt");
			long[][] lMatrizB = MatrizUtil.caregarMatriz(mLinhas, mColunas, "src/matB.txt");
			lMatrizC = new long[mLinhas][mColunas];
			
			int lMetade = mLinhas / divisoes;
		
//			Vamos dividir a matriz em 4 partes.
			long [][] lMatrizPrimeiraParte = MatrizUtil.cortar(lMetade, lMatrizA, 0, 1024);
			long [][] lMatrizSegundaParte = MatrizUtil.cortar(lMetade, lMatrizA, 1024, 2048);
			long [][] lMatrizTerceiraParte = MatrizUtil.cortar(lMetade, lMatrizA, 2048, 3072);
			long [][] lMatrizQuartaParte = MatrizUtil.cortar(lMetade, lMatrizA, 3072, 4096);

//			Registra o gerencioador de segurança
			System.out.println("Registrando o gerenciador de segurança...");
			System.setSecurityManager(new SecurityManager());
		
//			Cria a interface com o servidor
			
			long lStartTime = System.currentTimeMillis();
			executar("10.151.33.80:6000", 0, 1024, lMatrizPrimeiraParte, lMatrizB);
			executar("10.151.33.112:6000", 1024, 2048, lMatrizSegundaParte, lMatrizB);
			executar("10.151.33.162:6000", 2048, 3072, lMatrizTerceiraParte, lMatrizB);
			executar("10.151.33.44:6000", 3072, 4096, lMatrizQuartaParte, lMatrizB);
			
			System.out.println("Aguarde...");

			while (cont != divisoes) {
				System.out.println("Calculando...");
			}
		
			long lStopTime = System.currentTimeMillis();
			
			// Imprime o tempo de execução
			System.out.println("\tTempo de execução: " + (lStopTime - lStartTime) + " ms");
			System.out.println("\tTempo de execução: " + (lStopTime - lStartTime) / 1000 + " segundos");
			System.out.println("\tTempo de execução: " + ((lStopTime - lStartTime) / 1000) / 60 + " minutos");

			System.out.print("\nGravando arquivo da matriz C...");
			MatrizUtil.gravar(lMatrizC, "src/matC.txt");
			
//			Gera o MD5
			System.out.println("\nGerando MD5 da matriz C...");
			FileUtil.gerarMD5("matC.txt", "src/matC.txt", "src/matC");
		} catch (Exception e) { //(MalformedURLException | RemoteException | NotBoundException e) {
			System.out.print("\n\tErro: " + e.getMessage());
			System.exit(1);
		}
	}
	
	private static void executar (String ipPort, int aPosicaoInicial, int aPosicaoFinal, long aMatrizA[][], long aMatrizB[][]) {
		Thread thread = new Thread() {
			public void run() {
				try {
					MultiplicacaoMatrizResult lMatrizResult = conectarService(ipPort).multiplicar(aPosicaoInicial, aPosicaoFinal, aMatrizA, aMatrizB);
					lMatrizC = MatrizUtil.tranformarMetade(lMatrizResult.mPosicaoInicial, lMatrizResult.mPosicaoFinal, lMatrizResult.mResult, lMatrizC);
					cont++;
				} catch (Exception e) { //(MalformedURLException | RemoteException | NotBoundException e) {
					System.out.print("\n\tErro: " + e.getMessage());
					System.exit(1);
				}
			}
		};
		thread.start();
	}
	
	private static CalculadoraMatrizInterface conectarService(String aIPAndPort) throws MalformedURLException, RemoteException, NotBoundException {
		return (CalculadoraMatrizInterface) Naming.lookup("rmi://"+ aIPAndPort +"/CalculadoraMatriz");
	}

}
