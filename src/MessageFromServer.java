import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

public class MessageFromServer implements Runnable {
    private Socket socket;
    private ChatWindow chatWindow;

    public MessageFromServer(Socket socket, ChatWindow chatWindow) {
        this.socket = socket;
        this.chatWindow = chatWindow;
    }

    @Override
    public void run() {
        while (true) {
            try {
                InputStream is = this.socket.getInputStream();
                DataInput dis = new DataInputStream(is);
                String messageReceived = dis.readUTF();

                chatWindow.addMessage(messageReceived);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
