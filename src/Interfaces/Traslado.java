package Interfaces;

/** Interfaz gráfica para administrar traslados y todas sus relaciones. */
public class Traslado extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Traslado.class.getName());


    private final Residuos.opCRUD crud = new Residuos.opCRUD();
    // Constructor: inicializa componentes y prepara la ventana.
    public Traslado(){initComponents();configurar();}
    // Configura la ventana, carga datos iniciales y agrega los botones CRUD.
    private void configurar(){setLocationRelativeTo(null);setResizable(false);setDefaultCloseOperation(DISPOSE_ON_CLOSE);cargarCombos();Residuos.DialogosCRUD.agregarBotones(jPanel1,this::agregar,this::consultar,this::modificar,this::eliminar,this::limpiar,this::menu,305);pack();setLocationRelativeTo(null);}
    // Recarga los JComboBox con datos actuales de ObjectDB.
    private void cargarCombos(){cbxEmpresa.removeAllItems();for(clases.Empresa x:crud.readAll(clases.Empresa.class))cbxEmpresa.addItem(x.getEmp_nombre());cbxResiduo.removeAllItems();for(clases.Residuo x:crud.readAll(clases.Residuo.class))cbxResiduo.addItem(x.getRes_nombre());cbxCentro.removeAllItems();for(clases.Centro_Tratamiento x:crud.readAll(clases.Centro_Tratamiento.class))cbxCentro.addItem(x.getCen_descripcion());cbxTratamiento.removeAllItems();for(clases.Tipo_Tratamiento x:crud.readAll(clases.Tipo_Tratamiento.class))cbxTratamiento.addItem(x.getTrat_descripcion());cbxTransporte.removeAllItems();for(clases.Transporte x:crud.readAll(clases.Transporte.class))cbxTransporte.addItem(x.getTrans_tipo());}
    // Obtiene de ObjectDB el objeto seleccionado en un JComboBox.
    private <T> T seleccionado(javax.swing.JComboBox<String> cb,Class<T> tipo){if(cb.getSelectedItem()==null)throw new IllegalArgumentException("Falta seleccionar una relación.");return crud.find(tipo,cb.getSelectedItem().toString());}
    // Valida el formulario y guarda un registro nuevo.
    private void agregar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(txfOrigen.getText(),"Origen / PK");double cant=Residuos.Validaciones.noNegativo(txfCantidad.getText(),"Cantidad");String fi=Residuos.Validaciones.texto(txfFechaInicio.getText(),"Fecha inicio");String fl=Residuos.Validaciones.texto(txfFechaLlegada.getText(),"Fecha llegada");Residuos.Validaciones.fecha(fi,"Fecha inicio");Residuos.Validaciones.fecha(fl,"Fecha llegada");if(fl.compareTo(fi)<0)throw new IllegalArgumentException("La fecha de llegada no puede ser anterior al inicio.");double costo=Residuos.Validaciones.noNegativo(txfCosto.getText(),"Costo");double km=Residuos.Validaciones.noNegativo(txfKilometros.getText(),"Kilómetros");if(crud.exists(clases.Traslado.class,id))throw new IllegalArgumentException("Ya existe un traslado con ese origen/PK.");clases.Traslado o=new clases.Traslado(id,cant,fi,fl,costo,km);o.setEmpresa(seleccionado(cbxEmpresa,clases.Empresa.class));o.setResiduo(seleccionado(cbxResiduo,clases.Residuo.class));o.setCentro(seleccionado(cbxCentro,clases.Centro_Tratamiento.class));o.setTratamiento(seleccionado(cbxTratamiento,clases.Tipo_Tratamiento.class));o.setTransporte(seleccionado(cbxTransporte,clases.Transporte.class));crud.create(o);Residuos.DialogosCRUD.ok(this,"Traslado registrado.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Pide un criterio de búsqueda y muestra los resultados.
    private void consultar(java.awt.event.ActionEvent e){try{String c=Residuos.DialogosCRUD.elegirCampo(this,"origen","cantidad","inicio","llegada","costo","km");if(c==null)return;String q=Residuos.DialogosCRUD.pedir(this,"Criterio:","");Residuos.DialogosCRUD.tabla(this,crud.opBuscar("Traslado",c,q),"Consulta de traslados");}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Busca un registro, aplica cambios y actualiza ObjectDB.
    private void modificar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Origen / PK del traslado:",""),"PK");clases.Traslado o=crud.find(clases.Traslado.class,id);if(o==null)throw new IllegalArgumentException("No existe ese traslado.");txfOrigen.setText(o.getTras_origen());txfCantidad.setText(String.valueOf(o.getCantidad_trasladada()));txfFechaInicio.setText(o.getFecha_inicio());txfFechaLlegada.setText(o.getFecha_llegada());txfCosto.setText(String.valueOf(o.getCosto()));txfKilometros.setText(String.valueOf(o.getKm_recorridos()));cbxEmpresa.setSelectedItem(o.getEmpresa()!=null?o.getEmpresa().getEmp_nombre():null);cbxResiduo.setSelectedItem(o.getResiduo()!=null?o.getResiduo().getRes_nombre():null);cbxCentro.setSelectedItem(o.getCentro()!=null?o.getCentro().getCen_descripcion():null);cbxTratamiento.setSelectedItem(o.getTratamiento()!=null?o.getTratamiento().getTrat_descripcion():null);cbxTransporte.setSelectedItem(o.getTransporte()!=null?o.getTransporte().getTrans_tipo():null);o.setCantidad_trasladada(Residuos.Validaciones.noNegativo(Residuos.DialogosCRUD.pedir(this,"Cantidad:",String.valueOf(o.getCantidad_trasladada())),"Cantidad"));String fi=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Fecha inicio:",o.getFecha_inicio()),"Fecha inicio");String fl=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Fecha llegada:",o.getFecha_llegada()),"Fecha llegada");Residuos.Validaciones.fecha(fi,"Fecha inicio");Residuos.Validaciones.fecha(fl,"Fecha llegada");if(fl.compareTo(fi)<0)throw new IllegalArgumentException("La llegada no puede ser anterior al inicio.");o.setFecha_inicio(fi);o.setFecha_llegada(fl);o.setCosto(Residuos.Validaciones.noNegativo(Residuos.DialogosCRUD.pedir(this,"Costo:",String.valueOf(o.getCosto())),"Costo"));o.setKm_recorridos(Residuos.Validaciones.noNegativo(Residuos.DialogosCRUD.pedir(this,"Kilómetros:",String.valueOf(o.getKm_recorridos())),"Kilómetros"));o.setEmpresa(seleccionado(cbxEmpresa,clases.Empresa.class));o.setResiduo(seleccionado(cbxResiduo,clases.Residuo.class));o.setCentro(seleccionado(cbxCentro,clases.Centro_Tratamiento.class));o.setTratamiento(seleccionado(cbxTratamiento,clases.Tipo_Tratamiento.class));o.setTransporte(seleccionado(cbxTransporte,clases.Transporte.class));crud.update(o);Residuos.DialogosCRUD.ok(this,"Traslado modificado. La PK no fue alterada.");limpiar(null);}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Elimina un registro después de solicitar confirmación.
    private void eliminar(java.awt.event.ActionEvent e){try{String id=Residuos.Validaciones.texto(Residuos.DialogosCRUD.pedir(this,"Origen / PK del traslado a eliminar:",""),"PK");if(Residuos.DialogosCRUD.confirmar(this,"¿Eliminar el traslado '"+id+"'?")){crud.opDelete("Traslado",id);Residuos.DialogosCRUD.ok(this,"Traslado eliminado.");limpiar(null);}}catch(Exception ex){Residuos.DialogosCRUD.error(this,ex);}}
    // Limpia los campos y prepara una nueva captura.
    private void limpiar(java.awt.event.ActionEvent e){txfOrigen.setText("");txfCantidad.setText("");txfFechaInicio.setText("");txfFechaLlegada.setText("");txfCosto.setText("");txfKilometros.setText("");cargarCombos();txfOrigen.requestFocus();}
    // Cierra esta ventana y regresa al menú principal.
    private void menu(java.awt.event.ActionEvent e){dispose();new MenuPrincipal().setVisible(true);}

    
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelTraslado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbxEmpresa = new javax.swing.JComboBox<>();
        cbxResiduo = new javax.swing.JComboBox<>();
        cbxCentro = new javax.swing.JComboBox<>();
        cbxTratamiento = new javax.swing.JComboBox<>();
        cbxTransporte = new javax.swing.JComboBox<>();
        txfCantidad = new javax.swing.JTextField();
        txfOrigen = new javax.swing.JTextField();
        txfFechaLlegada = new javax.swing.JTextField();
        txfCosto = new javax.swing.JTextField();
        txfKilometros = new javax.swing.JTextField();
        txfFechaInicio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTraslado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelTraslado.setText("Traslado");
        jPanel1.add(labelTraslado, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 67, -1));

        jLabel1.setText("Id Empresa:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 60, 76, -1));

        jLabel2.setText("Id Residuo:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 103, -1, -1));

        jLabel3.setText("Id Centro:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 58, -1));

        jLabel4.setText("Id Tratamiento:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, -1));

        jLabel5.setText("Id Transporte:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 90, -1));

        jLabel6.setText("Origen Traslado:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, -1, -1));

        jLabel7.setText("Cantidad Trasladada:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, -1));

        jLabel8.setText("Fecha de Inicio:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, -1));

        jLabel9.setText("Fecha de llegada:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, -1, -1));

        jLabel10.setText("Costo:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 220, -1, -1));

        jLabel11.setText("Kilometros recorridos:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, -1, -1));

        cbxEmpresa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 150, -1));

        cbxResiduo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxResiduo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 150, -1));

        cbxCentro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxCentro, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 150, -1));

        cbxTratamiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxTratamiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 150, -1));

        cbxTransporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxTransporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 150, -1));
        jPanel1.add(txfCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 140, -1));
        jPanel1.add(txfOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, 140, -1));
        jPanel1.add(txfFechaLlegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, 150, -1));

        txfCosto.addActionListener(this::txfCostoActionPerformed);
        jPanel1.add(txfCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 150, -1));
        jPanel1.add(txfKilometros, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 260, 150, -1));
        jPanel1.add(txfFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 140, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfCostoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfCostoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Traslado().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxCentro;
    private javax.swing.JComboBox<String> cbxEmpresa;
    private javax.swing.JComboBox<String> cbxResiduo;
    private javax.swing.JComboBox<String> cbxTransporte;
    private javax.swing.JComboBox<String> cbxTratamiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTraslado;
    private javax.swing.JTextField txfCantidad;
    private javax.swing.JTextField txfCosto;
    private javax.swing.JTextField txfFechaInicio;
    private javax.swing.JTextField txfFechaLlegada;
    private javax.swing.JTextField txfKilometros;
    private javax.swing.JTextField txfOrigen;
    // End of variables declaration//GEN-END:variables
}
