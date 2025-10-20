package br.fiap.cp5.socketrsa;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        BigInteger p = new BigInteger("307");
        BigInteger q = new BigInteger("311");
        BigInteger e = new BigInteger("65537");
        RSA rsa = new RSA(p, q, e);
        System.out.println("Servidor: p=" + p + " q=" + q + " n=" + rsa.n + " phi=" + rsa.phi + " e=" + e + " d=" + rsa.d);
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor aguardando na porta 5000");
            try (Socket s = serverSocket.accept(); Connection conn = new Connection(s)) {
                System.out.println("Cliente conectado");
                conn.send("PUBKEY " + rsa.n.toString() + " " + rsa.e.toString());
                String clientLine = conn.receive();
                System.out.println("Recebido: " + clientLine);
                String[] cp = clientLine.split("\\s+");
                BigInteger clientN = new BigInteger(cp[1]);
                BigInteger clientE = new BigInteger(cp[2]);
                String msgLine = conn.receive();
                System.out.println("Recebido: " + (msgLine.length() > 60 ? msgLine.substring(0, 60) + "..." : msgLine));
                String textoCliente = rsa.decryptFromCiphertext(msgLine.substring(4));
                System.out.println("Mensagem do cliente (decifrada): " + textoCliente);
                String resposta = "Oi, cliente. Chave servidor n=" + rsa.n + " e=" + rsa.e;
                String cipher = rsa.encryptToCiphertext(resposta, clientE, clientN);
                conn.send("MSG " + cipher);
                conn.send("BYE");
                System.out.println("Conexao encerrada");
            }
        } catch (IOException ex) {
            System.out.println("Erro de IO: " + ex.getMessage());
        }
    }
}
