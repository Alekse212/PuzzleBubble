package pedro.ieslaencanta.com.dawpuzzletemplate;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Shuttle {
    private float angulo = 0;
    private static float min_angulo = -90;
    private static float max_angulo = 90;
    private Bubble bubble;
    private BubbleType[] bubbles_next = new BubbleType[2];
    private BubbleTypeExplotacion[] bubletypeexplotacions = new BubbleTypeExplotacion[2];
    private boolean debug;
    private float angulo_pass = 1.4f;
    private Point2D center;
    public static int WIDTH = 65, HEIGHT = 65;


    public Shuttle(Point2D center) {
        this.center = center;
        for(int i = 0; i < this.bubbles_next.length; i++){
            int temp = (int) (Math.random()*BubbleType.values().length);
            this.bubbles_next[i] = BubbleType.values()[ temp];
            this.bubletypeexplotacions[i] = BubbleTypeExplotacion.values()[temp];
        }
    }

    public Shuttle() {
    }

    public float getAngulo() {
        return angulo;
    }

    public void setAngulo(float angulo) {
        this.angulo = angulo;
    }

    public Bubble getBubble() {
        return bubble;
    }

    public void setBubble(Bubble bubble) {
        this.bubble = bubble;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }

    public void paint(GraphicsContext gc){
        Resources res = Resources.getInstance();
        Image arrow = res.getImage("spriters");
        int num_angulos = (int) (this.angulo/angulo_pass);
        if(num_angulos<0){
            num_angulos *= -1;
        }
        int image_y = 1546 + (num_angulos / 16) * Shuttle.HEIGHT;
        int image_x = 1 + (num_angulos - ((num_angulos / 16) * 16)) * Shuttle.WIDTH;
        if(image_y>1741){
            image_y = 1741;
            image_x = 976;
        }
        if (this.angulo<0) {
            gc.drawImage(arrow, image_x+Shuttle.WIDTH-1, image_y,
                    -Shuttle.WIDTH, Shuttle.HEIGHT,
                    (this.center.getX() - Shuttle.WIDTH / 2) * Game.SCALE,
                    ((this.center.getY()+10) - Shuttle.HEIGHT) * Game.SCALE,
                    Shuttle.WIDTH * Game.SCALE,
                    Shuttle.HEIGHT * Game.SCALE);
        }else {
            gc.drawImage(arrow, image_x, image_y,
                    Shuttle.WIDTH, Shuttle.HEIGHT,
                    (this.center.getX() - Shuttle.WIDTH / 2) * Game.SCALE,
                    (this.center.getY()+10 - Shuttle.HEIGHT) * Game.SCALE,
                    Shuttle.WIDTH * Game.SCALE,
                    Shuttle.HEIGHT * Game.SCALE);
        }
        for(int i = 0; i<this.bubbles_next.length; i++){
            Resources r = Resources.getInstance();
            if( i == 0) {
                gc.drawImage(r.getImage("balls"),
                        //inicio de la posicion
                        this.bubbles_next[i].getX(),
                        this.bubbles_next[i].getY(),
                        Bubble.WIDTH,
                        Bubble.HEIGHT,
                        //dibujar en el lienzo
                        (this.center.getX() - Bubble.WIDTH / 2) * Game.SCALE,
                        (this.center.getY()+10 - Bubble.HEIGHT / 2-Shuttle.HEIGHT/2) * Game.SCALE,
                        Bubble.WIDTH * Game.SCALE,
                        Bubble.HEIGHT * Game.SCALE);
            }
        }
    }

    public void move_right(){
        this.angulo += angulo_pass;
        if(this.angulo > Shuttle.max_angulo){
            this.angulo = Shuttle.max_angulo;
        }
    }

    public void move_left(){
        this.angulo -= angulo_pass;
        if(this.angulo > Shuttle.max_angulo){
                this.angulo = Shuttle.max_angulo;
        }
    }

    public void shoot(Rectangle2D r){
        this.bubble = new Bubble();
        this.bubble.setBalltype(this.bubbles_next[0]);
        this.bubble.setBubletypeExplotacion(this.bubletypeexplotacions[0]);
        for(int i=0; i<this.bubbles_next.length;i++){
            if(this.bubbles_next.length-1 != i) {
                this.bubbles_next[i] = this.bubbles_next[i + 1];
            }else{
                int temp = (int) (Math.random()*BubbleType.values().length);
                this.bubbles_next[i] = BubbleType.values()[temp];
                this.bubletypeexplotacions[i] = BubbleTypeExplotacion.values()[temp];
            }
        }
        this.bubble.init(new Point2D(
                (r.getMaxX() - r.getWidth() / 2),
                (r.getMaxY() - 18)
        ), this.angulo-90);
        this.bubble.play();
    }
}
