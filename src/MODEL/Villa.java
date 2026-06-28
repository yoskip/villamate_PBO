package Model;

public class Villa {
    private int id;
    private String nama;
    private int kapasitas;
    private double harga;
    private String status;
    private String deskripsi;
    private String gambar;

    public Villa() {}

    public Villa(int id, String nama, int kapasitas, double harga, String status, String deskripsi, String gambar) {
        this.id = id;
        this.nama = nama;
        this.kapasitas = kapasitas;
        this.harga = harga;
        this.status = status;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getKapasitas() { return kapasitas; }
    public void setKapasitas(int kapasitas) { this.kapasitas = kapasitas; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar = gambar; }
}
