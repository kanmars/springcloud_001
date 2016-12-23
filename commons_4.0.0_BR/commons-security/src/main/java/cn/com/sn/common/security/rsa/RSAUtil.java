package cn.com.sn.common.security.rsa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cn.com.sn.common.security.base64.Base64Util;

public class RSAUtil {
	/**
	 * 获取随机的RSA密钥公钥对
	 * @return
	 */
	public static KeyPair getKeyPair(){
		KeyPair result = null;
		try {
			result = KeyPairGenerator.getInstance("RSA").generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 根据keyStorePath,alias和password获取密钥公钥对
	 * @param keyStorePath	JavaKeyStore的储存位置
	 * @param alias	别名
	 * @param password	keyStore的密码
	 * @return
	 */
	public static KeyPair getKeyPair(String keyStorePath,String alias,char[] password){
		KeyPair result = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(new FileInputStream(keyStorePath), password);
			Key privateKey = keyStore.getKey(alias, password);
			if(privateKey instanceof PrivateKey){
				Certificate cert = keyStore.getCertificate(alias);
				PublicKey publicKey = cert.getPublicKey();
				result = new KeyPair(publicKey, (PrivateKey)privateKey);
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 根据x509证书的位置获取公钥，推荐使用pem格式证书
	 * @param x509CertficateFilePath
	 * @return
	 */
	public static PublicKey getPublicKeyFromX509CertficateFilePath(String x509CertficateFilePath){
		PublicKey publicKey = null;
		try {
			Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(x509CertficateFilePath));
			publicKey = certificate.getPublicKey();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return publicKey;
	}
	/**
	 * 根据x509证书的资源位置获取公钥，推荐使用pem格式证书
	 * @param x509CertficateResourcesPath
	 * @return
	 */
	public static PublicKey getPublicKeyFromX509CertficateResourcesPath(String x509CertficateResourcesPath){
		PublicKey publicKey = null;
		try {
			Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(RSAUtil.class.getResourceAsStream(x509CertficateResourcesPath));
			publicKey = certificate.getPublicKey();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return publicKey;
	}
	/**
	 * 从pkcs8PrivateKeyFilePath中获取到私钥
	 * @param pkcs8PrivateKeyFilePath
	 * @return
	 */
	public static PrivateKey getPrivateKeyFromPkcs8FilePath(String pkcs8PrivateKeyFilePath){
		PrivateKey privateKey = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pkcs8PrivateKeyFilePath)));
			String str = "";
			String s;
			s = br.readLine();
			while((s = br.readLine())!=null && s.charAt(0)!='-'){
				str += s +"\r";
			}
			byte[] b = Base64Util.decodeMessage(str);
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(b);
			privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}
	/**
	 * 从pkcs8PrivateKeyResourcesPath中获取到私钥
	 * @param pkcs8PrivateKeyResourcesPath
	 * @return
	 */
	public static PrivateKey getPrivateKeyFromPkcs8ResourcesPath(String pkcs8PrivateKeyResourcesPath){
		PrivateKey privateKey = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(RSAUtil.class.getResourceAsStream(pkcs8PrivateKeyResourcesPath)));
			String str = "";
			String s;
			s = br.readLine();
			while((s = br.readLine())!=null && s.charAt(0)!='-'){
				str += s +"\r";
			}
			byte[] b = Base64Util.decodeMessage(str);
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(b);
			privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}
	
	public static byte[] encodeMessage(Key key,byte[] message){
		byte[] result = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			result = cipher.doFinal(message);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static byte[] decodeMessage(Key key,byte[] message){
		byte[] result = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(message);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		//PrivateKey privateKey = getPrivateKeyFromPkcs8FilePath("E:\\server.key.pkcs8");
		//PublicKey publicKey = getPublicKeyFromX509CertficateFilePath("E:\\server.cert");
		
		Object Obj = RSAUtil.class.getResourceAsStream("/RSAPKI/server.key.pcks8");
		System.out.println(Obj);
		
		
		
		//PrivateKey privateKey = getPrivateKeyFromPkcs8ResourcesPath("/RSAPKI/server.key.pcks8");
		//PublicKey publicKey = getPublicKeyFromX509CertficateResourcesPath("/RSAPKI/server.cert");
//		String message="我是中国人";
//		byte[] encode = encodeMessage(publicKey, message.getBytes());
//
//		byte[] decode = decodeMessage(privateKey, encode);
//		System.out.println(new String(decode));
	}
	
}

