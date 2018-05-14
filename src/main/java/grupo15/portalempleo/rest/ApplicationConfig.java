/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Admin
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(grupo15.portalempleo.json.EmpresaReader.class);
        resources.add(grupo15.portalempleo.json.EmpresaWriter.class);
        resources.add(grupo15.portalempleo.json.EntidadPagoReader.class);
        resources.add(grupo15.portalempleo.json.GrupoWriter.class);
        resources.add(grupo15.portalempleo.json.OfertaReader.class);
        resources.add(grupo15.portalempleo.json.OfertaWriter.class);
        resources.add(grupo15.portalempleo.json.UsuarioReader.class);
        resources.add(grupo15.portalempleo.json.UsuarioWriter.class);
        resources.add(grupo15.portalempleo.rest.FormacionFacadeREST.class);
        resources.add(grupo15.portalempleo.rest.GrupoFacadeREST.class);
        resources.add(grupo15.portalempleo.rest.OfertaFacadeREST.class);
        resources.add(grupo15.portalempleo.rest.PresentarFacadeREST.class);
        resources.add(grupo15.portalempleo.rest.UsuarioFacadeREST.class);
    }
    
}
