import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subterraneo {
    private Map<String, List<Tunel>> estaciones;
    private List<Tunel> listaTuneles;

    public Subterraneo() {
        estaciones = new HashMap<>();
        listaTuneles = new ArrayList<>();
    }

    public void cargarDatos(String archivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        String linea;

        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split(";");
            String estacion1 = datos[0];
            String estacion2 = datos[1];
            int distancia = Integer.parseInt(datos[2]);

            estaciones.putIfAbsent(estacion1, new ArrayList<>());
            estaciones.putIfAbsent(estacion2, new ArrayList<>());

            estaciones.get(estacion1).add(new Tunel(estacion1, estacion2, distancia));
            estaciones.get(estacion2).add(new Tunel(estacion2, estacion1, distancia));
        }

        reader.close();
    }

    public void backtracking() {
        int[] mejorSolucion = { Integer.MAX_VALUE };
        List<Tunel> mejorListaTuneles = new ArrayList<>();

        String estacionInicial = estaciones.keySet().iterator().next();
        backtrack(estacionInicial, new ArrayList<>(), 0, mejorSolucion, mejorListaTuneles);

        mostrarResultado("Backtracking", mejorListaTuneles, mejorSolucion[0]);
    }

    private void backtrack(String estacionActual, List<Tunel> listaTuneles, int metrosTotales,
            int[] mejorSolucion, List<Tunel> mejorListaTuneles) {

        if (listaTuneles.size() == estaciones.size() - 1) {
            if (metrosTotales < mejorSolucion[0]) {
                mejorSolucion[0] = metrosTotales;
                mejorListaTuneles.clear();
                mejorListaTuneles.addAll(listaTuneles);
            }
            return;
        }

        for (Tunel tunel : estaciones.get(estacionActual)) {
            if (!contieneEstacion(listaTuneles, tunel.getEstacionDestino())) {
                listaTuneles.add(tunel);
                backtrack(tunel.getEstacionDestino(), listaTuneles, metrosTotales + tunel.getDistancia(),
                        mejorSolucion, mejorListaTuneles);
                listaTuneles.remove(tunel);
            }
        }
    }

    public void greedy() {
        List<Tunel> listaTuneles = new ArrayList<>();
        List<String> estacionesConectadas = new ArrayList<>();
        int metrosTotales = 0;

        while (estacionesConectadas.size() < estaciones.size()) {
            int mejorDistancia = Integer.MAX_VALUE;
            String mejorEstacion = null;
            Tunel mejorTunel = null;

            for (Map.Entry<String, List<Tunel>> entry : estaciones.entrySet()) {
                String estacion = entry.getKey();
                List<Tunel> tuneles = entry.getValue();

                if (estacionesConectadas.contains(estacion)) {
                    for (Tunel tunel : tuneles) {
                        if (!estacionesConectadas.contains(tunel.getEstacionDestino())
                                && tunel.getDistancia() < mejorDistancia) {
                            mejorDistancia = tunel.getDistancia();
                            mejorEstacion = tunel.getEstacionDestino();
                            mejorTunel = tunel;
                        }
                    }
                }
            }

            if (mejorTunel != null) {
                listaTuneles.add(mejorTunel);
                estacionesConectadas.add(mejorEstacion);
                metrosTotales += mejorDistancia;
            }
        }

        mostrarResultado("Greedy", listaTuneles, metrosTotales);
    }

    private boolean contieneEstacion(List<Tunel> listaTuneles, String estacion) {
        for (Tunel tunel : listaTuneles) {
            if (tunel.getEstacionDestino().equals(estacion)) {
                return true;
            }
        }
        return false;
    }

    private void mostrarResultado(String tecnica, List<Tunel> listaTuneles, int metrosTotales) {
        System.out.println("Técnica utilizada: " + tecnica);
        System.out.println("Lista de túneles a construir:");
        for (Tunel tunel : listaTuneles) {
            System.out.println(tunel.getEstacionOrigen() + "-" + tunel.getEstacionDestino());
        }
        System.out.println("Cantidad de metros totales a construir: " + metrosTotales);
        System.out.println();
    }
}