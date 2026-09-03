package modelo.actividades;

public class Taller extends Actividad {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook){
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales(){
        return requiereNotebook ? 5000 : 2000;
    }

    public void setRequiereNotebook( boolean rn){
        this.requiereNotebook = rn;
    }
    public boolean getRequiereNotebook(){ return this.requiereNotebook;}
}
