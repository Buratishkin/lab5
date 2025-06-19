package managers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordManager {
  private static final String PEPPER = "@p%m]ad*-92!^a121";
  private static final byte[] PEPPER_BYTES = PEPPER.getBytes(StandardCharsets.UTF_8);
  private static final Integer SALT_LENGTH = 16;

  public static String getSalt() {
    SecureRandom random = new SecureRandom();
    byte[] saltBytes = new byte[SALT_LENGTH];
    random.nextBytes(saltBytes);
    return Base64.getEncoder().encodeToString(saltBytes);
  }

  public static String hash(String password, String salt) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-224");

      byte[] saltBytes = Base64.getDecoder().decode(salt);

      byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

      byte[] combined = new byte[PEPPER_BYTES.length + passwordBytes.length + saltBytes.length];
      System.arraycopy(PEPPER_BYTES, 0, combined, 0, PEPPER_BYTES.length);
      System.arraycopy(passwordBytes, 0, combined, PEPPER_BYTES.length, passwordBytes.length);
      System.arraycopy(
          saltBytes, 0, combined, PEPPER_BYTES.length + passwordBytes.length, saltBytes.length);

      byte[] hashBytes = digest.digest(combined);

      return Base64.getEncoder().encodeToString(hashBytes);
    } catch (Exception e) {
      throw new IllegalArgumentException("Не найден или не доступен алгоритм SHA-224", e);
    }
  }

  public static boolean checkPassword(String inputPassword, String storedHash, String storedSalt) {
    String inputHash = hash(inputPassword, storedSalt);
    return inputHash.equals(storedHash);
  }
}
