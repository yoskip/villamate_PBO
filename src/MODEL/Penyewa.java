package Model;

public class Penyewa {
    private int id;
    private String nama;
    private String noKtp;
    private String noHp;
    private String alamat;

    public Penyewa() {}

    public Penyewa(int id, String nama, String noKtp, String noHp, String alamat) {
        this.id = id;
        this.nama = nama;
        this.noKtp = noKtp;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    @Override
    public String toString() {
        return nama + " - " + noKtp;
    }

    public String getNoKtp() { return noKtp; }
    public void setNoKtp(String noKtp) { this.noKtp = noKtp; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}
