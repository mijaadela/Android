package com.example.adela.proiectquizz.pojos;

public class RezultatTest {

    private String name;
    private String materieTest;
    private String nrIntrebariCorecte;
    private String nrIntrebari;
    private String timp;
    private long studentId;

    public RezultatTest() {
    }

    public RezultatTest(String name, String materieTest, String nrIntrebariCorecte, String nrIntrebari, String timp, long studentId) {
        this.name = name;
        this.materieTest = materieTest;
        this.nrIntrebariCorecte = nrIntrebariCorecte;
        this.nrIntrebari = nrIntrebari;
        this.timp = timp;
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNrIntrebariCorecte() {
        return nrIntrebariCorecte;
    }

    public void setNrIntrebariCorecte(String nrIntrebariCorecte) {
        this.nrIntrebariCorecte = nrIntrebariCorecte;
    }

    public String getTimp() {
        return timp;
    }

    public void setTimp(String timp) {
        this.timp = timp;
    }

    public String getNrIntrebari() {
        return nrIntrebari;
    }

    public void setNrIntrebari(String nrIntrebari) {
        this.nrIntrebari = nrIntrebari;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getMaterieTest() {
        return materieTest;
    }

    public void setMaterieTest(String materieTest) {
        this.materieTest = materieTest;
    }
}
