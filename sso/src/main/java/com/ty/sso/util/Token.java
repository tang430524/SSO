package com.ty.sso.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class Token {
	@Value("${aes.sKey}")
	 String sKey;//密钥
	@Value("${aes.ivParameter}")
	 String ivParameter;//偏移量
	@Value("${aes.sp}")
	 String aesSp;
	public static void main(String[] args) throws Exception {
		Token t=new Token();
		String cSrc =t.getToken("1001");
		System.out.println(cSrc);
	}
	
	/**
	 * 用str+时间+UUID 创建MD5加密token 在结合uid(str) 使用AES加密
	 * @param str
	 * @return
	 * @throws Exception 
	 */
	public  String getToken(String str) throws Exception{
			String uid=str;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String date=df.format(new Date());
			str+=df;
			 UUID uuid = UUID.randomUUID();
			System.out.println(date);// new Date()为获取当前系统时间
			System.out.println(uuid);// 
			str+=uuid.toString();
	        byte [] buf = str.getBytes();
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(buf);
	        byte [] tmp = md5.digest();
	        StringBuilder sb = new StringBuilder();
	        for (byte b:tmp) {
	            sb.append(Integer.toHexString(b&0xff));
	        }
	        sb.append(aesSp+uid);
	        System.out.println(sb);
	      
	        String enString = AesCBC.getInstance().encrypt(sb.toString(),"utf-8",sKey,ivParameter);
	        System.out.println(enString);
			return enString.toString();
	}
	public  String getIdByToken(String enString) throws Exception{
        String DeString = AesCBC.getInstance().decrypt(enString,"utf-8",sKey,ivParameter);
        return DeString.substring(DeString.lastIndexOf(aesSp)+aesSp.length());
	}
	
}
