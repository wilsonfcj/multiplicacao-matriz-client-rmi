package multiplicacao.matriz.server.rmi.model;

import java.io.Serializable;
import java.rmi.Remote;

public class MultiplicacaoMatrizResult implements Serializable,  Remote {
	
	private static final long serialVersionUID = 1L;
	
	public long[][] mResult;
	public int mPosicaoInicial;
	public int mPosicaoFinal;
	
	public MultiplicacaoMatrizResult(int aPosicaoInicial, int aPosicaoFinal) {
		super();
		this.mPosicaoInicial = aPosicaoInicial;
		this.mPosicaoFinal = aPosicaoFinal;
	}
	
	public MultiplicacaoMatrizResult() {
		super();
	}
}
