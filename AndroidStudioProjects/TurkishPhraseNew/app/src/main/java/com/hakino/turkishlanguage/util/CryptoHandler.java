package com.hakino.turkishlanguage.util;

import android.content.Context;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHandler {
    private static CryptoHandler instance = null;
    public static CryptoHandler getInstance() {
        if (instance == null) {
            instance = new CryptoHandler();
        }
        return instance;
    }

    public String encrypt(String message, Context context) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException {

        String key = SPManager.getEncryptKey(context);
        byte[] srcBuff = message.getBytes("UTF8");
        //here using substring because AES takes only 16 or 24 or 32 byte of key
        SecretKeySpec skeySpec = new
                SecretKeySpec((key).substring(0,16).getBytes(), "AES");
        IvParameterSpec ivSpec = new
                IvParameterSpec((key).substring(0,16).getBytes());
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
        byte[] dstBuff = ecipher.doFinal(srcBuff);
        return Base64.encodeToString(dstBuff, Base64.NO_WRAP);
    }

    public String decrypt(String encrypted, Context context) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {

        String key = SPManager.getDecryptKey(context);
        SecretKeySpec skeySpec = new
                SecretKeySpec((key).substring(0,16).getBytes(), "AES");
        IvParameterSpec ivSpec = new
                IvParameterSpec((key).substring(0,16).getBytes());
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        ecipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
        byte[] raw = Base64.decode(encrypted, Base64.DEFAULT);
        byte[] originalBytes = ecipher.doFinal(raw);
        return new String(originalBytes, "UTF8");
    }
}
