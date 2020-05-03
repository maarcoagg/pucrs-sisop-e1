package sisop.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class CalculaMedia  {

  private static class AverageTask implements Runnable {

    private CyclicBarrier barrier;
    private int i, j;
    private double media = 0;

    public AverageTask(CyclicBarrier barrier, int i, int j) {
        this.barrier = barrier;
        this.i = i;
        this.j = j;
    }

    @Override
    public void run() {        
      try {
        // Faz a media        
        media = Matriz.getNeighbors(i, j).stream().mapToDouble(val -> val).average().orElse(0.0);
        // Espera outras threads
        barrier.await();
        // Salva media
        Matriz.setValue(i, j, media);
      }         
      catch (InterruptedException ex) {
        Logger.getLogger(CalculaMedia.class.getName()).log(Level.SEVERE, null, ex);
      }         
      catch (BrokenBarrierException ex) {
        Logger.getLogger(CalculaMedia.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  public static void main(String args[]){
    int tam = 5, fases = 0;
    new Matriz(tam);

    // Print header
    System.out.println("\n╔═════════════════════════════════════════════════════════════════╗");
    System.out.println("║  Por: Guilherme Cavalheiro, Jamil Bannura e Marco Goedert.      ║");
    System.out.println("║  PUCRS - Pontifícia Universidade Católica do Rio Grande do Sul  ║");  
    System.out.println("║  Escola Politécnica - Sistemas Operacionais                     ║");
    System.out.println("╚═════════════════════════════════════════════════════════════════╝");

    // Get number of iterations
    Scanner sc = new Scanner(System.in);    
    System.out.print("\nQuantidade de fases: ");
    fases = sc.nextInt();
    sc.close();

    System.out.println("\nMatriz de origem: ");
    Matriz.printMatriz();
    
    for(int f = 0; f < fases; f++){
      List<Thread> threads = new ArrayList<>();

      // Start cyclic barrier
      final CyclicBarrier cb = new CyclicBarrier(tam*tam, new Runnable(){
        @Override
        public void run(){
            System.out.println("\nMédias da fase calculadas!");
        }
      });

      // Start all average threads 
      for (int i = 0; i < Matriz.getMatriz().length; i++){
        for (int j = 0; j < Matriz.getMatriz().length; j++){
          String threadLabel = ("Thread "+i+","+j);
          Thread t = new Thread(new AverageTask(cb, i, j), threadLabel);
          threads.add(t);
          t.start();
        }
      }

      // Join all threads
      for (Thread thread : threads) {
        try{
          thread.join();
        } catch(InterruptedException e){
          System.err.println("Thread interrompida: "+e);
        }
      }

      System.out.println("\nFase "+(f+1)+":");
      Matriz.printMatriz();
      
    }
  }
}