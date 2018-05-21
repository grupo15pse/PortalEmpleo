/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.Formacion;
import grupo15.portalempleo.entities.Grupo;
import grupo15.portalempleo.entities.Usuario;
import grupo15.portalempleo.jaas.UserEJB;
import grupo15.portalempleo.json.GrupoWriter;
import grupo15.portalempleo.json.UsuarioWriter;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Admin
 */
@Named(value = "usuarioClientBean")
@RequestScoped
public class UsuarioClientBean {

    Client client;
    WebTarget target;

    @Inject
    private UserEJB userEJB;

    /**
     * Creates a new instance of UsuarioClientBean
     */
    public UsuarioClientBean() {
    }

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();

    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public void addCandidato(Usuario candidato, Grupo grupo, Formacion formacion) {

        if (userEJB.findByEmail(candidato.getEmail()) == null) {
            target = client.target("http://localhost:8080/PortalEmpleo/webresources/usuario");
            target.register(UsuarioWriter.class).request().post(Entity.entity(candidato, MediaType.APPLICATION_JSON));

            target = client.target("http://localhost:8080/PortalEmpleo/webresources/grupo");
            target.register(GrupoWriter.class).request().post(Entity.entity(grupo, MediaType.APPLICATION_JSON));

            formacion.getFormacionPK().setCandidato(userEJB.findByEmail(candidato.getEmail()).getUsuarioId());

            target = client.target("http://localhost:8080/PortalEmpleo/webresources/formacion");
            target.request().post(Entity.entity(formacion, MediaType.APPLICATION_JSON));

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Éxito", "El candidato " + candidato.getNombre() + " ha sido insertado correctamente."));
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Pruebe con otro correo."));
            System.out.println("Pruebe con otro correo.");
        }

    }

}
