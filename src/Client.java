import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client extends ChatWindow {
    private Socket socket;

    public static void main(String[] args) {
        new Client();
    }

    @Override
    protected boolean connect() {
        try {
            this.socket = new Socket("127.0.0.1", 3333);
            MessageFromServer messageFromServer = new MessageFromServer(this.socket, this);
            new Thread(messageFromServer).start();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void sendMessage(String message) {
        try {
            OutputStream os = this.socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
