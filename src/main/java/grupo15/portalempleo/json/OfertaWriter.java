/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.json;

import grupo15.portalempleo.entities.Oferta;
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
public class OfertaWriter implements MessageBodyWriter<Oferta>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Oferta.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Oferta t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Oferta t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        
        String fecha = null;
        
        if (t.getFechaIncorp() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            fecha = format.format(t.getFechaIncorp());
        }
        
        JsonGenerator gen = Json.createGenerator(entityStream);
        gen.writeStartObject()
                .write("descripcion", t.getDescripcion())
                .write("empresa", t.getEmpresa())
                .write("fechaIcorp", fecha)
                .write("nombre",t.getNombre())
                .write("puestoTrabajo",t.getPuestoTrabajo())
                .write("reqMinimos", t.getReqMinimos())
                .writeEnd();
        gen.flush();
    }
    
}
