import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ChatWindow {
    private String nameApplication = "Messenger - Desktop Internal";
    private JFrame chatFrame = new JFrame(this.nameApplication);
    private JTextField messageTextField;
    private JTextArea chatTextArea;
    private JTextField nameTextField;
    private JFrame nameUserFrame;
    private String username;

    public ChatWindow() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.configUserNameFrame();
        });
    }

    protected void configUserNameFrame() {
        this.chatFrame.setVisible(false);
        this.nameUserFrame = new JFrame(this.nameApplication);
        this.nameUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.nameTextField = new JTextField(10);

        JLabel chooseNameLabel = new JLabel("Informe seu nome:");

        JButton serverAccessButton = new JButton("Entrar no chat");
        serverAccessButton.addActionListener(new serverAccessButtonListener());

        JPanel userNamePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 20);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        userNamePanel.add(chooseNameLabel, preLeft);
        userNamePanel.add(this.nameTextField, preRight);

        this.nameUserFrame.add(BorderLayout.CENTER, userNamePanel);
        this.nameUserFrame.add(BorderLayout.SOUTH, serverAccessButton);
        this.nameUserFrame.setSize(300, 300);
        this.nameUserFrame.setVisible(true);
    }

    class serverAccessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            username = nameTextField.getText();

            if (username.length() < 1) {
                JOptionPane.showMessageDialog(null, "Informe um nome de usuário.", "Atenção"
                        , JOptionPane.WARNING_MESSAGE);

                return;
            }

            if (connect()) {
                nameUserFrame.setVisible(false);
                showWindowChat();

                return;
            }

            JOptionPane.showMessageDialog(null, "Erro conectando ao servidor.", "Erro"
                    , JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showWindowChat() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.GRAY);
        southPanel.setLayout(new GridBagLayout());

        this.messageTextField = new JTextField(30);
        this.messageTextField.addActionListener(new SendMessageListener());

        JButton sendMessageButton = new JButton("Enviar");
        sendMessageButton.addActionListener(new SendMessageListener());

        this.chatTextArea = new JTextArea();
        this.chatTextArea.setEditable(false);
        this.chatTextArea.setFont(new Font("Serif", Font.PLAIN, 15));
        this.chatTextArea.setLineWrap(true);

        mainPanel.add(new JScrollPane(this.chatTextArea), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(this.messageTextField, left);
        southPanel.add(sendMessageButton, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        this.chatFrame.add(mainPanel);
        this.chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.chatFrame.setSize(470, 300);
        this.chatFrame.setVisible(true);
        this.messageTextField.requestFocusInWindow();
    }

    class SendMessageListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageTextField.getText().length() >= 1) {
                String msg = new StringBuilder("<")
                        .append(username)
                        .append(">:  ")
                        .append(messageTextField.getText())
                        .append("\n").toString();

                sendMessage(msg);
                messageTextField.setText("");
            }

            messageTextField.requestFocusInWindow();
        }
    }

    protected String getUsername() {
        return this.username;
    }

    public void addMessage(String message) {
        if (this.chatTextArea != null) {
            this.chatTextArea.append(message);
        }
    }

    protected abstract boolean connect();

    protected abstract void sendMessage(String message);
}