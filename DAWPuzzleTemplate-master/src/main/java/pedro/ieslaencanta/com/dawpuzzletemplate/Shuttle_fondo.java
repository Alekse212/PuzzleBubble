package pedro.ieslaencanta.com.dawpuzzletemplate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Shuttle_fondo {
        private float angulo = 0;
        private static float min_angulo = -90;
        private static float max_angulo = 90;
        private Bubble bubble;
        private Bubble[] bubbles_next;
        private boolean debug;
        private float angulo_pass = 1.4f;
        private Point2D center;
        public static int WIDTH = 65, HEIGHT = 40;


        public Shuttle_fondo(Point2D center) {
            this.center = center;
        }

        public Shuttle_fondo() {
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
            int num_angulos = (int) (this.angulo/angulo_pass)-1;
            if(num_angulos<0){
                num_angulos *= -1;
            }
            int image_y = 1805;
            int image_x = 1 + (num_angulos - ((num_angulos / 12) * 12)) * pedro.ieslaencanta.com.dawpuzzletemplate.Shuttle.WIDTH;
            gc.drawImage(arrow, image_x, image_y,
                        Shuttle_fondo.WIDTH, Shuttle_fondo.HEIGHT,
                        (this.center.getX() - Shuttle_fondo.WIDTH / 2) * Game.SCALE,
                        (this.center.getY() - Shuttle_fondo.HEIGHT) * Game.SCALE,
                        Shuttle_fondo.WIDTH * Game.SCALE,
                        Shuttle_fondo.HEIGHT * Game.SCALE);

        }

        public void move_right(){
            if(this.angulo < Shuttle_fondo.max_angulo) {
                this.angulo += angulo_pass;
            }
        }

        public void move_left(){
            if(this.angulo> Shuttle_fondo.min_angulo) {
                this.angulo -= angulo_pass;
            }
        }


    }


