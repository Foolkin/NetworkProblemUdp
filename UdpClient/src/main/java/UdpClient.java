import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class contains DatagramSocket with which you can connect to server
 */
public class UdpClient {

    /**
     * DatagramSocket, that will be used to set connection with server.
     */
    private DatagramSocket socket;

    /**
     * Constructor.
     * Initialize socket.
     */
    public UdpClient(){
        initDatagramSocket();
    }

    /**
     * Starts session with the server
     * @param address servers ip address
     * @param port servers port
     */
    public void start(InetAddress address, int port){
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(new SenderRunnable(socket, address, port));
        executor.execute(new ReceiverRunnalbe(socket));

        executor.shutdown();
    }

    /**
     * Initialize socket.
     */
    private void initDatagramSocket(){
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException("couldn't create datagram socket", e);
        }
    }

    /**
     * Starts client and set session with server.
     * address and port number receives from console.
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Unexpected number of arguments");
        }
        try {
            InetAddress address = InetAddress.getByName(args[0]);
            int port = Integer.valueOf(args[1]);

            new UdpClient().start(address, port);
        } catch (UnknownHostException e) {
            throw new RuntimeException("can't connect to server", e);
        }

    }
}
