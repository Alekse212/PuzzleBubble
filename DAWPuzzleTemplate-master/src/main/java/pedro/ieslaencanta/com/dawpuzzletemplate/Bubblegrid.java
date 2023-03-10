package pedro.ieslaencanta.com.dawpuzzletemplate;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Bubblegrid {
    private static int row = 12;
    private static int column = 8;
    private Rectangle2D rectangle2D;
    private int bum_balls_min = 3;
    private Bubble[][] grid_buble = new Bubble[Bubblegrid.row][Bubblegrid.column];
    private int explo_counter = 1;
    private boolean explotar = false;

    public Bubblegrid() {
    }

    public Bubblegrid(Rectangle2D rectangle2D) {
        this.rectangle2D = rectangle2D;
    }

    public boolean collision(Bubble bubble) {
        if (bubble != null) {
            int posYGrid = (int) ((bubble.getPosicion().getY()) / Bubble.HEIGHT) - 1;
            int posXGrid = (int) ((bubble.getPosicion().getX() - this.rectangle2D.getMinX()) / Bubble.WIDTH);
            if (posYGrid == 0) {
                int posX = (int) ((Bubble.WIDTH * (posXGrid)) + rectangle2D.getMinX())+ Bubble.WIDTH/2;
                int posY = (int) ((Bubble.HEIGHT * posYGrid) + rectangle2D.getMinY())+Bubble.HEIGHT/2;
                bubble.setPosicion(new Point2D(posX, posY));
                grid_buble[posYGrid][posXGrid] = bubble;
                explocion(posXGrid,posYGrid,bubble,1);
                explotar = false;
                return true;
            } else {
                for(int i = 0; i < grid_buble[posYGrid-1].length; i++){
                    if(grid_buble[posYGrid-1][i] != null) {
                        double distanceb = bubble.getPosicion().distance(grid_buble[posYGrid-1][i].getPosicion());
                        int posX = 0;
                        if(distanceb < 16){
//                            if(grid_buble[posYGrid][posXGrid] != null){
//                                if(grid_buble[posYGrid][posXGrid].getPosicion().getX() - bubble.getPosicion().getX() > 0 ){
                                    //posXGrid -= 1;
//                                }else{
                                    //posXGrid += 1;
//                                }
//                            }
                            if(posYGrid%2 == 0) {
                                posX = (int) ((Bubble.WIDTH * (posXGrid)) + rectangle2D.getMinX()) + Bubble.WIDTH / 2;
                            }else{
                                posXGrid = (int) ((bubble.getPosicion().getX() - this.rectangle2D.getMinX()-Bubble.WIDTH / 2) / Bubble.WIDTH);
//                                if(posXGrid != 0) {
//                                    posXGrid -= 1;
//                                }
                                posX = (int) ((Bubble.WIDTH * (posXGrid)) + rectangle2D.getMinX()) + Bubble.WIDTH;
                            }
                            int posY = (int) ((Bubble.HEIGHT * posYGrid) + rectangle2D.getMinY())+Bubble.HEIGHT/2;
                            bubble.setPosicion(new Point2D(posX, posY));
                            grid_buble[posYGrid][posXGrid] = bubble;
                            explocion(posXGrid,posYGrid,bubble,2);
                            explotar = false;
                            return true;
                        }
                    }
                }
            }
        }
        return  false;
    }
    private void explocion(int posxgrid, int posygrid, Bubble bubble, int ent){
        for(int i = posygrid-1; i < posygrid+1; i++){
            for (int j = posxgrid-1; j < posxgrid+2; j++) {
                if (i >= 0 && j >= 0) {
                    if (this.grid_buble[i][j] != bubble && this.grid_buble[i][j] != null && grid_buble[posygrid][posxgrid].getBalltype() != null) {
                        if (this.grid_buble[i][j].getBalltype() == grid_buble[posygrid][posxgrid].getBalltype()) {
                            if(!this.grid_buble[i][j].isExplotado()) {
                                this.grid_buble[i][j].setExplotado(true);
                                if (ent >= 3) {
                                    explotar = true;
                                }
                                explocion(j, i, this.grid_buble[i][j], ent + 1);
                            }
                        }
                    }
                }
            }
        }

        if(explotar){
            this.grid_buble[posygrid][posxgrid].setExplotado(true);
        }else{
            grid_buble[posygrid][posxgrid].setExplotado(false);
        }
    }

    public void paint(GraphicsContext gc){
        Bubble[][] balls = this.grid_buble;
        gc.strokeText("º x:" + this.rectangle2D.getMinX()+ " y:" + this.rectangle2D.getMinY(), (this.rectangle2D.getMinX()) *
                Game.SCALE, (this.rectangle2D.getMinY()) * Game.SCALE);
        for(int i=0; i<balls.length; i++){
            for(int j=0; j<balls[i].length; j++){
                if (balls[i][j] != null) {
                    if(balls[i][j].isExplotado()){
                        balls[i][j].paintexplocion(gc);
                        this.grid_buble[i][j] = null;
                    }
                    if (balls[i][j] != null) {
                        balls[i][j].paint(gc);
                    }

                }
            }
        }
    }
}
