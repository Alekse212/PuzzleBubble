package pedro.ieslaencanta.com.dawpuzzletemplate;

public enum BubbleTypeExplotacion {

        BLUE(170, 1845),
        RED(170, 1878),
        YELLOW(725, 1845),
        GREEN(725, 1878),
        PURPLE(170, 1911),
        ORANGE(725, 1911),
        GRAY(170, 1944),
        WRITE(725, 1944);
        private final int x;
        private final int y;

        BubbleTypeExplotacion(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
}

