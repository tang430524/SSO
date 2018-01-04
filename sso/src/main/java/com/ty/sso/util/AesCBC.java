package com.ty.sso.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
* AES 是一种可逆加密算法，对用户的敏感信息加密处理
* 对原始数据进行AES加密后，在进行Base64编码转化；
* 正确
*/
public class AesCBC {
/*已确认
* 加密用的Key 可以用26个字母和数字组成
* 此处使用AES-128-CBC加密模式，key需要为16位。
*/

    private static AesCBC instance=null;
    //private static 
    private AesCBC(){

    }
    public static AesCBC getInstance(){
        if (instance==null)
            instance= new AesCBC();
        return instance;
    }
    // 加密
    public String encrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
}

    // 解密
    public String decrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,encodingFormat);
            return originalString;
        } catch (Exception ex) {
            return null;
        }
}

    public static void main(String[] args) throws Exception {
        // 需要加密的字串
        String cSrc = "{\"hlpretag\":\"<span class=\\\"s-fc7\\\">\",\"hlposttag\":\"</span>\",\"s\":\"123\",\"type\":\"1002\",\"offset\":\"0\",\"total\":\"true\",\"limit\":\"30\",\"csrf_token\":\"\"}";
        System.out.println("加密前的字串是："+cSrc);
        // 加密
        String sKey="0CoJUm6Qyw8W8jud";
        String ivParameter="0102030405060708";
        String i="e1btGfszEahxv2ES";//s随机数
        String enString = AesCBC.getInstance().encrypt(cSrc,"utf-8",sKey,ivParameter);
//        System.out.println("加密后的字串是："+ enString);
//        
//        System.out.println("1jdzWuniG6UMtoa3T6uNLA==".equals(enString));
//        
//        // 解密
        String DeString = AesCBC.getInstance().decrypt(enString,"utf-8",sKey,ivParameter);
        System.out.println("解密后的字串是：" + DeString);
    	String encText = AesCBC.getInstance().encrypt(enString,"utf-8",i,ivParameter);
    	String encSecKey = AesCBC.getInstance().encrypt(enString,"utf-8",i,ivParameter);
    	
    	System.out.println(encText);
    }
    
//    public String  encryptedString(String a, String b) {
//    	int f, h, i, k, l;
//		BigInteger j;
//    	String g;
//		int[] c ;
//    	 int d = b.length(), e = 0;
//    	for (; d > e;) {c[e] = b.charAt(e); e++;}
//    	for (; 0 != c.length % a.length();) c[e++] = 0;
//    	for (f = c.length, g = "", e = 0; f > e; e += a.length()) {
//    		for (j = new BigInteger(), h = 0, i = e; i < e + a.chunkSize; ++h) j.digits[h] = c[i++], j.digits[h] += c[i++] << 8;
//    		k = a.barrett.powMod(j, a.e), l = 16 == a.radix ? biToHex(k) : biToString(k, a.radix), g += l + " "
//    	}
//    	return g.substring(0, g.length - 1)
//    }
    
    public void setMaxDigits(Object a) {
    	
    }
}