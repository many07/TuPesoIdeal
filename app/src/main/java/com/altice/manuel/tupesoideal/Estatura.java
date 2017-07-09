package com.altice.manuel.tupesoideal;

/**
 * Created by Manuel on 8/7/2017.
 * Esta clase sirve es una plantilla que guardará la estatura en pies y en metros
 */

class Estatura {
    private double estaturaEnPies;
    private double estaturaEnMetros;

    Estatura(){
        estaturaEnPies = 0.0;
        estaturaEnMetros = 0.0;
    }

    //Esta clase modifica el valor de la estatura dada la estatura en metros
    void setEstaturaEnMetros(double estaturaEnMetros) {
        this.estaturaEnMetros = estaturaEnMetros;
        this.estaturaEnPies = estaturaEnMetros/0.0254;
    }
    //Esta clase modifica el valor de la estatura dada la estatura en pies
    void setEstaturaEnPies(double estatura){
        this.estaturaEnPies = estatura;
        this.estaturaEnMetros = this.estaturaEnPies*0.0254;
    }

    void setEstaturaEnPies(int pies, int pulgadas){
        this.estaturaEnPies = pies*12 + pulgadas;
        this.estaturaEnMetros = this.estaturaEnPies*0.0254;
    }

    public double getEstaturaEnPies(){
        return this.estaturaEnPies;
    }
    double getEstaturaEnMetros(){
        return this.estaturaEnMetros;
    }
}
