/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.json;

import grupo15.portalempleo.entities.Oferta;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class OfertaReader implements MessageBodyReader<Oferta> {
    
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Oferta.class.isAssignableFrom(type);
    }
    
    @Override
    public Oferta readFrom(Class<Oferta> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        Oferta oferta = new Oferta();
        JsonParser parser = Json.createParser(entityStream);
        while (parser.hasNext()) {
            switch (parser.next()) {
                case KEY_NAME:
                    String key = parser.getString();
                    parser.next();
                    switch (key) {
                        case "descripcion":
                            oferta.setDescripcion(parser.getString());
                            break;
                        case "empresa":
                            oferta.setEmpresa(parser.getInt());
                            break;
                        case "fechaIncorp":
                            Date fecha = null;
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                             {
                                try {
                                    fecha = formatter.parse(parser.getString());
                                } catch (ParseException ex) {
                                    Logger.getLogger(UsuarioReader.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            oferta.setFechaIncorp(fecha);
                            break;
                        case "nombre":
                            oferta.setNombre(parser.getString());
                            break;
                        case "ofertaId":
                            oferta.setOfertaId(parser.getInt());
                            break;
                        case "puestoTrabajo":
                            oferta.setPuestoTrabajo(parser.getString());
                            break;
                        case "reqMinimos":
                            oferta.setReqMinimos(parser.getString());
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        return oferta;
    }
    
}
