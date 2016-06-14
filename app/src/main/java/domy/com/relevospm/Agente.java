package domy.com.relevospm;

import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by Domingo on 28/02/2016.
 */
public class  Agente {

    private int id;
    private int N_MES;
    private String MES;
    private String PUESTO;
    private int ORDEN;
    private int ESCALAFON;
    private int DNE;
    private String NOMBRE;
    private int GRUPO;
    private String TURNO;
    private String VACACIONES_I;
    private String VACACIONES_T;
    private String VACACIONES;
    private int CAMBIO_CON;
    private int POR;
    private String COMPENSA;
    private String OBSERVACIONES;

    public Agente(int id, int n_MES, String MES, String PUESTO, int ORDEN, int ESCALAFON, int DNE, String NOMBRE, int GRUPO, String TURNO, String VACACIONES_I, String VACACIONES_T, String VACACIONES, int CAMBIO_CON, int POR, String COMPENSA, String OBSERVACIONES) {
        this.id = id;
        this.N_MES = n_MES;
        this.MES = MES;
        this.PUESTO = PUESTO;
        this.ORDEN = ORDEN;
        this.ESCALAFON = ESCALAFON;
        this.DNE = DNE;
        this.NOMBRE = NOMBRE;
        this.GRUPO = GRUPO;
        this.TURNO = TURNO;
        this.VACACIONES_I = VACACIONES_I;
        this.VACACIONES_T = VACACIONES_T;
        this.VACACIONES = VACACIONES;
        this.CAMBIO_CON = CAMBIO_CON;
        this.POR = POR;
        this.COMPENSA = COMPENSA;
        this.OBSERVACIONES = OBSERVACIONES;
    }

    @Override
    public String toString() {
        return "Agente{" +
                "id=" + id +
                ", N_MES=" + N_MES +
                ", MES='" + MES + '\'' +
                ", PUESTO=" + PUESTO +
                ", ORDEN=" + ORDEN +
                ", ESCALAFON=" + ESCALAFON +
                ", DNE=" + DNE +
                ", NOMBRE='" + NOMBRE + '\'' +
                ", GRUPO=" + GRUPO +
                ", TURNO=" + TURNO +
                ", VACACIONES_I=" + VACACIONES_I +
                ", VACACIONES_T=" + VACACIONES_T +
                ", VACACIONES='" + VACACIONES + '\'' +
                ", CAMBIO_CON=" + CAMBIO_CON +
                ", POR=" + POR +
                ", COMPENSA='" + COMPENSA + '\'' +
                ", OBSERVACIONES='" + OBSERVACIONES + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getN_MES() {
        return N_MES;
    }

    public void setN_MES(int n_MES) {
        N_MES = n_MES;
    }

    public String getMES() {
        return MES;
    }

    public void setMES(String MES) {
        this.MES = MES;
    }

    public String getPUESTO() {
        return PUESTO;
    }

    public void setPUESTO(String PUESTO) {
        this.PUESTO = PUESTO;
    }

    public int getORDEN() {
        return ORDEN;
    }

    public void setORDEN(int ORDEN) {
        this.ORDEN = ORDEN;
    }

    public int getESCALAFON() {
        return ESCALAFON;
    }

    public void setESCALAFON(int ESCALAFON) {
        this.ESCALAFON = ESCALAFON;
    }

    public int getDNE() {
        return DNE;
    }

    public void setDNE(int DNE) {
        this.DNE = DNE;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public int getGRUPO() {
        return GRUPO;
    }

    public void setGRUPO(int GRUPO) {
        this.GRUPO = GRUPO;
    }

    public String getTURNO() {
        return TURNO;
    }

    public void setTURNO(String TURNO) {
        this.TURNO = TURNO;
    }

    public String getVACACIONES_I() {
        return VACACIONES_I;
    }

    public void setVACACIONES_I(String VACACIONES_I) {
        this.VACACIONES_I = VACACIONES_I;
    }

    public String getVACACIONES_T() {
        return VACACIONES_T;
    }

    public void setVACACIONES_T(String VACACIONES_T) {
        this.VACACIONES_T = VACACIONES_T;
    }

    public String getVACACIONES() {
        return VACACIONES;
    }

    public void setVACACIONES(String VACACIONES) {
        this.VACACIONES = VACACIONES;
    }

    public int getCAMBIO_CON() {
        return CAMBIO_CON;
    }

    public void setCAMBIO_CON(int CAMBIO_CON) {
        this.CAMBIO_CON = CAMBIO_CON;
    }

    public int getPOR() {
        return POR;
    }

    public void setPOR(int POR) {
        this.POR = POR;
    }

    public String getCOMPENSA() {
        return COMPENSA;
    }

    public void setCOMPENSA(String COMPENSA) {
        this.COMPENSA = COMPENSA;
    }

    public String getOBSERVACIONES() {
        return OBSERVACIONES;
    }

    public void setOBSERVACIONES(String OBSERVACIONES) {
        this.OBSERVACIONES = OBSERVACIONES;
    }

    //Programa principal que instancia un objeto de la clase Persona

    public static void main(String[] args) {

        int id = 1;
        int n_MES  = 1;
        String MES  = "ENERO";
        //  char PUESTO  = 'F'; ojo con los """ son '
        String PUESTO  = "F";
        int ORDEN  = 1;
        int ESCALAFON  = 12;
        int DNE  = 15910;
        String NOMBRE  = "DOMY";
        int GRUPO  = 5;
        String TURNO  = "M";
        String VACACIONES_I  = "2016-1-12";
        String VACACIONES_T  ="2016-1-12";
        String VACACIONES  = "V1";
        int CAMBIO_CON  = 15300;
        int POR  = 0;
        String COMPENSA  = "ANTES";
        String OBSERVACIONES = "";

        //Instanciamos un objeto de la clase persona
        Agente Agente1 = new Agente(id, n_MES, MES, PUESTO, ORDEN, ESCALAFON, DNE, NOMBRE, GRUPO,
                TURNO, VACACIONES_I, VACACIONES_T, VACACIONES, CAMBIO_CON, POR, COMPENSA, OBSERVACIONES);

        System.out.println( "to.. " + Agente1.toString());
        System.out.println( "vaca.. " + Agente1.getVACACIONES_I());
        System.out.println( "mes.. " + Agente1.getMES());


        // ejemplo de java date http://www.mkyong.com/java/java-date-and-calendar-examples/

        // abajo de joda

        DateTime today = new DateTime();

        DateTime.Property month = today.monthOfYear();
        System.out.println("Mes: " + month.getAsShortText());

    }

}