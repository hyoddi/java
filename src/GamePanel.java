import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Deque;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private static final double SCALE = 2.0; 
    private final Player player;
    private final int groundY;
    private final Image sprite;
    private final CommandInvoker invoker;

    public GamePanel(Player p, int width, int height, Image sprite, CommandInvoker invoker) {
        this.player = p;
        this.sprite = sprite;
        this.invoker = invoker;
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(250, 250, 250));
        groundY = height - 60;
        setDoubleBuffered(true);
        setFocusable(true);
    }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // background/ground
        g2.setColor(new Color(230,230,230));
        for (int x=0; x<getWidth(); x+=50) g2.drawLine(x, 0, x, getHeight());
        g2.setColor(new Color(200,190,170));
        g2.fillRect(0, groundY, getWidth(), getHeight()-groundY);

        // grid 
        int drawW = (int)Math.round(player.spriteW * SCALE);
        int drawH = (int)Math.round(player.spriteH * SCALE);

        // player location (player's foot is on the ground)
        int sx = player.x;
        int sy = groundY - player.y - drawH; 

        // attack aura
        if (player.auraTicks > 0) {

            g2.setColor(player.auraColor);

            int auraSize = 30; // 캐릭터보다 얼마나 더 크게 할지

            // 아우라 전체 크기
            int auraW = drawW + auraSize;
            int auraH = drawH + auraSize;

            // 중심을 맞추기 위해 반만큼 빼기
            int auraX = sx - auraSize / 2;
            int auraY = sy - auraSize / 2;

            g2.fillOval(auraX, auraY, auraW, auraH);
        }





        // draw sprite image
        if (sprite != null) {
            g2.drawImage(sprite, sx, sy, drawW, drawH, null);
            g2.setColor(new Color(0,0,0,60));
            g2.drawRect(sx, sy, drawW, drawH);
        } else {
            int arc = (int)Math.round(8 * SCALE);
            g2.setColor(player.baseColor);
            g2.fillRoundRect(sx, sy, drawW, drawH, arc, arc);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRoundRect(sx, sy, drawW, drawH, arc, arc);
        }

        // BubbleText nearby Player to show Undo Jump
        if (player.bubbleTicks > 0 && player.bubbleText != null) {
            g2.setColor(Color.BLACK);
            g2.drawString(player.bubbleText, sx + player.spriteW , sy - 20);
        }
        
        // HUD
        // draw HUD "←/→ Move | Space Jump | A Attack | U Undo | R Redo"
        g.setColor(Color.BLACK);
        g.drawString("←/→ Move | Space Jump | A Attack | U Undo | R Redo", 15, 15);



        // your code undo, redo 스택 현황 화면에 출력하기
        // Undo stack 표시 (왼쪽)
        Deque<ICommand> undoStack = invoker.getUndoStackNames();
        int y = 60;
        g2.setColor(Color.BLACK);
        g2.drawString("Undo Stack:", 15, y);
        for (ICommand cmdName : undoStack) {
            y += 15;
            g2.drawString("- " + cmdName.name(), 15, y);
        }

        // Redo stack 표시 (오른쪽)
        Deque<ICommand> redoStack = invoker.getRedoStackNames();
        y= 60;
        int x = getWidth() - 150;
        g2.setColor(Color.BLACK);
        g2.drawString("Redo Stack:", x, y);
        for (ICommand cmdName: redoStack) {
            y += 15;
            g2.drawString("- " + cmdName.name(), x, y);
        }

        g2.dispose();
    }
}
