package br.fiap.cp5.socketrsa;

import java.math.BigInteger;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        BigInteger p = new BigInteger("353");
        BigInteger q = new BigInteger("359");
        BigInteger e = new BigInteger("65537");
        RSA rsa = new RSA(p, q, e);
        System.out.println("Cliente: p=" + p + " q=" + q + " n=" + rsa.n + " phi=" + rsa.phi + " e=" + e + " d=" + rsa.d);
        try (Socket s = new Socket("127.0.0.1", 5000); Connection conn = new Connection(s)) {
            String serverLine = conn.receive();
            System.out.println("Recebido: " + serverLine);
            String[] sp = serverLine.split("\\s+");
            BigInteger serverN = new BigInteger(sp[1]);
            BigInteger serverE = new BigInteger(sp[2]);
            conn.send("PUBKEY " + rsa.n.toString() + " " + rsa.e.toString());
            String mensagem = "Oi, servidor. Chave cliente n=" + rsa.n + " e=" + rsa.e;
            String cipher = rsa.encryptToCiphertext(mensagem, serverE, serverN);
            conn.send("MSG " + cipher);
            String respostaCripto = conn.receive();
            System.out.println("Recebido: " + (respostaCripto.length() > 60 ? respostaCripto.substring(0, 60) + "..." : respostaCripto));
            String respostaAberta = rsa.decryptFromCiphertext(respostaCripto.substring(4));
            System.out.println("Mensagem do servidor (decifrada): " + respostaAberta);
            String bye = conn.receive();
            System.out.println(bye);
        }
    }
}
