package com.enrique.listusers;

public class Users {

    String UID, NOMBRES, APELLIDOS, CORREO, PASSWORD;
    int EDAD;

    public Users() {
    }

    public Users(String UID, String NOMBRES, String APELLIDOS, String CORREO, String PASSWORD, int EDAD) {
        this.UID = UID;
        this.NOMBRES = NOMBRES;
        this.APELLIDOS = APELLIDOS;
        this.CORREO = CORREO;
        this.PASSWORD = PASSWORD;
        this.EDAD = EDAD;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNOMBRES() {
        return NOMBRES;
    }

    public void setNOMBRES(String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }

    public String getAPELLIDOS() {
        return APELLIDOS;
    }

    public void setAPELLIDOS(String APELLIDOS) {
        this.APELLIDOS = APELLIDOS;
    }

    public String getCORREO() {
        return CORREO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public int getEDAD() {
        return EDAD;
    }

    public void setEDAD(int EDAD) {
        this.EDAD = EDAD;
    }
}
