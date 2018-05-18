/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.json;

import grupo15.portalempleo.entities.Usuario;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Admin
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioWriter implements MessageBodyWriter<Usuario> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Usuario.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Usuario t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Usuario t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        String fecha = null;

        if (t.getFechaNacimiento() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            fecha = format.format(t.getFechaNacimiento());
        }

        JsonGenerator gen = Json.createGenerator(entityStream);
        gen.writeStartObject()
                .write("telefono", t.getTelefono())
                .write("fechaNacimiento", fecha)
                .write("tarjeta", t.getTarjeta())
                .write("nombre", t.getNombre())
                .write("email", t.getEmail())
                .write("pass", t.getPass())
                .write("tipo", t.getTipo())
                .writeEnd();
        gen.flush();
    }

}
