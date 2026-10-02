package Interfaces;

/** Interfaz gráfica para administrar empresas mediante operaciones CRUD. */
public class Empresa extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Empresa.class.getName());


    private final Residuos.opCRUD crud = new Residuos.opCRUD();

    // Constructor: inicializa componentes y prepara la ventana.
    public Empresa() {
        initComponents();
        configurar();
    }

    // Configura la ventana, carga datos iniciales y agrega los botones CRUD.
    private void configurar() {
        setLocationRelativeTo(null); setResizable(false); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Residuos.DialogosCRUD.agregarBotones(jPanel1,this::agregar,this::consultar,this::modificar,this::eliminar,this::limpiar,this::menu,205);
        pack(); setLocationRelativeTo(null);
    }
    // Valida el formulario y guarda un registro nuevo.
    private void agregar(java.awt.event.ActionEvent e){try{
        String id=Residuos.Validaciones.texto(txfEmpresa.getText(),"Empresa");
        String ub=Residuos.Validaciones.texto(txfUbicacion.getText(),"Ubicación");
        if(crud.exists(clases.Empresa.class,id)) throw new IllegalArgumentException("Ya existe una empresa con ese nombre.");
        crud.create(new clases.Empresa(id,ub)); Residuos.DialogosCRUD.ok(this,"Empresa registrada correctamente."); limpiar(null);
    }catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Pide un criterio de búsqueda y muestra los resultados.
    private void consultar(java.awt.event.ActionEvent e){try{
        String campo=Residuos.DialogosCRUD.elegirCampo(this,"nombre","ubicacion"); if(campo==null)return;
        String criterio=Residuos.DialogosCRUD.pedir(this,"Criterio:","");
        Residuos.DialogosCRUD.tabla(this,crud.opBuscar("Empresa",campo,criterio),"Consulta de empresas");
    }catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Busca un registro, aplica cambios y actualiza ObjectDB.
    private void modificar(java.awt.event.ActionEvent e){try{
        String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Clave primaria (nombre) a modificar:",""),"Clave primaria");
        clases.Empresa obj=crud.find(clases.Empresa.class,id); if(obj==null)throw new IllegalArgumentException("No existe esa empresa.");
        txfEmpresa.setText(obj.getEmp_nombre());
        String ub=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nueva ubicación:",obj.getEmp_ubicacion()),"Ubicación");
        obj.setEmp_ubicacion(ub); crud.update(obj); Residuos.DialogosCRUD.ok(this,"Empresa modificada. La PK no fue alterada."); limpiar(null);
    }catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Elimina un registro después de solicitar confirmación.
    private void eliminar(java.awt.event.ActionEvent e){try{
        String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Nombre de la empresa a eliminar:",""),"Empresa");
        if(Residuos.DialogosCRUD.confirmar(this,"¿Eliminar la empresa '"+id+"'?")){crud.opDelete("Empresa",id);Residuos.DialogosCRUD.ok(this,"Empresa eliminada.");limpiar(null);}
    }catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Limpia los campos y prepara una nueva captura.
    private void limpiar(java.awt.event.ActionEvent e){txfEmpresa.setText("");txfUbicacion.setText("");txfEmpresa.requestFocus();}
    // Cierra esta ventana y regresa al menú principal.
    private void menu(java.awt.event.ActionEvent e){dispose();new MenuPrincipal().setVisible(true);}

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelEmpresa = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txfUbicacion = new javax.swing.JTextField();
        txfEmpresa = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelEmpresa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelEmpresa.setText("Empresa");
        jPanel1.add(labelEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, -1, -1));

        jLabel1.setText("Nombre de empresa: ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jLabel3.setText("Ubicación:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));
        jPanel1.add(txfUbicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 150, -1));
        jPanel1.add(txfEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
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
        java.awt.EventQueue.invokeLater(() -> new Empresa().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelEmpresa;
    private javax.swing.JTextField txfEmpresa;
    private javax.swing.JTextField txfUbicacion;
    // End of variables declaration//GEN-END:variables
}
