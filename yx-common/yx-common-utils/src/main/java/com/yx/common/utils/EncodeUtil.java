package com.yx.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import java.io.UnsupportedEncodingException;

/**
 *  提供加密的相关辅助功能
 * @author Administrator
 * @version 创建时间：2017年1月12日下午1:47:18
 */
public abstract class EncodeUtil {

	/**
	 * 所有的密码加密算法使用ShaPasswordEncoder
	 * @author Administrator
	 * @version 创建时间：2017年1月12日下午1:47:33
	 * @return ShaPasswordEncoder对象
	 */
	public static ShaPasswordEncoder getPasswordEncoder() {
		return new ShaPasswordEncoder();
	}


	/*public static ShaPasswordEncoder getPasswordEncoder(Integer type) {
		switch (type){
			case 2 :
				return new ShaPasswordEncoder(256);
			case 3 :
				return new ShaPasswordEncoder(384);
			case 4 :
				return new ShaPasswordEncoder(512);
			default:
				return new ShaPasswordEncoder();
		}
	}*/

	/**
	 * 对于已有系统的历史数据，其使用用户实体的id作为加密种子， 升级时需要将exkey的值设置为实体的id。
	 * @author Administrator
	 * @version 创建时间：2017年1月12日下午1:48:05
	 * @return SaltSource SaltSource对象
	 */
	public static SaltSource getSaltSource() {
		ReflectionSaltSource saltSource = new ReflectionSaltSource();
		saltSource.setUserPropertyToUse("getExkey");
		return saltSource;
	}
	
	/**
	 * 使用提供的加密种子进行加密密码，算法使用ShaPasswordEncoder。此为单向加密，无法解密出密码明文。
	 * @author Administrator
	 * @version 创建时间：2017年1月12日下午1:48:40
	 * @param rawPassword 密码明文
	 * @param salt 加密种子
	 * @return 密码密文
	 * @return String 加密后字符串
	 */
	public static String getEncodedPassword(String rawPassword, String salt) {
		ShaPasswordEncoder encoder = getPasswordEncoder();
		return encoder.encodePassword(rawPassword, salt);
	}

	/*public static String getEncodedPassword(String rawPassword, String salt,Integer type) {
		if(type == 5){
			Md5PasswordEncoder encoder=new Md5PasswordEncoder();
			return encoder.encodePassword(rawPassword, salt).substring(0,32);
		}else{
			ShaPasswordEncoder encoder = getPasswordEncoder(type);
			return encoder.encodePassword(rawPassword, salt).substring(0,32);
		}
	}*/

	/**
	 * 根据type类型选择不同加密方式
	 *
	 * @param rawPassword 密码
	 * @param salt 秘钥
	 * @param type 类型
	 * @return password 加密后的密码
	 */
	public static String getEncodedPassword(String rawPassword, String salt,Integer type) {
		ShaPasswordEncoder encoder = getPasswordEncoder();
		String passwordMode = null;
		String encodedPassword = null;

		StringBuffer pwdBuffer = new StringBuffer();

		switch (type){
			case 1://后缀加密方式 SHA-1(psd+salt)
				passwordMode = pwdBuffer.append(rawPassword).append(salt).toString();
				encodedPassword = encoder.encodePassword(passwordMode, salt);
				break;
			case 2://前缀加密方式 SHA-1(salt+psd)
				passwordMode = pwdBuffer.append(salt).append(rawPassword).toString();
				encodedPassword = encoder.encodePassword(passwordMode, salt);
				break;
			case 3://前后都加加密方式 SHA-1(salt+psd)
				passwordMode = pwdBuffer.append(salt).append(rawPassword).append(salt).toString();
				encodedPassword = encoder.encodePassword(passwordMode, salt);
				break;
			case 4://加密后，加后缀 SHA-1(psd)+salt
				passwordMode = encoder.encodePassword(passwordMode, salt);
				encodedPassword = pwdBuffer.append(passwordMode).append(salt).toString();
				break;
			case 5://前缀加密后，加后缀 SHA-1(salt+psd)+salt
				passwordMode = pwdBuffer.append(salt).append(passwordMode).toString();
				pwdBuffer = new StringBuffer();
				encodedPassword = pwdBuffer.append(encoder.encodePassword(passwordMode, salt)).append(salt).toString();
				break;
			case 6://前后都加加密，加后缀  SHA-1(salt+psd+salt)+salt
				passwordMode = pwdBuffer.append(salt).append(rawPassword).append(salt).toString();
				pwdBuffer = new StringBuffer();
				encodedPassword = pwdBuffer.append(encoder.encodePassword(passwordMode, salt)).append(salt).toString();
				break;
			case 7://前后都加加密，加前缀 salt+SHA-1(salt+psd+salt)
				passwordMode = pwdBuffer.append(salt).append(rawPassword).append(salt).toString();
				pwdBuffer = new StringBuffer();
				encodedPassword = pwdBuffer.append(salt).append(encoder.encodePassword(passwordMode, salt)).toString();
				break;
			default://原始加密 SHA-1(psd)
				encodedPassword = encoder.encodePassword(rawPassword, salt);
				break;
		}
		return encodedPassword;
	}

	/**
	 * 使用对称性加密字符串，可使用相应算法解密出原字符串
	 * @author Administrator
	 * @version 创建时间：2017年1月12日下午1:49:13
	 * @param rawStr 原字符串
	 * @param salt 加密种子
	 * @throws Exception 抛出异常
	 * @return String 加密结果
	 */
	public static String getEncodedStr(String rawStr, String salt) throws Exception {
		EncryptorAES encryptorAES = new EncryptorAES(salt);
		return encryptorAES.encryt(rawStr);
	}
	
	/**
	 * 解密出通过对称性加密的字符串明文
	 * @author Administrator
	 * @version 创建时间：2017年1月12日下午1:49:47
	 * @param encodedStr 加密密文
	 * @param salt 加密种子
	 * @throws Exception 抛出异常
	 * @return String 解密结果
	 */
	public static String getDecodedStr(String encodedStr, String salt) throws Exception {
		EncryptorAES encryptorAES = new EncryptorAES(salt);
		return encryptorAES.decrypt(encodedStr);
	}
	
	/**
	 * 线上充值时使用到的md5签名
	 * @author Administrator
	 * @version 创建时间：2017年1月12日下午1:50:20
	 * @param text 信息明文
	 * @param charSet 字符集
	 * @return String md5加密结果
	 */
	public static String md5(String text, String charSet) {
        return DigestUtils.md5Hex(getContentBytes(text, charSet));
    }
    
	/**
	 * 线上充值时使用到的sha签名
	 * @author Administrator
	 * @version 创建时间：2017年1月12日下午1:51:11
	 * @param text 信息明文
	 * @param charSet 字符集
	 * @return String 签名结果
	 */
    public static String sha(String text, String charSet) {
    	
        return DigestUtils.sha256Hex(getContentBytes(text, charSet));
    }
    
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }

        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误, 指定的编码集不对, 您目前指定的编码集是:" + charset);
        }
    }
    /**
     *  HMAC-SHA1 签名  暂用于有盾实名认证签名使用
     * @author Administrator
     * @version 2015年11月11日 上午9:13:17 
     * @param encryptKey 密钥
     * @param encryptText 被签名的字符串  
     * @return String 签名结果
     */
    public static String HmacSHA1Encrypt(String encryptKey,String encryptText)    
    {           
    	return HmacUtils.hmacSha1Hex(encryptKey, encryptText);
    }

	public static void main(String[] args) {
		try {
			System.out.println( "用户名  ：" + getEncodedPassword("HHgOdWSZG0TdvEzqjVlTupUDPbXYWqrJzGNSXyY5vtw=", "er4#4\\m3VD"));
		}catch (Exception e){
			System.out.println(e);
		}
	}
}
