package org.example.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("navBean")
@SessionScoped
public class NavigationBean implements Serializable {

    public String goToMain() {
        return "main.xhtml?faces-redirect=true";
    }

    public String backToIndex() {
        return "index.xhtml?faces-redirect=true";
    }
}

