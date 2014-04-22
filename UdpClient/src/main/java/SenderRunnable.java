import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * This class serves to send messages that comes from console.
 * Implements Runnable.
 * BYE keyword stops the thread.
 */
public class SenderRunnable implements Runnable{
    /**
     * Socket that used to set connection with server.
     */
    private DatagramSocket socket;

    /**
     * Server ip address.
     */
    private InetAddress address;

    /**
     * Server port number.
     */
    private int port;

    /**
     * Constructor.
     *
     * @param socket Socket that used to set connection with server.
     * @param address Server ip address.
     * @param port Server port number.
     */
    public SenderRunnable(DatagramSocket socket, InetAddress address, int port){
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

    /**
     * Get string from console and send it to server.
     * If entered BYE thread is stops
     */
    @Override
    public void run() {
        try (Scanner in = new Scanner(System.in)){
            boolean done = false;
            while (!done && in.hasNextLine()){
                String msg = in.nextLine();

                sendMSG(msg.getBytes(), msg.getBytes().length, address, port);

                if(msg.trim().equals(Constants.SHUTDOWN_MESSAGE))
                    done = true;
            }
        } finally {
            socket.close();
        }
    }

    /**
     * Send packet with message to DatagramSocket with current address
     *
     * @param msg message in byte array
     * @param length message length
     * @param address DatagramSocket address
     * @param port DatagramSocket port
     */
    public void sendMSG(byte[] msg, int length, InetAddress address, int port){
        DatagramPacket packet = new DatagramPacket(msg, length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException("can't send message to server", e);
        }

    }
}
