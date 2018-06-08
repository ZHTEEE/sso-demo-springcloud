package com.zhteee.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密模块
 */
@Component
public class Encrypt {
	
    // BASE64加密  
    public  String enBASE64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = Base64.encodeBase64String(b);  
        }  
        return s;  
    }  
  
    // BASE64解密  
    public  String deBASE64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            try {  
                b = Base64.decodeBase64(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    } 
    
    // SHA256加密  
    public  String enSHA256(String str){
    	String output = null;
		MessageDigest digest;
    try {
        digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(str.getBytes("UTF-8"));
         output = Hex.encodeHexString(hash);
    } catch (NoSuchAlgorithmException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
	return output;
}
    //MD5 加密
	public  String enMD5(String plainText) {
		String strs = null;
		try {
			// 生成实现指定摘要算法的 MessageDigest 对象。
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组更新摘要。
			md.update(plainText.getBytes());
			// 通过执行诸如填充之类的最终操作完成哈希计算。
			byte b[] = md.digest();
			// 生成具体的md5密码到buf数组
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			strs = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;

	}
}
