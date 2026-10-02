package Interfaces;

/** Interfaz gráfica para administrar químicos mediante operaciones CRUD. */
public class QuimicoIn extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuimicoIn.class.getName());


    private final Residuos.opCRUD crud = new Residuos.opCRUD();
    // Constructor: inicializa componentes y prepara la ventana.
    public QuimicoIn(){initComponents();configurar();}
    // Configura la ventana, carga datos iniciales y agrega los botones CRUD.
    private void configurar(){setLocationRelativeTo(null);setResizable(false);setDefaultCloseOperation(DISPOSE_ON_CLOSE);Residuos.DialogosCRUD.agregarBotones(jPanel1,this::agregar,this::consultar,this::modificar,this::eliminar,this::limpiar,this::menu,200);pack();setLocationRelativeTo(null);}
    // Valida el formulario y guarda un registro nuevo.
    private void agregar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(txfNombre.getText(),"Nombre");String tipo=Residuos.Validaciones.texto(txfTipo.getText(),"Peligrosidad");if(crud.exists(clases.Quimico.class,id))throw new IllegalArgumentException("Ya existe ese químico.");crud.create(new clases.Quimico(id,tipo));Residuos.DialogosCRUD.ok(this,"Químico registrado.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Pide un criterio de búsqueda y muestra los resultados.
    private void consultar(java.awt.event.ActionEvent e){try{String c=Residuos.DialogosCRUD.elegirCampo(this,"nombre","peligrosidad");if(c==null)return;String q=Residuos.DialogosCRUD.pedir(this,"Criterio:","");Residuos.DialogosCRUD.tabla(this,crud.opBuscar("Quimico",c,q),"Consulta de químicos");}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Busca un registro, aplica cambios y actualiza ObjectDB.
    private void modificar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nombre (PK) a modificar:",""),"PK");clases.Quimico o=crud.find(clases.Quimico.class,id);if(o==null)throw new IllegalArgumentException("No existe ese químico.");txfNombre.setText(o.getQuim_nombre());String tipo=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nueva peligrosidad:",o.getTipo_peligrosidad()),"Peligrosidad");o.setTipo_peligrosidad(tipo);crud.update(o);Residuos.DialogosCRUD.ok(this,"Químico modificado. La PK no fue alterada.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Elimina un registro después de solicitar confirmación.
    private void eliminar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nombre del químico a eliminar:",""),"PK");if(Residuos.DialogosCRUD.confirmar(this,"¿Eliminar el químico '"+id+"'?")){crud.opDelete("Quimico",id);Residuos.DialogosCRUD.ok(this,"Químico eliminado.");limpiar(null);}}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Limpia los campos y prepara una nueva captura.
    private void limpiar(java.awt.event.ActionEvent e){txfNombre.setText("");txfTipo.setText("");txfNombre.requestFocus();}
    // Cierra esta ventana y regresa al menú principal.
    private void menu(java.awt.event.ActionEvent e){dispose();new MenuPrincipal().setVisible(true);}

    
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelQuimico = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txfNombre = new javax.swing.JTextField();
        txfTipo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelQuimico.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelQuimico.setText("Quimico");
        jPanel1.add(labelQuimico, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, -1, -1));

        labelNombre.setText("Nombre de quimico:");
        jPanel1.add(labelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jLabel1.setText("Tipo Peligrosidad:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, -1, -1));
        jPanel1.add(txfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 160, -1));
        jPanel1.add(txfTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 160, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
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
        java.awt.EventQueue.invokeLater(() -> new QuimicoIn().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelQuimico;
    private javax.swing.JTextField txfNombre;
    private javax.swing.JTextField txfTipo;
    // End of variables declaration//GEN-END:variables
}
