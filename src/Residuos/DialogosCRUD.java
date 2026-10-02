package Residuos;

import clases.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableModel;

/** Utilidades Swing compartidas por las interfaces: diálogos, tablas y botones CRUD. */
public final class DialogosCRUD {
    private DialogosCRUD() {}

    public static String pedir(Component parent,String mensaje,String actual){return JOptionPane.showInputDialog(parent,mensaje,actual==null?"":actual);}
    public static void ok(Component parent,String mensaje){JOptionPane.showMessageDialog(parent,mensaje,"Operación correcta",JOptionPane.INFORMATION_MESSAGE);}
    public static void error(Component parent,Exception e){JOptionPane.showMessageDialog(parent,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
    public static boolean confirmar(Component parent,String mensaje){return JOptionPane.showConfirmDialog(parent,mensaje,"Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;}

    public static <T> T combo(Component parent,String titulo,List<T> lista){
        if(lista==null||lista.isEmpty())throw new IllegalStateException("No hay registros disponibles para seleccionar.");
        JComboBox<T> cb=new JComboBox<>(); for(T x:lista)cb.addItem(x);
        int r=JOptionPane.showConfirmDialog(parent,cb,titulo,JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(r!=JOptionPane.OK_OPTION)throw new IllegalArgumentException("Operación cancelada.");
        return (T)cb.getSelectedItem();
    }

    public static String elegirCampo(Component parent,String... campos){return (String)JOptionPane.showInputDialog(parent,"Seleccione el campo de búsqueda:","Consultar",JOptionPane.QUESTION_MESSAGE,null,campos,campos[0]);}

    public static void tabla(Component parent,TableModel model,String titulo){
        JTable t=new JTable(model); t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane sp=new JScrollPane(t); sp.setPreferredSize(new Dimension(1100,380));
        JOptionPane.showMessageDialog(parent,sp,titulo,JOptionPane.INFORMATION_MESSAGE);
    }

    public static void agregarBotones(JPanel panel, java.awt.event.ActionListener a, java.awt.event.ActionListener c, java.awt.event.ActionListener m, java.awt.event.ActionListener d, java.awt.event.ActionListener l, java.awt.event.ActionListener menu, int y){
        JButton b1=new JButton("Agregar"),b2=new JButton("Consultar"),b3=new JButton("Modificar"),b4=new JButton("Eliminar"),b5=new JButton("Limpiar"),b6=new JButton("Volver al menú");
        b1.addActionListener(a);b2.addActionListener(c);b3.addActionListener(m);b4.addActionListener(d);b5.addActionListener(l);b6.addActionListener(menu);
        panel.add(b1,new org.netbeans.lib.awtextra.AbsoluteConstraints(20,y,100,28));
        panel.add(b2,new org.netbeans.lib.awtextra.AbsoluteConstraints(130,y,100,28));
        panel.add(b3,new org.netbeans.lib.awtextra.AbsoluteConstraints(240,y,100,28));
        panel.add(b4,new org.netbeans.lib.awtextra.AbsoluteConstraints(20,y+38,100,28));
        panel.add(b5,new org.netbeans.lib.awtextra.AbsoluteConstraints(130,y+38,100,28));
        panel.add(b6,new org.netbeans.lib.awtextra.AbsoluteConstraints(240,y+38,120,28));
    }
}
