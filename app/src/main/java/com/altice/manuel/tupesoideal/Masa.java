package com.altice.manuel.tupesoideal;

/**
 * Created by Manuel on 8/7/2017.
 * Esta clase sirve es una plantilla que guardará la masa en kgs y en libras
 */

class Masa {
    private double masaEnLibras;
    private double masaEnKgs;

    Masa(){
        masaEnLibras = 0.0;
        masaEnKgs = 0.0;
    }

    //Esta funcion modifica el valor de la masa dado un valor de la masa en libras
    void setMasaEnLibras(double masaEnLibras) {
        this.masaEnLibras = masaEnLibras;
        this.masaEnKgs = masaEnLibras*0.4536;
    }

    //Esta funcion modifica el valor de la masa dado un valor de la masa en kgs
    void setMasaEnKgs(double masaEnKgs){
        this.masaEnKgs = masaEnKgs;
        this.masaEnLibras = masaEnKgs/0.4536;
    }

    double getMasaEnKgs() {
        return masaEnKgs;
    }

    public double getMasaEnLibras() {
        return masaEnLibras;
    }
}
