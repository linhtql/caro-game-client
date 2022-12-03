package com.nekol.controller;

import com.nekol.model.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaroActionListener implements ActionListener{
    
    private Point point;
    
    public CaroActionListener(Point point) {
        this.point = point;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}