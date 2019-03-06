/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potree.BTree;

/**
 *
 * @author Aluno
 */
public class No implements IN {

    private int[] vInfo;
    private int[] vPos;
    private No[] vLig;
    private int TL;

    public No() {
        this.vInfo = new int[2 * N + 1];
        this.vPos = new int[2 * N + 1];
        this.vLig = new No[2 * N + 2];
        this.TL = 0;
    }

    public No(int info, int posArq) {
        this();
        vInfo[0] = info;
        vPos[0] = posArq;
        TL = 1;
    }

    public int buscar(int info) {
        int i = 0;
        while (i < TL && info > vInfo[i]) {
            i++;
        }
        return i;
    }

    public void remanejar(int pos) {
        vLig[TL + 1] = vLig[TL];
        for (int i = TL; i > pos; i--) {
            vInfo[i] = vInfo[i - 1];
            vPos[i] = vPos[i - 1];
            vLig[i] = vLig[i - 1];
        }
    }

    public void remanejaExcluir(int pos) {        
        while (pos < TL - 1) {
            vInfo[pos] = vInfo[pos + 1];
            vLig[pos] = vLig[pos + 1];
            pos++;
        }
        vInfo[pos] = vInfo[pos + 1];
        vLig[pos] = vLig[pos + 1];
        TL--;
    }

    public int getvInfo(int pos) {
        return vInfo[pos];
    }

    public void setvInfo(int pos, int info) {
        this.vInfo[pos] = info;
    }

    public int getvPos(int pos) {
        return vPos[pos];
    }

    public void setvPos(int pos, int info) {
        this.vPos[pos] = info;
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
}
