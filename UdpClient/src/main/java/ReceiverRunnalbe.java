import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * This class serves to receive messages from server and output to console.
 * BYE Keyword stops the thread.
 */
public class ReceiverRunnalbe implements Runnable {
    /**
     * Socket that used get packet from server.
     */
    private DatagramSocket socket;

    /**
     * Constructor.
     *
     * @param socket Socket that used get packet from server.
     */
    public ReceiverRunnalbe(DatagramSocket socket) {
        this.socket = socket;
    }

    /**
     * Receives packets with message from server and displays it on console.
     * If receives BYE message thread is stops.
     * If socket can't receive packet throws RuntimeException.
     */
    @Override
    public void run() {
        try {
            boolean done = false;

            while (!done) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getData().length);
                Output.println(msg);

                if (msg.trim().equals("Echo: " + Constants.SHUTDOWN_MESSAGE))
                    done = true;
            }
        } catch (IOException e){
            throw new RuntimeException("can't receive message from server", e);
        }
        finally {
            socket.close();
        }
    }
}
