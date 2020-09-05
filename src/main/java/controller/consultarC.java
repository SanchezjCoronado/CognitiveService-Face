/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.consularD;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.consultaM;

/**
 *
 * @author deivy
 */
@Named(value = "consultarC")
@SessionScoped
public class consultarC implements Serializable{
    
     private String urlImage;
    private String resultado;
    private consularD dao = new consularD();
    consultaM modelo = new consultaM();

    public void consultar() throws Exception {
        try {
            modelo = dao.consultar(urlImage);
            modelo.setLINK(urlImage);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTA", "EXITOSA"));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "URL", "INCORRECTA"));
            throw ex;
        }
    }

    public void clear() throws Exception {
        try {
            modelo = new consultaM();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "LIMPIADO", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", null));
            throw e;
        }
    }


    public consultaM getModelo() {
        return modelo;
    }

    public void setModelo(consultaM modelo) {
        this.modelo = modelo;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public consularD getDao() {
        return dao;
    }

    public void setDao(consularD dao) {
        this.dao = dao;
    }
    
    
    
}
