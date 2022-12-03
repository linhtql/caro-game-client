package com.nekol.controller;

import com.nekol.model.User;
import com.nekol.view.CompetitorInfoFrame;
import com.nekol.view.CreateRoomPasswordFrame;
import com.nekol.view.FindRoomFrame;
import com.nekol.view.FriendListFrame;
import com.nekol.view.FriendRequestFrame;
import com.nekol.view.GameAIFrame;
import com.nekol.view.GameClientFrame;
import com.nekol.view.GameNoticeFrame;
import com.nekol.view.HomePageFrame;
import com.nekol.view.JoinRoomPasswordFrame;
import com.nekol.view.LoginFrame;
import com.nekol.view.RankFrame;
import com.nekol.view.RegisterFrame;
import com.nekol.view.RoomListFrame;
import com.nekol.view.RoomNameFrame;
import com.nekol.view.WaitingRoomFrame;
import javax.swing.JFrame;

public class Client {

    public enum View {
        LOGIN,
        REGISTER,
        HOMEPAGE,
        ROOMLIST,
        FRIENDLIST,
        FINDROOM,
        WAITINGROOM,
        GAMECLIENT,
        CREATEROOMPASSWORD,
        JOINROOMPASSWORD,
        COMPETITORINFO,
        RANK,
        GAMENOTICE,
        FRIENDREQUEST,
        GAMEAI,
        ROOMNAMEFRM
    }

    public static User user;

    public static LoginFrame loginFrame;
    public static RegisterFrame registerFrame;
    public static HomePageFrame homePageFrame;
    public static CompetitorInfoFrame competitorInfoFrame;
    public static RankFrame rankFrame;
    public static RoomListFrame roomListFrame;
    public static FriendListFrame friendListFrame;
    public static FindRoomFrame findRoomFrame;
    public static WaitingRoomFrame waitingRoomFrame;
    public static GameClientFrame gameClientFrame;
    public static CreateRoomPasswordFrame createRoomPasswordFrame;
    public static JoinRoomPasswordFrame joinRoomPasswordFrame;
    public static GameNoticeFrame gameNoticeFrame;
    public static GameAIFrame gameAIFrame;
    public static RoomNameFrame roomNameFrame;
    public static FriendRequestFrame friendRequestFrame;

    public static SocketHandle socketHandle;

    public Client() {

    }

    public void initView() {

        loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        socketHandle = new SocketHandle();
        socketHandle.run();
    }
    
        public static JFrame getVisibleJFrame(){
        if(roomListFrame!=null&&roomListFrame.isVisible())
            return roomListFrame;
        if(friendListFrame!=null&&friendListFrame.isVisible()){
            return friendListFrame;
        }
        if(createRoomPasswordFrame!=null&&createRoomPasswordFrame.isVisible()){
            return createRoomPasswordFrame;
        }
        if(joinRoomPasswordFrame!=null&&joinRoomPasswordFrame.isVisible()){
            return joinRoomPasswordFrame;
        }
        if(rankFrame!=null&&rankFrame.isVisible()){
            return rankFrame;
        }
        return homePageFrame;
    }
    
     public static void openView(View viewName){
        if(viewName != null){
            switch(viewName){
                case LOGIN:
                    loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);
                    break;
                case REGISTER:
                    registerFrame = new RegisterFrame();
                    registerFrame.setVisible(true);
                    break;
                case HOMEPAGE:
                    homePageFrame = new HomePageFrame();
                    homePageFrame.setVisible(true);
                    break;
                case ROOMLIST:
                   roomListFrame = new RoomListFrame();
                    roomListFrame.setVisible(true);
                    break;
                case FRIENDLIST:
                    friendListFrame = new FriendListFrame();
                    friendListFrame.setVisible(true);
                    break;
                case FINDROOM:
                    findRoomFrame = new FindRoomFrame();
                    findRoomFrame.setVisible(true);
                    break;
                case WAITINGROOM:
                    waitingRoomFrame = new WaitingRoomFrame();
                    waitingRoomFrame.setVisible(true);
                    break;
                
                case CREATEROOMPASSWORD:
                    createRoomPasswordFrame = new CreateRoomPasswordFrame();
                    createRoomPasswordFrame.setVisible(true);
                    break;
                case RANK:
                    rankFrame = new RankFrame();
                    rankFrame.setVisible(true);
                    break;
                case GAMEAI:
                    gameAIFrame = new GameAIFrame();
                    gameAIFrame.setVisible(true);
                    break;
                case ROOMNAMEFRM:
                    roomNameFrame = new RoomNameFrame();
                    roomNameFrame.setVisible(true);
            }
        }
    }

    public static void openView(View viewName, int arg1, String arg2) {
        if (viewName != null) {
            switch (viewName) {
                case JOINROOMPASSWORD:
                    joinRoomPasswordFrame = new JoinRoomPasswordFrame(arg1, arg2);
                    joinRoomPasswordFrame.setVisible(true);
                    break;
                case FRIENDREQUEST:
                    friendRequestFrame = new FriendRequestFrame(arg1, arg2);
                    friendRequestFrame.setVisible(true);
            }
        }
    }

    public static void openView(View viewName, User competitor, int room_ID, int isStart, String competitorIP) {
        if (viewName != null) {
            switch (viewName) {
                case GAMECLIENT:
                    gameClientFrame = new GameClientFrame(competitor, room_ID, isStart, competitorIP);
                    gameClientFrame.setVisible(true);
                    break;
            }
        }
    }

    public static void openView(View viewName, User user) {
        if (viewName != null) {
            switch (viewName) {
                case COMPETITORINFO:
                    competitorInfoFrame = new CompetitorInfoFrame(user);
                    competitorInfoFrame.setVisible(true);
                    break;
            }
        }
    }

    public static void openView(View viewName, String arg1, String arg2) {
        if (viewName != null) {
            switch (viewName) {
                case GAMENOTICE:
                    gameNoticeFrame = new GameNoticeFrame(arg1, arg2);
                    gameNoticeFrame.setVisible(true);
                    break;
                case LOGIN:
                    loginFrame = new LoginFrame(arg1, arg2);
                    loginFrame.setVisible(true);
            }
        }
    }

    public static void closeView(View viewName) {
        if (viewName != null) {
            switch (viewName) {
                case LOGIN:
                    loginFrame.dispose();
                    break;
                case REGISTER:
                    registerFrame.dispose();
                    break;
                case HOMEPAGE:
                    homePageFrame.dispose();
                    break;
                case ROOMLIST:
                    roomListFrame.dispose();
                    break;
                case FRIENDLIST:
                    friendListFrame.stopAllThread();
                    friendListFrame.dispose();
                    break;
                case FINDROOM:
                    findRoomFrame.stopAllThread();
                    findRoomFrame.dispose();
                    break;
                case WAITINGROOM:
                    waitingRoomFrame.dispose();
                    break;
                case GAMECLIENT:
                    gameClientFrame.stopAllThread();
                    gameClientFrame.dispose();
                    break;
                case CREATEROOMPASSWORD:
                    createRoomPasswordFrame.dispose();
                    break;
                case JOINROOMPASSWORD:
                    joinRoomPasswordFrame.dispose();
                    break;
                case COMPETITORINFO:
                    competitorInfoFrame.dispose();
                    break;
                case RANK:
                    rankFrame.dispose();
                    break;
                case GAMENOTICE:
                    gameNoticeFrame.dispose();
                    break;
                case FRIENDREQUEST:
                    friendRequestFrame.dispose();
                    break;
                case GAMEAI:
                    gameAIFrame.dispose();
                    break;
                case ROOMNAMEFRM:
                    roomNameFrame.dispose();
                    break;
            }

        }
    }

    public static void closeAllViews() {
       if(loginFrame!=null) loginFrame.dispose();
        if(registerFrame!=null) registerFrame.dispose();
        if(homePageFrame!=null) homePageFrame.dispose();
        if(roomListFrame!=null) roomListFrame.dispose();
        if(friendListFrame!=null){
            friendListFrame.stopAllThread();
            friendListFrame.dispose();
        } 
        if(findRoomFrame!=null){
            findRoomFrame.stopAllThread();
            findRoomFrame.dispose();
        } 
        if(waitingRoomFrame!=null) waitingRoomFrame.dispose();
        if(gameClientFrame!=null){
            gameClientFrame.stopAllThread();
            gameClientFrame.dispose();
        } 
        if(createRoomPasswordFrame!=null) createRoomPasswordFrame.dispose();
        if(joinRoomPasswordFrame!=null) joinRoomPasswordFrame.dispose();
        if(competitorInfoFrame!=null) competitorInfoFrame.dispose();
        if(rankFrame!=null) rankFrame.dispose();
        if(gameNoticeFrame!=null) gameNoticeFrame.dispose();
        if(friendRequestFrame!=null) friendRequestFrame.dispose();
        if(gameAIFrame!=null) gameAIFrame.dispose();
        if(roomNameFrame!=null) roomNameFrame.dispose();
    }

    public static void main(String[] args) {
        new Client().initView();
    }

}
