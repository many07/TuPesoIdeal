package com.altice.manuel.tupesoideal;

/**
 * Created by Manuel on 8/7/2017.
 * Esta clase es el usuario que ingresa sus datos, aqui se calcula t-o-d-o lo necesitado y se guarda en una variable.
 */

class Usuario {
    private boolean masaEnLibras;
    private boolean estaturaEnPies;
    private Masa masa;
    private Estatura estatura;
    private double minimoRecomendado;
    private double maximoRecomendado;
    private double imc;
    private int condicion;
    private int consejo;
    private String unidad;

    Usuario(){
        masaEnLibras = false;
        estaturaEnPies = false;
        masa = new Masa();
        estatura = new Estatura();
    }



    void calcularResultados(double gmasa, double gestarura, boolean gmasaEnLibras, boolean gestaturaEnPies){
        setEstaturaEnPies(gestaturaEnPies);
        setMasaEnLibras(gmasaEnLibras);
        setMasa(gmasa);
        setEstatura(gestarura);
        calcularIMC();
        setUnidad();
        asignarCondicionYConsejo();
        calcularMinimoRecomendado();
        calcularMaximoRecomendado();
    }

    private void setUnidad(){
        if(masaEnLibras){
            unidad = "lbs";
        }else{
            unidad = "mts";
        }
    }

    String getUnidad(){
        return unidad;
    }

    double getMinimoRecomendado(){
        return minimoRecomendado;
    }

    double getMaximoRecomendado(){
        return maximoRecomendado;
    }
    private void setEstaturaEnPies(boolean estaturaEnPies) {
        this.estaturaEnPies = estaturaEnPies;
    }

    int getCondicion(){
        return condicion;
    }

    int getConsejo() {
        return consejo;
    }

    private void setMasaEnLibras(boolean masaEnLibras) {
        this.masaEnLibras = masaEnLibras;
    }

    double getImc(){
        return imc;
    }

    private void setMasa(double gmasa){
        if (masaEnLibras){
           masa.setMasaEnLibras(gmasa);
        }else{
            masa.setMasaEnKgs(gmasa);
        }
    }

    private void setEstatura(double gestatura){
        if(estaturaEnPies){
            estatura.setEstaturaEnPies(gestatura);
        }else{
            estatura.setEstaturaEnMetros(gestatura);
        }
    }
    public void setEstatura(int gpies, int gpulgs){
        estatura.setEstaturaEnPies(gpies, gpulgs);
    }

    private void calcularMinimoRecomendado(){
        double emts = estatura.getEstaturaEnMetros();
        minimoRecomendado = 18*emts*emts;
        if(masaEnLibras){
            minimoRecomendado*=2.2;
        }
    }

    private void calcularMaximoRecomendado(){
        double emts = estatura.getEstaturaEnMetros();
        maximoRecomendado = 24.9*emts*emts;
        if(masaEnLibras){
            maximoRecomendado*=2.2;
        }
    }


    private void asignarCondicionYConsejo(){
        condicion = takeCondicion();
        consejo = takeConsejo();

    }

    private void calcularIMC(){
        double mc = masa.getMasaEnKgs();
        double ec = estatura.getEstaturaEnMetros();
        imc = mc/(ec*ec);
    }

    private int takeCondicion(){
        if(imc==0){
            return R.string.condicion_indefinido;
        } else if(imc<18){
            return R.string.condicion_bajo_peso;
        }else if(imc<25) {
            return R.string.condicion_normal;
        }else if(imc<27){
            return R.string.condicion_obesidad;
        }else if(imc<30){
            return R.string.condicion_obesidad_grado_1;
        }else if(imc<30){
            return R.string.condicion_obesidad_grado_2;
        }else if(imc<40){
            return R.string.condicion_obesidad_grado_3;
        }else{
            return R.string.condicion_obesidad_extrema;
        }
    }

    private int takeConsejo(){
        if(imc==0){
            return R.string.consejo_indefinido;
        } else if(imc<18){
            return R.string.consejo_bajo_peso;
        }else if(imc<25) {
            return R.string.consejo_normal;
        }else if(imc<27){
            return R.string.consejo_obesidad;
        }else if(imc<30){
            return R.string.consejo_obesidad_grado_1;
        }else if(imc<30){
            return R.string.consejo_obesidad_grado_2;
        }else if(imc<40){
            return R.string.consejo_obesidad_grado_3;
        }else{
            return R.string.consejo_obesidad_extrema;
        }
    }
}
