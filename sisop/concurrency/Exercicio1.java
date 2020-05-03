/* 
   Resposta ao Exerc�cio 1 acima
   Exemplo de exclusao mutua com semaforo,
   exemplificado com contador compartilhado. 
   Disciplina:  Sistemas Operacionais
   PUCRS - Escola Politecnica
   Prof: Fernando Dotti
*/
package sisop.concurrency;

import java.util.concurrent.Semaphore;

class CounterSema {
	
	private  int n;    // precisa volatile ?
	private Semaphore s;  
	
	public CounterSema(){ 	
		n = 0;
        s = new Semaphore(1);	
	} 
		
	public void incr(int id){
		try {
			s.acquire(); 
			} catch (InterruptedException ie) {}  
        n++;   
		s.release();  
	} 	
	public int value() { return n; }
}

class CounterThread extends Thread {

	private int id;
    private CounterSema c_s;
	private int limit;

    public CounterThread(int _id, CounterSema _c_s, int _limit){
		id = _id;	
    	c_s = _c_s;
		limit = _limit;
    }

    public void run() {
       for (int i = 0; i < limit; i++) {
		     c_s.incr(id);    
      }
    }
}

class TesteSemaphore {
	public static void main(String[] args) {
	
	  int nrIncr = 100000;	
	  
	  CounterSema c = new CounterSema();
      CounterThread p = new CounterThread(0,c,nrIncr);
      CounterThread q = new CounterThread(1,c,nrIncr);
	  
      p.start();
      q.start();
      try { p.join(); q.join(); }
	  
      catch (InterruptedException e) { }
      System.out.println("The value of n is " + c.value());
    }
	
}
