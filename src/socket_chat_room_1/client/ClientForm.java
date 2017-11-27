package socket_chat_room_1.client;

import socket_chat_room_1.Commands;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientForm {
    private JTextArea txtReceived;
    private JButton btnList;
    private JButton btnStats;
    private JButton btnKick;

    ClientChannel channel = new ClientChannel(this);

    public ClientForm() {
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Connect".equalsIgnoreCase( btnConnect.getText() )) {
                    String host;
                    int port;
                    try {
                        host = txtHost.getText().trim();
                        port = Integer.parseInt(txtPort.getText());
                    } catch (Exception ex) {
                        showMessage("Invalid Host or Port.\n" + ex);
                        return;
                    }
                    channel.connect(host, port);
                    btnConnect.setText("Stop");
                    enableChatting();
                } else {
                    channel.send(Commands.STOP);
                    btnConnect.setText("Connect");
                    disbleChatting();
                }
            }
        });
        btnBroadcast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = txtMessage.getText();
                if (message != null && ! message.trim().isEmpty()) {
                    channel.send(Commands.BROADCAST + ":" + message);
                }
                txtMessage.setText("");
            }
        });
        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                channel.send(Commands.LIST);
            }
        });
        btnKick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtUserId.getText();
                if (id == null || id.trim().isEmpty() ) {
                    showMessage("Please enter user id first");
                }
                channel.send(Commands.KICK + ":" + id);
            }
        });
        btnStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtUserId.getText();
                if (id == null || id.trim().isEmpty() ) {
                    showMessage("Please enter user id first");
                }
                channel.send(Commands.STATS + ":" + id);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientForm");
        ClientForm form = new ClientForm();
        frame.setContentPane(form.mainForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.getRootPane().setDefaultButton(form.btnBroadcast);
        form.btnConnect.requestFocus();
        frame.setVisible(true);
    }

    public void receive(String msg) {
        txtReceived.append(msg);
        txtReceived.append("\n");
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    public void enableChatting() {
        txtMessage.setEnabled(true);
        btnBroadcast.setEnabled(true);
        txtUserId.setEnabled(true);
        btnKick.setEnabled(true);
        btnStats.setEnabled(true);
        btnList.setEnabled(true);
        txtMessage.requestFocus();
    }

    public void disbleChatting() {
        txtMessage.setEnabled(false);
        btnBroadcast.setEnabled(false);
        txtUserId.setEnabled(false);
        btnKick.setEnabled(false);
        btnStats.setEnabled(false);
        btnList.setEnabled(false);
        btnConnect.requestFocus();
    }

    private JPanel mainForm;
    private JTextField txtHost;
    private JButton btnConnect;
    private JButton btnBroadcast;
    private JTextField txtMessage;
    private JTextField txtUserId;
    private JTextField txtPort;
}
