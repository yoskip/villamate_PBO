package Model;

import java.util.Date;

public class Transaksi {
    private int id;
    private int idVilla;
    private int idPenyewa;
    private Date tglCheckin;
    private Date tglCheckout;
    private int totalHari;
    private double totalBiaya;
    private String status;
    private Date tanggalTransaksi;

    public Transaksi() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdVilla() { return idVilla; }
    public void setIdVilla(int idVilla) { this.idVilla = idVilla; }

    public int getIdPenyewa() { return idPenyewa; }
    public void setIdPenyewa(int idPenyewa) { this.idPenyewa = idPenyewa; }

    public Date getTglCheckin() { return tglCheckin; }
    public void setTglCheckin(Date tglCheckin) { this.tglCheckin = tglCheckin; }

    public Date getTglCheckout() { return tglCheckout; }
    public void setTglCheckout(Date tglCheckout) { this.tglCheckout = tglCheckout; }

    public int getTotalHari() { return totalHari; }
    public void setTotalHari(int totalHari) { this.totalHari = totalHari; }

    public double getTotalBiaya() { return totalBiaya; }
    public void setTotalBiaya(double totalBiaya) { this.totalBiaya = totalBiaya; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getTanggalTransaksi() { return tanggalTransaksi; }
    public void setTanggalTransaksi(Date tanggalTransaksi) { this.tanggalTransaksi = tanggalTransaksi; }
}
