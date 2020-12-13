package modelo;

public class Juego {

    private String uid;
    private String Nombre;
   private  String Armas;
    private String Vehiculo;

    public Juego() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getArmas() {
        return Armas;
    }

    public void setArmas(String armas) {
        Armas = armas;
    }

    public String getVehiculo() {
        return Vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        Vehiculo = vehiculo;
    }

    @Override
    public String toString() {
        return Nombre;

    }
}
