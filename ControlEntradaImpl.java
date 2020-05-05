package es.upm.dit.adsw.practica4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControlEntradaImpl implements ControlEntrada {
	
	private Vehiculo v;
	private Tramo tramo_i;
	private volatile boolean ocupado;
	private volatile boolean fuera;
	private long tiempo;
	private Thread dentro = null;
	private List<Vehiculo> v_entrantes;
	
	public long entrarEnTramo() {
		dentro = Thread.currentThread();
		List<Vehiculo> v_entrantes = new ArrayList<>();
		v_entrantes.add(this.v);
		long inicio = System.currentTimeMillis();

		for(Vehiculo vh : v_entrantes) {
		if(vh.getPos() == this.v.getPos()) {
		try {
			wait();
			long fin = System.currentTimeMillis();
			tiempo = ((fin - inicio)/1000);
			ocupado = true;
			this.notifyAll();
		}		
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return tiempo;
		}
		}
		ocupado = false;
		fuera = false;
		return -1;
	}
	
	@Override
	public void salirDeTramo() {
		if (Thread.currentThread()!=dentro) {
			throw new RuntimeException();	
	}
		else fuera = true;
	}
}
