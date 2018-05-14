/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.json;

import grupo15.portalempleo.entities.EntidadPago;
import grupo15.portalempleo.entities.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Admin
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class EntidadPagoReader implements MessageBodyReader<EntidadPago>{

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return EntidadPago.class.isAssignableFrom(type);
    }

    @Override
    public EntidadPago readFrom(Class<EntidadPago> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        EntidadPago entidadPago = new EntidadPago();
        JsonParser parser = Json.createParser(entityStream);
        while (parser.hasNext()) {
            switch (parser.next()) {
                case KEY_NAME:
                    String key = parser.getString();
                    parser.next();
                    switch (key) {
                        case "email":
                            entidadPago.setEmail(parser.getString());
                            break;
                        case "estadopago":
                            entidadPago.setEstadoPago(parser.getString());
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        
        System.out.println("JSON " + entidadPago.getEmail());
        System.out.println("JSON " + entidadPago.getEstadoPago());
        return entidadPago;
    }
    
}
