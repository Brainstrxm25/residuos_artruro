package Interfaces;

/** Interfaz gráfica para administrar transportistas. */
public class Transportista extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Transportista.class.getName());


    private final Residuos.opCRUD crud = new Residuos.opCRUD();
    // Constructor: inicializa componentes y prepara la ventana.
    public Transportista(){initComponents();configurar();}
    // Configura la ventana, carga datos iniciales y agrega los botones CRUD.
    private void configurar(){setLocationRelativeTo(null);setResizable(false);setDefaultCloseOperation(DISPOSE_ON_CLOSE);Residuos.DialogosCRUD.agregarBotones(jPanel1,this::agregar,this::consultar,this::modificar,this::eliminar,this::limpiar,this::menu,225);pack();setLocationRelativeTo(null);}
    // Valida el formulario y guarda un registro nuevo.
    private void agregar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(txfNombre.getText(),"Nombre");String d=Residuos.Validaciones.texto(txfDireccion.getText(),"Dirección");String tel=Residuos.Validaciones.texto(txfTelefono.getText(),"Teléfono");Residuos.Validaciones.telefono(tel);if(crud.exists(clases.Transportista.class,id))throw new IllegalArgumentException("Ya existe ese transportista.");crud.create(new clases.Transportista(id,d,tel));Residuos.DialogosCRUD.ok(this,"Transportista registrado.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Pide un criterio de búsqueda y muestra los resultados.
    private void consultar(java.awt.event.ActionEvent e){try{String c=Residuos.DialogosCRUD.elegirCampo(this,"nombre","direccion","telefono");if(c==null)return;String q=Residuos.DialogosCRUD.pedir(this,"Criterio:","");Residuos.DialogosCRUD.tabla(this,crud.opBuscar("Transportista",c,q),"Consulta de transportistas");}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Busca un registro, aplica cambios y actualiza ObjectDB.
    private void modificar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nombre (PK) a modificar:",""),"PK");clases.Transportista o=crud.find(clases.Transportista.class,id);if(o==null)throw new IllegalArgumentException("No existe ese transportista.");txfNombre.setText(o.getTrans_nombre());txfDireccion.setText(o.getDireccion());txfTelefono.setText(o.getTelefono());o.setDireccion(Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nueva dirección:",o.getDireccion()),"Dirección"));String tel=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nuevo teléfono:",o.getTelefono()),"Teléfono");Residuos.Validaciones.telefono(tel);o.setTelefono(tel);crud.update(o);Residuos.DialogosCRUD.ok(this,"Transportista modificado. La PK no fue alterada.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Elimina un registro después de solicitar confirmación.
    private void eliminar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nombre del transportista a eliminar:",""),"PK");if(Residuos.DialogosCRUD.confirmar(this,"¿Eliminar el transportista '"+id+"'?")){crud.opDelete("Transportista",id);Residuos.DialogosCRUD.ok(this,"Transportista eliminado.");limpiar(null);}}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Limpia los campos y prepara una nueva captura.
    private void limpiar(java.awt.event.ActionEvent e){txfNombre.setText("");txfDireccion.setText("");txfTelefono.setText("");txfNombre.requestFocus();}
    // Cierra esta ventana y regresa al menú principal.
    private void menu(java.awt.event.ActionEvent e){dispose();new MenuPrincipal().setVisible(true);}

    
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelTransportista = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txfNombre = new javax.swing.JTextField();
        txfDireccion = new javax.swing.JTextField();
        txfTelefono = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTransportista.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelTransportista.setText("Transportista");
        jPanel1.add(labelTransportista, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, -1, -1));

        jLabel1.setText("Nombre Transportista:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jLabel2.setText("Dirección:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, -1, -1));

        jLabel3.setText("Teléfono:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, -1, -1));
        jPanel1.add(txfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 150, -1));
        jPanel1.add(txfDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 150, -1));
        jPanel1.add(txfTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
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
        java.awt.EventQueue.invokeLater(() -> new Transportista().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTransportista;
    private javax.swing.JTextField txfDireccion;
    private javax.swing.JTextField txfNombre;
    private javax.swing.JTextField txfTelefono;
    // End of variables declaration//GEN-END:variables
}
