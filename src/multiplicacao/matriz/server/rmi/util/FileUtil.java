package multiplicacao.matriz.server.rmi.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.MessageDigest;

public class FileUtil {
	
	public static byte[] getFileBytes(File file)  {
		int len = (int)file.length();
		byte[] sendBuf = new byte[len];
		FileInputStream inFile = null;
		try {
			inFile = new FileInputStream(file);
			inFile.read(sendBuf, 0, len);
		} catch (Exception e) {
			System.err.print("\n\tErro: "+e.getMessage());
			System.exit(1);
		}
		return sendBuf;
	}
	
	public static boolean gerarMD5(String aNomeArq, String aCaminhoTxt, String aCaminhoSalvar) {
		try {
			File matCFile = new File(aCaminhoTxt);
			int matCSize = (int)matCFile.length();
			byte[] matCBytes = new byte[matCSize];
			matCBytes = FileUtil.getFileBytes(matCFile);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(matCBytes);
			System.out.print("\nHash: "+ StringUtil.toHexFormat(hash));
			System.out.print("\nGravando arquivo matC.md5...");
			File md5File = new File(aCaminhoSalvar + ".md5");
			BufferedWriter writer = new BufferedWriter(new FileWriter(md5File));
			writer.write(StringUtil.toHexFormat(hash)+ "   " + aNomeArq);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.err.print("\n\tErro: "+e.getMessage());
			System.exit(1);
		}
		return true;
	}
}
