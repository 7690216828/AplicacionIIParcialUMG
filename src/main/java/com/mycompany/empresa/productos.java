package com.mycompany.empresa;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JTextField;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class productos {
    int codigo;
    String nombre;
    float precio;
    int cantidad;
    String Fecha;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    

    

    public void InsertarProducto(JTextField paramNombre, JTextField paramPrecio, JTextField paramCantidad, JTextField paramFecha) {
      
        setNombre(paramNombre.getText());
        setPrecio(Float.parseFloat(paramPrecio.getText()));
        setCantidad(Integer.parseInt(paramCantidad.getText()));
        setFecha(paramFecha.getText());

        conexion objetoconexion = new conexion();
        String consulta = "INSERT INTO producto (nombreProducto, precioUnitario, cantidadProducto, fechaVencimiento) values ( ?,?,?,?); ";
        try {
            CallableStatement cs = objetoconexion.estableceConexion().prepareCall(consulta);
            
            cs.setString(1, getNombre());
            cs.setFloat(2, getPrecio());
            cs.setInt(3, getCantidad());
            cs.setString(4, getFecha());
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se insertó correctamente el producto");
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "No se pudo insertar"+e.toString());

        }
    }
    public void MostrarProductos(JTable paramTablaTotalProductos){
        conexion objetoconexion = new conexion();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        
          paramTablaTotalProductos.setRowSorter(OrdenarTabla);
          String sql="";
          
          modelo.addColumn("ID");
          modelo.addColumn("Nombre");
          modelo.addColumn("Precio");
          modelo.addColumn("Cantidad");
          modelo.addColumn("FechaVencimiento");
          
          paramTablaTotalProductos.setModel(modelo);
          
          sql="SELECT * FROM producto;";
          String []datos = new String[5];
          Statement st;
            try{
            
            st = objetoconexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                modelo.addRow(datos);   
                        }
            paramTablaTotalProductos.setModel(modelo);
            
            
            }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "El error es el siguiente "+e.toString());
            }
          
          
    
    
    }
    public void SeleccionarAlumno(JTable paramTablaProductos, JTextField paramCodigo, JTextField paramNombre, JTextField paramPrecio, JTextField paramCantidad, JTextField paramFechaVenc){
        try{
            int fila = paramTablaProductos.getSelectedRow();
            if (fila>=0) {
                paramCodigo.setText((paramTablaProductos.getValueAt(fila, 0).toString()));
                paramNombre.setText((paramTablaProductos.getValueAt(fila, 1).toString()));
                paramPrecio.setText((paramTablaProductos.getValueAt(fila, 2).toString()));
                paramCantidad.setText((paramTablaProductos.getValueAt(fila, 3).toString()));
                paramFechaVenc.setText((paramTablaProductos.getValueAt(fila, 4).toString()));
                
            }
            else{
            
            JOptionPane.showMessageDialog(null, "Fila NO seleccionada");
            }
        
        }catch(Exception e){
        
        JOptionPane.showMessageDialog(null, "error de seleccion "+e.toString());
        }
    
    }
    
    public void modificar(JTextField paramCodigo,JTextField paramNombre,JTextField paramPrecio, JTextField paramCantidad, JTextField paramFechaVenc){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombre(paramNombre.getText());
        setPrecio(Integer.parseInt(paramPrecio.getText()));
        setCantidad(Integer.parseInt(paramCantidad.getText()));
        setFecha(paramFechaVenc.getText());
        
        conexion objetoconexion = new conexion();
        String consulta = "UPDATE producto set nombreProducto =?, precioUnitario = ?,cantidadProducto = ?, fechaVencimiento = ? where codigoProducto=?";
       // String consulta = "UPDATE producto SET producto.nombreProducto =?, producto.precioUnitario = ?, producto.cantidadProducto= ?, producto.fechaVencimiento = ?, WHERE producto.codigoProducto = ?;";
        try{
            CallableStatement cs = objetoconexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, getNombre());
            cs.setFloat(2, getPrecio());
            cs.setInt(3, getCantidad());
            cs.setString(4, getFecha());
            cs.setInt(5, getCodigo());
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "Modificaion Exitosa");
        
        }catch(SQLException e){
        
        JOptionPane.showMessageDialog(null, "El error es el siguiente "+e.toString());
        }
        
    }
    
    public void eliminar(JTextField paramCodigo){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        conexion objetoconexion = new conexion();
        
        String consulta ="DELETE FROM producto where codigoProducto = ?;";

        try {
        CallableStatement cs = objetoconexion.estableceConexion().prepareCall(consulta);
        cs.setInt(1, getCodigo());
        cs.execute();
        JOptionPane.showMessageDialog(null,"Se eliminó correctamente el registro");
        
        }catch(Exception e){
        JOptionPane.showMessageDialog(null,"El error es el siguiente "+ e.toString());
        }
               
    
    }
   public void generarPDF() throws SQLException{
       conexion objetoconexion = new conexion();
       Document documento = new Document();
        try {
            PdfWriter.getInstance(documento, new FileOutputStream("C:/Users/jorge/OneDrive/Escritorio/caiman.pdf"));
            documento.open();

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("ID");
            tabla.addCell("NOMBRE");
            tabla.addCell("PRECIO");
            tabla.addCell("CANTIDAD");
            tabla.addCell("VENCIMIENTO");
            
            Connection conectar = objetoconexion.estableceConexion();
            PreparedStatement seleccionar = conectar.prepareStatement("SELECT * FROM producto");
            ResultSet consultar = seleccionar.executeQuery();
             if (consultar.next()) {
              do {
                  tabla.addCell(consultar.getString(1));
                  tabla.addCell(consultar.getString(2));
                  tabla.addCell(consultar.getString(3));
                  tabla.addCell(consultar.getString(4));
                  tabla.addCell(consultar.getString(5));
              } while (consultar.next());
              documento.add(tabla);
              
          }
            
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println("Error en la generacion del PDF");

        }
        documento.close();
        JOptionPane.showMessageDialog(null, "Documento creado con exito");

   }
   
   public void Buscar (JTextField paramNombre, JTable paramTablaBuscar){
       String sql="";
       sql="SELECT * FROM producto where nombreProducto = (?);";
       conexion objetoconexion = new conexion();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        
          paramTablaBuscar.setRowSorter(OrdenarTabla);
          
          
          modelo.addColumn("ID");
          modelo.addColumn("Nombre");
          modelo.addColumn("Precio");
          modelo.addColumn("Cantidad");
          modelo.addColumn("FechaVencimiento");
          
          paramTablaBuscar.setModel(modelo);
          
          
          String []datos = new String[5];
          Statement st;
            try{
           CallableStatement cs = objetoconexion.estableceConexion().prepareCall(sql);
              cs.setString(1, paramNombre.getText());
              cs.execute();
              ResultSet rs = cs.executeQuery();
              
              
              if(rs.next()){
                JOptionPane.showMessageDialog(null, "Registro Encontrado");
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                modelo.addRow(datos);  
                paramTablaBuscar.setModel(modelo);
                        }else{
                  JOptionPane.showMessageDialog(null, "Registro Encontrado");
              
              
              }
            
            
            
            }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, "El error es el siguiente"+e.toString());
            }
   }

}
