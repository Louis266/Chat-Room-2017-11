package socket_chat_room_1.client;

import socket_chat_room_1.Commands;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientForm {
	/**
	 * basic fields for a chat channel
	 * @author caiyihua
	 */
	private JPanel mainForm;//main panel
    private JTextField txtHost;
    private JButton btnConnect;//button handling connection function
    private JButton btnBroadcast;//button handling broadcast function
    private JTextField txtMessage;
    private JTextField txtUserId;
    private JTextField txtPort;
    private JTextArea txtReceived;//the area that show all received messages
    private JButton btnList;//button of function LIST
    private JButton btnStats;//button of function STATS
    private JButton btnKick;//button of function KICK
    
    

    ClientChannel channel = new ClientChannel(this);// assign a new channel with the form of itself

    public ClientForm() {
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Connect".equalsIgnoreCase(btnConnect.getText())) {// if a connection is established
                    String host;
                    int port;
                    try {
                        host = txtHost.getText().trim();//get connection host
                        port = Integer.parseInt(txtPort.getText());// get connection port
                    } catch (Exception ex) {
                        showMessage("Invalid Host or Port.\n" + ex);//if not 
                        return;
                    }
                    if (!channel.connect(host, port)) {// if not connected, break out 
                        return;
                    }
                    ;
                    btnConnect.setText("Stop");//when connected, set the button as stop for breaking connection
                    enableChatting();
                } else {
                    channel.send(Commands.STOP);//when connected, set stop button 
                    btnConnect.setText("Connect");
                    disbleChatting();//disable all button function
                }
            }
        });
        /**
         * broadcast function
         */
        btnBroadcast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = txtMessage.getText();
                if (message != null && !message.trim().isEmpty()) {
                    channel.send(Commands.BROADCAST + ":" + message);
                }
                txtMessage.setText("");
            }
        });
        /**
         * list function
         */
        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                channel.send(Commands.LIST);
            }
        });
        /**
         * kick function
         */
        btnKick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtUserId.getText();
                if (id == null || id.trim().isEmpty()) {
                    showMessage("Please enter user id first");
                }
                channel.send(Commands.KICK + ":" + id);
            }
        });
        /**
         * stats function
         */
        btnStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtUserId.getText();
                if (id == null || id.trim().isEmpty()) {
                    showMessage("Please enter user id first");
                }
                channel.send(Commands.STATS + ":" + id);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientForm");//set up new frame
        ClientForm form = new ClientForm();//set up new form
        frame.setContentPane(form.mainForm);//add form into the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.getRootPane().setDefaultButton(form.btnBroadcast);
        ((DefaultCaret) form.txtReceived.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        form.btnConnect.requestFocus();
        frame.setVisible(true);
    }

    public void receive(String msg) {
        txtReceived.append(msg);
        txtReceived.append("\n");
//        txtReceived.setCaretPosition(txtReceived.getText().length());
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    public void enableChatting() {//set all button able to be clicked
        txtMessage.setEnabled(true);
        btnBroadcast.setEnabled(true);
        txtUserId.setEnabled(true);
        btnKick.setEnabled(true);
        btnStats.setEnabled(true);
        btnList.setEnabled(true);
        txtMessage.requestFocus();
    }

    public void disbleChatting() {// set all button as not able to be clicked
        txtMessage.setEnabled(false);
        btnBroadcast.setEnabled(false);
        txtUserId.setEnabled(false);
        btnKick.setEnabled(false);
        btnStats.setEnabled(false);
        btnList.setEnabled(false);
        btnConnect.requestFocus();
    }
    
 /**
  * all GUI components and layouts are handled in IntelliJ, following code is automatically created by IntelliJ
  */

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainForm = new JPanel();
        mainForm.setLayout(new GridBagLayout());
        mainForm.setMinimumSize(new Dimension(640, 480));
        mainForm.setPreferredSize(new Dimension(640, 480));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainForm.add(scrollPane1, gbc);
        txtReceived = new JTextArea();
        txtReceived.setEditable(false);
        scrollPane1.setViewportView(txtReceived);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setFocusCycleRoot(false);
        panel1.setPreferredSize(new Dimension(0, 32));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        mainForm.add(panel1, gbc);
        btnConnect = new JButton();
        btnConnect.setText("Connect");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(btnConnect, gbc);
        txtHost = new JTextField();
        txtHost.setMinimumSize(new Dimension(120, 24));
        txtHost.setPreferredSize(new Dimension(120, 24));
        txtHost.setText("localhost");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(txtHost, gbc);
        final JLabel label1 = new JLabel();
        label1.setMinimumSize(new Dimension(50, 16));
        label1.setPreferredSize(new Dimension(50, 16));
        label1.setText("   Port:");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label1, gbc);
        txtPort = new JTextField();
        txtPort.setMinimumSize(new Dimension(60, 24));
        txtPort.setPreferredSize(new Dimension(60, 24));
        txtPort.setText("9999");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(txtPort, gbc);
        final JLabel label2 = new JLabel();
        label2.setPreferredSize(new Dimension(50, 16));
        label2.setText("  Host:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label2, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setOpaque(true);
        panel2.setPreferredSize(new Dimension(0, 32));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainForm.add(panel2, gbc);
        txtMessage = new JTextField();
        txtMessage.setEditable(true);
        txtMessage.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(txtMessage, gbc);
        btnBroadcast = new JButton();
        btnBroadcast.setDefaultCapable(true);
        btnBroadcast.setEnabled(false);
        btnBroadcast.setText("Broadcast");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(btnBroadcast, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.setMinimumSize(new Dimension(640, 42));
        panel3.setPreferredSize(new Dimension(640, 42));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        mainForm.add(panel3, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("User ID:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label3, gbc);
        txtUserId = new JTextField();
        txtUserId.setEnabled(false);
        txtUserId.setMinimumSize(new Dimension(60, 24));
        txtUserId.setPreferredSize(new Dimension(60, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(txtUserId, gbc);
        btnKick = new JButton();
        btnKick.setEnabled(false);
        btnKick.setText("Kick");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(btnKick, gbc);
        btnStats = new JButton();
        btnStats.setEnabled(false);
        btnStats.setText("Stats");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(btnStats, gbc);
        btnList = new JButton();
        btnList.setEnabled(false);
        btnList.setText("List");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(btnList, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainForm;
    }
}
