package Hilos;

import modelo.EventoUniversitarios;
import modelo.Inscripcion;
import modelo.actividades.Actividad;

public class EnvioTicketsThreads extends Thread{

    private EventoUniversitarios evento;
    public EnvioTicketsThreads (EventoUniversitarios evento){
        super("Hilo de envio de Tickets");
        this.evento = evento;
    }
    @Override
    public void run(){
        System.out.println(getName() + " Inicio del encio de tickets ");
        for (Actividad actividad: evento.getActividades()){
            for(Inscripcion inscripcion: actividad.getIncripciones()){
                if("CONFIRMADA".equals(inscripcion.getEstado())){
                    inscripcion.getTicket().enviarTicket();
                    try{
                        Thread.sleep(500);

                    }catch (InterruptedException e){
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        System.out.println(getName() + " FIn del envio de Tickets");
    }
}
