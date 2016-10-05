package com.tea.landlordapp.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public final class EncryptionService {

   private static final byte[] bestKey = {(byte) 0xd6, (byte) 0x1d, (byte) 0xf4, (byte) 0xcf, (byte) 0x3d, (byte) 0x8a, (byte) 0x2, (byte) 0x7e, (byte) 0x65, (byte) 0x32, (byte) 0x4e, (byte) 0x2f,
            (byte) 0xf2, (byte) 0x17, (byte) 0xc0, (byte) 0x9d, (byte) 0xc2, (byte) 0xf1, (byte) 0x3f, (byte) 0xd3, (byte) 0x69, (byte) 0xbd, (byte) 0xb8, (byte) 0x8c, (byte) 0x37, (byte) 0xca,
            (byte) 0x76, (byte) 0xd, (byte) 0xf7, (byte) 0xfd, (byte) 0x12, (byte) 0x1b};

   static final String salt = KeyGenerators.string().generateKey();

   @Deprecated
   public static final String decrypt(String str) {
      if (StringUtils.isBlank(str)) return "";

      return decryptAES(str);
   }

   private static String decryptAES(String str) {
      if (StringUtils.isEmpty(str)) return "";

      // Data is base64 encoded - get raw byte array
      byte[] rawData;
      try {
         rawData = Base64.decodeBase64(str.getBytes("UTF-8"));
      } catch (final UnsupportedEncodingException e) {
         // TODO Auto-generated catch block
         System.err.format("Decode Error. Input was %s\n", str);
         e.printStackTrace();
         return "";
      }

      // IV is first 16 bytes - break apart the rawData
      final byte[] iv = ArrayUtils.subarray(rawData, 0, 16);
      final byte[] cipherText = ArrayUtils.subarray(rawData, 16, rawData.length);

      // int ptLength = cipher.update(cipherText, 0, cipherText.length,
      // plainText, 0);
      byte[] plainText;
      String res = "";
      try {
         final Cipher cipher = getCipherInstance();
         final SecretKeySpec key = getAesKey();

         // decryption pass

         // byte[] plainText = new byte[cipherText.length];
         cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
         plainText = cipher.doFinal(cipherText);
         res = new String(plainText);
      } catch (final Exception e) {
         // TODO Auto-generated catch block
         System.err.format("Decryption Error.  Input was %s\n", str);
         e.printStackTrace();
      }
      return res;
   }

   private static final String encrypt(String str) {
      return encryptAES(str);
   }

   private static String encryptAES(String str) {
      if (StringUtils.isEmpty(str)) {
         return null;
      }

      // create random IV
      final byte[] iv = new byte[16];
      final SecureRandom rnd = new SecureRandom();
      rnd.nextBytes(iv);

      final SecretKeySpec key = getAesKey();
      // int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
      byte[] cipherText;
      try {
         final Cipher cipher = getCipherInstance();
         final byte[] input = str.getBytes("UTF-8");

         // encryption pass

         // byte[] cipherText = new byte[input.length];
         cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
         cipherText = cipher.doFinal(input);
         final byte[] rawData = ArrayUtils.addAll(iv, cipherText);
         final byte[] encodedData = Base64.encodeBase64(rawData);
         return new String(encodedData);
      } catch (final Exception e) {
         // TODO Auto-generated catch block
         System.err.format("Encryption Error.  Original value was %s\n", str);
         e.printStackTrace();
      }

      return "";

   }

   private static SecretKeySpec getAesKey() {
      final SecretKeySpec key = new SecretKeySpec(bestKey, "AES");
      return key;
   }

   private static Cipher getCipherInstance() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
      Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
      final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING", "BC");
      return cipher;
   }

   public static String hash(String clearText) {
      if (ObjectUtils.equals(clearText, null)) {
         return "";
      }

      if (StringUtils.isEmpty(clearText)) {
         return "";
      }

      return new StandardPasswordEncoder().encode(clearText);
   }

   public static String hashIgnoreCase(String clearText) {
      if (StringUtils.isEmpty(clearText)) {
         return "";
      }

      return new StandardPasswordEncoder().encode(clearText.toLowerCase());
   }

   public static boolean hashMatches(String trial, String reference) {
      if (ObjectUtils.equals(trial, null) || ObjectUtils.equals(reference, null)) {
         return false;
      }

      if (StringUtils.isEmpty(trial) && StringUtils.isEmpty(reference)) {
         return true;
      }

      return new StandardPasswordEncoder().matches(trial, reference);
   }

   public static boolean hashMatchesIgnoreCase(String trial, String reference) {
      if (ObjectUtils.equals(trial, null) || ObjectUtils.equals(reference, null)) {
         return false;
      }

      if (StringUtils.isEmpty(trial) && StringUtils.isEmpty(reference)) {
         return true;
      }

      return new StandardPasswordEncoder().matches(trial.toLowerCase(), reference);
   }
   
   public static byte[] digest(String source) throws NoSuchAlgorithmException{
	   MessageDigest md = MessageDigest.getInstance("SHA-256");
	   md.update(source.getBytes());
	   return md.digest();
   }
}
