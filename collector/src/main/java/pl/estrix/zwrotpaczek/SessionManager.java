package pl.estrix.zwrotpaczek;

import com.gluonhq.charm.down.common.PlatformFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import pl.estrix.zwrotpaczek.dto.ShipmentDetailsDto;
import pl.estrix.zwrotpaczek.dto.ShipmentListItemDto;
import pl.estrix.zwrotpaczek.dto.ShipmentProductDto;

import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SessionManager {

    public static Map<String,String> properties = new ConcurrentHashMap<>();
    public static Map<String,String> customsProperties = new ConcurrentHashMap<>();
    public final String SESSION_PROPERTIES_FILENAME;
    private File path;
    private final Properties props = new Properties();

    private static ShipmentListItemDto shipmentListItemDtoList;

    private static ShipmentDetailsDto shipmentDetailsDto;

    private String screenResolution;

    private static SessionManager instance = null;

    private static String SERVICE_URL = "http://192.168.11.2";
//    private static String SERVICE_URL = "http://192.168.1.100";
//    private static String SERVICE_PORT = "8080";
//    private static String SERVICE_URL = "http://localhost";
    private static String SERVICE_PORT = "8080";
//    private static String SERVICE_URL = "http://paczki.e-strix.com";
//    private static String SERVICE_PORT = "8016";
    private static String SERVICE_SESSION = "";

    public static BooleanProperty connectionStatus = new SimpleBooleanProperty(Boolean.FALSE);

    public SessionManager() {
        try {
            path = PlatformFactory.getPlatform().getPrivateStorage();
        } catch (IOException e) {
            String tmp = System.getProperty("java.io.tmpdir");
            path = new File(tmp);
        }
        this.SESSION_PROPERTIES_FILENAME = "returnPkg_setting.properties";
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void saveSession() {
        try {
            for (Map.Entry<String, String> entry : customsProperties.entrySet()) {
                props.setProperty(entry.getKey(), entry.getValue());
            }
            File file=new File(path,SESSION_PROPERTIES_FILENAME);
            props.store(new FileWriter(file), SESSION_PROPERTIES_FILENAME);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int restoreSession() {
        Reader reader = null;
        try {
            File file=new File(path,SESSION_PROPERTIES_FILENAME);
            reader = new FileReader(file);
            props.load(reader);

            for ( Map.Entry entry : props.entrySet()){
                if(!customsProperties.containsKey(entry.getKey())) {
                    customsProperties.put(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        } catch (FileNotFoundException ignored) {
            if(!customsProperties.containsKey("serverAddress")) {
                customsProperties.put("serverAddress", SERVICE_URL);
            }
            if(!customsProperties.containsKey("serverPort")) {
                customsProperties.put("serverPort", SERVICE_PORT);
            }
            if(!customsProperties.containsKey("collectorId")) {
                customsProperties.put("collectorId", "0");
            }
            return -1;
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public void loadProperties(String path) {
        try {
            final Properties prop = new Properties();
            try (final InputStream stream = AppMain.class.getResourceAsStream(path)) {
                prop.load(stream);
            }

            for (Map.Entry entry : prop.entrySet()){
                properties.put(entry.getKey().toString(),entry.getValue().toString());
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public ShipmentListItemDto getShipmentListItemDtoList() {
        return shipmentListItemDtoList;
    }

    public void setShipmentListItemDtoList(ShipmentListItemDto shipmentListItemDtoList) {
        this.shipmentListItemDtoList = shipmentListItemDtoList;
    }

    public ShipmentDetailsDto getShipmentDetailsDto() {
        return shipmentDetailsDto;
    }
    public void addShipmentDetailsDto(ShipmentDetailsDto shipmentDetailsDto) {
        if (this.shipmentDetailsDto == null){
            this.shipmentDetailsDto = shipmentDetailsDto;
        }
        this.shipmentDetailsDto.getShipmentProductDtoList().addAll(shipmentDetailsDto.getShipmentProductDtoList());
    }

    public void setShipmentDetailsDto(ShipmentDetailsDto shipmentDetailsDto) {
        this.shipmentDetailsDto = shipmentDetailsDto;
    }

    public static String getServiceSession() {
        return SERVICE_SESSION;
    }

    public static void setServiceSession(String serviceSession) {
        SERVICE_SESSION = serviceSession;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public void setKeyToMap(String key, String value){
        customsProperties.put(key, value);
        saveSession();
    }

    public ShipmentProductDto findShipmentProduct(String ean){
        for (ShipmentProductDto shipmentProductDto : shipmentDetailsDto.getShipmentProductDtoList()){
            if (shipmentProductDto.getEan().equals(ean)){
                return shipmentProductDto;
            }
        }
        return null;
    }

    public void updateShipmentProduct(ShipmentProductDto shipmentProductDto){
        int i =0;
        for (ShipmentProductDto tmp : shipmentDetailsDto.getShipmentProductDtoList()){
            if (shipmentProductDto.getEan().equals(tmp.getEan())){
                shipmentProductDto.setUpdate(new Date());
                shipmentDetailsDto.getShipmentProductDtoList().set( i, shipmentProductDto );
            }
            i++;
        }
    }

    public static Map<String, String> getProperties() {
        return properties;
    }

    public static void setProperties(Map<String, String> properties) {
        SessionManager.properties = properties;
    }

    public static String getServiceUrl() {
        return SERVICE_URL;
    }

    public static void setServiceUrl(String serviceUrl) {
        SERVICE_URL = serviceUrl;
    }

    public static String getServicePort() {
        return SERVICE_PORT;
    }

    public static void setServicePort(String servicePort) {
        SERVICE_PORT = servicePort;
    }
}
