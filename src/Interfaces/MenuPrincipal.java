package Interfaces;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Menú principal de la aplicación.
 * Cada botón abre una de las ventanas del sistema.
 */
public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        // Configuración básica de la ventana principal.
        setTitle("Sistema de Gestión de Residuos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720, 520);
        setLocationRelativeTo(null);

        // Dos columnas; el número de filas se calcula automáticamente.
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        // Para agregar otra opción al menú, copiar una línea y cambiar texto/ventana.
        agregar(panel, "Empresas", () -> new Empresa().setVisible(true));
        agregar(panel, "Residuos", () -> new Residuo().setVisible(true));
        agregar(panel, "Envases", () -> new EnvaseIn().setVisible(true));
        agregar(panel, "Químicos", () -> new QuimicoIn().setVisible(true));
        agregar(panel, "Composiciones químicas", () -> new Composicion_Quimico().setVisible(true));
        agregar(panel, "Centros de tratamiento", () -> new Centro_Tratamiento().setVisible(true));
        agregar(panel, "Tipos de tratamiento", () -> new Tipo_Tratamiento().setVisible(true));
        agregar(panel, "Transportistas", () -> new Transportista().setVisible(true));
        agregar(panel, "Transportes", () -> new Transporte().setVisible(true));
        agregar(panel, "Traslados", () -> new Traslado().setVisible(true));
        agregar(panel, "Búsqueda general", () -> new BusquedaGeneral().setVisible(true));

        add(panel);
    }

    /** Crea un botón y conecta la acción que abrirá su ventana. */
    private void agregar(JPanel panel, String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> accion.run());
        panel.add(boton);
    }

    public static void main(String[] args) {
        // Crea la interfaz dentro del hilo gráfico recomendado por Swing.
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
