public class Menu {
    private String nama;
    private int harga;
    private String katagori;

    public Menu(String nama, int harga, String katagori) {
        this.nama= nama;
        this.harga = harga;
        this.katagori = katagori;
    }
    public String getNama() {
        return nama;
    }
    public int getHarga() {
        return harga;
    }
    public String getKatagori() {
        return katagori;
    }
    public void setHarga(int harga) {
        this.harga = harga;
    }
    @Override 
    public String toString() {
        return nama + "(" + katagori + ") - Rp " + harga;
    }
}
