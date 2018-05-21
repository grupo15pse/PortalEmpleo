/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.json;

import grupo15.portalempleo.entities.Usuario;
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
public class UsuarioReader implements MessageBodyReader<Usuario> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Usuario.class.isAssignableFrom(type);
    }

    @Override
    public Usuario readFrom(Class<Usuario> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        Usuario user = new Usuario();
        JsonParser parser = Json.createParser(entityStream);
        while (parser.hasNext()) {
            switch (parser.next()) {
                case KEY_NAME:
                    String key = parser.getString();
                    parser.next();
                    switch (key) {
                        case "email":
                            user.setEmail(parser.getString());
                            break;
                        case "fechaNacimiento":
                            Date fecha = null;
                            try {
                                System.out.println("Parser " + parser.getString());

                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                fecha = formatter.parse(parser.getString());
                            } catch (ParseException ex) {
                                Logger.getLogger(UsuarioReader.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            user.setFechaNacimiento(fecha);
                            break;
                        case "nombre":
                            user.setNombre(parser.getString());
                            break;
                        case "pass":
                            user.setPass(parser.getString());
                            break;
                        case "tarjeta":
                            user.setTarjeta(parser.getBigDecimal().toBigInteger());
                            break;
                        case "telefono":
                            user.setTelefono(parser.getString());
                            break;
                        case "tipo":
                            user.setTipo(parser.getString());
                            break;
                        case "usuarioId":
                            user.setUsuarioId(parser.getInt());
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        return user;
    }

}
