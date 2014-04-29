import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * This class contains DatagramSocket that could send echo messages to client.
 */
public class UdpServer {
    /**
     * DatagramSocket, that will be used to connect with client.
     */
    private DatagramSocket socket;

    /**
     * Constructor.
     *
     * @param port port number.
     */
    public UdpServer(int port){
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException("can't initialize datagram socket", e);
        }
    }

    /**
     * Starts listening socket. If receive incoming packet create session.
     */
    public void start(){
        try {
            DatagramPacket packet = recievePacket();
            String greetings = "Anotheria bootcamp server. \nEnter BYE to exit.";

            InetAddress address = packet.getAddress();
            int port = packet.getPort();

            sendBackPacket(greetings.getBytes(), greetings.getBytes().length, address, port);

            echo();

        } finally {
            socket.close();
        }
    }

    /**
     * Receives packet, gets message and send back echo message
     */
    public void echo(){
        boolean done = false;

        while (!done) {
            DatagramPacket packet = recievePacket();
            InetAddress address = packet.getAddress();
            int port = packet.getPort();

            String message = new String(packet.getData(), 0, packet.getLength());
            String echo = "Echo: " + message;

            sendBackPacket(echo.getBytes(), echo.getBytes().length, address, port);
            if (message.trim().equals(Constants.SHUTDOWN_MESSAGE))
                done = true;
        }
    }

    /**
     * Waits until a packet is received.
     * If socket can't receive packet throws RuntimeException.
     * @return received packed
     */
    public DatagramPacket recievePacket(){
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            throw new RuntimeException("can't receive a packet", e);
        }
        return packet;
    }

    /**
     * Send packet with message to DatagramSocket with current address
     *
     * @param message message in byte array
     * @param length message length
     * @param address DatagramSocket address
     * @param port DatagramSocket port
     */
    public void sendBackPacket(byte[] message, int length, InetAddress address, int port){
        DatagramPacket packet = new DatagramPacket(message, length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException("can't send a packet", e);
        }
    }

    /**
     * Starts server on port with number 8189
     * @param args
     */
    public static void main(String[] args){
        if (args.length < 1) {
            throw new IllegalArgumentException("Input socket port");
        }

        final int port = Integer.parseInt(args[0]);

        new UdpServer(port).start();
    }
}
