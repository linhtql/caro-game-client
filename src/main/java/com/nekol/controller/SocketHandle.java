package com.nekol.controller;

import com.nekol.model.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import lombok.Data;

@Data
public class SocketHandle implements Runnable {

    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private int idServer;

    public List<User> getListUser(String[] message) {
        List<User> friend = new ArrayList<>();
        for (int i = 1; i < message.length; i = i + 4) {
            friend.add(new User(Integer.parseInt(message[i]),
                    message[i + 1],
                    message[i + 2].equals("1"),
                    message[i + 3].equals("1")));
        }
        return friend;
    }

    public List<User> getListRank(String[] message) {
        List<User> friend = new ArrayList<>();
        for (int i = 1; i < message.length; i = i + 9) {
            friend.add(new User(Integer.parseInt(message[i]),
                    message[i + 1],
                    message[i + 2],
                    message[i + 3],
                    Integer.parseInt(message[i + 4]),
                    Integer.parseInt(message[i + 5]),
                    Integer.parseInt(message[i + 6]),
                    Integer.parseInt(message[i + 7])));
        }
        return friend;
    }

    public User getUserFromString(int start, String[] message) {
        return new User(Integer.parseInt(message[start]),
                message[start + 1],
                message[start + 2],
                message[start + 3],
                Integer.parseInt(message[start + 4]),
                Integer.parseInt(message[start + 5]),
                Integer.parseInt(message[start + 6]),
                Integer.parseInt(message[start + 7]));
    }

    @Override
    public void run() {

        try {
            socketOfClient = new Socket("127.0.0.1", 7777);
            System.out.println("Kết nối thành công!");
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            String message;
            while (true) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                String[] messageSplit = message.split(",");
                if (messageSplit[0].equals("server-send-id")) {
                    idServer = Integer.parseInt(messageSplit[1]);
                }

                // if login success
                if (messageSplit[0].equals("login-success")) {
                    System.out.println("Đăng nhập thành công");
                    Client.closeAllViews();
                    User user = getUserFromString(1, messageSplit);
                    Client.user = user;
                    Client.openView(Client.View.HOMEPAGE);
                }

                // if login fail
                if (messageSplit[0].equals("wrong-user")) {
                    System.out.println("Thông tin sai");
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.openView(Client.View.LOGIN, messageSplit[1], messageSplit[2]);
                    Client.loginFrame.showError("Tài khoản hoặc mật khẩu không chính xác");
                }

                // login in another place
                if (messageSplit[0].equals("dupplicate-login")) {
                    System.out.println("Đã đăng nhập");
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.openView(Client.View.LOGIN, messageSplit[1], messageSplit[2]);
                    Client.loginFrame.showError("Tài khoản đã đăng nhập ở nơi khác");
                }

                // process register duplicate name
                if (messageSplit[0].equals("duplicate-username")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.REGISTER);
                    JOptionPane.showMessageDialog(Client.registerFrame, "Tên tài khoản đã được người khác sử dụng");
                }

                // process recieve information, chat from server
                if (messageSplit[0].equals("chat-server")) {
                    if (Client.homePageFrame != null) {
                        Client.homePageFrame.addMessage(messageSplit[1]);
                    }
                }

                // process information partner isFriend
                if (messageSplit[0].equals("check-friend-response")) {
                    if (Client.competitorInfoFrame != null) {
                        Client.competitorInfoFrame.checkFriend((messageSplit[1].equals("1")));
                    }
                }

                // process result with find room from server
                if (messageSplit[0].equals("room-fully")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                    JOptionPane.showMessageDialog(Client.homePageFrame, "Phòng chơi đã đủ 2 người chơi");
                }

                // process when don't find room from into room feature
                if (messageSplit[0].equals("room-not-found")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                    JOptionPane.showMessageDialog(Client.homePageFrame, "Không tìm thấy phòng");
                }

                // process when pass room wrong
                if (messageSplit[0].equals("room-wrong-password")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                    JOptionPane.showMessageDialog(Client.homePageFrame, "Mật khẩu phòng sai");
                }

                // process when view rank
                if (messageSplit[0].equals("return-get-rank-charts")) {
                    if (Client.rankFrame != null) {
                        Client.rankFrame.setDataToTable(getListRank(messageSplit));
                    }
                }

                // process list room
                if (messageSplit[0].equals("room-list")) {
                    Vector<String> rooms = new Vector<>();
                    Vector<String> passwords = new Vector<>();
                    for (int i = 1; i < messageSplit.length; i = i + 2) {
                        rooms.add("Phòng " + messageSplit[i]);
                        passwords.add(messageSplit[i + 1]);
                    }
                    Client.roomListFrame.updateRoomList(rooms, passwords);
                }

                // process list friend
                if (messageSplit[0].equals("return-friend-list")) {
                    Client.friendListFrame.updateFriendList(getListUser(messageSplit));
                    if (Client.friendListFrame != null) {
                        
                    }
                }

                // go to room
                if (messageSplit[0].equals("go-to-room")) {
                    System.out.println("Vào phòng");
                    int roomID = Integer.parseInt(messageSplit[1]);
                    String competitorIP = messageSplit[2];
                    int isStart = Integer.parseInt(messageSplit[3]);

                    User competitor = getUserFromString(4, messageSplit);
                    if (Client.findRoomFrame != null) {
                        Client.findRoomFrame.showFindedRoom();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(Client.findRoomFrame, "Lỗi khi sleep thread");
                        }
                    } else if (Client.waitingRoomFrame != null) {
                        Client.waitingRoomFrame.showFindedCompetitor();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(Client.waitingRoomFrame, "Lỗi khi sleep thread");
                        }
                    }
                    Client.closeAllViews();
                    System.out.println("Đã vào phòng: " + roomID);
                    //Xử lý vào phòng
                    Client.openView(Client.View.GAMECLIENT,
                            competitor,
                            roomID,
                            isStart,
                            competitorIP);
                    Client.gameClientFrame.newgame();
                }

                // create room and server return name room
                if (messageSplit[0].equals("your-created-room")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.WAITINGROOM);
                    Client.waitingRoomFrame.setRoomName(messageSplit[1]);
                    if (messageSplit.length == 3) {
                        Client.waitingRoomFrame.setRoomPassword("Mật khẩu phòng: " + messageSplit[2]);
                    }
                }

                // process request friend
                if (messageSplit[0].equals("make-friend-request")) {
                    int ID = Integer.parseInt(messageSplit[1]);
                    String nickname = messageSplit[2];
                    Client.openView(Client.View.FRIENDREQUEST, ID, nickname);
                }

                // process when request solo
                if (messageSplit[0].equals("duel-notice")) {
                    int res = JOptionPane.showConfirmDialog(Client.getVisibleJFrame(), "Bạn nhận được lời thách đấu của " + messageSplit[2] + " (ID=" + messageSplit[1] + ")", "Xác nhận thách đấu", JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION) {
                        Client.socketHandle.write("agree-duel," + messageSplit[1]);
                    } else {
                        Client.socketHandle.write("disagree-duel," + messageSplit[1]);
                    }
                }
                // process no solo
                if (messageSplit[0].equals("disagree-duel")) {
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                    JOptionPane.showMessageDialog(Client.homePageFrame, "Đối thủ không đồng ý thách đấu");
                }
                // process go one step
                if (messageSplit[0].equals("caro")) {
                    Client.gameClientFrame.addCompetitorMove(messageSplit[1], messageSplit[2]);
                }
                if (messageSplit[0].equals("chat")) {
                    Client.gameClientFrame.addMessage(messageSplit[1]);
                }
                if (messageSplit[0].equals("draw-request")) {
                    Client.gameClientFrame.showDrawRequest();
                }
                if (messageSplit[0].equals("draw-refuse")) {
                    if (Client.gameNoticeFrame != null) {
                        Client.closeView(Client.View.GAMENOTICE);
                    }
                    Client.gameClientFrame.displayDrawRefuse();
                }

                if (messageSplit[0].equals("new-game")) {
                    System.out.println("New game");
                    Thread.sleep(4000);
                    Client.gameClientFrame.updateNumberOfGame();
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.gameClientFrame.newgame();
                }
                if (messageSplit[0].equals("draw-game")) {
                    System.out.println("Draw game");
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.openView(Client.View.GAMENOTICE, "Ván chơi hòa", "Ván chơi mới dang được thiết lập");
                    Client.gameClientFrame.displayDrawGame();
                    Thread.sleep(4000);
                    Client.gameClientFrame.updateNumberOfGame();
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.gameClientFrame.newgame();
                }
                if (messageSplit[0].equals("competitor-time-out")) {
                    Client.gameClientFrame.increaseWinMatchToUser();
                    Client.openView(Client.View.GAMENOTICE, "Bạn đã thắng do đối thủ quá thới gian", "Đang thiết laapju ván chơi mới");
                    Thread.sleep(4000);
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.gameClientFrame.updateNumberOfGame();
                    Client.gameClientFrame.newgame();
                }
                if (messageSplit[0].equals("voice-message")) {
                    switch (messageSplit[1]) {
                        case "close-mic":
                            Client.gameClientFrame.addVoiceMessage("đã tắt mic");
                            break;
                        case "open-mic":
                            Client.gameClientFrame.addVoiceMessage("đã bật mic");
                            break;
                        case "close-speaker":
                            Client.gameClientFrame.addVoiceMessage("đã tắt âm thanh cuộc trò chuyện");
                            break;
                        case "open-speaker":
                            Client.gameClientFrame.addVoiceMessage("đã bật âm thanh cuộc trò chuyện");
                            break;
                    }
                }

                if (messageSplit[0].equals("left-room")) {
                    Client.gameClientFrame.stopTimer();
                    Client.closeAllViews();
                    Client.openView(Client.View.GAMENOTICE, "Đối thủ đã thoát khỏi phòng", "Đang trở về trang chủ");
                    Thread.sleep(3000);
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }

}
