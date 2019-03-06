/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potree.BTreeMais;

/**
 *
 * @author Rafael Delanhese
 */
public class No implements INBMais{
    private int [] vInfo;
    private No [] vLig;
    private int TL;
    private No prox;
    private No ant;
    
    public No(){
        this.vInfo = new int [N];
        this.vLig = new No [N + 1];
        prox = null;
        ant = null;
    }
    
    public No(int pos, int info){
        this();
        vInfo[pos] = info;        
        TL = 1;
    }

     public int getvInfo(int pos) {
        return vInfo[pos];
    }

    public void setvInfo(int pos, int info) {
        this.vInfo[pos] = info;
    }

    public No getvLig(int pos) {
        return vLig[pos];
    }

    public void setvLig(int pos, No info) {
        this.vLig[pos] = info;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }
        
    public void remanejar(int pos) {
        //vLig[TL + 1] = vLig[TL];
        for (int i = TL - 1 ; i > pos; i--) {
            vInfo[i] = vInfo[i - 1];           
            vLig[i] = vLig[i - 1];
        }
    }
    
    public int buscar(int info) {
        int i = 0;
        while (i < TL && info > vInfo[i]) {
            i++;
        }
        return i;
    }
}
