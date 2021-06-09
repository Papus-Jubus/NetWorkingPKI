
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) {
        try {
            new Servor().start();
        } catch (Exception e) {
            System.out.println(e + "  Erreur lors de l'initialisation du serveur a " + e.getLocalizedMessage());

        }

    }

    /*
     * Class Servor
     */
    public static class Servor extends Thread {

        public void run() {
            int NombreClient = 0;

            while (true) {
                try {
                    ServerSocket ss = new ServerSocket(23);
                    System.out.println("Serveur lance");
                    Socket s = ss.accept();
                    ++NombreClient;
                    new Conversation(s, NombreClient);
                } catch (Exception e) {
                    System.out.println(e + "  Erreur lors de la creation du serveur");
                    e.printStackTrace();

                }

            }

        }

    }

    /**
     * Conversation
     */

    public static class Conversation extends Thread {
        private Socket s;
        private int number;

        public Conversation(Socket s, int num) {
            super();
            this.s = s;
            this.number = num;
        }

        public void run() {
            try {
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = s.getOutputStream();
                PrintWriter pr = new PrintWriter(os, true);

                String IP = s.getRemoteSocketAddress().toString();

                System.out.println("Connexion client numero" + number + " IP:" + IP);
                pr.println("Bienvenue,vous etes le client numero " + number);// le serveur envoie un message de
                                                                             // bienvenue au
                                                                             // client

                while (true) {
                    String requete = br.readLine();

                    if (requete != null) {
                        System.out.println(IP + " a envoye la requete " + requete);
                        String rep = "Size: " + requete.length();
                        System.out.println(rep);

                    }
                }
            } catch (Exception e) {
                System.out.println("Erreur Code Conversation du serveur");
            }

        }
    }

}
