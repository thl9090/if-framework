package com.yx.common.utils;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.Security;

/**
 * 该工具类来源于老系统
 * 对称性解密器。
 * @author TangHuaLiang
 * @version 2018-07-19
 */
public class EncryptorAES {

	private Charset _charset = Charset.forName("utf-8");
    private KeyGenerator _keygen;
    private SecretKey _deskey;
    private Cipher _cipher;
    private byte[] _cipherByte;
    private String _wappedSeed;

    public EncryptorAES(String seed) {
    	try {
    		Security.addProvider(new com.sun.crypto.provider.SunJCE());
    		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
    		secureRandom.setSeed(this.getWappedSeed(seed).getBytes(_charset));

            _keygen = KeyGenerator.getInstance("AES");
            _keygen.init(128, secureRandom);
            _deskey = _keygen.generateKey();
            _cipher = Cipher.getInstance("AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private String getWappedSeed(String rawSeed) {
    	if(_wappedSeed == null){
    		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        	_wappedSeed = encoder.encodePassword(rawSeed, "caff.v2.root.seed");
    	}
    	return _wappedSeed;
	}

    public String encryt(String str) throws Exception{
    	try {
    		_cipher.init(Cipher.ENCRYPT_MODE, _deskey);
            byte[] src = str.getBytes(_charset);
            _cipherByte = _cipher.doFinal(src);
             return new String(Base64.encode(_cipherByte), _charset);
		} catch (Exception e) {
			throw new Exception("EncrypAES.decrypt() error", e);
		}
    }

    public String decrypt(String encrytedStr) throws Exception {
    	try {
    		_cipher.init(Cipher.DECRYPT_MODE, _deskey);
    		byte[] decryptData = Base64.decode(encrytedStr.getBytes(_charset));
            _cipherByte = _cipher.doFinal(decryptData);
            return new String(_cipherByte, _charset);
		} catch (Exception e) {
			throw new Exception("EncrypAES.decrypt() error", e);
		}
    }

}
