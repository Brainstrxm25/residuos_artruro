package Interfaces;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;

/** Interfaz gráfica para relacionar residuos con químicos y registrar cantidades. */
public class Composicion_Quimico extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Composicion_Quimico.class.getName());
    private final Residuos.opCRUD crud = new Residuos.opCRUD();
    private String idSeleccionado;
    private JButton btnAgregar,btnConsultar,btnModificar,btnEliminar,btnLimpiar,btnMenu;

    // Constructor: inicializa componentes y prepara la ventana.
    public Composicion_Quimico(){
        initComponents();
        jLabel3.setText("Químico:"); jLabel4.setText("Residuo:");
        crearBotones(); cargarCombos(); modoNuevo(); setLocationRelativeTo(null); setResizable(false); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    // Crea un botón y lo conecta con la acción indicada.
    private JButton crearBoton(String texto,int x,int y,int ancho,java.awt.event.ActionListener a){JButton b=new JButton(texto);b.addActionListener(a);jPanel1.add(b,new org.netbeans.lib.awtextra.AbsoluteConstraints(x,y,ancho,28));return b;}
    // Crea los botones de acciones de la ventana.
    private void crearBotones(){
        btnAgregar=crearBoton("Agregar",25,245,120,this::btnAgregarActionPerformed); btnConsultar=crearBoton("Consultar",165,245,120,this::btnConsultarActionPerformed);
        btnModificar=crearBoton("Modificar",305,245,120,this::btnModificarActionPerformed); btnEliminar=crearBoton("Eliminar",25,285,120,this::btnEliminarActionPerformed);
        btnLimpiar=crearBoton("Limpiar",165,285,120,this::btnLimpiarActionPerformed); btnMenu=crearBoton("Volver al menú",305,285,120,this::btnMenuActionPerformed);
        getContentPane().setPreferredSize(new java.awt.Dimension(457,335)); pack();
    }
    // Recarga los JComboBox con datos actuales de ObjectDB.
    private void cargarCombos(){
        List<clases.Quimico> qs=crud.readAll(clases.Quimico.class); List<clases.Residuo> rs=crud.readAll(clases.Residuo.class);
        cbxIdQuimico.setModel(new DefaultComboBoxModel<>(qs.stream().map(q->q.getQuim_nombre()).sorted().toArray(String[]::new)));
        cbxIdComposicion.setModel(new DefaultComboBoxModel<>(rs.stream().map(r->r.getRes_nombre()).sorted().toArray(String[]::new)));
    }
    // Limpia el formulario y lo deja listo para un registro nuevo.
    private void modoNuevo(){
        idSeleccionado=null;txfNombre.setText("");txfCantidad.setText("");
        if(cbxIdQuimico.getItemCount()>0)cbxIdQuimico.setSelectedIndex(0); if(cbxIdComposicion.getItemCount()>0)cbxIdComposicion.setSelectedIndex(0);
        txfNombre.setEditable(true);btnAgregar.setEnabled(true);btnModificar.setEnabled(false);btnEliminar.setEnabled(false);txfNombre.requestFocus();
    }
    // Carga un registro existente y activa el modo de edición.
    private void modoEdicion(clases.Composicion_Quimico c){
        idSeleccionado=c.getComp_nombre();txfNombre.setText(c.getComp_nombre());txfCantidad.setText(String.valueOf(c.getCantidad()));
        if(c.getQuimico()!=null)cbxIdQuimico.setSelectedItem(c.getQuimico().getQuim_nombre()); if(c.getResiduo()!=null)cbxIdComposicion.setSelectedItem(c.getResiduo().getRes_nombre());
        txfNombre.setEditable(false);btnAgregar.setEnabled(false);btnModificar.setEnabled(true);btnEliminar.setEnabled(true);
    }
    private double cantidad(){String s=Residuos.Validaciones.texto(txfCantidad.getText(),"Cantidad").replace(',','.');return Residuos.Validaciones.noNegativo(s,"Cantidad");}
    private clases.Quimico quimicoSeleccionado(){String id=(String)cbxIdQuimico.getSelectedItem();if(id==null)throw new IllegalArgumentException("Registre un químico antes de crear una composición.");return crud.find(clases.Quimico.class,id);}
    private clases.Residuo residuoSeleccionado(){String id=(String)cbxIdComposicion.getSelectedItem();if(id==null)throw new IllegalArgumentException("Registre un residuo antes de crear una composición.");return crud.find(clases.Residuo.class,id);}
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt){
        try{String id=Residuos.Validaciones.texto(txfNombre.getText(),"Nombre de la composición");double cant=cantidad();if(crud.exists(clases.Composicion_Quimico.class,id))throw new IllegalArgumentException("Ya existe esa composición.");clases.Composicion_Quimico c=new clases.Composicion_Quimico(id,cant);c.setQuimico(quimicoSeleccionado());c.setResiduo(residuoSeleccionado());crud.create(c);JOptionPane.showMessageDialog(this,"Composición registrada correctamente.");modoNuevo();}
        catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
    }
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt){
        try{String criterio=JOptionPane.showInputDialog(this,"Nombre o parte del nombre:",txfNombre.getText());if(criterio==null)return;List<?> datos=crud.opRead("Composicion_Quimico","nombre",criterio);if(datos.isEmpty()){JOptionPane.showMessageDialog(this,"No hay composiciones registradas.");return;}@SuppressWarnings("unchecked")List<clases.Composicion_Quimico> lista=(List<clases.Composicion_Quimico>)(List<?>)datos;clases.Composicion_Quimico c=Residuos.DialogosCRUD.combo(this,"Seleccione una composición",lista);if(c!=null)modoEdicion(c);}
        catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Consulta",JOptionPane.ERROR_MESSAGE);}
    }
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt){
        if(idSeleccionado==null){JOptionPane.showMessageDialog(this,"Primero consulte una composición.");return;}
        try{clases.Composicion_Quimico c=crud.find(clases.Composicion_Quimico.class,idSeleccionado);if(c==null)throw new IllegalArgumentException("La composición ya no existe.");c.setCantidad(cantidad());c.setQuimico(quimicoSeleccionado());c.setResiduo(residuoSeleccionado());crud.update(c);JOptionPane.showMessageDialog(this,"Composición modificada. La PK no fue alterada.");modoNuevo();}
        catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
    }
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt){
        if(idSeleccionado==null){JOptionPane.showMessageDialog(this,"Primero consulte una composición.");return;}if(JOptionPane.showConfirmDialog(this,"¿Eliminar la composición "+idSeleccionado+"?","Confirmar",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
        try{crud.opDelete("Composicion_Quimico",idSeleccionado);JOptionPane.showMessageDialog(this,"Composición eliminada correctamente.");modoNuevo();}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"No se puede eliminar",JOptionPane.WARNING_MESSAGE);}
    }
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt){modoNuevo();}
    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt){dispose();new MenuPrincipal().setVisible(true);}

    
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelComposicion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txfNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbxIdQuimico = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbxIdComposicion = new javax.swing.JComboBox<>();
        txfCantidad = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelComposicion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelComposicion.setText("Composicion del Quimico");
        jPanel1.add(labelComposicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));

        jLabel1.setText("Nombre de la composición:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));
        jPanel1.add(txfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 140, -1));

        jLabel2.setText("Cantidad");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        jLabel3.setText("Id del quimico:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        cbxIdQuimico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxIdQuimico, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 140, -1));

        jLabel4.setText("Id de composición:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        cbxIdComposicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxIdComposicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 140, -1));
        jPanel1.add(txfCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 140, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Composicion_Quimico().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxIdComposicion;
    private javax.swing.JComboBox<String> cbxIdQuimico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelComposicion;
    private javax.swing.JTextField txfCantidad;
    private javax.swing.JTextField txfNombre;
    // End of variables declaration//GEN-END:variables
}
