package Interfaces;

import Residuos.opCRUD;
import clases.Centro_Tratamiento;
import clases.Composicion_Quimico;
import clases.Envase;
import clases.Quimico;
import clases.Residuo;
import clases.Tipo_Tratamiento;
import clases.Transporte;
import clases.Transportista;
import clases.Traslado;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

/**
 * Ventana de búsqueda general del sistema.
 * Permite consultar, editar y eliminar registros de diferentes entidades.
 * Para agregar nuevas entidades o campos de búsqueda, revisar crearEntidades().
 */
public class BusquedaGeneral extends JFrame {

    // Objeto central para consultar y modificar la base ObjectDB.
    private final opCRUD crud = new opCRUD();

    // Controles utilizados para seleccionar entidad, campo y criterio.
    private final JComboBox<EntidadConfig> cbxBuscar = new JComboBox<>();
    private final JComboBox<CampoBusqueda> cbxPor = new JComboBox<>();
    private final JTextField txtCriterio = new JTextField();
    private final JTable tablaResultados = new JTable();

    // Botones principales de la ventana.
    private final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnEditar = new JButton("Editar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JButton btnCerrar = new JButton("Cerrar");

    // Objetos que corresponden a las filas mostradas actualmente.
    private List<?> resultadosActuales = Collections.emptyList();

    // Catálogo de entidades y campos disponibles en la búsqueda.
    private static final Map<String, EntidadConfig> ENTIDADES = crearEntidades();

    // Constructor: prepara la interfaz y carga sus opciones.
    public BusquedaGeneral() {
        initComponents();
        cargarEntidades();
        actualizarCampos();
        actualizarBotones();
    }

    // Construye manualmente la interfaz Swing.
    private void initComponents() {
        setTitle("Búsqueda general - Gestión de residuos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 580));
        setSize(1180, 650);
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout(12, 12));
        raiz.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(raiz);

        JLabel titulo = new JLabel("Búsqueda general");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        raiz.add(titulo, BorderLayout.NORTH);

        JPanel filtros = new JPanel(new GridBagLayout());
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros de búsqueda"));
        filtros.setPreferredSize(new Dimension(320, 0));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 7, 7, 7);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        g.gridx = 0;
        g.gridy = 0;
        filtros.add(new JLabel("Buscar en:"), g);

        g.gridy++;
        filtros.add(cbxBuscar, g);

        g.gridy++;
        filtros.add(new JLabel("Por:"), g);

        g.gridy++;
        filtros.add(cbxPor, g);

        g.gridy++;
        filtros.add(new JLabel("Criterio:"), g);

        g.gridy++;
        filtros.add(txtCriterio, g);

        g.gridy++;
        g.insets = new Insets(18, 7, 7, 7);
        filtros.add(btnBuscar, g);

        g.gridy++;
        g.insets = new Insets(7, 7, 7, 7);
        JPanel acciones = new JPanel(new GridLayout(2, 2, 8, 8));
        acciones.add(btnEditar);
        acciones.add(btnEliminar);
        acciones.add(btnLimpiar);
        acciones.add(btnCerrar);
        filtros.add(acciones, g);

        g.gridy++;
        g.weighty = 1.0;
        filtros.add(new JPanel(), g);

        raiz.add(filtros, BorderLayout.WEST);

        JPanel resultados = new JPanel(new BorderLayout());
        resultados.setBorder(BorderFactory.createTitledBorder("Resultados"));

        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaResultados.setFillsViewportHeight(true);
        tablaResultados.setRowHeight(24);
        tablaResultados.setDefaultEditor(Object.class, null);

        resultados.add(new JScrollPane(tablaResultados), BorderLayout.CENTER);
        raiz.add(resultados, BorderLayout.CENTER);

        cbxBuscar.addActionListener(e -> actualizarCampos());
        btnBuscar.addActionListener(e -> buscar(true));
        btnEditar.addActionListener(e -> editarSeleccionado());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        btnLimpiar.addActionListener(e -> limpiar());
        btnCerrar.addActionListener(e -> dispose());
        txtCriterio.addActionListener(e -> buscar(true));

        tablaResultados.getSelectionModel().addListSelectionListener(e -> actualizarBotones());
        tablaResultados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tablaResultados.getSelectedRow() >= 0) {
                    editarSeleccionado();
                }
            }
        });
    }

    // Llena el combo "Buscar en" con las entidades configuradas.
    private void cargarEntidades() {
        DefaultComboBoxModel<EntidadConfig> modelo = new DefaultComboBoxModel<>();
        for (EntidadConfig e : ENTIDADES.values()) {
            modelo.addElement(e);
        }
        cbxBuscar.setModel(modelo);
    }

    // Cambia los campos de búsqueda según la entidad elegida.
    private void actualizarCampos() {
        cbxPor.removeAllItems();
        EntidadConfig entidad = (EntidadConfig) cbxBuscar.getSelectedItem();
        if (entidad != null) {
            for (CampoBusqueda campo : entidad.campos) {
                cbxPor.addItem(campo);
            }
        }
        limpiarResultados();
    }

    // Lee registros y filtra los que contienen el criterio escrito.
    private void buscar(boolean avisar) {
        try {
            EntidadConfig entidad = entidadSeleccionada();
            CampoBusqueda campo = (CampoBusqueda) cbxPor.getSelectedItem();
            if (campo == null) {
                throw new IllegalStateException("Seleccione un campo de búsqueda.");
            }

            String criterio = txtCriterio.getText().trim().toLowerCase(Locale.ROOT);
            List<?> todos = crud.readAll(entidad.tipo);
            List<Object> encontrados = new ArrayList<>();

            if (criterio.isEmpty()) {
                encontrados.addAll((List<?>) todos);
                if (avisar) {
                    JOptionPane.showMessageDialog(this,
                            "No se indicó un criterio. Se mostrarán todos los registros.",
                            "Búsqueda",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                for (Object obj : todos) {
                    Object valor = campo.extractor.apply(obj);
                    if (valor != null && String.valueOf(valor).toLowerCase(Locale.ROOT).contains(criterio)) {
                        encontrados.add(obj);
                    }
                }
            }

            resultadosActuales = encontrados;
            TableModel modelo = crud.listToTable(encontrados, entidad.clave);
            tablaResultados.setModel(modelo);
            tablaResultados.setDefaultEditor(Object.class, null);
            ajustarAnchos();
            actualizarBotones();

            if (avisar && !criterio.isEmpty() && encontrados.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron coincidencias para el criterio indicado.",
                        "Sin resultados",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            mostrarError("Error al realizar la búsqueda", ex);
        }
    }

    // Edita el objeto correspondiente a la fila seleccionada.
    private void editarSeleccionado() {
        int fila = tablaResultados.getSelectedRow();
        if (fila < 0 || fila >= resultadosActuales.size()) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un registro para editar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            EntidadConfig entidad = entidadSeleccionada();
            Object obj = resultadosActuales.get(fila);
            boolean modificado = editarObjeto(entidad.clave, obj);
            if (modificado) {
                crud.update(obj);
                JOptionPane.showMessageDialog(this,
                        "Registro actualizado correctamente.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                buscar(false);
            }
        } catch (Exception ex) {
            mostrarError("Error al actualizar el registro", ex);
        }
    }

    // Crea los controles de edición apropiados para cada entidad.
    private boolean editarObjeto(String entidad, Object obj) {
        switch (entidad) {
            case "Empresa": {
                clases.Empresa e = (clases.Empresa) obj;
                JTextField ubicacion = new JTextField(e.getEmp_ubicacion());
                if (!confirmarEdicion("Editar empresa: " + e.getEmp_nombre(),
                        "Ubicación", ubicacion)) return false;
                e.setEmp_ubicacion(texto(ubicacion, "Ubicación"));
                return true;
            }
            case "Envase": {
                Envase e = (Envase) obj;
                JTextField categoria = new JTextField(e.getCategoria_material());
                if (!confirmarEdicion("Editar envase: " + e.getEnv_descripcion(),
                        "Categoría material", categoria)) return false;
                e.setCategoria_material(texto(categoria, "Categoría material"));
                return true;
            }
            case "Quimico": {
                Quimico q = (Quimico) obj;
                JTextField peligrosidad = new JTextField(q.getTipo_peligrosidad());
                if (!confirmarEdicion("Editar químico: " + q.getQuim_nombre(),
                        "Tipo de peligrosidad", peligrosidad)) return false;
                q.setTipo_peligrosidad(texto(peligrosidad, "Tipo de peligrosidad"));
                return true;
            }
            case "Centro_Tratamiento": {
                Centro_Tratamiento c = (Centro_Tratamiento) obj;
                JTextField ubicacion = new JTextField(c.getCen_ubicacion());
                if (!confirmarEdicion("Editar centro: " + c.getCen_descripcion(),
                        "Ubicación", ubicacion)) return false;
                c.setCen_ubicacion(texto(ubicacion, "Ubicación"));
                return true;
            }
            case "Tipo_Tratamiento": {
                Tipo_Tratamiento t = (Tipo_Tratamiento) obj;
                JOptionPane.showMessageDialog(this,
                        "El único atributo de '" + t.getTrat_descripcion() + "' es su clave primaria.\n"
                        + "Por seguridad la clave primaria no se modifica desde la búsqueda general.",
                        "Sin campos editables",
                        JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            case "Transportista": {
                Transportista t = (Transportista) obj;
                JTextField direccion = new JTextField(t.getDireccion());
                JTextField telefono = new JTextField(t.getTelefono());
                if (!confirmarEdicion("Editar transportista: " + t.getTrans_nombre(),
                        "Dirección", direccion,
                        "Teléfono", telefono)) return false;
                t.setDireccion(texto(direccion, "Dirección"));
                t.setTelefono(texto(telefono, "Teléfono"));
                return true;
            }
            case "Transporte": {
                Transporte t = (Transporte) obj;
                JComboBox<Transportista> transportista = comboTransportistas(t.getTransportista());
                if (!confirmarEdicion("Editar transporte: " + t.getTrans_tipo(),
                        "Transportista", transportista)) return false;
                t.setTransportista((Transportista) transportista.getSelectedItem());
                return true;
            }
            case "Residuo": {
                Residuo r = (Residuo) obj;
                JTextField cantidad = new JTextField(String.valueOf(r.getCantidad_total()));
                JComboBox<clases.Empresa> empresa = comboEmpresas(r.getEmpresa());
                JComboBox<Envase> envase = comboEnvases(r.getEnvase());
                if (!confirmarEdicion("Editar residuo: " + r.getRes_nombre(),
                        "Cantidad total", cantidad,
                        "Empresa", empresa,
                        "Envase", envase)) return false;
                r.setCantidad_total(numero(cantidad, "Cantidad total"));
                r.setEmpresa((clases.Empresa) empresa.getSelectedItem());
                r.setEnvase((Envase) envase.getSelectedItem());
                return true;
            }
            case "Composicion_Quimico": {
                Composicion_Quimico c = (Composicion_Quimico) obj;
                JTextField cantidad = new JTextField(String.valueOf(c.getCantidad()));
                JComboBox<Residuo> residuo = comboResiduos(c.getResiduo());
                JComboBox<Quimico> quimico = comboQuimicos(c.getQuimico());
                if (!confirmarEdicion("Editar composición: " + c.getComp_nombre(),
                        "Cantidad", cantidad,
                        "Residuo", residuo,
                        "Químico", quimico)) return false;
                c.setCantidad(numero(cantidad, "Cantidad"));
                c.setResiduo((Residuo) residuo.getSelectedItem());
                c.setQuimico((Quimico) quimico.getSelectedItem());
                return true;
            }
            case "Traslado": {
                Traslado t = (Traslado) obj;
                JTextField cantidad = new JTextField(String.valueOf(t.getCantidad_trasladada()));
                JTextField inicio = new JTextField(t.getFecha_inicio());
                JTextField llegada = new JTextField(t.getFecha_llegada());
                JTextField costo = new JTextField(String.valueOf(t.getCosto()));
                JTextField km = new JTextField(String.valueOf(t.getKm_recorridos()));
                JComboBox<clases.Empresa> empresa = comboEmpresas(t.getEmpresa());
                JComboBox<Residuo> residuo = comboResiduos(t.getResiduo());
                JComboBox<Centro_Tratamiento> centro = comboCentros(t.getCentro());
                JComboBox<Tipo_Tratamiento> tratamiento = comboTratamientos(t.getTratamiento());
                JComboBox<Transporte> transporte = comboTransportes(t.getTransporte());

                if (!confirmarEdicion("Editar traslado: " + t.getTras_origen(),
                        "Cantidad trasladada", cantidad,
                        "Fecha de inicio", inicio,
                        "Fecha de llegada", llegada,
                        "Costo", costo,
                        "Km recorridos", km,
                        "Empresa", empresa,
                        "Residuo", residuo,
                        "Centro", centro,
                        "Tratamiento", tratamiento,
                        "Transporte", transporte)) return false;

                t.setCantidad_trasladada(numero(cantidad, "Cantidad trasladada"));
                t.setFecha_inicio(texto(inicio, "Fecha de inicio"));
                t.setFecha_llegada(texto(llegada, "Fecha de llegada"));
                t.setCosto(numero(costo, "Costo"));
                t.setKm_recorridos(numero(km, "Km recorridos"));
                t.setEmpresa((clases.Empresa) empresa.getSelectedItem());
                t.setResiduo((Residuo) residuo.getSelectedItem());
                t.setCentro((Centro_Tratamiento) centro.getSelectedItem());
                t.setTratamiento((Tipo_Tratamiento) tratamiento.getSelectedItem());
                t.setTransporte((Transporte) transporte.getSelectedItem());
                return true;
            }
            default:
                throw new IllegalArgumentException("Entidad no soportada: " + entidad);
        }
    }

    // Elimina la fila seleccionada respetando las restricciones de opCRUD.
    private void eliminarSeleccionado() {
        int fila = tablaResultados.getSelectedRow();
        if (fila < 0 || fila >= resultadosActuales.size()) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un registro para eliminar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            EntidadConfig entidad = entidadSeleccionada();
            Object obj = resultadosActuales.get(fila);
            String id = obtenerId(entidad.clave, obj);

            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Desea eliminar este registro?\n\nEntidad: " + entidad.etiqueta
                    + "\nClave primaria: " + id,
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (opcion != JOptionPane.YES_OPTION) return;

            crud.opDelete(entidad.clave, id);
            JOptionPane.showMessageDialog(this,
                    "Registro eliminado correctamente.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            buscar(false);

        } catch (Exception ex) {
            mostrarError("No se pudo eliminar el registro", ex);
        }
    }

    // Obtiene la clave primaria del objeto según su tipo.
    private String obtenerId(String entidad, Object obj) {
        switch (entidad) {
            case "Empresa": return ((clases.Empresa) obj).getEmp_nombre();
            case "Residuo": return ((Residuo) obj).getRes_nombre();
            case "Envase": return ((Envase) obj).getEnv_descripcion();
            case "Quimico": return ((Quimico) obj).getQuim_nombre();
            case "Composicion_Quimico": return ((Composicion_Quimico) obj).getComp_nombre();
            case "Centro_Tratamiento": return ((Centro_Tratamiento) obj).getCen_descripcion();
            case "Tipo_Tratamiento": return ((Tipo_Tratamiento) obj).getTrat_descripcion();
            case "Transportista": return ((Transportista) obj).getTrans_nombre();
            case "Transporte": return ((Transporte) obj).getTrans_tipo();
            case "Traslado": return ((Traslado) obj).getTras_origen();
            default: throw new IllegalArgumentException("Entidad no soportada: " + entidad);
        }
    }

    // Muestra un panel de edición y devuelve true si se confirma.
    private boolean confirmarEdicion(String titulo, Object... etiquetaComponente) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        for (int i = 0; i < etiquetaComponente.length; i += 2) {
            panel.add(new JLabel(String.valueOf(etiquetaComponente[i]) + ":"));
            panel.add((JComponent) etiquetaComponente[i + 1]);
        }
        return JOptionPane.showConfirmDialog(this, panel, titulo,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
    }

    // Valida que un JTextField obligatorio tenga contenido.
    private String texto(JTextField campo, String nombre) {
        String valor = campo.getText().trim();
        if (valor.isEmpty()) throw new IllegalArgumentException(nombre + " no puede estar vacío.");
        return valor;
    }

    // Convierte un JTextField a número y valida errores de formato.
    private double numero(JTextField campo, String nombre) {
        String valor = campo.getText().trim();
        if (valor.isEmpty()) throw new IllegalArgumentException(nombre + " no puede estar vacío.");
        try {
            double n = Double.parseDouble(valor);
            if (n < 0) throw new IllegalArgumentException(nombre + " no puede ser negativo.");
            return n;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(nombre + " debe ser un número válido.");
        }
    }

    private JComboBox<clases.Empresa> comboEmpresas(clases.Empresa actual) {
        return crearCombo(crud.readAll(clases.Empresa.class), actual,
                clases.Empresa::getEmp_nombre);
    }

    private JComboBox<Envase> comboEnvases(Envase actual) {
        return crearCombo(crud.readAll(Envase.class), actual,
                Envase::getEnv_descripcion);
    }

    private JComboBox<Residuo> comboResiduos(Residuo actual) {
        return crearCombo(crud.readAll(Residuo.class), actual,
                Residuo::getRes_nombre);
    }

    private JComboBox<Quimico> comboQuimicos(Quimico actual) {
        return crearCombo(crud.readAll(Quimico.class), actual,
                Quimico::getQuim_nombre);
    }

    private JComboBox<Centro_Tratamiento> comboCentros(Centro_Tratamiento actual) {
        return crearCombo(crud.readAll(Centro_Tratamiento.class), actual,
                Centro_Tratamiento::getCen_descripcion);
    }

    private JComboBox<Tipo_Tratamiento> comboTratamientos(Tipo_Tratamiento actual) {
        return crearCombo(crud.readAll(Tipo_Tratamiento.class), actual,
                Tipo_Tratamiento::getTrat_descripcion);
    }

    private JComboBox<Transporte> comboTransportes(Transporte actual) {
        return crearCombo(crud.readAll(Transporte.class), actual,
                Transporte::getTrans_tipo);
    }

    private JComboBox<Transportista> comboTransportistas(Transportista actual) {
        return crearCombo(crud.readAll(Transportista.class), actual,
                Transportista::getTrans_nombre);
    }

    // Crea JComboBox reutilizables para seleccionar entidades relacionadas.
    private <T> JComboBox<T> crearCombo(List<T> elementos, T actual, Function<T, String> etiqueta) {
        if (elementos == null || elementos.isEmpty()) {
            throw new IllegalStateException("No hay registros relacionados disponibles para seleccionar.");
        }

        JComboBox<T> combo = new JComboBox<>();
        for (T elemento : elementos) combo.addItem(elemento);

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    @SuppressWarnings("unchecked")
                    T item = (T) value;
                    setText(etiqueta.apply(item));
                }
                return this;
            }
        });

        if (actual != null) {
            String idActual = etiqueta.apply(actual);
            for (int i = 0; i < combo.getItemCount(); i++) {
                T item = combo.getItemAt(i);
                if (etiqueta.apply(item).equals(idActual)) {
                    combo.setSelectedIndex(i);
                    break;
                }
            }
        }
        return combo;
    }

    // Restablece criterio, tabla y selección.
    private void limpiar() {
        txtCriterio.setText("");
        if (cbxPor.getItemCount() > 0) cbxPor.setSelectedIndex(0);
        limpiarResultados();
        txtCriterio.requestFocus();
    }

    // Vacía la tabla y la lista de objetos mostrados.
    private void limpiarResultados() {
        resultadosActuales = Collections.emptyList();
        tablaResultados.setModel(new javax.swing.table.DefaultTableModel());
        actualizarBotones();
    }

    // Activa Editar/Eliminar solo si hay una fila seleccionada.
    private void actualizarBotones() {
        boolean seleccionado = tablaResultados.getSelectedRow() >= 0
                && tablaResultados.getSelectedRow() < resultadosActuales.size();
        btnEditar.setEnabled(seleccionado);
        btnEliminar.setEnabled(seleccionado);
    }

    // Ajusta el ancho visual de las columnas de resultados.
    private void ajustarAnchos() {
        for (int i = 0; i < tablaResultados.getColumnCount(); i++) {
            tablaResultados.getColumnModel().getColumn(i).setPreferredWidth(145);
        }
    }

    // Devuelve la configuración de la entidad elegida.
    private EntidadConfig entidadSeleccionada() {
        EntidadConfig entidad = (EntidadConfig) cbxBuscar.getSelectedItem();
        if (entidad == null) throw new IllegalStateException("Seleccione una entidad.");
        return entidad;
    }

    // Muestra errores con un formato uniforme.
    private void mostrarError(String titulo, Exception ex) {
        String mensaje = ex.getMessage();
        if (mensaje == null || mensaje.trim().isEmpty()) mensaje = ex.toString();
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }

    // AQUÍ se agregan/quitan entidades y campos de la búsqueda general.
    private static Map<String, EntidadConfig> crearEntidades() {
        Map<String, EntidadConfig> m = new LinkedHashMap<>();

        agregar(m, new EntidadConfig("Empresa", "Empresas", clases.Empresa.class,
                campo("Nombre", o -> ((clases.Empresa) o).getEmp_nombre()),
                campo("Ubicación", o -> ((clases.Empresa) o).getEmp_ubicacion())));

        agregar(m, new EntidadConfig("Residuo", "Residuos", Residuo.class,
                campo("Nombre", o -> ((Residuo) o).getRes_nombre()),
                campo("Cantidad total", o -> ((Residuo) o).getCantidad_total()),
                campo("Empresa", o -> ((Residuo) o).getEmpresa() == null ? null : ((Residuo) o).getEmpresa().getEmp_nombre()),
                campo("Envase", o -> ((Residuo) o).getEnvase() == null ? null : ((Residuo) o).getEnvase().getEnv_descripcion())));

        agregar(m, new EntidadConfig("Envase", "Envases", Envase.class,
                campo("Descripción", o -> ((Envase) o).getEnv_descripcion()),
                campo("Categoría material", o -> ((Envase) o).getCategoria_material())));

        agregar(m, new EntidadConfig("Quimico", "Químicos", Quimico.class,
                campo("Nombre", o -> ((Quimico) o).getQuim_nombre()),
                campo("Peligrosidad", o -> ((Quimico) o).getTipo_peligrosidad())));

        agregar(m, new EntidadConfig("Composicion_Quimico", "Composiciones químicas", Composicion_Quimico.class,
                campo("Nombre", o -> ((Composicion_Quimico) o).getComp_nombre()),
                campo("Cantidad", o -> ((Composicion_Quimico) o).getCantidad()),
                campo("Residuo", o -> ((Composicion_Quimico) o).getResiduo() == null ? null : ((Composicion_Quimico) o).getResiduo().getRes_nombre()),
                campo("Químico", o -> ((Composicion_Quimico) o).getQuimico() == null ? null : ((Composicion_Quimico) o).getQuimico().getQuim_nombre())));

        agregar(m, new EntidadConfig("Centro_Tratamiento", "Centros de tratamiento", Centro_Tratamiento.class,
                campo("Descripción", o -> ((Centro_Tratamiento) o).getCen_descripcion()),
                campo("Ubicación", o -> ((Centro_Tratamiento) o).getCen_ubicacion())));

        agregar(m, new EntidadConfig("Tipo_Tratamiento", "Tipos de tratamiento", Tipo_Tratamiento.class,
                campo("Descripción", o -> ((Tipo_Tratamiento) o).getTrat_descripcion())));

        agregar(m, new EntidadConfig("Transportista", "Transportistas", Transportista.class,
                campo("Nombre", o -> ((Transportista) o).getTrans_nombre()),
                campo("Dirección", o -> ((Transportista) o).getDireccion()),
                campo("Teléfono", o -> ((Transportista) o).getTelefono())));

        agregar(m, new EntidadConfig("Transporte", "Transportes", Transporte.class,
                campo("Tipo", o -> ((Transporte) o).getTrans_tipo()),
                campo("Transportista", o -> ((Transporte) o).getTransportista() == null ? null : ((Transporte) o).getTransportista().getTrans_nombre())));

        agregar(m, new EntidadConfig("Traslado", "Traslados", Traslado.class,
                campo("Origen", o -> ((Traslado) o).getTras_origen()),
                campo("Cantidad trasladada", o -> ((Traslado) o).getCantidad_trasladada()),
                campo("Fecha de inicio", o -> ((Traslado) o).getFecha_inicio()),
                campo("Fecha de llegada", o -> ((Traslado) o).getFecha_llegada()),
                campo("Costo", o -> ((Traslado) o).getCosto()),
                campo("Km recorridos", o -> ((Traslado) o).getKm_recorridos()),
                campo("Empresa", o -> ((Traslado) o).getEmpresa() == null ? null : ((Traslado) o).getEmpresa().getEmp_nombre()),
                campo("Residuo", o -> ((Traslado) o).getResiduo() == null ? null : ((Traslado) o).getResiduo().getRes_nombre()),
                campo("Centro", o -> ((Traslado) o).getCentro() == null ? null : ((Traslado) o).getCentro().getCen_descripcion()),
                campo("Tratamiento", o -> ((Traslado) o).getTratamiento() == null ? null : ((Traslado) o).getTratamiento().getTrat_descripcion()),
                campo("Transporte", o -> ((Traslado) o).getTransporte() == null ? null : ((Traslado) o).getTransporte().getTrans_tipo())));

        return m;
    }

    // Crea una configuración sencilla para un campo buscable.
    private static CampoBusqueda campo(String etiqueta, Function<Object, Object> extractor) {
        return new CampoBusqueda(etiqueta, extractor);
    }

    // Agrega una entidad al catálogo manteniendo el orden visual.
    private static void agregar(Map<String, EntidadConfig> mapa, EntidadConfig entidad) {
        mapa.put(entidad.clave, entidad);
    }

    private static final class EntidadConfig {
        final String clave;
        final String etiqueta;
        final Class<?> tipo;
        final List<CampoBusqueda> campos;

        EntidadConfig(String clave, String etiqueta, Class<?> tipo, CampoBusqueda... campos) {
            this.clave = clave;
            this.etiqueta = etiqueta;
            this.tipo = tipo;
            this.campos = Arrays.asList(campos);
        }

        @Override
        public String toString() {
            return etiqueta;
        }
    }

    private static final class CampoBusqueda {
        final String etiqueta;
        final Function<Object, Object> extractor;

        CampoBusqueda(String etiqueta, Function<Object, Object> extractor) {
            this.etiqueta = etiqueta;
            this.extractor = extractor;
        }

        @Override
        public String toString() {
            return etiqueta;
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> new BusquedaGeneral().setVisible(true));
    }
}
