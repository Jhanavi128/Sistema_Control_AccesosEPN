import java.util.Scanner;
import service.ControlAccesoService;

public class App {
    public static void main(String[] args) {
        ControlAccesoService servicio = new ControlAccesoService();
        Scanner lector = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("   SISTEMA DE CONTROL DE ACCESOS EPN    ");
        System.out.println("========================================");

        System.out.print("\nEscanee o ingrese el código QR: ");
        String codigo = lector.nextLine();

        // Llamamos a la lógica del servicio
        String resultado = servicio.procesarAcceso(codigo);

        System.out.println("\nRESULTADO: " + resultado);
        System.out.println("========================================");
        
        lector.close();
    }
}