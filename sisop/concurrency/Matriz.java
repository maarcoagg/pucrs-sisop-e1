package sisop.concurrency;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class Matriz {
  
    private static int tam = 0;
    private static double[][] matriz;
  
    public Matriz() {
  
    }
  
    public Matriz(int t) {
      tam = t;
      initMatriz();    
    }
    
    //Gera a primeira matriz com valores inteiros aleatórios até 100
    public static void initMatriz(){
      Random r = new Random();
      matriz = new double[tam][tam];
  
      for(int i = 0; i < matriz.length; i++){
        for(int j = 0; j < matriz.length; j++){
          matriz[i][j] = r.nextInt(100);
        }
      }
    }
  
    public static double[][] getMatriz(){
      return matriz;
    }
  
    public static void setValue(int i, int j, double val){
        matriz[i][j] = val;
    }

    public static int getTam(){
      return tam;
    }
  
    //Retorna uma lista com todos os vizinhos da posição recebida por parâmetro
    public static List<Double> getNeighbors(int i, int j){
        List<Double> neighbors = new ArrayList<>();
        
        //Oeste
        try{
            neighbors.add((matriz[i][j - 1]));
        }
        catch (ArrayIndexOutOfBoundsException np){
        //System.out.println("Vizinho não existe");
        }

        //Norte
        try{
            neighbors.add(matriz[i - 1][j]);
        }
        catch (ArrayIndexOutOfBoundsException np){
        //System.out.println("Vizinho não existe");
        }

        //Leste
        try{
            neighbors.add(matriz[i][j + 1]);
        }
        catch (ArrayIndexOutOfBoundsException np){
        //System.out.println("Vizinho não existe");
        }
        
        //Sul
        try{
            neighbors.add(matriz[i + 1][j]);
        }
        catch (ArrayIndexOutOfBoundsException np){
        //System.out.println("Vizinho não existe");
        }
      return neighbors;
    }

    //Imprime a matriz pro usuário
    public static void printMatriz(){
        DecimalFormat df2 = new DecimalFormat("#.##");

        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz.length; j++){
                double val = matriz[i][j];
                System.out.print(df2.format(val)+"\t");
            }
            System.out.println("");
        }
    }  
}