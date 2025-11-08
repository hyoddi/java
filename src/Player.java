import java.awt.*;

public class Player {
    int x = 80;     // X position on screen (starts from left)
    int y = 0;      // Y position (height above ground, 0 is ground)
    boolean onGround = true; // True if player is on the ground

    int vx = 0;     // X velocity (instant movement)
    int vy = 0;
    int attackCooldown = 0; // ticks

    int spriteW = 50;
    int spriteH = 60;
    Color baseColor = new Color(60, 140, 255);

    Color auraColor = null;  // Aura
    int auraTicks = 0;       // Number of frames to maintain aura

    String bubbleText = null; // bubbleText nearby Player
    int bubbleTicks = 0;


    public void move(int dx) {
        x += dx;
        
    }

    public void jump(int dy) {
        if (onGround){ // 땅에 붙어있을 때만 점프 가능
            y += dy;
            onGround = false;
        }
    }

    public void attack() {
        auraColor = Color.RED;
        auraTicks = 80;
    }


    // For every frame: gravity/attack cool down
    void tickPhysics() {
        
        // 땅에 닿아있지 않다면 (점프)
        if (onGround == false) {
            vy += 1.5;  // 중력에 의해 속도 증가
            y -= vy;        // 속도만큼 위치 변화

            if (y <= 0) { //땅에 닿았을 때
                y= 0; // 땅에 발 붙이기
                vy = 0;
                onGround = true;
            }

        }



        
        if (attackCooldown > 0) attackCooldown--;

        // Aura lifespan decrease
        if (auraTicks > 0 && --auraTicks == 0) {
            auraColor = null;
        }

        // bubbleText
        if (bubbleTicks > 0) bubbleTicks--;
    }
}
