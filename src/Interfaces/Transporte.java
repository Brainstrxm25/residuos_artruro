package Interfaces;

/** Interfaz gráfica para administrar transportes y su transportista asociado. */
public class Transporte extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Transporte.class.getName());


    private final Residuos.opCRUD crud = new Residuos.opCRUD();
    // Constructor: inicializa componentes y prepara la ventana.
    public Transporte(){initComponents();configurar();}
    // Configura la ventana, carga datos iniciales y agrega los botones CRUD.
    private void configurar(){setLocationRelativeTo(null);setResizable(false);setDefaultCloseOperation(DISPOSE_ON_CLOSE);cargarTransportistas();Residuos.DialogosCRUD.agregarBotones(jPanel1,this::agregar,this::consultar,this::modificar,this::eliminar,this::limpiar,this::menu,190);pack();setLocationRelativeTo(null);}
    private void cargarTransportistas(){cbxTransportista.removeAllItems();for(clases.Transportista t:crud.readAll(clases.Transportista.class))cbxTransportista.addItem(t.getTrans_nombre());}
    // Obtiene el transportista elegido en el JComboBox.
    private clases.Transportista transportistaSeleccionado(){if(cbxTransportista.getSelectedItem()==null)throw new IllegalArgumentException("Debe seleccionar un transportista.");return crud.find(clases.Transportista.class,cbxTransportista.getSelectedItem().toString());}
    // Valida el formulario y guarda un registro nuevo.
    private void agregar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(txfTipo.getText(),"Tipo de transporte");if(crud.exists(clases.Transporte.class,id))throw new IllegalArgumentException("Ya existe ese transporte.");clases.Transportista tp=transportistaSeleccionado();clases.Transporte o=new clases.Transporte(id);o.setTransportista(tp);crud.create(o);Residuos.DialogosCRUD.ok(this,"Transporte registrado.");cargarTransportistas();limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Pide un criterio de búsqueda y muestra los resultados.
    private void consultar(java.awt.event.ActionEvent e){try{String q=Residuos.DialogosCRUD.pedir(this,"Tipo de transporte (o parte):","");Residuos.DialogosCRUD.tabla(this,crud.opBuscar("Transporte","tipo",q),"Consulta de transportes");}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Busca un registro, aplica cambios y actualiza ObjectDB.
    private void modificar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Tipo (PK) a modificar:",""),"PK");clases.Transporte o=crud.find(clases.Transporte.class,id);if(o==null)throw new IllegalArgumentException("No existe ese transporte.");txfTipo.setText(o.getTrans_tipo());String nombre=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Tipo de transporte (la PK no se modificará):",o.getTrans_tipo()),"Tipo");if(!nombre.equals(id))throw new IllegalArgumentException("La clave primaria no puede editarse.");o.setTransportista(transportistaSeleccionado());crud.update(o);Residuos.DialogosCRUD.ok(this,"Transporte modificado. La PK no fue alterada.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Elimina un registro después de solicitar confirmación.
    private void eliminar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Tipo del transporte a eliminar:",""),"PK");if(Residuos.DialogosCRUD.confirmar(this,"¿Eliminar el transporte '"+id+"'?")){crud.opDelete("Transporte",id);Residuos.DialogosCRUD.ok(this,"Transporte eliminado.");limpiar(null);}}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Limpia los campos y prepara una nueva captura.
    private void limpiar(java.awt.event.ActionEvent e){txfTipo.setText("");cargarTransportistas();txfTipo.requestFocus();}
    // Cierra esta ventana y regresa al menú principal.
    private void menu(java.awt.event.ActionEvent e){dispose();new MenuPrincipal().setVisible(true);}

    
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelTransporte = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txfTipo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbxTransportista = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTransporte.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelTransporte.setText("Transporte");
        jPanel1.add(labelTransporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, -1));

        jLabel1.setText("Tipo de Transporte:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        txfTipo.addActionListener(this::txfTipoActionPerformed);
        jPanel1.add(txfTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 150, -1));

        jLabel2.setText("Id Transportista: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        cbxTransportista.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxTransportista, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfTipoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Transporte().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxTransportista;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTransporte;
    private javax.swing.JTextField txfTipo;
    // End of variables declaration//GEN-END:variables
}
