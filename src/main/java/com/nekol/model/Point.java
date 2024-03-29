package com.nekol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    
    public int x, y;
	
	
	public Point findStartXPoint() {
		int startX = x - 5 < 0 ? 0 : x - 5;
		return new Point(startX, y);
	}
	public Point findEndXPoint() {
		int endX = x + 5 > 19 ? 19 : x + 5;
		return new Point(endX, y);
	}
	public Point findStartYPoint() {
		int startY = y - 5 < 0 ? 0 : y - 5;
		return new Point(x, startY);
	}
	public Point findEndYPoint() {
		int endY = y + 5 > 19 ? 19 : y + 5;
		return new Point(x, endY);
	}
	public Point findLeftTopPoint() {
		int startX = x - 5 < 0 ? 0 : x - 5;
		int startY = y - 5 < 0 ? 0 : y - 5;
		return new Point(startX, startY);
	}
	public Point findRightTopPoint() {
		int endX = x + 5 > 19 ? 19 : x + 5;
		int startY = y - 5 < 0 ? 0 : y - 5;
		return new Point(endX, startY);
	}
	public Point findLeftBottomPoint() {
		int startX = x - 5 < 0 ? 0 : x - 5;
		int endY = y + 5 > 19 ? 19 : y + 5;
		return new Point(startX, endY);
	}
	public Point findRightBottomPoint() {
		int endX = x + 5 > 19 ? 19 : x + 5;
		int endY = y + 5 > 19 ? 19 : y + 5;
		return new Point(endX, endY);
	}
	
	public void log() {
		System.out.println("x: "+ this.x + "| y: " + this.y);
	}

}