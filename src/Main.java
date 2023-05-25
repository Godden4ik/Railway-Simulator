public class Main {
    public static void main(String[] args) {
        Thread menu = new Thread(new Controller());
        menu.start();
    }
}
