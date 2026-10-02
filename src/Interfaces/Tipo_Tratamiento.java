package Interfaces;

/** Interfaz gráfica para administrar tipos de tratamiento. */
public class Tipo_Tratamiento extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Tipo_Tratamiento.class.getName());


    private final Residuos.opCRUD crud = new Residuos.opCRUD();
    // Constructor: inicializa componentes y prepara la ventana.
    public Tipo_Tratamiento(){initComponents();configurar();}
    // Configura la ventana, carga datos iniciales y agrega los botones CRUD.
    private void configurar(){setLocationRelativeTo(null);setResizable(false);setDefaultCloseOperation(DISPOSE_ON_CLOSE);Residuos.DialogosCRUD.agregarBotones(jPanel1,this::agregar,this::consultar,this::modificar,this::eliminar,this::limpiar,this::menu,125);pack();setLocationRelativeTo(null);}
    // Valida el formulario y guarda un registro nuevo.
    private void agregar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(txfDescripcion.getText(),"Descripción");if(crud.exists(clases.Tipo_Tratamiento.class,id))throw new IllegalArgumentException("Ya existe ese tipo de tratamiento.");crud.create(new clases.Tipo_Tratamiento(id));Residuos.DialogosCRUD.ok(this,"Tipo de tratamiento registrado.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Pide un criterio de búsqueda y muestra los resultados.
    private void consultar(java.awt.event.ActionEvent e){try{String q=Residuos.DialogosCRUD.pedir(this,"Descripción o parte de la descripción:","");Residuos.DialogosCRUD.tabla(this,crud.opBuscar("Tipo_Tratamiento","descripcion",q),"Consulta de tratamientos");}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Busca un registro, aplica cambios y actualiza ObjectDB.
    private void modificar(java.awt.event.ActionEvent e){Residuos.DialogosCRUD.error(this,new IllegalArgumentException("La única propiedad es la clave primaria y no puede editarse."));}
    // Elimina un registro después de solicitar confirmación.
    private void eliminar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Descripción del tratamiento a eliminar:",""),"PK");if(Residuos.DialogosCRUD.confirmar(this,"¿Eliminar el tratamiento '"+id+"'?")){crud.opDelete("Tipo_Tratamiento",id);Residuos.DialogosCRUD.ok(this,"Tratamiento eliminado.");limpiar(null);}}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Limpia los campos y prepara una nueva captura.
    private void limpiar(java.awt.event.ActionEvent e){txfDescripcion.setText("");txfDescripcion.requestFocus();}
    // Cierra esta ventana y regresa al menú principal.
    private void menu(java.awt.event.ActionEvent e){dispose();new MenuPrincipal().setVisible(true);}

    
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txfDescripcion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tipo de Tratamiento");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, -1));

        jLabel2.setText("Descripcion del tratamiento: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        txfDescripcion.addActionListener(this::txfDescripcionActionPerformed);
        jPanel1.add(txfDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
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
        java.awt.EventQueue.invokeLater(() -> new Tipo_Tratamiento().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txfDescripcion;
    // End of variables declaration//GEN-END:variables
}
