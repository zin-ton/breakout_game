package com.example.breakout_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem{
    private static int gridRows;
    private static int gridCols;

    public static void setGridRows(int gridRows) {
        Brick.gridRows = gridRows;
    }
    public static void setGridCols(int gridCols) {
        Brick.gridCols = gridCols;
    }
    private double x, y;
    private Color color;

    public Brick(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x*graphicsContext.getCanvas().getWidth()/gridRows, y*graphicsContext.getCanvas().getHeight()/gridCols, graphicsContext.getCanvas().getHeight()/gridCols, graphicsContext.getCanvas().getWidth()/gridRows);
    }
}
