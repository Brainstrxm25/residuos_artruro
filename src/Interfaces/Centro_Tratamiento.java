package Interfaces;

import java.util.List;
import javax.swing.*;

/** Interfaz gráfica para administrar centros de tratamiento. */
public class Centro_Tratamiento extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Centro_Tratamiento.class.getName());
    private final Residuos.opCRUD crud = new Residuos.opCRUD();
    private String idSeleccionado;
    private JButton btnAgregar, btnConsultar, btnModificar, btnEliminar, btnLimpiar, btnMenu;

    // Constructor: inicializa componentes y prepara la ventana.
    public Centro_Tratamiento() {
        initComponents();
        crearBotones();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        modoNuevo();
    }

    // Crea un botón y lo conecta con la acción indicada.
    private JButton crearBoton(String texto,int x,int y,int ancho,java.awt.event.ActionListener accion){
        JButton b=new JButton(texto); b.addActionListener(accion);
        jPanel1.add(b,new org.netbeans.lib.awtextra.AbsoluteConstraints(x,y,ancho,28));
        return b;
    }
    // Crea los botones de acciones de la ventana.
    private void crearBotones(){
        btnAgregar=crearBoton("Agregar",30,205,100,this::btnAgregarActionPerformed);
        btnConsultar=crearBoton("Consultar",145,205,110,this::btnConsultarActionPerformed);
        btnModificar=crearBoton("Modificar",270,205,110,this::btnModificarActionPerformed);
        btnEliminar=crearBoton("Eliminar",30,245,100,this::btnEliminarActionPerformed);
        btnLimpiar=crearBoton("Limpiar",145,245,110,this::btnLimpiarActionPerformed);
        btnMenu=crearBoton("Volver al menú",270,245,110,this::btnMenuActionPerformed);
        getContentPane().setPreferredSize(new java.awt.Dimension(410,295)); pack();
    }
    // Limpia el formulario y lo deja listo para un registro nuevo.
    private void modoNuevo(){
        idSeleccionado=null; txfDescripcion.setText(""); txfUbicacion.setText("");
        txfDescripcion.setEditable(true); btnAgregar.setEnabled(true); btnModificar.setEnabled(false); btnEliminar.setEnabled(false);
        txfDescripcion.requestFocus();
    }
    // Carga un registro existente y activa el modo de edición.
    private void modoEdicion(clases.Centro_Tratamiento c){
        idSeleccionado=c.getCen_descripcion();
        txfDescripcion.setText(c.getCen_descripcion()); txfUbicacion.setText(c.getCen_ubicacion());
        txfDescripcion.setEditable(false); btnAgregar.setEnabled(false); btnModificar.setEnabled(true); btnEliminar.setEnabled(true);
    }
    // Valida que un campo obligatorio tenga contenido.
    private String requerido(String s,String campo){return Residuos.Validaciones.texto(s,campo);}
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt){
        try{
            String id=requerido(txfDescripcion.getText(),"Descripción");
            String ub=requerido(txfUbicacion.getText(),"Ubicación");
            if(crud.exists(clases.Centro_Tratamiento.class,id)) throw new IllegalArgumentException("Ya existe un centro con esa descripción.");
            crud.create(new clases.Centro_Tratamiento(id,ub));
            JOptionPane.showMessageDialog(this,"Centro registrado correctamente."); modoNuevo();
        }catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
    }
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt){
        try{
            String criterio=JOptionPane.showInputDialog(this,"Descripción o parte de la descripción:",txfDescripcion.getText());
            if(criterio==null)return;
            List<?> datos=crud.opRead("Centro_Tratamiento","descripcion",criterio);
            if(datos.isEmpty()){JOptionPane.showMessageDialog(this,"No hay centros registrados.");return;}
            @SuppressWarnings("unchecked") List<clases.Centro_Tratamiento> lista=(List<clases.Centro_Tratamiento>)(List<?>)datos;
            clases.Centro_Tratamiento elegido=Residuos.DialogosCRUD.combo(this,"Seleccione un centro",lista);
            if(elegido!=null)modoEdicion(elegido);
        }catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Consulta",JOptionPane.ERROR_MESSAGE);}
    }
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt){
        if(idSeleccionado==null){JOptionPane.showMessageDialog(this,"Primero consulte y seleccione un centro.");return;}
        try{
            String ub=requerido(txfUbicacion.getText(),"Ubicación");
            clases.Centro_Tratamiento c=crud.find(clases.Centro_Tratamiento.class,idSeleccionado);
            if(c==null)throw new IllegalArgumentException("El centro ya no existe.");
            c.setCen_ubicacion(ub); crud.update(c);
            JOptionPane.showMessageDialog(this,"Centro modificado correctamente. La PK no fue alterada."); modoNuevo();
        }catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
    }
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt){
        if(idSeleccionado==null){JOptionPane.showMessageDialog(this,"Primero consulte y seleccione un centro.");return;}
        if(JOptionPane.showConfirmDialog(this,"¿Eliminar el centro "+idSeleccionado+"?","Confirmar",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
        try{crud.opDelete("Centro_Tratamiento",idSeleccionado);JOptionPane.showMessageDialog(this,"Centro eliminado correctamente.");modoNuevo();}
        catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"No se puede eliminar",JOptionPane.WARNING_MESSAGE);}
    }
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt){modoNuevo();}
    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt){dispose();new MenuPrincipal().setVisible(true);}

    
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        labelDescripcion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txfDescripcion = new javax.swing.JTextField();
        txfUbicacion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Centro de Tratamiento");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, -1, -1));

        labelDescripcion.setText("Descripción del Centro:");
        jPanel1.add(labelDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jLabel2.setText("Ubicación:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        txfDescripcion.addActionListener(this::txfDescripcionActionPerformed);
        jPanel1.add(txfDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 150, -1));
        jPanel1.add(txfUbicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfDescripcionActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Centro_Tratamiento().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelDescripcion;
    private javax.swing.JTextField txfDescripcion;
    private javax.swing.JTextField txfUbicacion;
    // End of variables declaration//GEN-END:variables
}
