package controller.algorithm;

import controller.servlet.utils.ProcessPoiState;
import controller.servlet.NotifyingThread;
import model.dao.AmenitiesRegionDaoImpl;
import model.dao.PoiDaoImpl;
import model.dao.PoiProcessDaoImpl;
import model.dao.WayDaoImpl;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.data.Poi;
import model.data.Way;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Clase AmenityProcessing, permite procesar un fichero XML que contenga amenities 
 * (nodos o caminos) e introducirlos
 * en las tablas correspondientes de la Base de Datos.
 * @author David Moreno del Hoyo.
 * @version 1.0
 */
public class AmenityProcessing extends NotifyingThread{
    
    /**
     * Ruta a los ficheros a procesar.
     */
    private final String path;
    
    /**
     * Nombre de la región.
     */
    private final String regionName;
    
    /**
     * Identificador de la región.
     */
    private double regionId;
    
    /**
     * Objeto de tipo AmenityDao que permitirá insertar amenities (nodos)
     * en la Base de Datos.
     */
    private final PoiDaoImpl pDao;
    
    /**
     * Objeto de tipo WayDao que permitirá insertar caminos
     * en la Base de Datos.
     */    
    private final WayDaoImpl wDao;
    
    /**
     * Constructor de clase.
     * @param path la ruta a los ficheros a procesar
     * @param regionName el nombre de la región a la que pertenecen las amenities.
     */
    public AmenityProcessing(String path, String regionName){
        this.path = path;
        pDao = new PoiDaoImpl();
        wDao = new WayDaoImpl();
        this.regionName = regionName;
    }

    /**
     * Método que permite leer los nodos de un fichero xml con 
     * las amenities obtenidas mediante la herramienta osmfilter.
     * Se filtrarán nodos y caminos.
     */
    public void process(){
        
        boolean processingNodes = false;
        boolean processingWays = false;
        
        PoiProcessDaoImpl pPDao = new PoiProcessDaoImpl();
        
        // Creación de la región.
        AmenitiesRegionDaoImpl aRDao = new AmenitiesRegionDaoImpl();
        regionId = aRDao.create(regionName);
        
        // Obtención de las posibles carpetas de usuarios.
        File file = new File(path);
        Logger.getLogger(AmenityProcessing.class.getName()).log(Level.INFO, "Processing file: {0}", file.getName());
        DocumentBuilderFactory miFactory = DocumentBuilderFactory.newInstance();
        
        try {
            
            pPDao.updatePoiProcess(ProcessPoiState.FILE_READING);
            
            DocumentBuilder miBuilder = miFactory.newDocumentBuilder();
            Document documento = miBuilder.parse(file);
            documento.getDocumentElement().normalize();

            // Obtención de todos los nodos "nodes" y de todos los "ways".
            List<NodeList> nodeList = new ArrayList();
            nodeList.add(documento.getElementsByTagName("node"));
            Logger.getLogger(AmenityProcessing.class.getName()).log(Level.INFO, "Number of nodes to process: {0}", nodeList.get(0).getLength());
            nodeList.add(documento.getElementsByTagName("way"));
            Logger.getLogger(AmenityProcessing.class.getName()).log(Level.INFO, "Number of ways to process: {0}", nodeList.get(1).getLength());
            Logger.getLogger(AmenityProcessing.class.getName()).log(Level.INFO, "All nodes added.");
            
            // Procesado de cada uno de los nodos leídos.
            for(NodeList nodes:nodeList){
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    switch(node.getNodeName()){
                        case "node":
                            if(!processingNodes){
                                pPDao.updatePoiProcess(ProcessPoiState.NODE_PROCESSING);
                            }
                            processingNodes = true;
                            processNode((Element)node);
                            break;
                        case "way":
                            if(!processingWays){
                                pPDao.updatePoiProcess(ProcessPoiState.WAY_PROCESSING);
                            }
                            processingWays = true;
                            processWay((Element)node);
                            break;
                    }
                }
            }
            Logger.getLogger(AmenityProcessing.class.getName()).log(Level.INFO, "Updating geometry of nodes.");
            pDao.updateTheGeom();
            Logger.getLogger(AmenityProcessing.class.getName()).log(Level.INFO, "All nodes processed.");
        }
        catch(IOException | ParserConfigurationException | SAXException e){
            Logger.getLogger(AmenityProcessing.class.getName()).log(Level.SEVERE, "Failed while processing xml file {0}", e.getMessage());
        }
        pPDao.updatePoiProcess(ProcessPoiState.PROCESS_ENDED);
        
    }

    /**
     * Método que permite procesar un nodo del fichero XML. Este nodo contendrá un identificador único,
     * una localización geográfica y una lista de tags.
     * @param node el nodo a procesar.
     */
    public void processNode(Element node){
        
        BigDecimal id = new BigDecimal(node.getAttributeNode("id").getValue());
        double latitude = Double.parseDouble(node.getAttributeNode("lat").getValue());
        double longitude = Double.parseDouble(node.getAttributeNode("lon").getValue());
        String name = "";
        String amenity = "";
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i<nodeList.getLength(); i++){
            if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
                if(nodeList.item(i).getNodeName().equals("tag")){
                    if(((Element)nodeList.item(i)).getAttributeNode("k").getValue().equals("name") || ((Element)nodeList.item(i)).getAttributeNode("k").getValue().equals("name:en")){
                        name = ((Element)nodeList.item(i)).getAttributeNode("v").getValue();
                    }
                    if(((Element)nodeList.item(i)).getAttributeNode("k").getValue().equals("amenity")){
                        amenity = ((Element)nodeList.item(i)).getAttributeNode("v").getValue();
                    }
                }
            }
        }
        
        //Inserción en la Base de Datos del nodo procesado.
        pDao.insertPoi(new Poi(id, latitude, longitude, name, amenity, regionId));
    }

    /**
     * Método processWay, este método permite procesar un camino almacenando su identificador, los nodos que 
     * tiene asociados y las etiquetas.
     * @param node el nodo a procesar.
     */
    public void processWay(Element node){

        ArrayList<BigDecimal> aNodeIds = new ArrayList();
        NodeList nodeList = node.getChildNodes();
        String amenity = "";
        double id = Double.parseDouble(node.getAttributeNode("id").getValue());

        for(int i = 0; i<nodeList.getLength(); i++){
            if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
                // Generación del array de nodos a los que referencia.
                if(nodeList.item(i).getNodeName().equals("nd")){
                    String ndRef = (((Element)nodeList.item(i)).getAttributeNode("ref").getValue());
                    aNodeIds.add(new BigDecimal(ndRef));
                }
                if(nodeList.item(i).getNodeName().equals("tag")){
                    if(((Element)nodeList.item(i)).getAttributeNode("k").getValue().equals("amenity")){
                        amenity = ((Element)nodeList.item(i)).getAttributeNode("v").getValue();
                    }
                }
            }
        }
        
        // Inserción en la Base de Datos del way procesado.
        wDao.insertWay(new Way(id, aNodeIds, amenity, regionId));
    }

    /**
     * non-javadoc
     */
    @Override
    public void doRun() {
        process(); 
    }

}
