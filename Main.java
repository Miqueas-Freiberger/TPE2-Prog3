import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Subterraneo subterraneos = new Subterraneo();
        try {
            subterraneos.cargarDatos("Tuneles.txt");

            System.out.println("Solución utilizando Backtracking:");
            subterraneos.backtracking();

            System.out.println("Solución utilizando Greedy:");
            subterraneos.greedy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}