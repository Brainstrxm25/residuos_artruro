package Interfaces;

/** Interfaz gráfica para administrar residuos y sus relaciones con empresa y envase. */
public class Residuo extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Residuo.class.getName());


    private final Residuos.opCRUD crud = new Residuos.opCRUD();
    // Constructor: inicializa componentes y prepara la ventana.
    public Residuo(){initComponents();configurar();}
    // Configura la ventana, carga datos iniciales y agrega los botones CRUD.
    private void configurar(){setLocationRelativeTo(null);setResizable(false);setDefaultCloseOperation(DISPOSE_ON_CLOSE);cargarCombos();Residuos.DialogosCRUD.agregarBotones(jPanel1,this::agregar,this::consultar,this::modificar,this::eliminar,this::limpiar,this::menu,250);pack();setLocationRelativeTo(null);}
    // Recarga los JComboBox con datos actuales de ObjectDB.
    private void cargarCombos(){cbxIdEmpresa.removeAllItems();for(clases.Empresa e:crud.readAll(clases.Empresa.class))cbxIdEmpresa.addItem(e.getEmp_nombre());cbxIdEnvase.removeAllItems();for(clases.Envase e:crud.readAll(clases.Envase.class))cbxIdEnvase.addItem(e.getEnv_descripcion());}
    // Obtiene la empresa elegida en el JComboBox.
    private clases.Empresa empresaSeleccionada(){if(cbxIdEmpresa.getSelectedItem()==null)throw new IllegalArgumentException("Debe seleccionar una empresa.");return crud.find(clases.Empresa.class,cbxIdEmpresa.getSelectedItem().toString());}
    // Obtiene el envase elegido en el JComboBox.
    private clases.Envase envaseSeleccionado(){if(cbxIdEnvase.getSelectedItem()==null)throw new IllegalArgumentException("Debe seleccionar un envase.");return crud.find(clases.Envase.class,cbxIdEnvase.getSelectedItem().toString());}
    // Valida el formulario y guarda un registro nuevo.
    private void agregar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(jTextField1.getText(),"Nombre del residuo");double cant=Residuos.Validaciones.noNegativo(jTextField2.getText(),"Cantidad total");if(crud.exists(clases.Residuo.class,id))throw new IllegalArgumentException("Ya existe ese residuo.");clases.Residuo o=new clases.Residuo(id,cant);o.setEmpresa(empresaSeleccionada());o.setEnvase(envaseSeleccionado());crud.create(o);Residuos.DialogosCRUD.ok(this,"Residuo registrado.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Pide un criterio de búsqueda y muestra los resultados.
    private void consultar(java.awt.event.ActionEvent e){try{String c=Residuos.DialogosCRUD.elegirCampo(this,"nombre","cantidad");if(c==null)return;String q=Residuos.DialogosCRUD.pedir(this,"Criterio:","");Residuos.DialogosCRUD.tabla(this,crud.opBuscar("Residuo",c,q),"Consulta de residuos");}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Busca un registro, aplica cambios y actualiza ObjectDB.
    private void modificar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nombre (PK) del residuo:",""),"PK");clases.Residuo o=crud.find(clases.Residuo.class,id);if(o==null)throw new IllegalArgumentException("No existe ese residuo.");jTextField1.setText(o.getRes_nombre());jTextField2.setText(String.valueOf(o.getCantidad_total()));cbxIdEmpresa.setSelectedItem(o.getEmpresa()!=null?o.getEmpresa().getEmp_nombre():null);cbxIdEnvase.setSelectedItem(o.getEnvase()!=null?o.getEnvase().getEnv_descripcion():null);o.setCantidad_total(Residuos.Validaciones.noNegativo(Residuos.DialogosCRUD.pedir(this,"Nueva cantidad:",String.valueOf(o.getCantidad_total())),"Cantidad total"));o.setEmpresa(empresaSeleccionada());o.setEnvase(envaseSeleccionado());crud.update(o);Residuos.DialogosCRUD.ok(this,"Residuo modificado. La PK no fue alterada.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Elimina un registro después de solicitar confirmación.
    private void eliminar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nombre del residuo a eliminar:",""),"PK");if(Residuos.DialogosCRUD.confirmar(this,"¿Eliminar el residuo '"+id+"'?")){crud.opDelete("Residuo",id);Residuos.DialogosCRUD.ok(this,"Residuo eliminado.");limpiar(null);}}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Limpia los campos y prepara una nueva captura.
    private void limpiar(java.awt.event.ActionEvent e){jTextField1.setText("");jTextField2.setText("");cargarCombos();jTextField1.requestFocus();}
    // Cierra esta ventana y regresa al menú principal.
    private void menu(java.awt.event.ActionEvent e){dispose();new MenuPrincipal().setVisible(true);}

    
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxIdEmpresa = new javax.swing.JComboBox<>();
        cbxIdEnvase = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Residuo");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        jLabel2.setText("Id Empresa:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jLabel3.setText("Id Envase:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        jLabel4.setText("Nombre Residuo:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel5.setText("Cantidad Total:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        cbxIdEmpresa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxIdEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 150, -1));

        cbxIdEnvase.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxIdEnvase, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 150, -1));

        jTextField1.addActionListener(this::jTextField1ActionPerformed);
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 150, -1));
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Residuo().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxIdEmpresa;
    private javax.swing.JComboBox<String> cbxIdEnvase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
