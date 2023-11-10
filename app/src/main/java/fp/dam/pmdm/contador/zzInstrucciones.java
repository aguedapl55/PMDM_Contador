package fp.dam.pmdm.contador;

public class zzInstrucciones {

    private final String palabras;
    private final int imagen;

    public zzInstrucciones(String palabras, int imagen) {
        this.palabras = palabras;
        this.imagen = imagen;
    }

    public String getPalabras() {
        return palabras;
    }

    public int getImagen() {
        return imagen;
    }
}
