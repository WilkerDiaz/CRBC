package com.beco.cr.pda;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.paddings.*;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.modes.*;
import org.bouncycastle.crypto.params.*;

//Codigo cambiado para prueba sobre la maquina virtual PMEA
public class TriDES {

    private static BlockCipher engine;
    private static BufferedBlockCipher cipher;
    private static byte[] key;
    
    public static void prepare(byte[] raw)throws Exception {
		 engine = new DESedeEngine();
    	cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
    	key = raw;
		 
    }
    public static String encrypt(String plainText)throws Exception {
        cipher.init(true, new KeyParameter(key));
        byte[] ptBytes = plainText.getBytes("UTF8");
        byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
        int tam = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
        try {
            cipher.doFinal(rv, tam);
        } catch (Exception ce) {
            ce.printStackTrace();
        }
        return new String(BASE64Encoder.encode(rv)).trim();
    }

    public static  String decrypt(String textoCifrado) throws Exception{
        cipher.init(false, new KeyParameter(key));
    	byte[] cipherText = BASE64Encoder.decode(textoCifrado);
        byte[] rv = new byte[cipher.getOutputSize(cipherText.length)];
        int tam = cipher.processBytes(cipherText, 0, cipherText.length, rv, 0);
        try {
            cipher.doFinal(rv, tam);
        } catch (Exception ce) {
            ce.printStackTrace();
        }
        return new String(rv,"UTF8").trim();
    }
}


/*
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class TriDES {
	 private static Cipher ecipher;
	 private static Cipher dcipher;


	   public static void prepare(byte[] raw)throws Exception {
	    	SecretKeySpec skeySpec = new SecretKeySpec(raw, "DESede");
	    	if(ecipher == null){
	    		ecipher = Cipher.getInstance("DESede");
	            ecipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    	}
	    	if(dcipher == null){
	            dcipher = Cipher.getInstance("DESede");
	            dcipher.init(Cipher.DECRYPT_MODE, skeySpec);
	    	}
	    }

	    public static String encrypt(String str) {
	    	
	        try {
	            // Encode the string into bytes using utf-8
	            byte[] utf8 = str.getBytes("UTF8");

	            // Encrypt
	            byte[] enc = ecipher.doFinal(utf8);

	            // Encode bytes to base64 to get a string
	            return new String(BASE64Encoder.encode(enc));
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        return null;
	    }

	    public static String decrypt(String str) {
	        try {
	            // Decode base64 to get bytes
	            byte[] dec = BASE64Encoder.decode(str);

	            // Decrypt
	            byte[] utf8 = dcipher.doFinal(dec);

	            // Decode using utf-8
	            return new String(utf8, "UTF8");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        return null;
	    }
		
}
*/
