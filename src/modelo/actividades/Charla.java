package modelo.actividades;

public class Charla extends Actividad {

    private String disertante;

    public Charla (int id, String titulo, int cupoMaximo, String disertante){
        super(id, titulo, cupoMaximo);
        this.disertante = disertante;
    }
    @Override
    public double calcularCostoMateriales(){
        return 0;
    }



    public void setDisertante(String disertante){
        this.disertante = disertante;
    }
    public String getDisertante (){
        return this.disertante;
    }
}
