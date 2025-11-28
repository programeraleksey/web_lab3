package org.example;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@ViewScoped
public class TimeBean implements Serializable {

    private String currentTime;

    @PostConstruct
    public void init() {
        updateTime();
    }

    public void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        currentTime = sdf.format(new Date());
    }

    public String getCurrentTime() {
        return currentTime;
    }
}
