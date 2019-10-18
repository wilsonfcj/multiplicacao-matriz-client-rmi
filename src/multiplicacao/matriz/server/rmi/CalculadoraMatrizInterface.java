package multiplicacao.matriz.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import multiplicacao.matriz.server.rmi.model.MultiplicacaoMatrizResult;

public interface CalculadoraMatrizInterface extends Remote {
	MultiplicacaoMatrizResult multiplicar(int aPosicaoInicial, int aPosicaoFinal, long aMatrizA[][], long aMatrizB[][]) throws RemoteException;
}
