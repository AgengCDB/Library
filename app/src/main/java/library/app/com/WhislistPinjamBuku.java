package library.app.com;

import android.support.annotation.NonNull;

public class WhislistPinjamBuku {
    private int _id;
    private String judul_buku;
    private String kategori_buku;

    public WhislistPinjamBuku(){

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getJudul_buku() {
        return judul_buku;
    }

    public void setJudul_buku(String judul_buku) {
        this.judul_buku = judul_buku;
    }

    public String getKategori_buku() {
        return kategori_buku;
    }

    public void setKategori_buku(String kategori_buku) {
        this.kategori_buku = kategori_buku;
    }

    @NonNull
    @Override
    public String toString() {
        return "Judul buku: "+judul_buku+ "\nKategori: "+kategori_buku;
    }
}
