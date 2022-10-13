import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.CompactNumberFormat;
import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder constructor = factory.newDocumentBuilder();
            Document doc = constructor.parse("src\\compras.xml");

            NodeList listaCompras = doc.getElementsByTagName("compra");

            recorrerNodo(listaCompras);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }

    private static void recorrerNodo(NodeList listaNodos) {
double precioSupp = 0;
        for (int i = 0; i < listaNodos.getLength(); i++) {

            Node nodoCompra = listaNodos.item(i);
            if (nodoCompra.getNodeType() == Node.ELEMENT_NODE &&
                    (nodoCompra.getNodeName().equals("compra")
                    )) {
                Element compra = (Element) nodoCompra;
                Compra objCompra = new Compra();
                NodeList listaTickets = compra.getChildNodes();
                for (int j = 0; j < listaTickets.getLength(); j++) {
                    Node nodoticket = listaTickets.item(j);
                    if (nodoticket.getNodeType() == Node.ELEMENT_NODE) {
                        if (nodoticket.getNodeName().equals("fecha")) {
                            objCompra.setFecha(nodoticket.getTextContent());
                        } else
                        if (nodoticket.getNodeName().equals("ticket")) {
                            NodeList listaproductos = nodoticket.getChildNodes();
                            for (int k = 0; k < listaproductos.getLength(); k++) {
                                Node nodoProducto = listaproductos.item(k);
                                if (nodoProducto.getNodeType() == Node.ELEMENT_NODE) {
                                    Element producto = (Element) nodoProducto;
                                    if (nodoProducto.getNodeName().equals("producto")) {
                                        objCompra.aumentarCantidadProductos();
                                        NodeList listaPrecios = producto.getChildNodes();
                                        for (int l = 0; l < listaPrecios.getLength(); l++) {
                                            Node precio = listaPrecios.item(l);

                                            if(precio.getNodeType() == Node.ELEMENT_NODE){
                                                Element precios = (Element) listaPrecios.item(l);
                                                if(precio.getNodeName().equals("precio_unidad")){
                                                    precioSupp = Double.parseDouble(precios.getTextContent().replace(",", "."));
                                                    objCompra.aumentarValorTotal(precioSupp);
                                                }if(precio.getNodeName().equals("unidades")){
                                                    int p = 1;
                                                    while(p < Double.parseDouble(precio.getTextContent().replace(",","."))){
                                                        objCompra.aumentarValorTotal(precioSupp);
                                                        p++;
                                                        if(p>=1){

                                                            objCompra.aumentarCantidadProductos();
                                                        }
                                                    }
                                                    precioSupp = 0;
                                                }else if (precio.getNodeName().equals("descuento")){
                                                    objCompra.sumarDescuento(Double.valueOf(precio.getTextContent().replace(",",".")));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                System.out.println("========================================");
                System.out.println("========================================");
                System.out.println("========================================");
                System.out.println("Fecha: " + objCompra.getFecha());
                System.out.println("========================================");

                System.out.println("Cantidad total de Productos: " + objCompra.getCantidadProductos());
                System.out.println("Precio Total: " +  Math.round(objCompra.getValorTotal()*100.0)/100.0+ "€");
                System.out.println("Descuento Total: " + Math.round(objCompra.getTotalDescuentos()*100.0)/100.0+ "€");
                Double descuento = objCompra.getValorTotal() - objCompra.getTotalDescuentos();
                System.out.println("========================================");

                System.out.println("Precio Final: " + Math.round(descuento*100.0)/100.0 + "€");
                System.out.println("========================================");
            }

        }
    }

}