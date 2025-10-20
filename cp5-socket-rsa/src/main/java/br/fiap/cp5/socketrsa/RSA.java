package br.fiap.cp5.socketrsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RSA {
    public final BigInteger p;
    public final BigInteger q;
    public final BigInteger n;
    public final BigInteger phi;
    public final BigInteger e;
    public final BigInteger d;

    public RSA(BigInteger p, BigInteger q, BigInteger e) {
        this.p = p;
        this.q = q;
        this.e = e;
        this.n = p.multiply(q);
        this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.d = e.modInverse(this.phi);
    }

    public String encryptToCiphertext(String plain, BigInteger pubE, BigInteger pubN) {
        byte[] bytes = plain.getBytes(StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int ub = b & 0xFF;
            BigInteger m = BigInteger.valueOf(ub);
            BigInteger c = m.modPow(pubE, pubN);
            sb.append(c.toString()).append(" ");
        }
        return sb.toString().trim();
    }

    public String decryptFromCiphertext(String ciphertext) {
        String[] parts = ciphertext.trim().split("\\s+");
        byte[] out = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            BigInteger c = new BigInteger(parts[i]);
            BigInteger m = c.modPow(this.d, this.n);
            out[i] = (byte) m.intValue();
        }
        return new String(out, StandardCharsets.UTF_8);
    }
}
