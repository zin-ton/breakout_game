package com.example.breakout_game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameCanvas extends Canvas {
    private ArrayList <Brick> bricks = new ArrayList<>();
    private GraphicsContext graphicsContext;
    private Paddle paddle;
    private Ball ball;
    private boolean gameRunning = false;

    private AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate;
        @Override
        public void handle(long now) {
            double diff = (now - lastUpdate)/1_000_000_000.;
            lastUpdate = now;
            if(ifBallIsOutOfCanvasXPlus() && ball.getY() < getHeight()){
                ball.bounceHorizontally();
                ball.setPosition(new Point2D(getWidth()-20, ball.getY()));
            }
            if(ifBallInsidePaddle() && ball.getY() < getHeight()){
                ball.bounceVertically();
                ball.setPosition(new Point2D(ball.getX(), paddle.getY()-20-ball.getHeight()/2));
            }
            if(ifBallIsOutOfCanvasXMinus() && ball.getY() < getHeight()){
                ball.bounceHorizontally();
                ball.setPosition(new Point2D(20, ball.getY()));
            }
            if(ifBallIsOutOfCanvasYPlus()){
                ball.bounceVertically();
                ball.setPosition(new Point2D(ball.getX(), 20));
            }
            if(shouldBallBounceFromPaddle() && ball.getY() < getHeight()){
                ball.bounceVertically();
            }
            if(shouldBallBounceHorizontally() && ball.getY() < getHeight()){
                ball.bounceHorizontally();
            }
            if(shouldBallBounceVertically() && ball.getY() < getHeight()){
                ball.bounceVertically();
            }
            ball.updatePosition(diff);
            draw();
        }
        @Override

        public void start() {
            super.start();
            lastUpdate = System.nanoTime();
        }
    };
    public void loadLevel(){
        Brick.setGridRows(20);
        Brick.setGridCols(10);
        for(int i = 2; i < 8; i++){
            for(int j=0; j<20;++j){
                bricks.add(new Brick(j, i, Color.RED));
            }
        }
    }
    public GameCanvas() {
        super(640, 700);

        this.setOnMouseMoved(mouseEvent -> {
            paddle.setPosition(mouseEvent.getX());
            if(!gameRunning)
                ball.setPosition(new Point2D(mouseEvent.getX(), paddle.getY() - ball.getWidth() / 2));
//            else
//                ball.updatePosition();
            draw();
        });

        this.setOnMouseClicked(mouseEvent -> {
            gameRunning = true;
            animationTimer.start();
        });
    }

    public void initialize() {
        graphicsContext = this.getGraphicsContext2D();
        GraphicsItem.setCanvasSize(getWidth(), getHeight());
        paddle = new Paddle();
        ball = new Ball();
    }

    public void draw() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, getWidth(), getHeight());
        paddle.draw(graphicsContext);
        ball.draw(graphicsContext);
        loadLevel();
    }
    private boolean shouldBallBounceHorizontally() {
        return ball.getX() <= 0 || ball.getX() >= getWidth() - ball.getWidth();
    }
    private boolean shouldBallBounceVertically() {
        return ball.getY() <= 0 ;
    }
    private boolean shouldBallBounceFromPaddle(){
        return ball.getY() + ball.getHeight() >= paddle.getY() &&
                ball.getX() + ball.getWidth() >= paddle.getX() &&
                ball.getX() <= paddle.getX() + paddle.getWidth();
    }
    private boolean ifBallIsOutOfCanvasYPlus(){
        return ball.getY() < 0;
    }
    private boolean ifBallIsOutOfCanvasXPlus(){
        return ball.getX()+ball.getWidth() > getWidth();
    }
    private boolean ifBallIsOutOfCanvasXMinus(){
        return ball.getX() < 0;
    }
    private boolean ifBallInsidePaddle(){
        return ball.getY() < paddle.getY()+paddle.height &&
                ball.getY() > paddle.getY() - paddle.getHeight() &&
                ball.getX() + ball.getWidth() > paddle.getX() &&
                ball.getX() < paddle.getX() + paddle.getWidth();
    }
}
